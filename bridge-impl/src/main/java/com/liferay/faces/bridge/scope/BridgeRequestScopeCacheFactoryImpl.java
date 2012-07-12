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

import javax.portlet.PortletContext;
import javax.portlet.faces.Bridge;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeRequestScopeCacheFactoryImpl extends BridgeRequestScopeCacheFactory {

	// Private Constants
	private static final int DEFAULT_MAX_MANAGED_REQUEST_SCOPES = 100;
	private static final String ATTR_BRIDGE_REQUEST_SCOPE_CACHE = "com.liferay.faces.bridge.bridgeRequestScopeCache";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeRequestScopeCacheFactoryImpl.class);

	@Override
	public BridgeRequestScopeCache getBridgeRequestScopeCache(PortletContext portletContext) {

		BridgeRequestScopeCache bridgeRequestScopeCache = null;

		synchronized (portletContext) {
			bridgeRequestScopeCache = (BridgeRequestScopeCache) portletContext.getAttribute(
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

				bridgeRequestScopeCache = new BridgeRequestScopeCacheImpl(maxSize);

				portletContext.setAttribute(ATTR_BRIDGE_REQUEST_SCOPE_CACHE, bridgeRequestScopeCache);
			}
		}

		return bridgeRequestScopeCache;
	}

	public BridgeRequestScopeCacheFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}
}
