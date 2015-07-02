/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.config.internal;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeConfigFactoryImpl extends BridgeConfigFactory {

	// Private Constants
	private static final String BRIDGE_CONFIG = BridgeConfig.class.getName();

	@Override
	public BridgeConfig getBridgeConfig(PortletConfig portletConfig) {

		PortletContext portletContext = portletConfig.getPortletContext();
		BridgeConfig bridgeConfig = (BridgeConfig) portletContext.getAttribute(BRIDGE_CONFIG);

		if (bridgeConfig == null) {
			bridgeConfig = new BridgeConfigImpl(portletConfig);
			portletContext.setAttribute(BRIDGE_CONFIG, bridgeConfig);
		}

		return bridgeConfig;
	}

	@Override
	public PortletConfig getPortletConfig(PortletConfig portletConfig) {
		return portletConfig;
	}

	public BridgeConfigFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}
}
