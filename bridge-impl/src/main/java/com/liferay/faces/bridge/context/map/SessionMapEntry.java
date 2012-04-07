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
package com.liferay.faces.bridge.context.map;

import javax.portlet.PortletSession;

import com.liferay.faces.bridge.util.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class SessionMapEntry extends AbstractPropertyMapEntry<Object> {

	private PortletSession portletSession;
	private int scope;

	/**
	 * Constructs a new SessionMapEntry object instance.
	 *
	 * @param  portletSession  The portlet session.
	 * @param  key             The session map key name.
	 * @param  scope           The scope of the session map, which can be PortletSession.PORTLET_SCOPE or
	 *                         PortletSession.APPLICATION_SCOPE
	 */
	public SessionMapEntry(PortletSession portletSession, String key, int scope) {
		super(key);
		this.portletSession = portletSession;
		this.scope = scope;
	}

	public Object getValue() {
		return portletSession.getAttribute(getKey(), scope);
	}

	public Object setValue(Object value) {
		Object oldValue = getValue();
		portletSession.setAttribute(getKey(), value, scope);

		return oldValue;
	}
}
