/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.portlet.PortletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


/**
 * @author  Neil Griffin
 */
public class ServletContextAdapterImpl implements ServletContext {

	private PortletContext portletContext;
	private String requestContextPath;

	public ServletContextAdapterImpl(PortletContext portletContext, String requestContextPath) {
		this.portletContext = portletContext;
		this.requestContextPath = requestContextPath;
	}

	public void log(String msg) {
		portletContext.log(msg);
	}

	public void log(Exception exception, String message) {
		portletContext.log(message, exception);
	}

	public void log(String message, Throwable throwable) {
		portletContext.log(message, throwable);
	}

	public void removeAttribute(String name) {
		portletContext.removeAttribute(name);
	}

	public Object getAttribute(String name) {
		return portletContext.getAttribute(name);
	}

	public void setAttribute(String name, Object object) {
		portletContext.setAttribute(name, object);
	}

	public Enumeration<?> getAttributeNames() {
		return portletContext.getAttributeNames();
	}

	public ServletContext getContext(String uripath) {
		return new ServletContextAdapterImpl(portletContext, uripath);
	}

	public String getContextPath() {
		return requestContextPath;
	}

	public String getInitParameter(String name) {
		return portletContext.getInitParameter(name);
	}

	public Enumeration<?> getInitParameterNames() {
		return portletContext.getInitParameterNames();
	}

	public int getMajorVersion() {
		return 2;
	}

	public String getMimeType(String file) {
		return portletContext.getMimeType(file);
	}

	public int getMinorVersion() {
		return 5;
	}

	public RequestDispatcher getNamedDispatcher(String name) {
		throw new UnsupportedOperationException();
	}

	public String getRealPath(String path) {
		return portletContext.getRealPath(path);
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		throw new UnsupportedOperationException();
	}

	public URL getResource(String path) throws MalformedURLException {
		return portletContext.getResource(path);
	}

	public InputStream getResourceAsStream(String path) {
		return portletContext.getResourceAsStream(path);
	}

	public Set<?> getResourcePaths(String path) {
		return portletContext.getResourcePaths(path);
	}

	public String getServerInfo() {
		return portletContext.getServerInfo();
	}

	public Servlet getServlet(String name) throws ServletException {
		throw new UnsupportedOperationException();
	}

	public String getServletContextName() {
		return portletContext.getPortletContextName();
	}

	public Enumeration<?> getServletNames() {
		throw new UnsupportedOperationException();
	}

	public Enumeration<?> getServlets() {
		throw new UnsupportedOperationException();
	}
}
