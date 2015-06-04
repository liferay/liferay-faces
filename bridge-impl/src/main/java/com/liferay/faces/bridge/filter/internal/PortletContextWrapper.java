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
package com.liferay.faces.bridge.filter.internal;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.faces.FacesWrapper;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;


/**
 * @author  Neil Griffin
 */
public abstract class PortletContextWrapper implements PortletContext, FacesWrapper<PortletContext> {

	public void log(String msg) {
		getWrapped().log(msg);
	}

	public void log(String message, Throwable throwable) {
		getWrapped().log(message, throwable);
	}

	public void removeAttribute(String name) {
		getWrapped().removeAttribute(name);
	}

	public Object getAttribute(String name) {
		return getWrapped().getAttribute(name);
	}

	public void setAttribute(String name, Object object) {
		getWrapped().setAttribute(name, object);
	}

	public Enumeration<String> getAttributeNames() {
		return getWrapped().getAttributeNames();
	}

	public Enumeration<String> getContainerRuntimeOptions() {
		return getWrapped().getContainerRuntimeOptions();
	}

	public String getInitParameter(String name) {
		return getWrapped().getInitParameter(name);
	}

	public Enumeration<String> getInitParameterNames() {
		return getWrapped().getInitParameterNames();
	}

	public int getMajorVersion() {
		return getWrapped().getMajorVersion();
	}

	public String getMimeType(String file) {
		return getWrapped().getMimeType(file);
	}

	public int getMinorVersion() {
		return getWrapped().getMinorVersion();
	}

	public PortletRequestDispatcher getNamedDispatcher(String name) {
		return getWrapped().getNamedDispatcher(name);
	}

	public String getPortletContextName() {
		return getWrapped().getPortletContextName();
	}

	public String getRealPath(String path) {
		return getWrapped().getRealPath(path);
	}

	public PortletRequestDispatcher getRequestDispatcher(String path) {
		return getWrapped().getRequestDispatcher(path);
	}

	public URL getResource(String path) throws MalformedURLException {
		return getWrapped().getResource(path);
	}

	public InputStream getResourceAsStream(String path) {
		return getWrapped().getResourceAsStream(path);
	}

	public Set<String> getResourcePaths(String path) {
		return getWrapped().getResourcePaths(path);
	}

	public String getServerInfo() {
		return getWrapped().getServerInfo();
	}

	public abstract PortletContext getWrapped();
}
