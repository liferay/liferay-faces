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
package com.liferay.faces.bridge.internal;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.context.BridgeContext;


/**
 * This class provides a compatibility layer that isolates differences related to JSF 1.2.
 *
 * @author  Neil Griffin
 */
public abstract class BridgePhaseCompat_1_2_Impl extends BridgePhaseBaseImpl {

	public BridgePhaseCompat_1_2_Impl(PortletConfig portletConfig, BridgeConfig bridgeConfig) {
		super(portletConfig, bridgeConfig);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void removeBridgeContextAttribute(PortletRequest portletRequest) {
		portletRequest.removeAttribute(BridgeExt.BRIDGE_CONTEXT_ATTRIBUTE);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void setBridgeContextAttribute(PortletRequest portletRequest, BridgeContext bridgeContext) {
		portletRequest.setAttribute(BridgeExt.BRIDGE_CONTEXT_ATTRIBUTE, bridgeContext);
	}
}
