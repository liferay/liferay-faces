/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.bridge.application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.io.Filterable;
import com.liferay.faces.util.io.ResourceOutputStream;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 * @author  Vernon Singleton
 */
public class ResourceOutputStreamRichFacesImpl extends ResourceOutputStream implements Filterable {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceOutputStreamRichFacesImpl.class);

	// Private Constants
	private static final String EXTENSION_CSS = ".css";
	private static final String ORG_RICHFACES_IMAGES = "org.richfaces.images";
	private static final String PACKED_JS = "packed.js";

	// FACES-1214
	protected enum RichFacesImageResource {
		TYPE1(ResourceRichFacesImpl.ORG_RICHFACES, "../../org.richfaces.images/", "richfaces-type1"),
		TYPE2(ResourceRichFacesImpl.ORG_RICHFACES, "../../", "richfaces-type2"),
		TYPE3(ORG_RICHFACES_IMAGES, "../org.richfaces.images/", "richfaces-type3"),
		TYPE4(ORG_RICHFACES_IMAGES, "org.richfaces.images/", "richfaces-type4");

		private String libraryName;
		private String pathPrefix;
		private String substitutionToken;

		private RichFacesImageResource(String libraryName, String pathPrefix, String substitutionToken) {
			this.libraryName = libraryName;
			this.pathPrefix = pathPrefix;
			this.substitutionToken = substitutionToken;
		}

		public String getLibraryName() {
			return libraryName;
		}

		public String getPathPrefix() {
			return pathPrefix;
		}

		public String getSubstitutionToken() {
			return substitutionToken;
		}
	}

	public ResourceOutputStreamRichFacesImpl(Resource resource, int size) {
		super(resource, size);
	}

	public void filter() throws IOException {

		String resourceName = getResource().getResourceName();

		FacesContext facesContext = FacesContext.getCurrentInstance();

		// If this is a RichFaces CSS resource like packed.css or skinning.css, then fix the URLs
		// inside of the CSS text before sending it back as part of the response. For more info, see
		// http://issues.liferay.com/browse/FACES-1214
		if (resourceName.indexOf(EXTENSION_CSS) > 0) {

			String textBefore = toString();
			String textAfter = fixRichFacesImageURLs(facesContext, textBefore);
			reset();
			write(textAfter.getBytes());
		}

		// Otherwise, if this is the packed.js JavaScript resource, then fix the JS code so that
		// rich:fileUpload will work.
		else if (resourceName.indexOf(PACKED_JS) >= 0) {

			String textBefore = toString();
			String textAfter = fixRichFacesPackedDotJs(facesContext, textBefore);
			reset();
			write(textAfter.getBytes());
		}
	}

	/**
	 * This method is part of a fix for FACES-1214. Some of the RichFacess CSS resources have relative URLs that must be
	 * translated to ResourceURLs so that they work in a portlet environment.
	 */
	protected String fixRichFacesImageURLs(FacesContext facesContext, String cssText) {

		// Since the same image URL often appears more then once, maintain a cache of URLs for fast lookup.
		Map<String, String> resourceURLCache = new HashMap<String, String>();
		ResourceHandler resourceHandler = facesContext.getApplication().getResourceHandler();

		// For each of the RichFaces image resource types:
		for (RichFacesImageResource richFacesImageResource : RichFacesImageResource.values()) {

			// Parse the specified CSS text, and replace each relative URL with a ResourceURL.
			boolean doneProcessingURLs = false;

			while (!doneProcessingURLs) {

				String pathPrefix = richFacesImageResource.getPathPrefix();
				int urlStartPos = cssText.indexOf(pathPrefix);

				if (urlStartPos > 0) {

					int fileNameStartPos = urlStartPos + pathPrefix.length();

					int dotPos = cssText.indexOf(StringPool.PERIOD, fileNameStartPos);

					if (dotPos > 0) {
						boolean doneFindingExtension = false;
						int extensionStartPos = dotPos + 1;
						int extensionFinishPos = extensionStartPos;

						while (!doneFindingExtension) {

							if ((extensionFinishPos < cssText.length()) &&
									Character.isLetterOrDigit(cssText.charAt(extensionFinishPos))) {
								extensionFinishPos++;
							}
							else {
								doneFindingExtension = true;
							}
						}

						String relativePathKey = cssText.substring(urlStartPos, extensionFinishPos);
						String imageResourceURL = resourceURLCache.get(relativePathKey);

						if (imageResourceURL == null) {
							String resourceName = cssText.substring(fileNameStartPos, extensionFinishPos);
							String libraryName = richFacesImageResource.getLibraryName();
							String substitutionToken = richFacesImageResource.getSubstitutionToken();
							Resource imageResource = resourceHandler.createResource(resourceName, libraryName);
							imageResourceURL = imageResource.getRequestPath();
							imageResourceURL = imageResourceURL.replaceAll(libraryName, substitutionToken);
							resourceURLCache.put(relativePathKey, imageResourceURL);
						}

						StringBuilder buf = new StringBuilder();
						buf.append(cssText.substring(0, urlStartPos));
						buf.append(imageResourceURL);
						buf.append(cssText.substring(extensionFinishPos));
						cssText = buf.toString();
					}
					else {
						logger.error("Unable to find image filename in URL");
					}
				}
				else {
					doneProcessingURLs = true;
				}
			}
		}

		for (RichFacesImageResource richFacesImageResource : RichFacesImageResource.values()) {
			cssText = cssText.replace(richFacesImageResource.getSubstitutionToken(),
					richFacesImageResource.getLibraryName());
		}

		return cssText;
	}

	protected String fixRichFacesPackedDotJs(FacesContext facesContext, String javaScriptText) {

		// Replace the URL used by rich:fileUpload for forum submission.
		// http://issues.liferay.com/browse/FACES-1234
		// https://issues.jboss.org/browse/RF-12273
		String token = "this.form.attr(\"action\", originalAction + delimiter + UID + \"=\" + this.loadableItem.uid);";
		int pos = javaScriptText.indexOf(token);

		if (pos > 0) {
			logger.debug("Found first token in packed.js");

			StringBuilder buf = new StringBuilder();
			buf.append(javaScriptText.substring(0, pos));
			buf.append(
				"this.form.attr(\"action\", this.form.children(\"input[name='javax.faces.encodedURL']\").val() + delimiter + UID + \"=\" + this.loadableItem.uid);");
			buf.append(javaScriptText.substring(pos + token.length() + 1));
			javaScriptText = buf.toString();
		}

		// Fix JavaScript error "TypeError: jQuery.atmosphere is undefined" by inserting checks for undefined variable.
		// http://issues.liferay.com/browse/FACES-1532
		token = "if (jQuery.atmosphere.requests.length > 0) {";
		pos = javaScriptText.indexOf(token);

		if (pos > 0) {
			logger.debug("Found second token in packed.js");

			StringBuilder buf = new StringBuilder();
			buf.append(javaScriptText.substring(0, pos));
			buf.append("if (!jQuery.atmosphere) { return; }; ");
			buf.append(javaScriptText.substring(pos));
			javaScriptText = buf.toString();
		}

		// jQuery.atmosphere.unsubscribe();
		token = "jQuery.atmosphere.unsubscribe();";
		pos = javaScriptText.indexOf(token);

		if (pos > 0) {
			logger.debug("Found third token in packed.js");

			StringBuilder buf = new StringBuilder();
			buf.append(javaScriptText.substring(0, pos));
			buf.append("if (!jQuery.atmosphere) { return; }; ");
			buf.append(javaScriptText.substring(pos));
			javaScriptText = buf.toString();
		}

		return javaScriptText;
	}

}
