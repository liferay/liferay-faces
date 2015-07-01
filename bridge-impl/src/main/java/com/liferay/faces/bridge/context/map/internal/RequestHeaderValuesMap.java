/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context.map.internal;

import java.util.Enumeration;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.ClientDataRequest;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.internal.BridgeExt;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class RequestHeaderValuesMap extends RequestHeaderValuesMapCompat {

	// Private Constants
	private static final String CHARSET = "charset";
	private static final String PARTIAL_AJAX = "partial/ajax";

	// serialVersionUID
	private static final long serialVersionUID = 4910578014366086738L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(RequestHeaderValuesMap.class);

	// Private Constants
	private static final String HEADER_ACCEPT = "Accept";
	private static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	private static final String HEADER_FACES_REQUEST = "Faces-Request";

	public RequestHeaderValuesMap(BridgeContext bridgeContext) {

		PortletRequest portletRequest = bridgeContext.getPortletRequest();
		Enumeration<String> propertyNames = portletRequest.getPropertyNames();
		boolean foundAccept = false;
		boolean foundContentType = false;
		boolean foundFacesRequest = false;

		if (propertyNames != null) {

			while (propertyNames.hasMoreElements()) {

				boolean addHeader = true;
				String name = propertyNames.nextElement();

				if (name.equalsIgnoreCase(HEADER_ACCEPT_LANGUAGE)) {
					Enumeration<Locale> locales = portletRequest.getLocales();

					if (locales != null) {
						addHeader = false;

						StringBuilder buf = new StringBuilder();

						for (int i = 0; locales.hasMoreElements(); i++) {

							if (i > 0) {
								buf.append(",");
							}

							Locale locale = locales.nextElement();
							buf.append(locale.getLanguage());

							String country = locale.getCountry();

							if ((country != null) && (country.length() > 0)) {
								buf.append("-");
								buf.append(country);
							}
						}

						super.put(name, new String[] { buf.toString() });
					}
				}

				if (addHeader) {
					Enumeration<String> properties = portletRequest.getProperties(name);

					StringBuilder buf = new StringBuilder();

					if (properties != null) {

						for (int i = 0; properties.hasMoreElements(); i++) {

							if (i > 0) {
								buf.append(",");
							}

							buf.append(properties.nextElement());
						}
					}

					String values = buf.toString();
					super.put(name, new String[] { values });

					// NOTE: Need to check that the portlet container actually provided a value before the bridge can
					// claim that it has detected "Accept", "Content-Type", or "Faces-Request".
					// http://issues.liferay.com/browse/FACES-34
					if (values.length() > 0) {

						if (!foundAccept) {
							foundAccept = name.equalsIgnoreCase(HEADER_ACCEPT);
						}

						if (!foundContentType) {
							foundContentType = name.equalsIgnoreCase(HEADER_CONTENT_TYPE);
						}

						if (!foundFacesRequest) {
							foundFacesRequest = name.equalsIgnoreCase(HEADER_FACES_REQUEST);
						}
					}
				}
			}
		}

		if (!foundAccept) {
			addAcceptHeader(portletRequest);
		}

		if (!foundContentType) {
			addContentTypeHeader(portletRequest);
		}

		if (!foundFacesRequest) {

			// If this is a ResourceRequest, and the resource handler chain doesn't consider this to be a resource, then
			// we assume that it's Ajax and add the "Faces-Request" header with value "partial/ajax". Note that this is
			// normally done by the jsf.js JavaScript library, but in a portlet environment, the original XmlHttpRequest
			// is not made available to the portlet bridge.
			if (portletRequest instanceof ResourceRequest) {

				// If the BridgeExt.FACES_AJAX_PARAMETER request parameter is "true" then set the "partial/ajax" header
				// so that the Ajax-based PartialResponseWriter renders XML. Otherwise, since the URL was probably setup
				// with "portlet:resource" don't setup the header because it needs to fully run the JSF lifecycle with a
				// real (non-parital) ResponseWriter that renders HTML.
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();
				String facesAjaxParam = externalContext.getRequestParameterMap().get(BridgeExt.FACES_AJAX_PARAMETER);

				if ((facesAjaxParam != null) && BooleanHelper.isTrueToken(facesAjaxParam)) {
					put(HEADER_FACES_REQUEST, new String[] { PARTIAL_AJAX });
				}
			}
		}

		addJSF1Headers(portletRequest);
	}

	/**
	 * Adds an "Accept" header to the hashmap, according to the response content types in the specified request. Example
	 * Value: Accept: text/html
	 *
	 * @param  portletRequest  The current portlet request.
	 *
	 * @see    <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.1">RFC2616</a>
	 */
	protected void addAcceptHeader(PortletRequest portletRequest) {
		StringBuilder header = new StringBuilder();

		Enumeration<String> responseContentTypes = portletRequest.getResponseContentTypes();
		boolean firstElement = true;

		while (responseContentTypes.hasMoreElements()) {

			if (!firstElement) {
				header.append(",");
			}

			String responseContentType = responseContentTypes.nextElement();
			header.append(responseContentType);
			firstElement = false;
		}

		String acceptHeader = header.toString();
		logger.debug("Adding acceptHeader=[{0}] to header map", acceptHeader);
		put(HEADER_ACCEPT, new String[] { header.toString() });
	}

	/**
	 * Adds a "Content-Type" header to the hashmap, according to the content-type and character-encoding in the
	 * specified request. Example Value: Content-Type: text/html; charset=ISO-8859-4
	 *
	 * @param  portletRequest  The current portlet request.
	 *
	 * @see    <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.17">RFC2616</a>
	 */
	protected void addContentTypeHeader(PortletRequest portletRequest) {

		// If the specified portletRequest contains characterSetEncoding and contentType information, then
		// use that to build the header.
		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;
			String contentType = clientDataRequest.getContentType();
			String characterSetEncoding = clientDataRequest.getCharacterEncoding();

			StringBuilder header = new StringBuilder();
			header.append(HEADER_CONTENT_TYPE);
			header.append(": ");
			header.append(contentType);

			if (characterSetEncoding != null) {
				header.append("; ");
				header.append(CHARSET);
				header.append("=");
				header.append(characterSetEncoding);
			}

			String contentTypeHeader = header.toString();
			logger.debug("Adding contentTypeHeader=[{0}] to header map", contentTypeHeader);
			put(HEADER_CONTENT_TYPE, new String[] { header.toString() });
		}
		else {
			// TCK TestPage142: getRequestHeaderMapRenderTest
		}
	}
}
