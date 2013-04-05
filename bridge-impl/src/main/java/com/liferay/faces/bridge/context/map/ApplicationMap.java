/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.portlet.PortletContext;

import com.liferay.faces.bridge.bean.BeanManager;
import com.liferay.faces.util.map.AbstractPropertyMap;
import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class ApplicationMap extends AbstractPropertyMap<Object> {

	// Private Data Members
	private BeanManager beanManager;
	private PortletContext portletContext;
	private boolean preferPreDestroy;

	public ApplicationMap(PortletContext portletContext, BeanManager beanManager, boolean preferPreDestroy) {
		this.portletContext = portletContext;
		this.preferPreDestroy = preferPreDestroy;
		this.beanManager = beanManager;
	}

	/**
	 * According to the JSF 2.0 JavaDocs for {@link ExternalContext.getApplicationMap}, before a managed-bean is removed
	 * from the map, any public no-argument void return methods annotated with javax.annotation.PreDestroy must be
	 * called first.
	 */
	@Override
	public void clear() {
		Set<Map.Entry<String, Object>> mapEntries = entrySet();

		if (mapEntries != null) {

			for (Map.Entry<String, Object> mapEntry : mapEntries) {
				String potentialManagedBeanName = mapEntry.getKey();
				Object potentialManagedBeanValue = mapEntry.getValue();

				if (beanManager.isManagedBean(potentialManagedBeanName, potentialManagedBeanValue)) {
					beanManager.invokePreDestroyMethods(potentialManagedBeanValue, preferPreDestroy);
				}
			}
		}

		super.clear();
	}

	/**
	 * According to the JSF 2.0 JavaDocs for {@link ExternalContext.getApplicationMap}, before a managed-bean is removed
	 * from the map, any public no-argument void return methods annotated with javax.annotation.PreDestroy must be
	 * called first.
	 */
	@Override
	public Object remove(Object key) {
		String potentialManagedBeanName = (String) key;
		Object potentialManagedBeanValue = super.remove(key);

		if (beanManager.isManagedBean(potentialManagedBeanName, potentialManagedBeanValue)) {
			beanManager.invokePreDestroyMethods(potentialManagedBeanValue, preferPreDestroy);
		}

		return potentialManagedBeanValue;
	}

	@Override
	protected AbstractPropertyMapEntry<Object> createPropertyMapEntry(String name) {
		return new ApplicationMapEntry(portletContext, name);
	}

	@Override
	protected void removeProperty(String name) {
		portletContext.removeAttribute(name);
	}

	@Override
	protected Object getProperty(String name) {
		return portletContext.getAttribute(name);
	}

	@Override
	protected void setProperty(String name, Object value) {
		portletContext.setAttribute(name, value);
	}

	@Override
	protected Enumeration<String> getPropertyNames() {
		return portletContext.getAttributeNames();
	}
}
