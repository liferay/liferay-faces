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

import javax.faces.FacesException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;


/**
 * @author  Neil Griffin
 */
public class BridgePhaseFactoryImpl extends BridgePhaseFactory {

	@Override
	public BridgePhase getBridgeActionPhase(ActionRequest actionRequest, ActionResponse actionResponse,
		PortletConfig portletConfig) throws FacesException {

		return new BridgePhaseActionImpl(actionRequest, actionResponse, portletConfig);
	}

	@Override
	public BridgePhase getBridgeEventPhase(EventRequest eventRequest, EventResponse eventResponse,
		PortletConfig portletConfig) throws FacesException {
		return new BridgePhaseEventImpl(eventRequest, eventResponse, portletConfig);
	}

	@Override
	public BridgePhase getBridgeRenderPhase(RenderRequest renderRequest, RenderResponse renderResponse,
		PortletConfig portletConfig) throws FacesException {
		return new BridgePhaseRenderImpl(renderRequest, renderResponse, portletConfig);
	}

	@Override
	public BridgePhase getBridgeResourcePhase(ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		PortletConfig portletConfig) throws FacesException {
		return new BridgePhaseResourceImpl(resourceRequest, resourceResponse, portletConfig);
	}

	public BridgePhaseFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
