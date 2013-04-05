/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * Unlike the {@link ResourceHandlerOuterImpl} class, this class is designed to be the innermost {@link ResourceHandler}
 * in the chain-of-responsibility (only the Mojarra/MyFaces ResourceHandlerImpl has a more inner status). In order to
 * achive this innermost status, it is registered in the application section of the bridge's faces-config.xml
 * descriptor. It is responsible for wrapping resources created by Mojarra/MyFaces so that resource URLs will work in a
 * portlet environment. It is also responsible for serving up resources via the {@link
 * #handleResourceRequest(FacesContext)} method.
 *
 * @author  Neil Griffin
 */
public class ResourceHandlerInnerImpl extends ResourceHandlerWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceHandlerInnerImpl.class);

	// Public Constants
	public static final String ORG_RICHFACES = "org.richfaces";

	// Private Constants
	private static final String ENCODED_RESOURCE_TOKEN = "javax.faces.resource=";
	private static final String EXTENSION_CSS = ".css";
	private static final String ORG_RICHFACES_IMAGES = "org.richfaces.images";
	private static final String PACKED_JS = "packed.js";
	private static final String RICHFACES_STATIC_RESOURCE = "org.richfaces.staticResource";

	// FACES-1214
	protected enum RichFacesImageResource {
		TYPE1(ORG_RICHFACES, "../../org.richfaces.images/", "richfaces-type1"),
		TYPE2(ORG_RICHFACES, "../../", "richfaces-type2"),
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

	// Private Data Members
	private Integer resourceBufferSize;
	private ResourceHandler wrappedResourceHandler;

	public ResourceHandlerInnerImpl(ResourceHandler wrappedResourceHandler) {
		this.wrappedResourceHandler = wrappedResourceHandler;
	}

	/**
	 * Determines whether or not the specified URL has already been encoded.
	 *
	 * @param   url  The URL to check.
	 *
	 * @return  True if the specified URL is already encoded.
	 */
	public static boolean isEncodedFacesResourceURL(String url) {

		if ((url != null) && (url.indexOf(ENCODED_RESOURCE_TOKEN) > 0)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Determines whether or not the specified URL is a Faces URL.
	 *
	 * @param   url  The URL to check.
	 *
	 * @return  True if the specified URL is a Faces URL.
	 */
	public static boolean isFacesResourceURL(String url) {

		if ((url != null) && (url.indexOf(BridgeConstants.JAVAX_FACES_RESOURCE) >= 0)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Resource createResource(String resourceName) {
		Resource wrappableResource = wrappedResourceHandler.createResource(resourceName);

		if (wrappableResource == null) {
			return new MissingResourceImpl(wrappedResourceHandler, resourceName);
		}
		else {
			return new ResourceImpl(wrappableResource);
		}
	}

	@Override
	public Resource createResource(String resourceName, String libraryName) {
		Resource wrappableResource = wrappedResourceHandler.createResource(resourceName, libraryName);

		if (wrappableResource == null) {
			return new MissingResourceImpl(wrappedResourceHandler, resourceName, libraryName);
		}
		else {
			return new ResourceImpl(wrappableResource);
		}
	}

	@Override
	public Resource createResource(String resourceName, String libraryName, String contentType) {
		Resource wrappableResource = wrappedResourceHandler.createResource(resourceName, libraryName, contentType);

		if (wrappableResource == null) {
			return new MissingResourceImpl(wrappedResourceHandler, resourceName, libraryName, contentType);
		}
		else {
			return new ResourceImpl(wrappableResource);
		}
	}

	@Override
	public void handleResourceRequest(FacesContext facesContext) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String resourceName = requestParameterMap.get(BridgeConstants.JAVAX_FACES_RESOURCE);

		// Assume that the resource  ExternalContext.encodeResourceURL(String) was properly called, and that
		// which adds the "javax.faces.resource" request parameter.
		// If the "javax.faces.resource" request parameter was found, then ask Faces to create the resource and
		// assume that calling resource.getInputStream() will provide the ability to send the contents of the
		// resource to the response.
		if (resourceName != null) {
			String libraryName = requestParameterMap.get(BridgeConstants.LN);

			// Ryan Lubke's blog indicates that the locale and version are handled automatically, so the assumption
			// is that the bridge doesn't need to handle the "loc" and "v" request parameters. Additionally, the
			// ResourceHandler.createResource(...) methods don't take locale/version parameters, so there is
			// nothing we can do about it here. See: https://blogs.oracle.com/rlubke/entry/jsf_2_0_new_feature5
			String locale = null;
			String version = null;

			if (logger.isTraceEnabled()) {

				// Surround with isTraceEnabled check in order to avoid unnecessary creation of object array.
				logger.trace("Handling - resourceName=[{0}], libraryName[{1}], locale=[{2}], version=[{3}]",
					new Object[] { resourceName, libraryName, locale, version });
			}

			// FACES-57: Provide the opportunity for applications to decorate the createResource methods of this
			// class by delegating creation of the resource to the chain-of-responsibility found in the application's
			// ResourceHandler.
			ResourceHandler resourceHandlerChain = facesContext.getApplication().getResourceHandler();
			Resource resource = null;

			if (libraryName == null) {
				resource = resourceHandlerChain.createResource(resourceName);
			}
			else {
				resource = resourceHandlerChain.createResource(resourceName, libraryName);
			}

			boolean needsUpdate = resource.userAgentNeedsUpdate(facesContext);

			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			PortletContainer portletContainer = bridgeContext.getPortletContainer();

			if (!portletContainer.isAbleToSetHttpStatusCode()) {

				if (!needsUpdate) {
					needsUpdate = true;
					logger.debug(
						"Portlet container is not able to set PortletResponse.HTTP_STATUS_CODE to HttpServletResponse.SC_NOT_MODIFIED ({0}) for resourceName=[{1}]",
						HttpServletResponse.SC_NOT_MODIFIED, resourceName);
				}
			}

			if (needsUpdate) {
				logger.trace("Handling - Resource was either modified or has not yet been downloaded.");

				ReadableByteChannel readableByteChannel = null;
				WritableByteChannel writableByteChannel = null;
				InputStream inputStream = null;
				int bufferSize = getResourceBufferSize(externalContext);
				ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);

				try {

					// Open an input stream in order to read the resource's contents/data.
					inputStream = resource.getInputStream();

					if (inputStream != null) {

						// Set the response headers by copying them from the resource.
						Map<String, String> responseHeaderMap = resource.getResponseHeaders();

						if (responseHeaderMap != null) {
							Iterator<Map.Entry<String, String>> itr = responseHeaderMap.entrySet().iterator();

							while (itr.hasNext()) {
								Map.Entry<String, String> mapEntry = itr.next();
								String name = mapEntry.getKey();
								String value = mapEntry.getValue();
								externalContext.setResponseHeader(name, value);

								if (logger.isDebugEnabled()) {

									// Surround with isDebugEnabled check in order to avoid unnecessary creation
									// of object array.
									logger.debug("Handling - COPIED resource header name=[{0}] value=[{1}]",
										new Object[] { name, value });
								}
							}
						}

						// Set the response Content-Type header.
						String responseContentType = resource.getContentType();
						logger.trace("Handling - responseContentType=[{0}]", responseContentType);

						if (responseContentType != null) {
							externalContext.setResponseContentType(responseContentType);
						}

						// Rather than write the input stream directly to the PortletResponse, write it to an
						// intermediate ByteArrayOutputStream so that the length can be calculated for the
						// Content-Length header. See: http://issues.liferay.com/browse/FACES-1207
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bufferSize);

						int responseContentLength = 0;
						readableByteChannel = Channels.newChannel(inputStream);
						writableByteChannel = Channels.newChannel(byteArrayOutputStream);

						int bytesRead = readableByteChannel.read(byteBuffer);

						if (logger.isTraceEnabled()) {

							// Surround with isTraceEnabled check in order to avoid unnecessary conversion of
							// int to String.
							logger.trace("Handling - bytesRead=[{0}]", Integer.toString(bytesRead));
						}

						int bytesWritten = 0;

						while (bytesRead != -1) {
							byteBuffer.rewind();
							byteBuffer.limit(bytesRead);

							do {
								bytesWritten += writableByteChannel.write(byteBuffer);
							}
							while (bytesWritten < responseContentLength);

							byteBuffer.clear();
							responseContentLength += bytesRead;
							bytesRead = readableByteChannel.read(byteBuffer);

							if (logger.isTraceEnabled()) {

								// Surround with isTraceEnabled check in order to avoid unnecessary conversion
								// of int to String.
								logger.trace("Handling - MORE bytesRead=[{0}]", Integer.toString(bytesRead));
							}
						}

						// If this is a RichFaces static resource, then
						if (resourceName.startsWith(RICHFACES_STATIC_RESOURCE)) {

							// If this is a RichFaces CSS resource like packed.css or skinning.css, then fix the URLs
							// inside of the CSS text before sending it back as part of the response. For more info, see
							// http://issues.liferay.com/browse/FACES-1214
							if (resourceName.indexOf(EXTENSION_CSS) > 0) {

								String cssText = fixRichFacesImageURLs(facesContext, byteArrayOutputStream.toString());
								responseContentLength = cssText.length();
								byteArrayOutputStream = new ByteArrayOutputStream();
								byteArrayOutputStream.write(cssText.getBytes());
							}

							// Otherwise, if this is the packed.js JavaScript resource, then fix the JS code so that
							// rich:fileUpload will work.
							else if (resourceName.indexOf(PACKED_JS) >= 0) {
								String javaScriptText = fixRichFacesFileUpload(facesContext,
										byteArrayOutputStream.toString());
								responseContentLength = javaScriptText.length();
								byteArrayOutputStream = new ByteArrayOutputStream();
								byteArrayOutputStream.write(javaScriptText.getBytes());
							}
						}

						// Now that we know how big the file is, set the response Content-Length header and the status.
						externalContext.setResponseContentLength(responseContentLength);
						externalContext.setResponseStatus(HttpServletResponse.SC_OK);

						// Set the response buffer size.
						externalContext.setResponseBufferSize(responseContentLength);

						if (logger.isTraceEnabled()) {

							// Surround with isTraceEnabled check in order to avoid unnecessary conversion of
							// int to String.
							logger.trace("Handling - responseBufferSize=[{0}]",
								Integer.toString(responseContentLength));
						}

						// Write the data to the response.
						byteArrayOutputStream.writeTo(externalContext.getResponseOutputStream());
						byteArrayOutputStream.flush();
						byteArrayOutputStream.close();

						if (logger.isDebugEnabled()) {
							logger.debug(
								"HANDLED (SC_OK) resourceName=[{0}], libraryName[{1}], locale=[{2}], version=[{3}], responseContentType=[{4}], responseContentLength=[{5}]",
								new Object[] {
									resourceName, libraryName, locale, version, responseContentType,
									responseContentLength
								});
						}
					}
					else {
						externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
						logger.error(
							"NOT HANDLED (SC_NOT_FOUND) because InputStream was null - resourceName=[{0}], libraryName[{1}], locale=[{2}], version=[{3}]",
							new Object[] { resourceName, libraryName, locale, version });
					}
				}
				catch (IOException e) {
					externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
					logger.error(
						"NOT HANDLED (SC_NOT_FOUND) resourceName=[{0}], libraryName[{1}], locale=[{2}], version=[{3}] errorMessage=[{4}]",
						new Object[] { resourceName, libraryName, locale, version, e.getMessage() }, e);
				}
				finally {

					if (writableByteChannel != null) {
						writableByteChannel.close();
					}

					if (readableByteChannel != null) {
						readableByteChannel.close();
					}

					if (inputStream != null) {
						inputStream.close();
					}
				}
			}
			else {

				externalContext.setResponseStatus(HttpServletResponse.SC_NOT_MODIFIED);

				if (logger.isDebugEnabled()) {

					// Surround with isDebugEnabled check in order to avoid unnecessary creation of object
					// array.
					logger.debug(
						"HANDLED (SC_NOT_MODIFIED) resourceName=[{0}], libraryName[{1}], locale=[{2}], version=[{3}]",
						new Object[] { resourceName, libraryName, locale, version });
				}

			}
		}
		else {
			logger.debug("NOT HANDLED - Missing request parameter {0} so delegating handleResourceRequest to chain",
				BridgeConstants.JAVAX_FACES_RESOURCE);
			wrappedResourceHandler.handleResourceRequest(facesContext);
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

	private String fixRichFacesFileUpload(FacesContext facesContext, String javaScriptText) {

		String token = "this.form.attr(\"action\", originalAction + delimiter + UID + \"=\" + this.loadableItem.uid);";
		int pos = javaScriptText.indexOf(token);

		if (pos > 0) {
			StringBuilder buf = new StringBuilder();
			buf.append(javaScriptText.substring(0, pos));
			buf.append(
				"this.form.attr(\"action\", this.form.children(\"input[name='javax.faces.encodedURL']\").val() + delimiter + UID + \"=\" + this.loadableItem.uid);");
			buf.append(javaScriptText.substring(pos + token.length() + 1));
			javaScriptText = buf.toString();
		}

		return javaScriptText;
	}

	/**
	 * Gets the size of the buffer (in bytes) that is to be used when loading contents of resources that are to be sent
	 * back via {@link ExternalContext#getResponseOutputStream()}. The default value is 1024 (1 kilobyte).
	 */
	protected int getResourceBufferSize(ExternalContext externalContext) {

		if (resourceBufferSize == null) {

			resourceBufferSize = 1024;

			String constantName = BridgeConfigConstants.PARAM_RESOURCE_BUFFER_SIZE1;
			String sizeAsString = externalContext.getInitParameter(constantName);

			if (sizeAsString == null) {

				// Backward compatibility
				constantName = BridgeConfigConstants.PARAM_RESOURCE_BUFFER_SIZE2;
				sizeAsString = externalContext.getInitParameter(constantName);
			}

			if (sizeAsString != null) {

				try {
					resourceBufferSize = Integer.parseInt(sizeAsString);
					logger.debug("Found portlet.xml init-param name=[{0}] value=[{1}]", constantName,
						resourceBufferSize);
				}
				catch (NumberFormatException e) {
					logger.error("Invalid value=[{0}] for portlet.xml init-param {1}", sizeAsString, constantName);
				}
			}
			else {
				logger.debug("Returning default portletResourceBufferSize=[{0}]", resourceBufferSize);
			}
		}

		return resourceBufferSize;
	}

	@Override
	public boolean isResourceRequest(FacesContext facesContext) {

		// If the "javax.faces.resource" request parameter is present, then that means the resource's URL was
		// properly created with the bridge's ExternalContextImpl.encodeResource(String) method, and so now that
		// the userAgent is requesting the URL via the portlet RESOURCE_PHASE, then the bridge recognizes
		// the resource and we return true.
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String resourceId = requestParameterMap.get(BridgeConstants.JAVAX_FACES_RESOURCE);

		if (resourceId != null) {
			logger.debug("Bridge found {0} request parameter and recognized resourceId=[{1}] as a resource",
				new Object[] { BridgeConstants.JAVAX_FACES_RESOURCE, resourceId });

			return true;
		}
		else {
			logger.debug("Bridge did not find the {0} request parameter so delegating isResourceRequest to chain",
				BridgeConstants.JAVAX_FACES_RESOURCE);

			return wrappedResourceHandler.isResourceRequest(facesContext);
		}
	}

	@Override
	public ResourceHandler getWrapped() {
		return wrappedResourceHandler;
	}
}
