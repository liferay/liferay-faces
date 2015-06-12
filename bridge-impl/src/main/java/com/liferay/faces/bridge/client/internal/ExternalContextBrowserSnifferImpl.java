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
package com.liferay.faces.bridge.client.internal;

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
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public class ExternalContextBrowserSnifferImpl extends ExternalContext implements Wrapper<ExternalContext> {

	// Private Data Members
	private ExternalContext wrappedExternalContext;
	private HttpServletRequest httpServletRequest;

	public ExternalContextBrowserSnifferImpl(ExternalContext externalContext, HttpServletRequest httpServletRequest) {
		this.wrappedExternalContext = externalContext;
		this.httpServletRequest = httpServletRequest;
	}

	@Override
	public void dispatch(String path) throws IOException {
		wrappedExternalContext.dispatch(path);
	}

	@Override
	public String encodeActionURL(String url) {
		return wrappedExternalContext.encodeActionURL(url);
	}

	@Override
	public String encodeNamespace(String name) {
		return wrappedExternalContext.encodeNamespace(name);
	}

	@Override
	public String encodeResourceURL(String url) {
		return wrappedExternalContext.encodeResourceURL(url);
	}

	@Override
	public void log(String message) {
		wrappedExternalContext.log(message);
	}

	@Override
	public void log(String message, Throwable exception) {
		wrappedExternalContext.log(message, exception);
	}

	@Override
	public void redirect(String url) throws IOException {
		wrappedExternalContext.redirect(url);
	}

	@Override
	public Map<String, Object> getApplicationMap() {
		return wrappedExternalContext.getApplicationMap();
	}

	@Override
	public String getAuthType() {
		return wrappedExternalContext.getAuthType();
	}

	@Override
	public Object getContext() {
		return wrappedExternalContext.getContext();
	}

	@Override
	public boolean isUserInRole(String role) {
		return wrappedExternalContext.isUserInRole(role);
	}

	@Override
	public String getInitParameter(String name) {
		return wrappedExternalContext.getInitParameter(name);
	}

	@Override
	public Map getInitParameterMap() {
		return wrappedExternalContext.getInitParameterMap();
	}

	@Override
	public String getRemoteUser() {
		return wrappedExternalContext.getRemoteUser();
	}

	@Override
	public Object getRequest() {
		return httpServletRequest;
	}

	@Override
	public String getRequestContextPath() {
		return wrappedExternalContext.getRequestContextPath();
	}

	@Override
	public Map<String, Object> getRequestCookieMap() {
		return wrappedExternalContext.getRequestCookieMap();
	}

	@Override
	public Map<String, String> getRequestHeaderMap() {
		return wrappedExternalContext.getRequestHeaderMap();
	}

	@Override
	public Map<String, String[]> getRequestHeaderValuesMap() {
		return wrappedExternalContext.getRequestHeaderValuesMap();
	}

	@Override
	public Locale getRequestLocale() {
		return wrappedExternalContext.getRequestLocale();
	}

	@Override
	public Iterator<Locale> getRequestLocales() {
		return wrappedExternalContext.getRequestLocales();
	}

	@Override
	public Map<String, Object> getRequestMap() {
		return wrappedExternalContext.getRequestMap();
	}

	@Override
	public Map<String, String> getRequestParameterMap() {
		return wrappedExternalContext.getRequestParameterMap();
	}

	@Override
	public Iterator<String> getRequestParameterNames() {
		return wrappedExternalContext.getRequestParameterNames();
	}

	@Override
	public Map<String, String[]> getRequestParameterValuesMap() {
		return wrappedExternalContext.getRequestParameterValuesMap();
	}

	@Override
	public String getRequestPathInfo() {
		return wrappedExternalContext.getRequestPathInfo();
	}

	@Override
	public String getRequestServletPath() {
		return wrappedExternalContext.getRequestServletPath();
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		return wrappedExternalContext.getResource(path);
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		return wrappedExternalContext.getResourceAsStream(path);
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		return wrappedExternalContext.getResourcePaths(path);
	}

	@Override
	public Object getResponse() {
		return wrappedExternalContext.getResponse();
	}

	@Override
	public Object getSession(boolean create) {
		return wrappedExternalContext.getSession(create);
	}

	@Override
	public Map<String, Object> getSessionMap() {
		return wrappedExternalContext.getSessionMap();
	}

	@Override
	public Principal getUserPrincipal() {
		return wrappedExternalContext.getUserPrincipal();
	}

	@Override
	public ExternalContext getWrapped() {
		return wrappedExternalContext;
	}
}
