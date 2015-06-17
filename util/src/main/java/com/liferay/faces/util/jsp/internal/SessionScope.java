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
package com.liferay.faces.util.jsp.internal;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import com.liferay.faces.util.map.AbstractPropertyMap;
import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class SessionScope extends AbstractPropertyMap<Object> {

	// Private Data Members
	private HttpSession httpSession;

	public SessionScope(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	@Override
	protected AbstractPropertyMapEntry<Object> createPropertyMapEntry(String name) {
		return new SessionScopeEntry(httpSession, name);
	}

	@Override
	protected void removeProperty(String name) {
		httpSession.removeAttribute(name);
	}

	@Override
	protected Object getProperty(String name) {
		return httpSession.getAttribute(name);
	}

	@Override
	protected void setProperty(String name, Object value) {
		httpSession.setAttribute(name, value);
	}

	@Override
	protected Enumeration<String> getPropertyNames() {
		return (Enumeration<String>) httpSession.getAttributeNames();
	}

}
