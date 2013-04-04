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
package com.liferay.faces.bridge.scope;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.servlet.BridgeSessionListener;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeRequestScopeManagerImpl implements BridgeRequestScopeManager {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeRequestScopeManagerImpl.class);

	public void removeBridgeRequestScopesByPortlet(PortletConfig portletConfig) {
		String portletNameToRemove = portletConfig.getPortletName();
		BridgeRequestScopeCacheFactory bridgeRequestScopeCacheFactory = (BridgeRequestScopeCacheFactory)
			BridgeFactoryFinder.getFactory(BridgeRequestScopeCacheFactory.class);
		PortletContext portletContext = portletConfig.getPortletContext();
		BridgeRequestScopeCache bridgeRequestScopeCache = bridgeRequestScopeCacheFactory.getBridgeRequestScopeCache(
				portletContext);
		Set<Map.Entry<String, BridgeRequestScope>> mapEntries = bridgeRequestScopeCache.entrySet();

		if (mapEntries != null) {

			List<String> keysToRemove = new ArrayList<String>();

			for (Map.Entry<String, BridgeRequestScope> mapEntry : mapEntries) {
				BridgeRequestScope bridgeRequestScope = mapEntry.getValue();
				String bridgeRequestScopeId = bridgeRequestScope.getId();
				String portletName = bridgeRequestScopeId.split("[:][:][:]")[0];

				if (portletNameToRemove.equals(portletName)) {
					keysToRemove.add(mapEntry.getKey());
				}
			}

			for (String key : keysToRemove) {
				bridgeRequestScopeCache.remove(key);
			}
		}
	}

	/**
	 * This method is designed to be invoked from a {@link javax.servlet.http.HttpSessionListener} like {@link
	 * BridgeSessionListener} when a session timeout/expiration occurs. The logic in this method is a little awkward
	 * because we have to try and remove BridgeRequestScope instances from {@link Map} instances in the {@link
	 * ServletContext} rather than the {@link PortletContext} because we only have access to the Servlet-API when
	 * sessions expire.
	 */
	public void removeBridgeRequestScopesBySession(HttpSession httpSession) {

		// For each ServletContext attribute name:
		String httpSessionId = httpSession.getId();
		ServletContext servletContext = httpSession.getServletContext();
		@SuppressWarnings("unchecked")
		Enumeration<String> attributeNames = servletContext.getAttributeNames();

		if (attributeNames != null) {

			while (attributeNames.hasMoreElements()) {
				String attributeName = attributeNames.nextElement();

				// Get the value associated with the current attribute name.
				Object attributeValue = servletContext.getAttribute(attributeName);

				// If the value is a type of java.util.Map then it is possible that it contains BridgeRequestScope
				// instances.
				if ((attributeValue != null) && (attributeValue instanceof Map)) {

					// Prepare to iterate over the map entries.
					Map<?, ?> map = (Map<?, ?>) attributeValue;

					Set<?> entrySet = null;

					try {
						entrySet = map.entrySet();
					}
					catch (Exception e) {
						// ignore -- some maps like Mojarra's Flash scope will throw a NullPointerException
					}

					if (entrySet != null) {

						// Iterate over the map entries, and build up a list of BridgeRequestScope keys that are to be
						// removed. Doing it this way prevents ConcurrentModificationExceptions from being thrown.
						List<Object> keysToRemove = new ArrayList<Object>();

						for (Object mapEntryAsObject : entrySet) {
							Map.Entry<?, ?> mapEntry = (Map.Entry<?, ?>) mapEntryAsObject;
							Object key = mapEntry.getKey();
							Object value = mapEntry.getValue();

							if ((value != null) && (value instanceof BridgeRequestScope)) {
								BridgeRequestScope bridgeRequestScope = (BridgeRequestScope) value;
								String bridgeRequestScopeSessionId = bridgeRequestScope.getId().split("[:][:][:]")[1];

								if (httpSessionId.equals(bridgeRequestScopeSessionId)) {
									keysToRemove.add(key);
								}
							}
						}

						// For each BridgeRequestScope key that is to be removed:
						for (Object bridgeRequestScopeId : keysToRemove) {

							// Remove it from the map.
							Object bridgeRequestScope = map.remove(bridgeRequestScopeId);
							logger.debug(
								"Removed bridgeRequestScopeId=[{0}] bridgeRequestScope=[{1}] from cache due to session timeout",
								bridgeRequestScopeId, bridgeRequestScope);
						}
					}
				}
			}
		}
	}
}
