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
package com.liferay.faces.bridge.context.url.internal;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.context.url.BridgeURI;
import com.liferay.faces.bridge.internal.BridgeConstants;
import com.liferay.faces.bridge.util.internal.URLUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeURIImpl implements BridgeURI {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeURIImpl.class);

	// Private Constants
	private static final String RELATIVE_PATH_PREFIX = "../";

	// Private Data Members
	private Boolean escaped;
	private Boolean external;
	private Boolean hierarchical;
	private Map<String, String[]> parameters;
	private Boolean pathRelative;
	private Bridge.PortletPhase portletPhase;
	private Boolean portletScheme;
	private String query;
	private String stringValue;
	private URI uri;

	public BridgeURIImpl(String uri) throws URISyntaxException {
		this.stringValue = uri;
		this.uri = new URI(uri);
		portletScheme = "portlet".equals(this.uri.getScheme());

		if (portletScheme) {
			int queryPos = uri.indexOf('?');

			if (queryPos > 0) {
				query = uri.substring(queryPos + 1);
			}
		}
	}

	@Override
	public String toString() {
		return stringValue;
	}

	public String getContextRelativePath(String contextPath) {

		String contextRelativePath = null;

		// If the URI is not external, then determine the relative path of the URI based on the specified context
		// path.
		if (!isExternal()) {

			String path = uri.getPath();

			if ((path != null) && (path.length() > 0)) {

				// If the context path is present, then remove it since we want the return value to be a path that
				// is relative to the context path.
				int contextPathPos = path.indexOf(contextPath);

				if (contextPathPos >= 0) {
					contextRelativePath = path.substring(contextPathPos + contextPath.length());
				}
				else {
					contextRelativePath = path;
				}
			}
		}

		return contextRelativePath;
	}

	public boolean isEscaped() {

		if (escaped == null) {

			escaped = Boolean.FALSE;

			String query = getQuery();

			if (query != null) {

				int ampersandPos = query.indexOf("&");

				while (ampersandPos > 0) {

					String queryPart = query.substring(ampersandPos);

					if (queryPart.startsWith("&amp;")) {
						escaped = Boolean.TRUE;
						ampersandPos = query.indexOf("&", ampersandPos + 1);
					}
					else {
						escaped = Boolean.FALSE;

						break;
					}
				}
			}
		}

		return escaped;
	}

	public boolean isAbsolute() {
		return uri.isAbsolute();
	}

	public boolean isOpaque() {
		return portletScheme || uri.isOpaque();
	}

	public boolean isPathRelative() {

		if (pathRelative == null) {

			pathRelative = Boolean.FALSE;

			String path = getPath();

			if ((path != null) && (path.length() > 0) &&
					(!path.startsWith("/") || path.startsWith(RELATIVE_PATH_PREFIX))) {
				pathRelative = Boolean.TRUE;
			}

		}

		return pathRelative;
	}

	public boolean isPortletScheme() {
		return portletScheme;
	}

	public boolean isRelative() {
		return !isAbsolute();
	}

	public boolean isExternal() {

		if (external == null) {

			external = Boolean.FALSE;

			if (!portletScheme) {

				if (isAbsolute()) {
					external = Boolean.TRUE;
				}
				else {

					if (!stringValue.startsWith("/") && !stringValue.startsWith(RELATIVE_PATH_PREFIX)) {
						external = Boolean.TRUE;
					}
				}
			}

			if (stringValue.startsWith(BridgeConstants.WSRP_REWRITE)) {
				external = Boolean.FALSE;
			}
		}

		return external;
	}

	public boolean isHierarchical() {

		if (hierarchical == null) {

			hierarchical = Boolean.FALSE;

			if ((isAbsolute() && uri.getSchemeSpecificPart().startsWith("/")) || isRelative()) {
				hierarchical = Boolean.TRUE;
			}
		}

		return hierarchical;
	}

	public Map<String, String[]> getParameterMap() {

		if (parameters == null) {
			parameters = Collections.unmodifiableMap(URLUtil.parseParameterMapValuesArray(uri.toString()));
		}

		return parameters;
	}

	@Override
	public String getPath() {
		return uri.getPath();
	}

	public Bridge.PortletPhase getPortletPhase() {

		if (portletPhase == null) {

			String uriAsString = uri.toString();

			if (uriAsString != null) {

				if (portletScheme) {

					if (uriAsString.startsWith("portlet:action")) {
						portletPhase = Bridge.PortletPhase.ACTION_PHASE;
					}
					else if (uriAsString.startsWith("portlet:render")) {
						portletPhase = Bridge.PortletPhase.RENDER_PHASE;
					}
					else if (uriAsString.startsWith("portlet:resource")) {
						portletPhase = Bridge.PortletPhase.RESOURCE_PHASE;
					}
					else {
						portletPhase = Bridge.PortletPhase.RESOURCE_PHASE;
						logger.warn("Invalid keyword after 'portlet:' in URI=[{0}]", uriAsString);
					}
				}
			}
			else {
				portletPhase = Bridge.PortletPhase.RESOURCE_PHASE;
				logger.warn("Unable to determine portlet phase in null URI");
			}
		}

		return portletPhase;
	}

	public String getQuery() {

		if (query == null) {
			return uri.getQuery();
		}
		else {
			return query;
		}
	}
}
