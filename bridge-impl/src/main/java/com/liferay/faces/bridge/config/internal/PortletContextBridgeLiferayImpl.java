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
package com.liferay.faces.bridge.config.internal;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequestDispatcher;


/**
 * This class is part of a workaround in the bridge for LPS-3311 and LPS-8355 (both fixed in Liferay Portal 6.0).
 *
 * @author  Neil Griffin
 */
public class PortletContextBridgeLiferayImpl implements PortletContext {

	// Private Data Members
	private PortletContext wrappedPortletContext;

	public PortletContextBridgeLiferayImpl(PortletContext portletContext) {
		this.wrappedPortletContext = portletContext;
	}

	public void log(String msg) {
		wrappedPortletContext.log(msg);
	}

	public void log(String message, Throwable throwable) {
		wrappedPortletContext.log(message, throwable);
	}

	public void removeAttribute(String name) {
		wrappedPortletContext.removeAttribute(name);
	}

	public Object getAttribute(String name) {
		return wrappedPortletContext.getAttribute(name);
	}

	public void setAttribute(String name, Object object) {
		wrappedPortletContext.setAttribute(name, object);
	}

	public Enumeration<String> getAttributeNames() {
		return wrappedPortletContext.getAttributeNames();
	}

	public Enumeration<String> getContainerRuntimeOptions() {
		return wrappedPortletContext.getContainerRuntimeOptions();
	}

	public String getInitParameter(String name) {
		return wrappedPortletContext.getInitParameter(name);
	}

	public Enumeration<String> getInitParameterNames() {
		return wrappedPortletContext.getInitParameterNames();
	}

	public int getMajorVersion() {
		return wrappedPortletContext.getMajorVersion();
	}

	public String getMimeType(String file) {
		return wrappedPortletContext.getMimeType(file);
	}

	public int getMinorVersion() {
		return wrappedPortletContext.getMinorVersion();
	}

	public PortletRequestDispatcher getNamedDispatcher(String name) {
		return wrappedPortletContext.getNamedDispatcher(name);
	}

	public String getPortletContextName() {
		return wrappedPortletContext.getPortletContextName();
	}

	public String getRealPath(String path) {
		return wrappedPortletContext.getRealPath(path);
	}

	public PortletRequestDispatcher getRequestDispatcher(String path) {
		return new PortletRequestDispatcherBridgeLiferayImpl(wrappedPortletContext.getRequestDispatcher(path));
	}

	public URL getResource(String path) throws MalformedURLException {
		return wrappedPortletContext.getResource(path);
	}

	public InputStream getResourceAsStream(String path) {
		return wrappedPortletContext.getResourceAsStream(path);
	}

	public Set<String> getResourcePaths(String path) {
		return wrappedPortletContext.getResourcePaths(path);
	}

	public String getServerInfo() {
		return wrappedPortletContext.getServerInfo();
	}
}
