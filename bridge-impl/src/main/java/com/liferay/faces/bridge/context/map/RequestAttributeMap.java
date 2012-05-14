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

import java.util.Enumeration;

import javax.faces.context.ExternalContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.util.AbstractPropertyMap;
import com.liferay.faces.bridge.util.AbstractPropertyMapEntry;
import com.liferay.faces.bridge.util.ManagedBeanUtil;


/**
 * @author  Neil Griffin
 */
public class RequestAttributeMap extends AbstractPropertyMap<Object> {

	// Private Data Members
	private PortletRequest portletRequest;
	private boolean preferPreDestroy;

	public RequestAttributeMap(PortletRequest portletRequest, boolean preferPreDestroy) {
		this.portletRequest = portletRequest;
		this.preferPreDestroy = preferPreDestroy;
	}

	/**
	 * According to the JSF 2.0 JavaDocs for {@link ExternalContext.getRequestMap}, before a managed-bean is removed
	 * from the map, any public no-argument void return methods annotated with javax.annotation.PreDestroy must be
	 * called first.
	 */
	@Override
	public Object remove(Object key) {
		Object potentialManagedBean = super.remove(key);
		ManagedBeanUtil.invokePreDestroyMethods(potentialManagedBean, preferPreDestroy);

		return potentialManagedBean;
	}

	@Override
	protected AbstractPropertyMapEntry<Object> createPropertyMapEntry(String name) {
		return new RequestAttributeMapEntry(portletRequest, name);
	}

	@Override
	protected void removeProperty(String name) {
		portletRequest.removeAttribute(name);
	}

	@Override
	protected Object getProperty(String name) {
		return portletRequest.getAttribute(name);
	}

	@Override
	protected void setProperty(String name, Object value) {
		portletRequest.setAttribute(name, value);
	}

	@Override
	protected Enumeration<String> getPropertyNames() {
		return portletRequest.getAttributeNames();
	}
}
