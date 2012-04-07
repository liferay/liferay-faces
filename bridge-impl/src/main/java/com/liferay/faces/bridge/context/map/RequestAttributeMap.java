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

	// Private Constants
	private static final String PATH_INFO_ATTRIBUTE = "javax.servlet.include.path_info";
	private static final String SERVLET_PATH_ATTRIBUTE = "javax.servlet.include.servlet_path";

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

	/**
	 * This method returns the value of the specified attribute name according to the current request. Liferay Portal's
	 * portlet container uses a servlet dispatcher when executing the portlet lifecycle. This approach requires the
	 * portal to save some standard Servlet-API request attributes like javax.servlet.include.path_info and
	 * javax.servlet.include.servlet_path. Unfortunately, some JSF implementations expect that these attributes will
	 * only exist in a JSF servlet-based (not portlet-based) webapp. Therefore this method will return null if one of
	 * those request attributes is specified.
	 *
	 * @see  https://issues.apache.org/jira/browse/PORTLETBRIDGE-77
	 */
	@Override
	protected Object getProperty(String name) {
		Object value = null;

		if (name != null) {

			if (!(name.equals(PATH_INFO_ATTRIBUTE) || name.equals(SERVLET_PATH_ATTRIBUTE))) {
				value = portletRequest.getAttribute(name);
			}
		}

		return value;
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
