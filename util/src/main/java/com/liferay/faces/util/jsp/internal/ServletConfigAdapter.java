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
package com.liferay.faces.util.jsp.internal;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;


/**
 * @author  Neil Griffin
 */
public class ServletConfigAdapter implements ServletConfig {

	// Private Data Members
	private ServletContext servletContext;

	public ServletConfigAdapter(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public String getInitParameter(String name) {
		return servletContext.getInitParameter(name);
	}

	public Enumeration<String> getInitParameterNames() {
		return (Enumeration<String>) servletContext.getInitParameterNames();
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public String getServletName() {
		return ServletConfigAdapter.class.getName();
	}
}
