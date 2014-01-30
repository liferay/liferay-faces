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
package com.liferay.faces.bridge.config;

/**
 * The ServletMapping interface provides a representation of a servlet-mapping entry from the WEB-INF/web.xml
 * descriptor.
 *
 * @author  Neil Griffin
 */
public interface ServletMapping {

	/**
	 * Flag indicating whether or not the servlet-mapping url-pattern is extension-mapped.
	 */
	public abstract boolean isExtensionMapped();

	/**
	 * Flag indicating whether or not the servlet-mapping url-pattern is path-mapped.
	 */
	public abstract boolean isPathMapped();

	/**
	 * If the servlet-mapping url-pattern is extension-mapped (like *.faces), then this method returns the .faces
	 * extension. Otherwise returns null.
	 */
	public abstract String getExtension();

	/**
	 * Flag indicating whether or not the specified <code>uri</code> matches the servlet-mapping url-pattern.
	 */
	public abstract boolean isMatch(String uri);

	/**
	 * If the servlet-mapping url-pattern is path-mapped (like /views/foo/bar/*), then this method returns the
	 * /views/foo/bar path. Otherwise returns null.
	 */
	public abstract String getServletPath();

	/**
	 * Returns the servlet-mapping url-pattern.
	 */
	public abstract String getUrlPattern();

}
