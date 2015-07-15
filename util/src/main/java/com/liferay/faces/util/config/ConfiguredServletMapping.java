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
package com.liferay.faces.util.config;

/**
 * This interface provides a representation of a servlet-mapping entry from a web-app descriptor.
 *
 * @author  Neil Griffin
 */
public interface ConfiguredServletMapping {

	/**
	 * Flag indicating whether or not the servlet-mapping url-pattern is extension-mapped.
	 */
	public boolean isExtensionMapped();

	/**
	 * Flag indicating whether or not the servlet-mapping url-pattern is path-mapped.
	 */
	public boolean isPathMapped();

	/**
	 * If the servlet-mapping url-pattern is extension-mapped (like *.faces), then this method returns the .faces
	 * extension. Otherwise returns null.
	 */
	public String getExtension();

	/**
	 * Flag indicating whether or not the specified <code>uri</code> matches the servlet-mapping url-pattern.
	 */
	public boolean isMatch(String uri);

	/**
	 * Returns the servlet-mapping servlet-name.
	 */
	public String getServletName();

	/**
	 * If the servlet-mapping url-pattern is path-mapped (like /views/foo/bar/*), then this method returns the
	 * /views/foo/bar path. Otherwise returns null.
	 */
	public String getServletPath();

	/**
	 * Flag indicating whether or not the servlet-mapping is implicit, meaning it is associated with {@link
	 * javax.faces.application.ViewHandler#DEFAULT_SUFFIX} or {@link
	 * javax.faces.application.ViewHandler#DEFAULT_SUFFIX_PARAM_NAME}.
	 */
	public boolean isImplicit();

	/**
	 * Returns the servlet-mapping url-pattern.
	 */
	public String getUrlPattern();
}
