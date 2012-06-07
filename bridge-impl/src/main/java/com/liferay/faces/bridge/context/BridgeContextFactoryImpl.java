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
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.ProductMap;


/**
 * @author  Neil Griffin
 */
public class BridgeContextFactoryImpl extends BridgeContextFactory {

	// Private Constants
	private static final boolean TCK_JSR_329_DETECTED = ProductMap.getInstance().get(BridgeConstants.TCK_JSR_329)
		.isDetected();

	// Private Data Members
	private BridgeContextFactory wrappedFactory;

	public BridgeContextFactoryImpl() {
	}

	public BridgeContextFactoryImpl(BridgeContextFactory bridgeRequestScopeFactory) {
		wrappedFactory = bridgeRequestScopeFactory;
	}

	@Override
	public BridgeContext getBridgeContext(BridgeConfig bridgeConfig, PortletConfig portletConfig,
		PortletContext portletContext, PortletRequest portletRequest, PortletResponse portletResponse,
		Bridge.PortletPhase portletPhase) {

		BridgeContext bridgeContext = null;

		if (wrappedFactory != null) {
			bridgeContext = wrappedFactory.getBridgeContext(bridgeConfig, portletConfig, portletContext, portletRequest,
					portletResponse, portletPhase);
		}

		if (bridgeContext == null) {
			bridgeContext = new BridgeContextImpl(bridgeConfig, portletConfig, portletContext, portletRequest,
					portletResponse, portletPhase);

			// If the TCK is detected, then wrap the default BridgeContext implementation with a wrapper that can
			// handle special cases of the TCK.
			if (TCK_JSR_329_DETECTED) {
				bridgeContext = new BridgeContextTCKImpl(bridgeContext);
			}
		}

		return bridgeContext;
	}

	public BridgeContextFactory getWrapped() {
		return wrappedFactory;
	}

}
