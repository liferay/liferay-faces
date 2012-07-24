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
package com.liferay.faces.bridge.context;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


/**
 * @author  Neil Griffin
 */
public abstract class IncongruityContextBaseImpl extends IncongruityContext {

	// Private Constants
	private static final String INCONGRUOUS_ACTIONS = "incongruousActions";
	private static final String REQUEST_CHARACTER_ENCODING = "requestCharacterEncoding";
	private static final String REQUEST_CONTENT_TYPE = "requestContentType";
	private static final String RESPONSE_CHARACTER_ENCODING = "responseCharacterEncoding";

	// Protected Enumerations
	protected enum IncongruousAction {
		RESPONSE_FLUSH_BUFFER, RESPONSE_RESET, SET_REQUEST_CHARACTER_ENCODING, SET_RESPONSE_BUFFER_SIZE,
		SET_RESPONSE_CHARACTER_ENCODING, SET_RESPONSE_CONTENT_LENGTH, SET_RESPONSE_CONTENT_TYPE, SET_RESPONSE_STATUS,
		WRITE_RESPONSE_OUTPUT_WRITER, WRITE_RESPONSE_OUTPUT_STREAM
	}

	// Protected Data Members
	protected Map<String, Object> attributeMap;

	public IncongruityContextBaseImpl() {
		this.attributeMap = new HashMap<String, Object>();

		Set<IncongruousAction> incongruousActions = new HashSet<IncongruousAction>();
		this.attributeMap.put(INCONGRUOUS_ACTIONS, incongruousActions);
	}

	@Override
	public void dispatch(String path) throws IOException {
		throw new IllegalStateException();
	}

	@Override
	public String encodeActionURL(String url) {
		throw new IllegalStateException();
	}

	@Override
	public String encodeNamespace(String name) {
		throw new IllegalStateException();
	}

	@Override
	public String encodeResourceURL(String url) {
		throw new IllegalStateException();
	}

	@Override
	public void log(String message) {
		throw new IllegalStateException();
	}

	@Override
	public void log(String message, Throwable exception) {
		throw new IllegalStateException();
	}

	@Override
	public void redirect(String url) throws IOException {
		throw new IllegalStateException();
	}

	@Override
	public Map<String, Object> getApplicationMap() {
		throw new IllegalStateException();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributeMap;
	}

	@Override
	public String getAuthType() {
		throw new IllegalStateException();
	}

	@Override
	public Object getContext() {
		throw new IllegalStateException();
	}

	@Override
	public boolean isUserInRole(String role) {
		throw new IllegalStateException();
	}

	@SuppressWarnings("unchecked")
	protected Set<IncongruousAction> getIncongruousActions() {
		return (Set<IncongruousAction>) attributeMap.get(INCONGRUOUS_ACTIONS);
	}

	@Override
	public String getInitParameter(String name) {
		throw new IllegalStateException();
	}

	@Override
	public Map<?, ?> getInitParameterMap() {
		throw new IllegalStateException();
	}

	@Override
	public String getRemoteUser() {
		throw new IllegalStateException();
	}

	@Override
	public Object getRequest() {
		throw new IllegalStateException();
	}

	@Override
	public void setRequest(Object request) {
	}

	@Override
	public String getRequestCharacterEncoding() {
		return (String) attributeMap.get(REQUEST_CHARACTER_ENCODING);
	}

	@Override
	public void setRequestCharacterEncoding(String encoding) throws UnsupportedEncodingException {
		attributeMap.put(REQUEST_CHARACTER_ENCODING, encoding);
		getIncongruousActions().add(IncongruousAction.SET_REQUEST_CHARACTER_ENCODING);
	}

	@Override
	public String getRequestContentType() {
		return (String) attributeMap.get(REQUEST_CONTENT_TYPE);
	}

	@Override
	public void setRequestContentType(String contentType) {
		attributeMap.put(REQUEST_CONTENT_TYPE, contentType);
	}

	@Override
	public String getRequestContextPath() {
		throw new IllegalStateException();
	}

	@Override
	public Map<String, Object> getRequestCookieMap() {
		throw new IllegalStateException();
	}

	@Override
	public Map<String, String> getRequestHeaderMap() {
		throw new IllegalStateException();
	}

	@Override
	public Map<String, String[]> getRequestHeaderValuesMap() {
		throw new IllegalStateException();
	}

	@Override
	public Locale getRequestLocale() {
		throw new IllegalStateException();
	}

	@Override
	public Iterator<Locale> getRequestLocales() {
		throw new IllegalStateException();
	}

	@Override
	public Map<String, Object> getRequestMap() {
		throw new IllegalStateException();
	}

	@Override
	public Map<String, String> getRequestParameterMap() {
		throw new IllegalStateException();
	}

	@Override
	public Iterator<String> getRequestParameterNames() {
		throw new IllegalStateException();
	}

	@Override
	public Map<String, String[]> getRequestParameterValuesMap() {
		throw new IllegalStateException();
	}

	@Override
	public String getRequestPathInfo() {
		throw new IllegalStateException();
	}

	@Override
	public String getRequestServletPath() {
		throw new IllegalStateException();
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		throw new IllegalStateException();
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		throw new IllegalStateException();
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		throw new IllegalStateException();
	}

	@Override
	public Object getResponse() {
		throw new IllegalStateException();
	}

	@Override
	public void setResponse(Object response) {
		throw new IllegalStateException();
	}

	@Override
	public String getResponseCharacterEncoding() {
		return (String) attributeMap.get(RESPONSE_CHARACTER_ENCODING);
	}

	@Override
	public void setResponseCharacterEncoding(String encoding) {
		attributeMap.put(RESPONSE_CHARACTER_ENCODING, encoding);
		getIncongruousActions().add(IncongruousAction.SET_RESPONSE_CHARACTER_ENCODING);
	}

	@Override
	public Object getSession(boolean create) {
		throw new IllegalStateException();
	}

	@Override
	public Map<String, Object> getSessionMap() {
		throw new IllegalStateException();
	}

	@Override
	public Principal getUserPrincipal() {
		throw new IllegalStateException();
	}
}
