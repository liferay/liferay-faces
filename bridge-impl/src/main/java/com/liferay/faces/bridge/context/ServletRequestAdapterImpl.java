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
package com.liferay.faces.bridge.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ClientDataRequest;
import javax.portlet.PortletRequest;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;


/**
 * @author  Neil Griffin
 */
public class ServletRequestAdapterImpl implements ServletRequest {

	// Private Data Members
	private PortletRequest portletRequest;

	public ServletRequestAdapterImpl(PortletRequest portletRequest) {
		this.portletRequest = portletRequest;
	}

	public void removeAttribute(String name) {
		portletRequest.removeAttribute(name);
	}

	public Object getAttribute(String name) {
		return portletRequest.getAttribute(name);
	}

	public void setAttribute(String name, Object value) {
		portletRequest.setAttribute(name, value);
	}

	public Enumeration<?> getAttributeNames() {
		return portletRequest.getAttributeNames();
	}

	public String getCharacterEncoding() {

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			return clientDataRequest.getCharacterEncoding();
		}
		else {
			return null;
		}
	}

	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
		throw new UnsupportedOperationException();
	}

	public int getContentLength() {

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			return clientDataRequest.getContentLength();
		}
		else {
			return 0;
		}
	}

	public String getContentType() {

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			return clientDataRequest.getContentType();
		}
		else {
			return null;
		}
	}

	public boolean isSecure() {
		return portletRequest.isSecure();
	}

	public ServletInputStream getInputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

	public String getLocalAddr() {
		return null;
	}

	public Locale getLocale() {
		return portletRequest.getLocale();
	}

	public Enumeration<?> getLocales() {
		return portletRequest.getLocales();
	}

	public String getLocalName() {
		return portletRequest.getLocale().toString();
	}

	public int getLocalPort() {
		return 0;
	}

	public String getParameter(String name) {
		return portletRequest.getParameter(name);
	}

	public Map<?, ?> getParameterMap() {
		return portletRequest.getParameterMap();
	}

	public Enumeration<?> getParameterNames() {
		return portletRequest.getParameterNames();
	}

	public String[] getParameterValues(String name) {
		return portletRequest.getParameterValues(name);
	}

	public String getProtocol() {
		return null;
	}

	public BufferedReader getReader() throws IOException {

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			return clientDataRequest.getReader();
		}
		else {
			return null;
		}
	}

	public String getRealPath(String path) {
		return null;
	}

	public String getRemoteAddr() {
		return null;
	}

	public String getRemoteHost() {
		return null;
	}

	public int getRemotePort() {
		return 0;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return null;
	}

	public String getScheme() {

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			return clientDataRequest.getScheme();
		}
		else {
			return null;
		}
	}

	public String getServerName() {

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			return clientDataRequest.getServerName();
		}
		else {
			return null;
		}
	}

	public int getServerPort() {

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			return clientDataRequest.getServerPort();
		}
		else {
			return 0;
		}
	}
}
