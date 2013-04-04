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
package com.liferay.faces.bridge.config;

import javax.faces.FacesException;
import javax.portlet.PortletConfig;


/**
 * @author  Neil Griffin
 */
public class BridgeConfigFactoryImpl extends BridgeConfigFactory {

	// Private Data Members
	private BridgeConfig bridgeConfig;

	public BridgeConfigFactoryImpl(PortletConfig portletConfig) {
		bridgeConfig = new BridgeConfigImpl(portletConfig);
	}

	@Override
	public BridgeConfig getBridgeConfig() throws FacesException {
		return bridgeConfig;
	}

	public BridgeConfigFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
