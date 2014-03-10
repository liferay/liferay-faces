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
package com.liferay.faces.bridge.context;

import java.util.List;

import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class FacesViewImpl implements FacesView {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesViewImpl.class);

	// Private Constants
	private static final String EXTENSION_JSP = ".jsp";

	// Private Data Members
	private String viewId;
	private String extension;
	private boolean extensionMapped;
	private String navigationQueryString;
	private String servletPath;
	private boolean pathMapped;

	public FacesViewImpl(String viewId, List<String> configuredExtensions,
		List<ConfiguredServletMapping> configuredFacesServletMappings) {
		this(viewId, null, configuredExtensions, configuredFacesServletMappings);
	}

	public FacesViewImpl(String viewId, String navigationQueryString, List<String> configuredSuffixes,
		List<ConfiguredServletMapping> configuredFacesServletMappings) {
		this.viewId = viewId;
		this.navigationQueryString = navigationQueryString;

		if (configuredFacesServletMappings != null) {
			ConfiguredServletMapping extensionMappedServletMapping = null;

			// Determine whether or not the target viewId matches any of the path-mapped servlet-mapping entries.
			for (ConfiguredServletMapping facesServletMapping : configuredFacesServletMappings) {

				if (facesServletMapping.isPathMapped()) {

					logger.debug("Attempting to determine if viewId=[{0}] is path-mapped to urlPatttern=[{1}]", viewId,
						facesServletMapping.getUrlPattern());

					if (facesServletMapping.isMatch(viewId)) {
						this.servletPath = facesServletMapping.getServletPath();
						this.pathMapped = true;

						break;
					}
				}
			}

			// If not path-mapped, then
			if (!this.pathMapped) {

				// Determine whether or not the target viewId matches any of the extension-mapped servlet-mapping
				// entries.
				for (ConfiguredServletMapping facesServletMapping : configuredFacesServletMappings) {

					if (facesServletMapping.isExtensionMapped()) {

						if (extensionMappedServletMapping == null) {
							extensionMappedServletMapping = facesServletMapping;
						}

						logger.debug("Attempting to determine if viewId=[{0}] is extension-mapped to urlPattern=[{1}]",
							viewId, facesServletMapping.getUrlPattern());

						if (facesServletMapping.isMatch(viewId)) {
							this.extension = facesServletMapping.getExtension();
							this.extensionMapped = true;

							// See: http://issues.liferay.com/browse/FACES-1224
							if (EXTENSION_JSP.equals(this.extension)) {

								// TCK TestPage159: getRequestServletPathTest
								int pos = viewId.lastIndexOf(StringPool.PERIOD);

								if (pos > 0) {
									this.extension = configuredFacesServletMappings.get(0).getExtension();
									this.viewId = viewId.substring(0, pos) + this.extension;
								}
							}

							break;
						}
					}
				}

				// If the target viewId did not match any of the extension-mapped servlet-mapping entries, then the
				// developer may have specified an extension like .jsp/.jspx in the WEB-INF/portlet.xml descriptor.
				if (!this.extensionMapped) {

					// For each of the valid extensions (.jsp, .jspx, etc.) that the developer may have specified:
					for (String configuredSuffix : configuredSuffixes) {

						if (viewId.contains(configuredSuffix)) {
							this.extension = configuredSuffix;
							this.extensionMapped = true;

							logger.debug("Associated viewId=[{0}] as extension-mapped to urlPattern=[*.{1}]", viewId,
								configuredSuffix);

							/* TODO: At one point in development, the line below was in place to replace ".jsp" with
							 * ".faces" but was potentially causing navigation-rules to fail because the to-view-id
							 * might not have been matching. Need to re-enable the line below and then retest with the
							 * TCK.
							 */
							// viewId = viewId.replace(extension, this.extension);

							break;
						}
					}
				}
			}
		}
	}

	public boolean isExtensionMapped() {
		return extensionMapped;
	}

	public boolean isPathMapped() {
		return pathMapped;
	}

	public boolean isServletMapped() {
		return (extensionMapped || pathMapped);
	}

	public String getExtension() {
		return extension;
	}

	public String getQueryString() {
		return navigationQueryString;
	}

	public String getServletPath() {
		return servletPath;
	}

	public String getViewId() {
		return viewId;
	}
}
