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
 * This interface provides a representation of a servlet entry from a web-app descriptor.
 *
 * @author  Neil Griffin
 */
public interface ConfiguredServlet {

	/**
	 * Returns the information contained in the multipart-config element of the configured servlet.
	 */
	public MultiPartConfig getMultiPartConfig();

	/**
	 * Returns the value of the servlet-class element of the configured servlet.
	 */
	public String getServletClass();

	/**
	 * Returns the value of the servlet-name element of the configured servlet.
	 */
	public String getServletName();
}
