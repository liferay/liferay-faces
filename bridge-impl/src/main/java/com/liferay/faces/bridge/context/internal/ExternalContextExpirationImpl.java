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
package com.liferay.faces.bridge.context.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.context.map.internal.ContextMapFactory;


/**
 * This class is an implementation of {@link ExternalContext} that can be used during session expiration.
 *
 * @author  Neil Griffin
 */
public class ExternalContextExpirationImpl extends ExternalContext {

	// Private Data Members
	private Map<String, Object> applicationMap;
	private ServletContext servletContext;

	public ExternalContextExpirationImpl(ServletContext servletContext) {

		this.servletContext = servletContext;

		// Initialize the application map.
		ContextMapFactory contextMapFactory = (ContextMapFactory) BridgeFactoryFinder.getFactory(
				ContextMapFactory.class);
		this.applicationMap = contextMapFactory.getServletContextAttributeMap(servletContext);

	}

	@Override
	public void dispatch(String path) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String encodeActionURL(String url) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String encodeNamespace(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String encodeResourceURL(String url) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void log(String message) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void log(String message, Throwable exception) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void redirect(String url) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getApplicationMap() {
		return applicationMap;
	}

	@Override
	public String getAuthType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getContext() {
		return servletContext;
	}

	@Override
	public boolean isUserInRole(String role) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getInitParameter(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String> getInitParameterMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRemoteUser() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getRequest() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRequestContextPath() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getRequestCookieMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String> getRequestHeaderMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String[]> getRequestHeaderValuesMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Locale getRequestLocale() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<Locale> getRequestLocales() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getRequestMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String> getRequestParameterMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<String> getRequestParameterNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, String[]> getRequestParameterValuesMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRequestPathInfo() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getRequestServletPath() {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getResponse() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getSession(boolean create) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getSessionMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Principal getUserPrincipal() {
		throw new UnsupportedOperationException();
	}
}
