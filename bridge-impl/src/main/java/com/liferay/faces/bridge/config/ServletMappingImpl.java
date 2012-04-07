/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.config;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * This class represents a servlet-mapping found in the WEB-INF/web.xml descriptor.
 *
 * @author  Neil Griffin
 */
public class ServletMappingImpl implements ServletMapping {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ServletMappingImpl.class);

	// Private Strings
	private static final String EXTENSION_WILDCARD = BridgeConstants.CHAR_ASTERISK + BridgeConstants.CHAR_PERIOD;

	// Private Data Members
	private String extension;
	private boolean extensionMapped;
	private String path;
	private boolean pathMapped;
	private String urlPattern;

	public ServletMappingImpl(String urlPattern) {
		this.urlPattern = urlPattern;

		if (urlPattern != null) {

			// If the specified urlPattern is path-mapped (like /views/foo/bar/*), then set a flag and remember the
			// path (/views/foo/bar).
			if (urlPattern.startsWith(BridgeConstants.CHAR_FORWARD_SLASH) &&
					urlPattern.endsWith(BridgeConstants.CHAR_ASTERISK)) {
				pathMapped = true;
				path = urlPattern.substring(0,
						urlPattern.length() - BridgeConstants.CHAR_FORWARD_SLASH.length() -
						BridgeConstants.CHAR_ASTERISK.length());
			}

			// Otherwise, if the specified urlPattern is extension-mapped (like *.faces), then set a flag and remember
			// the extension (.faces).
			else {

				if (urlPattern.startsWith(EXTENSION_WILDCARD)) {
					extensionMapped = true;
					extension = urlPattern.substring(BridgeConstants.CHAR_ASTERISK.length());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.liferay.faces.bridge.config.ServletMapping#isExtensionMapped()
	 */
	public boolean isExtensionMapped() {
		return extensionMapped;
	}

	/* (non-Javadoc)
	 * @see com.liferay.faces.bridge.config.ServletMapping#isPathMapped()
	 */
	public boolean isPathMapped() {
		return pathMapped;
	}

	/* (non-Javadoc)
	 * @see com.liferay.faces.bridge.config.ServletMapping#getExtension()
	 */
	public String getExtension() {
		return extension;
	}

	/* (non-Javadoc)
	 * @see com.liferay.faces.bridge.config.ServletMapping#isMatch(java.lang.String)
	 */
	public boolean isMatch(String uri) {

		boolean match = false;

		if (uri != null) {

			if (extensionMapped) {
				match = uri.endsWith(extension);
				logger.debug(
					"Testing match for servlet-mapping url-pattern=[{0}] EXTENSION=[{1}] uri=[{2}] match=[{3}]",
					urlPattern, extension, uri, match);
			}
			else if (pathMapped) {
				int pos = uri.lastIndexOf(BridgeConstants.CHAR_FORWARD_SLASH);
				String uriPath = uri;

				if (pos > 0) {
					uriPath = uri.substring(0, pos);
				}

				match = (path.contains(uriPath) || uriPath.startsWith(path));
				logger.debug("Testing match for servlet-mapping url-pattern=[{0}] PATH=[{1}] uri=[{2}] match=[{3}]",
					urlPattern, path, uri, match);
			}
		}

		return match;
	}

	/* (non-Javadoc)
	 * @see com.liferay.faces.bridge.config.ServletMapping#getPath()
	 */
	public String getServletPath() {
		return path;
	}

	/* (non-Javadoc)
	 * @see com.liferay.faces.bridge.config.ServletMapping#getUrlPattern()
	 */
	public String getUrlPattern() {
		return urlPattern;
	}
}
