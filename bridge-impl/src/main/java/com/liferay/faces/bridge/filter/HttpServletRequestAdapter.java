/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.faces.FacesWrapper;
import javax.portlet.ClientDataRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * This class provides an {@link HttpServletRequest} adapter/wrapper around the current {@link PortletResponse}. Typical
 * usage is to hack-around Servlet-API dependencies in JSF implementations.
 *
 * @author  Neil Griffin
 */
public class HttpServletRequestAdapter implements HttpServletRequest, FacesWrapper<PortletRequest> {

	// Private Data Members
	private PortletRequest wrappedPortletRequest;

	public HttpServletRequestAdapter(PortletRequest portletRequest) {
		this.wrappedPortletRequest = portletRequest;
	}

	public void removeAttribute(String name) {
		getWrapped().removeAttribute(name);
	}

	public Object getAttribute(String name) {
		return getWrapped().getAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		getWrapped().setAttribute(name, value);
	}

	public Enumeration<String> getAttributeNames() {
		return getWrapped().getAttributeNames();
	}

	public String getAuthType() {
		return getWrapped().getAuthType();
	}

	public String getCharacterEncoding() {

		PortletRequest portletRequest = getWrapped();

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			return clientDataRequest.getCharacterEncoding();
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
		PortletRequest portletRequest = getWrapped();

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			clientDataRequest.setCharacterEncoding(enc);
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	public int getContentLength() {

		PortletRequest portletRequest = getWrapped();

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			return clientDataRequest.getContentLength();
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	public String getContentType() {

		PortletRequest portletRequest = getWrapped();

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			return clientDataRequest.getContentType();
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	public String getContextPath() {
		return getWrapped().getContextPath();
	}

	public Cookie[] getCookies() {
		return getWrapped().getCookies();
	}

	public boolean isRequestedSessionIdValid() {
		return getWrapped().isRequestedSessionIdValid();
	}

	public long getDateHeader(String name) {
		throw new UnsupportedOperationException();
	}

	public boolean isRequestedSessionIdFromCookie() {
		throw new UnsupportedOperationException();
	}

	public boolean isSecure() {
		return getWrapped().isSecure();
	}

	public boolean isUserInRole(String role) {
		return getWrapped().isUserInRole(role);
	}

	public String getHeader(String name) {
		throw new UnsupportedOperationException();
	}

	public Enumeration<String> getHeaderNames() {
		throw new UnsupportedOperationException();
	}

	public Enumeration<String> getHeaders(String name) {
		throw new UnsupportedOperationException();
	}

	public ServletInputStream getInputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

	public int getIntHeader(String arg0) {
		throw new UnsupportedOperationException();
	}

	public boolean isRequestedSessionIdFromURL() {
		throw new UnsupportedOperationException();
	}

	public boolean isRequestedSessionIdFromUrl() {
		throw new UnsupportedOperationException();
	}

	public String getLocalAddr() {
		throw new UnsupportedOperationException();
	}

	public Locale getLocale() {
		return getWrapped().getLocale();
	}

	public Enumeration<Locale> getLocales() {
		return getWrapped().getLocales();
	}

	public String getLocalName() {
		return getWrapped().getLocale().getDisplayName();
	}

	public int getLocalPort() {
		return getWrapped().getServerPort();
	}

	public String getMethod() {

		PortletRequest portletRequest = getWrapped();

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			return clientDataRequest.getMethod();
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	public String getParameter(String name) {
		return getWrapped().getParameter(name);
	}

	public Map<String, String[]> getParameterMap() {
		return getWrapped().getParameterMap();
	}

	public Enumeration<String> getParameterNames() {
		return getWrapped().getParameterNames();
	}

	public String[] getParameterValues(String name) {
		return getWrapped().getParameterValues(name);
	}

	public String getPathInfo() {
		throw new UnsupportedOperationException();
	}

	public String getPathTranslated() {
		throw new UnsupportedOperationException();
	}

	public String getProtocol() {
		throw new UnsupportedOperationException();
	}

	public String getQueryString() {
		throw new UnsupportedOperationException();
	}

	public BufferedReader getReader() throws IOException {

		PortletRequest portletRequest = getWrapped();

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			return clientDataRequest.getReader();
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	public String getRealPath(String path) {
		throw new UnsupportedOperationException();
	}

	public String getRemoteAddr() {
		throw new UnsupportedOperationException();
	}

	public String getRemoteHost() {
		throw new UnsupportedOperationException();
	}

	public int getRemotePort() {
		throw new UnsupportedOperationException();
	}

	public String getRemoteUser() {
		return getWrapped().getRemoteUser();
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		throw new UnsupportedOperationException();
	}

	public String getRequestedSessionId() {
		return getWrapped().getRequestedSessionId();
	}

	public String getRequestURI() {
		throw new UnsupportedOperationException();
	}

	public StringBuffer getRequestURL() {
		throw new UnsupportedOperationException();
	}

	public String getScheme() {
		return getWrapped().getScheme();
	}

	public String getServerName() {
		return getWrapped().getServerName();
	}

	public int getServerPort() {
		return getWrapped().getServerPort();
	}

	public String getServletPath() {
		throw new UnsupportedOperationException();
	}

	public HttpSession getSession() {
		throw new UnsupportedOperationException();
	}

	public HttpSession getSession(boolean create) {
		throw new UnsupportedOperationException();
	}

	public Principal getUserPrincipal() {
		return getWrapped().getUserPrincipal();
	}

	public PortletRequest getWrapped() {
		return wrappedPortletRequest;
	}

}
