/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.scope.BridgeRequestScope;


/**
 * @author  Neil Griffin
 */
public class BridgeContextFactoryImpl extends BridgeContextFactory {

	@Override
	public BridgeContext getBridgeContext(BridgeConfig bridgeConfig, BridgeRequestScope bridgeRequestScope,
		PortletConfig portletConfig, PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse, Bridge.PortletPhase portletPhase, PortletContainer portletContainer,
		IncongruityContext incongruityContext) {

		BridgeContext bridgeContext = new BridgeContextImpl(bridgeConfig, bridgeRequestScope, portletConfig,
				portletContext, portletRequest, portletResponse, portletPhase, portletContainer, incongruityContext);

		return bridgeContext;
	}

	public BridgeContextFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
