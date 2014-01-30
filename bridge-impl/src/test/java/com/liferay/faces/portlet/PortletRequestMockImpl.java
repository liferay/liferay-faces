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
package com.liferay.faces.portlet;

import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.WindowState;
import javax.servlet.http.Cookie;


/**
 * @author  Neil Griffin
 */
public class PortletRequestMockImpl implements PortletRequest {

	// Private Data Members
	private String contextPath;

	public PortletRequestMockImpl(String contextPath) {
		this.contextPath = contextPath;
	}

	public void removeAttribute(String name) {
		throw new UnsupportedOperationException();
	}

	public Object getAttribute(String name) {
		throw new UnsupportedOperationException();
	}

	public void setAttribute(String name, Object o) {
		throw new UnsupportedOperationException();
	}

	public Enumeration<String> getAttributeNames() {
		throw new UnsupportedOperationException();
	}

	public String getAuthType() {
		throw new UnsupportedOperationException();
	}

	public String getContextPath() {
		return contextPath;
	}

	public Cookie[] getCookies() {
		throw new UnsupportedOperationException();
	}

	public boolean isPortletModeAllowed(PortletMode mode) {
		throw new UnsupportedOperationException();
	}

	public boolean isRequestedSessionIdValid() {
		throw new UnsupportedOperationException();
	}

	public boolean isWindowStateAllowed(WindowState state) {
		throw new UnsupportedOperationException();
	}

	public boolean isSecure() {
		throw new UnsupportedOperationException();
	}

	public boolean isUserInRole(String role) {
		throw new UnsupportedOperationException();
	}

	public Locale getLocale() {
		throw new UnsupportedOperationException();
	}

	public Enumeration<Locale> getLocales() {
		throw new UnsupportedOperationException();
	}

	public String getParameter(String name) {
		throw new UnsupportedOperationException();
	}

	public Map<String, String[]> getParameterMap() {
		throw new UnsupportedOperationException();
	}

	public Enumeration<String> getParameterNames() {
		throw new UnsupportedOperationException();
	}

	public String[] getParameterValues(String name) {
		throw new UnsupportedOperationException();
	}

	public PortalContext getPortalContext() {
		throw new UnsupportedOperationException();
	}

	public PortletMode getPortletMode() {
		throw new UnsupportedOperationException();
	}

	public PortletSession getPortletSession() {
		throw new UnsupportedOperationException();
	}

	public PortletSession getPortletSession(boolean create) {
		throw new UnsupportedOperationException();
	}

	public PortletPreferences getPreferences() {
		throw new UnsupportedOperationException();
	}

	public Map<String, String[]> getPrivateParameterMap() {
		throw new UnsupportedOperationException();
	}

	public Enumeration<String> getProperties(String name) {
		throw new UnsupportedOperationException();
	}

	public String getProperty(String name) {
		throw new UnsupportedOperationException();
	}

	public Enumeration<String> getPropertyNames() {
		throw new UnsupportedOperationException();
	}

	public Map<String, String[]> getPublicParameterMap() {
		throw new UnsupportedOperationException();
	}

	public String getRemoteUser() {
		throw new UnsupportedOperationException();
	}

	public String getRequestedSessionId() {
		throw new UnsupportedOperationException();
	}

	public String getResponseContentType() {
		throw new UnsupportedOperationException();
	}

	public Enumeration<String> getResponseContentTypes() {
		throw new UnsupportedOperationException();
	}

	public String getScheme() {
		throw new UnsupportedOperationException();
	}

	public String getServerName() {
		throw new UnsupportedOperationException();
	}

	public int getServerPort() {
		throw new UnsupportedOperationException();
	}

	public Principal getUserPrincipal() {
		throw new UnsupportedOperationException();
	}

	public String getWindowID() {
		throw new UnsupportedOperationException();
	}

	public WindowState getWindowState() {
		throw new UnsupportedOperationException();
	}
}
