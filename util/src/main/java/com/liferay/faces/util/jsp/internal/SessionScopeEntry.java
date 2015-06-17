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

import javax.servlet.http.HttpSession;

import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class SessionScopeEntry extends AbstractPropertyMapEntry<Object> {

	// Private Data Members
	private HttpSession httpSession;

	public SessionScopeEntry(HttpSession httpSession, String key) {
		super(key);
		this.httpSession = httpSession;
	}

	public Object getValue() {
		return httpSession.getAttribute(getKey());
	}

	public Object setValue(Object value) {

		Object oldValue = getValue();
		httpSession.setAttribute(getKey(), value);

		return oldValue;
	}

}
