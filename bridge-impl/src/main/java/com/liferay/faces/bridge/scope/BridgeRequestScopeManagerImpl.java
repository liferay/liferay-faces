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
package com.liferay.faces.bridge.scope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.ResourceResponse;
import javax.portlet.StateAwareResponse;
import javax.portlet.faces.Bridge;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.bridge.servlet.BridgeSessionListener;


/**
 * @author  Neil Griffin
 */
public class BridgeRequestScopeManagerImpl implements BridgeRequestScopeManager {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeRequestScopeManagerImpl.class);

	// Private Constants
	private static final int DEFAULT_MAX_MANAGED_REQUEST_SCOPES = 100;
	private static final String ATTR_BRIDGE_REQUEST_SCOPE_CACHE = "com.liferay.faces.bridge.bridgeRequestScopeCache";
	private static final String RENDER_PARAM_BRIDGE_REQUEST_SCOPE_ID = "com.liferay.faces.bridge.bridgeRequestScopeId";

	public void removeBridgeRequestScope(BridgeRequestScope bridgeRequestScope, PortletContext portletContext) {

		if (bridgeRequestScope != null) {
			getBridgeRequestScopeCache(portletContext).remove(bridgeRequestScope.getId());
		}
	}

	public void removeBridgeRequestScopesByPortlet(PortletConfig portletConfig, PortletContext portletContext) {
		String portletNameToRemove = portletConfig.getPortletName();

		if (portletNameToRemove != null) {
			Map<String, BridgeRequestScope> bridgeRequestScopeCache = getBridgeRequestScopeCache(portletContext);
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

	public BridgeRequestScope getBridgeRequestScope(PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		BridgeRequestScope bridgeRequestScope = null;

		// Determine if there is a bridge request scope "id" saved as a render parameter. Note that in order to avoid
		// collisions with bridge request scopes for other portlets, the render parameter name has to be namespaced with
		// the portlet name.
		String portletName = portletConfig.getPortletName();
		String bridgeRequestScopeKey = portletName + RENDER_PARAM_BRIDGE_REQUEST_SCOPE_ID;

		// If there is a render parameter value found for the "id", then return the cached bridge request scope
		// associated with the "id".
		String bridgeRequestScopeId = (String) portletRequest.getParameter(bridgeRequestScopeKey);

		if (bridgeRequestScopeId != null) {
			bridgeRequestScope = getBridgeRequestScopeCache(portletContext).get(bridgeRequestScopeId);

			if (bridgeRequestScope != null) {
				logger.debug("Found render parameter name=[{0}] value=[{1}] and cached bridgeRequestScope=[{2}]",
					bridgeRequestScopeKey, bridgeRequestScopeId, bridgeRequestScope);
			}
			else {
				logger.error("Found render parameter name=[{0}] value=[{1}] BUT bridgeRequestScope is not in the cache",
					bridgeRequestScopeKey, bridgeRequestScopeId);
			}
		}

		// Otherwise, if there is a portlet session attribute found for the "id", then return the cached bridge
		// request scope associated with the "id". Note: This occurs after an Ajax-based ResourceRequest so that
		// non-excluded request attributes can be picked up by a subsequent RenderRequest.
		else {

			// TCK TestPage073: scopeAfterRedisplayResourcePPRTest
			PortletSession portletSession = portletRequest.getPortletSession();
			bridgeRequestScopeId = (String) portletSession.getAttribute(bridgeRequestScopeKey);

			if (bridgeRequestScopeId != null) {

				portletSession.removeAttribute(bridgeRequestScopeKey);

				bridgeRequestScope = getBridgeRequestScopeCache(portletContext).get(bridgeRequestScopeId);

				if (bridgeRequestScope != null) {

					logger.debug("Found session attribute name=[{0}] value=[{1}] and cached bridgeRequestScope=[{2}]",
						bridgeRequestScopeKey, bridgeRequestScopeId, bridgeRequestScope);
				}
				else {
					logger.error(
						"Found session attribute name=[{0}] value=[{1}] but bridgeRequestScope is not in the cache",
						bridgeRequestScopeKey, bridgeRequestScopeId);
				}
			}
		}

		// Otherwise, ask the BridgeRequestScopeFactory to create a new bridge request scope and return it.
		if (bridgeRequestScope == null) {
			logger.debug("No value found for render-parameter/session-attribute name=[{0}]", bridgeRequestScopeKey);

			BridgeRequestScopeFactory bridgeRequestScopeFactory = (BridgeRequestScopeFactory) BridgeFactoryFinder
				.getFactory(BridgeRequestScopeFactory.class);

			PortletSession portletSession = portletRequest.getPortletSession();
			String sessionId = portletSession.getId();
			String idPrefix = portletName + ":::" + sessionId + ":::";
			bridgeRequestScope = bridgeRequestScopeFactory.getBridgeRequestScope(portletConfig, portletContext,
					portletRequest, idPrefix);
			bridgeRequestScopeId = bridgeRequestScope.getId();

			// Only store the newly created BridgeRequestScope in the cache during the ACTION_PHASE, EVENT_PHASE, or
			// RESOURCE_PHASE or the portlet lifecycle. No sense in storing it in the cache during the RENDER_PHASE
			// since the lifespan of a BridgeRequestScope starts with an full postback action (most commonly), and with
			// Ajax request (less commonly).
			boolean storeInCache = false;

			if (portletResponse instanceof StateAwareResponse) {
				logger.debug("Setting render parameter name=[{0}] value=[{1}]", bridgeRequestScopeKey,
					bridgeRequestScopeId);

				StateAwareResponse stateAwareResponse = (StateAwareResponse) portletResponse;
				stateAwareResponse.setRenderParameter(bridgeRequestScopeKey, bridgeRequestScopeId);
				storeInCache = true;
			}
			else if (portletResponse instanceof ResourceResponse) {

				// TCK TestPage073: scopeAfterRedisplayResourcePPRTest
				portletSession.setAttribute(bridgeRequestScopeKey, bridgeRequestScopeId);
				storeInCache = true;
			}

			if (storeInCache) {
				logger.debug("Caching bridgeRequestScopeId=[{0}] bridgeRequestScope=[{1}]", bridgeRequestScopeId,
					bridgeRequestScope);

				getBridgeRequestScopeCache(portletContext).put(bridgeRequestScopeId, bridgeRequestScope);
			}

		}

		return bridgeRequestScope;
	}

	@SuppressWarnings("unchecked")
	protected Map<String, BridgeRequestScope> getBridgeRequestScopeCache(PortletContext portletContext) {

		Map<String, BridgeRequestScope> bridgeRequestScopeCache = null;

		synchronized (portletContext) {
			bridgeRequestScopeCache = (Map<String, BridgeRequestScope>) portletContext.getAttribute(
					ATTR_BRIDGE_REQUEST_SCOPE_CACHE);

			if (bridgeRequestScopeCache == null) {

				// Spec Section 3.2: Support for configuration of maximum number of bridge request scopes.
				int maxSize = DEFAULT_MAX_MANAGED_REQUEST_SCOPES;
				String maxManagedRequestScopes = portletContext.getInitParameter(Bridge.MAX_MANAGED_REQUEST_SCOPES);

				if (maxManagedRequestScopes != null) {

					try {
						maxSize = Integer.parseInt(maxManagedRequestScopes);
					}
					catch (NumberFormatException e) {
						logger.error("Unable to parse portlet.xml init-param name=[{0}] error=[{1}]",
							Bridge.MAX_MANAGED_REQUEST_SCOPES, e.getMessage());
					}
				}

				bridgeRequestScopeCache = Collections.synchronizedMap(new BridgeRequestScopeCache(maxSize));

				portletContext.setAttribute(ATTR_BRIDGE_REQUEST_SCOPE_CACHE, bridgeRequestScopeCache);
			}
		}

		return bridgeRequestScopeCache;
	}

}
