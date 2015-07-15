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
package com.liferay.faces.util.config.internal;

import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class represents a servlet-mapping found in the WEB-INF/web.xml descriptor.
 *
 * @author  Neil Griffin
 */
public class ConfiguredServletMappingImpl implements ConfiguredServletMapping {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ConfiguredServletMappingImpl.class);

	// Private Strings
	private static final String EXTENSION_WILDCARD = "*.";

	// Private Data Members
	private String extension;
	private boolean extensionMapped;
	private boolean implicit;
	private String path;
	private boolean pathMapped;
	private String servletName;
	private String urlPattern;

	public ConfiguredServletMappingImpl(String servletName, String urlPattern, boolean implicit) {

		this.servletName = servletName;
		this.urlPattern = urlPattern;
		this.implicit = implicit;

		if (urlPattern != null) {

			// If the specified urlPattern is path-mapped (like /views/foo/bar/*), then set a flag and remember the
			// path (/views/foo/bar).
			if (urlPattern.startsWith("/") && urlPattern.endsWith("*")) {
				pathMapped = true;
				path = urlPattern.substring(0, urlPattern.length() - "/".length() - "*".length());
			}

			// Otherwise, if the specified urlPattern is extension-mapped (like *.faces), then set a flag and remember
			// the extension (.faces).
			else if (urlPattern.startsWith(EXTENSION_WILDCARD)) {

				extensionMapped = true;
				extension = urlPattern.substring("*".length());
			}

			// Otherwise, assume that the specified urlPattern is path-mapped without a wildcard (like /foo/bar)
			else {

				pathMapped = true;
				path = urlPattern;
			}
		}
	}

	@Override
	public String toString() {
		return "extension=" + extension + " extensionMapped=" + extensionMapped + " path=" + path + " pathMapped=" +
			pathMapped + " servletName=" + servletName + " urlPattern=" + urlPattern;
	}

	public boolean isExtensionMapped() {
		return extensionMapped;
	}

	public boolean isPathMapped() {
		return pathMapped;
	}

	public String getExtension() {
		return extension;
	}

	public boolean isMatch(String uri) {

		boolean match = false;

		if (uri != null) {

			if (extensionMapped) {
				match = uri.endsWith(extension);
				logger.trace(
					"Testing match for servlet-mapping url-pattern=[{0}] EXTENSION=[{1}] uri=[{2}] match=[{3}]",
					urlPattern, extension, uri, match);
			}
			else if (pathMapped) {
				int pos = uri.lastIndexOf("/");
				String uriPath = uri;

				if (pos > 0) {
					uriPath = uri.substring(0, pos);
				}

				match = (path.contains(uriPath) || uriPath.startsWith(path));
				logger.trace("Testing match for servlet-mapping url-pattern=[{0}] PATH=[{1}] uri=[{2}] match=[{3}]",
					urlPattern, path, uri, match);
			}
		}

		return match;
	}

	public String getServletName() {
		return servletName;
	}

	public String getServletPath() {
		return path;
	}

	@Override
	public boolean isImplicit() {
		return implicit;
	}

	public String getUrlPattern() {
		return urlPattern;
	}
}
