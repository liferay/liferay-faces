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
package com.liferay.faces.bridge.context;

import javax.portlet.PortletConfig;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigImpl;


/**
 * @author  Neil Griffin
 */
public class BridgeContextImpl extends BridgeContext {

	// Private Data Members
	private BridgeConfig bridgeConfig;
	private PortletConfig portletConfig;

	public BridgeContextImpl(PortletConfig portletConfig) {
		this.portletConfig = portletConfig;
	}

	@Override
	public BridgeConfig getBridgeConfig() {

		if (bridgeConfig == null) {
			bridgeConfig = new BridgeConfigImpl();
		}

		return bridgeConfig;
	}

	@Override
	public PortletConfig getPortletConfig() {
		return portletConfig;
	}
}
