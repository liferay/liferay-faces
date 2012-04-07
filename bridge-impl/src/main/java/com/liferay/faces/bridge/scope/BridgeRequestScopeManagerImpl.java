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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
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
	private static final String SUPPORTED_PUBLIC_RENDER_PARAMETERS_MAP =
		"com.liferay.faces.bridge.supportedPublicRenderParametersMap";

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
		String renderParameterName = portletName + RENDER_PARAM_BRIDGE_REQUEST_SCOPE_ID;
		String bridgeRequestScopeId = (String) portletRequest.getParameter(renderParameterName);

		// PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-209 Special Case: If a
		// bridgeRequestScopeId was detected in a render parameter, then that means there should be a BridgeRequestScope
		// in the cache. However, if the current portlet is participating in Inter-Portlet Communication (IPC) via
		// supported-public-render-parameter entries in portlet.xml, then we need to check if any of the values of these
		// have changed. If any of them have changed, then the BridgeRequestScope must be removed from the cache so that
		// a new one will be created below. This will allow the IPC to work correctly.
		if (bridgeRequestScopeId != null) {

			bridgeRequestScope = getBridgeRequestScopeCache(portletContext).get(bridgeRequestScopeId);

			if (bridgeRequestScope != null) {

				@SuppressWarnings("unchecked")
				Map<String, String> supportedPublicRenderParametersMap = (Map<String, String>) bridgeRequestScope.get(
						SUPPORTED_PUBLIC_RENDER_PARAMETERS_MAP);
				Enumeration<String> supportedPublicRenderParameterNames = portletConfig.getPublicRenderParameterNames();

				if ((supportedPublicRenderParametersMap != null) && (supportedPublicRenderParameterNames != null)) {

					while (supportedPublicRenderParameterNames.hasMoreElements()) {
						String name = supportedPublicRenderParameterNames.nextElement();
						String oldValue = supportedPublicRenderParametersMap.get(name);
						String newValue = portletRequest.getParameter(name);

						if (((oldValue == null) && (newValue != null)) || ((oldValue != null) && (newValue == null)) ||
								((oldValue != null) && !oldValue.equals(newValue))) {
							logger.debug(
								"Removed bridgeRequestScopeId=[{0}] bridgeRequestScope=[{1}] from cache due since public render parameter name=[{2}] changed from oldValue=[{3}] to newValue=[{4}]",
								new Object[] { bridgeRequestScopeId, bridgeRequestScope, name, oldValue, newValue });
							getBridgeRequestScopeCache(portletContext).remove(bridgeRequestScopeId);
							bridgeRequestScopeId = null;
							bridgeRequestScope = null;

							break;
						}
					}
				}
			}
		}

		// If there was a render parameter value found for the "id", then return the cached bridge request scope
		// associated with the "id".
		if (bridgeRequestScopeId != null) {
			bridgeRequestScope = getBridgeRequestScopeCache(portletContext).get(bridgeRequestScopeId);

			if (bridgeRequestScope != null) {
				logger.debug("Found render parameter name=[{0}] value=[{1}] and cached bridgeRequestScope=[{2}]",
					renderParameterName, bridgeRequestScopeId, bridgeRequestScope);
			}
			else {
				logger.debug(
					"Found render parameter name=[{0}] value=[{1}] but bridgeRequestScope is not in the cache.",
					renderParameterName, bridgeRequestScopeId);
			}
		}

		// If a BridgeRequestScope hasn't been determined by this point, then ask the BridgeRequestScopeFactory to
		// create a new bridge request scope and return it.
		if (bridgeRequestScope == null) {
			logger.debug("No value found for render parameter name=[{0}]", renderParameterName);

			BridgeRequestScopeFactory bridgeRequestScopeFactory = (BridgeRequestScopeFactory) BridgeFactoryFinder
				.getFactory(BridgeRequestScopeFactory.class);

			String sessionId = portletRequest.getPortletSession().getId();
			String idPrefix = portletName + ":::" + sessionId + ":::";
			bridgeRequestScope = bridgeRequestScopeFactory.getBridgeRequestScope(portletConfig, portletContext, portletRequest, idPrefix);
			bridgeRequestScopeId = bridgeRequestScope.getId();

			if (portletResponse instanceof StateAwareResponse) {
				logger.debug("Setting render parameter name=[{0}] value=[{1}]", renderParameterName,
					bridgeRequestScopeId);

				StateAwareResponse stateAwareResponse = (StateAwareResponse) portletResponse;
				stateAwareResponse.setRenderParameter(renderParameterName, bridgeRequestScopeId);
			}

			logger.debug("Caching bridgeRequestScopeId=[{0}] bridgeRequestScope=[{1}]", bridgeRequestScopeId,
				bridgeRequestScope);

			// Save the values of the Supported Public Render Parameters in a map inside the BridgeRequestScope so that
			// this method can detect value changes on subsequent renders.
			Map<String, String> supportedPublicRenderParametersMap = new HashMap<String, String>();
			Enumeration<String> publicRenderParameterNames = portletConfig.getPublicRenderParameterNames();

			if (publicRenderParameterNames != null) {

				while (publicRenderParameterNames.hasMoreElements()) {
					String name = publicRenderParameterNames.nextElement();
					String value = portletRequest.getParameter(name);
					supportedPublicRenderParametersMap.put(name, value);
				}
			}

			bridgeRequestScope.put(SUPPORTED_PUBLIC_RENDER_PARAMETERS_MAP, supportedPublicRenderParametersMap);

			getBridgeRequestScopeCache(portletContext).put(bridgeRequestScopeId, bridgeRequestScope);
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
