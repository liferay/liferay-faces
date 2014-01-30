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
package com.liferay.faces.bridge.filter;

import java.io.IOException;
import java.util.Collection;

import javax.portlet.PortletRequest;
import javax.portlet.filter.PortletRequestWrapper;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 * This class isolates methods that were added to {@link HttpServletRequest} in the Servlet 3.0 API.
 *
 * @author  Neil Griffin
 */
public abstract class HttpServletRequest_3_0_Adapter extends PortletRequestWrapper implements HttpServletRequest {

	public HttpServletRequest_3_0_Adapter(PortletRequest portletRequest) {
		super(portletRequest);
	}

	public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
		throw new UnsupportedOperationException();
	}

	public void login(String username, String password) throws ServletException {
		throw new UnsupportedOperationException();
	}

	public void logout() throws ServletException {
		throw new UnsupportedOperationException();
	}

	public AsyncContext startAsync() throws IllegalStateException {
		throw new UnsupportedOperationException();
	}

	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
		throws IllegalStateException {
		throw new UnsupportedOperationException();
	}

	public AsyncContext getAsyncContext() {
		throw new UnsupportedOperationException();
	}

	public boolean isAsyncStarted() {
		throw new UnsupportedOperationException();
	}

	public boolean isAsyncSupported() {
		throw new UnsupportedOperationException();
	}

	public DispatcherType getDispatcherType() {
		throw new UnsupportedOperationException();
	}

	public Part getPart(String name) throws IOException, ServletException {
		throw new UnsupportedOperationException();
	}

	public Collection<Part> getParts() throws IOException, ServletException {
		throw new UnsupportedOperationException();
	}

	public ServletContext getServletContext() {
		throw new UnsupportedOperationException();
	}
}
