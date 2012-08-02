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

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.config.Product;
import com.liferay.faces.bridge.config.ProductMap;
import com.liferay.faces.bridge.util.ManagedBeanUtil;
import com.liferay.faces.util.map.AbstractPropertyMap;
import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class RequestAttributeMap extends AbstractPropertyMap<Object> {

	private static final boolean FACES_1233_WORKAROUND_ENABLED;

	static {

		// Versions of Liferay Portal prior to 6.1 have a bug in PortletRequest.removeAttribute(String) that needs to
		// be worked-around in this class. See: http://issues.liferay.com/browse/FACES-1233
		Product liferay = ProductMap.getInstance().get(BridgeConstants.LIFERAY_PORTAL);
		FACES_1233_WORKAROUND_ENABLED = (liferay.isDetected() && (liferay.getBuildId() < 6100));
	}

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

		if ((FACES_1233_WORKAROUND_ENABLED) &&
				(BridgeConstants.REQ_ATTR_PATH_INFO.equals(name) ||
					BridgeConstants.REQ_ATTR_SERVLET_PATH.equals(name))) {
			return null;
		}
		else {
			return portletRequest.getAttribute(name);
		}
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
