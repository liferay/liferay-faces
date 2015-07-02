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

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.faces.bridge.BridgePhase;
import com.liferay.faces.bridge.BridgePhaseFactory;
import com.liferay.faces.bridge.config.BridgeConfig;


/**
 * @author  Neil Griffin
 */
public class BridgePhaseFactoryImpl extends BridgePhaseFactory {

	@Override
	public BridgePhase getBridgeActionPhase(ActionRequest actionRequest, ActionResponse actionResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {

		return new BridgePhaseActionImpl(actionRequest, actionResponse, portletConfig, bridgeConfig);
	}

	@Override
	public BridgePhase getBridgeEventPhase(EventRequest eventRequest, EventResponse eventResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {
		return new BridgePhaseEventImpl(eventRequest, eventResponse, portletConfig, bridgeConfig);
	}

	@Override
	public BridgePhase getBridgeRenderPhase(RenderRequest renderRequest, RenderResponse renderResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {
		return new BridgePhaseRenderImpl(renderRequest, renderResponse, portletConfig, bridgeConfig);
	}

	@Override
	public BridgePhase getBridgeResourcePhase(ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		PortletConfig portletConfig, BridgeConfig bridgeConfig) {
		return new BridgePhaseResourceImpl(resourceRequest, resourceResponse, portletConfig, bridgeConfig);
	}

	public BridgePhaseFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}
}
