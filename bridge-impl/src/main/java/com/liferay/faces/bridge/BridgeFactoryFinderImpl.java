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
package com.liferay.faces.bridge;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigFactory;
import com.liferay.faces.bridge.config.BridgeConfigFactoryImpl;


/**
 * @author  Neil Griffin
 */
public class BridgeFactoryFinderImpl extends BridgeFactoryFinder {

	// Private Constants
	private static final String BRIDGE_FACTORY_CACHE = "com.liferay.faces.bridge.bridgeFactoryCache";

	// Private Data Members
	private BridgeConfig bridgeConfig;
	Map<Class<? extends FactoryWrapper<?>>, FactoryWrapper<?>> bridgeFactoryCache;

	@SuppressWarnings("unchecked")
	@Override
	public void init(PortletConfig portletConfig) {

		PortletContext portletContext = portletConfig.getPortletContext();

		synchronized (portletContext) {
			bridgeFactoryCache = (Map<Class<? extends FactoryWrapper<?>>, FactoryWrapper<?>>)
				portletContext.getAttribute(BRIDGE_FACTORY_CACHE);

			if (bridgeFactoryCache == null) {
				bridgeFactoryCache = new HashMap<Class<? extends FactoryWrapper<?>>, FactoryWrapper<?>>();
				portletContext.setAttribute(BRIDGE_FACTORY_CACHE, bridgeFactoryCache);
			}
		}

		BridgeConfigFactory bridgeConfigFactory = new BridgeConfigFactoryImpl(portletConfig);
		bridgeFactoryCache.put(BridgeConfigFactory.class, bridgeConfigFactory);
		bridgeConfig = bridgeConfigFactory.getBridgeConfig();
	}

	@Override
	public FactoryWrapper<?> getFactoryInstance(Class<? extends FactoryWrapper<?>> clazz) {

		FactoryWrapper<?> factory = null;

		if (clazz != null) {

			factory = bridgeFactoryCache.get(clazz);

			if (factory == null) {
				factory = (FactoryWrapper<?>) bridgeConfig.getAttributes().get(clazz.getName());

				if (factory != null) {
					bridgeFactoryCache.put(clazz, factory);
				}
			}
		}

		return factory;
	}

}
