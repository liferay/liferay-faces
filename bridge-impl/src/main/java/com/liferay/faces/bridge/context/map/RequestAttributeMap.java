/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.bean.BeanManager;
import com.liferay.faces.util.map.AbstractPropertyMap;
import com.liferay.faces.util.map.AbstractPropertyMapEntry;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class RequestAttributeMap extends AbstractPropertyMap<Object> {

	// Private Constants
	private static final boolean NULL_PATH_ATTRIBUTES;
	private static final String REQUEST_SCOPED_FQCN = "javax.faces.bean.RequestScoped";

	static {

		// Versions of Liferay Portal prior to 6.1 have a bug in PortletRequest.removeAttribute(String) that needs to
		// be worked-around in this class. See: http://issues.liferay.com/browse/FACES-1233
		// Additionally, Resin requires a similar workaround. See: http://issues.liferay.com/browse/FACES-1612
		Product liferay = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL);
		Product resin = ProductMap.getInstance().get(ProductConstants.RESIN);
		NULL_PATH_ATTRIBUTES = (liferay.isDetected() && (resin.isDetected() || (liferay.getBuildId() < 6100)));
	}

	// Private Data Members
	private BeanManager beanManager;
	private boolean distinctRequestScopedManagedBeans;
	private String namespace;
	private PortletRequest portletRequest;
	private boolean preferPreDestroy;
	private Set<String> removedAttributeNames;

	public RequestAttributeMap(PortletRequest portletRequest, BeanManager beanManager, String namespace,
		boolean preferPreDestroy, boolean distinctRequestScopedManagedBeans,
		Set<String> removedAttributeNames) {
		this.portletRequest = portletRequest;
		this.namespace = namespace;
		this.preferPreDestroy = preferPreDestroy;
		this.distinctRequestScopedManagedBeans = distinctRequestScopedManagedBeans;
		this.beanManager = beanManager;
		this.removedAttributeNames = removedAttributeNames;
	}

	/**
	 * According to the JSF 2.0 JavaDocs for {@link ExternalContext.getRequestMap}, before a managed-bean is removed
	 * from the map, any public no-argument void return methods annotated with javax.annotation.PreDestroy must be
	 * called first.
	 */
	@Override
	public Object remove(Object key) {

		String keyAsString = (String) key;
		Object potentialManagedBeanValue = super.remove(key);

		if (beanManager.isManagedBean(keyAsString, potentialManagedBeanValue)) {
			beanManager.invokePreDestroyMethods(potentialManagedBeanValue, preferPreDestroy);
		}

		return potentialManagedBeanValue;
	}

	@Override
	protected AbstractPropertyMapEntry<Object> createPropertyMapEntry(String name) {
		return new RequestAttributeMapEntry(portletRequest, name);
	}

	@Override
	protected void removeProperty(String name) {
		removedAttributeNames.add(name);
		portletRequest.removeAttribute(name);
	}

	@Override
	public boolean containsKey(Object key) {
		return super.containsKey(key);
	}

	@Override
	protected Object getProperty(String name) {

		if ((NULL_PATH_ATTRIBUTES) &&
				(BridgeConstants.REQ_ATTR_PATH_INFO.equals(name) ||
					BridgeConstants.REQ_ATTR_SERVLET_PATH.equals(name))) {
			return null;
		}
		else {
			Object attributeValue = portletRequest.getAttribute(name);

			// FACES-1446: Strictly enforce Liferay Portal's private-request-attribute feature so that each portlet
			// will have its own managed-bean instance.
			if (distinctRequestScopedManagedBeans) {

				if (attributeValue != null) {

					boolean requestScopedBean = false;
					Annotation[] annotations = attributeValue.getClass().getAnnotations();

					if (annotations != null) {

						for (Annotation annotation : annotations) {

							if (annotation.annotationType().getName().equals(REQUEST_SCOPED_FQCN)) {
								requestScopedBean = true;

								break;
							}
						}
					}

					if (requestScopedBean) {

						// If the private-request-attribute feature is enabled in WEB-INF/liferay-portlet.xml, then the
						// NamespaceServletRequest.getAttribute(String) method first tries to get the attribute value by
						// prepending the portlet namespace. If the value is null, then it attempts to get it WITHOUT
						// the prepending the portlet namespace. But that causes a problem for @RequestScoped
						// managed-beans if the same name is used in a different portlet. In the case that the JSF
						// runtime is trying to resolve an EL-expression like "#{backingBean}", then this method must
						// return null if the bean has not yet been created (for this portlet) by the JSF managed-bean
						// facility.
						Object namespacedAttributeValue = portletRequest.getAttribute(namespace + name);

						if (namespacedAttributeValue != attributeValue) {
							attributeValue = null;
						}
					}
				}
			}

			return attributeValue;
		}
	}

	@Override
	protected void setProperty(String name, Object value) {
		portletRequest.setAttribute(name, value);
	}

	@Override
	protected Enumeration<String> getPropertyNames() {

		List<String> attributeNames = new ArrayList<String>();

		Enumeration<String> portletRequestAttributeNames = portletRequest.getAttributeNames();

		if (portletRequestAttributeNames != null) {

			while (portletRequestAttributeNames.hasMoreElements()) {
				String attributeName = portletRequestAttributeNames.nextElement();

				if (!removedAttributeNames.contains(attributeName)) {
					attributeNames.add(attributeName);
				}
			}
		}

		return Collections.enumeration(attributeNames);
	}
}
