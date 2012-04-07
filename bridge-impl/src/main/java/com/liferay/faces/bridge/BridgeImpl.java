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
package com.liferay.faces.bridge;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeException;
import javax.portlet.faces.BridgeUninitializedException;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeImpl implements Bridge {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeImpl.class);

	// Private Data Members
	private boolean initialized = false;
	private BridgePhaseFactory bridgePhaseFactory;
	private PortletConfig portletConfig;

	public void destroy() {
		logger.trace("destroy this=[{0}]", this);
		initialized = false;
	}

	public void doFacesRequest(ActionRequest actionRequest, ActionResponse actionResponse)
		throws BridgeDefaultViewNotSpecifiedException, BridgeUninitializedException, BridgeException {

		if (initialized) {
			BridgePhase bridgePhase = bridgePhaseFactory.getBridgeActionPhase(actionRequest, actionResponse,
					portletConfig);
			bridgePhase.execute();
		}
		else {
			throw new BridgeUninitializedException();
		}
	}

	public void doFacesRequest(EventRequest eventRequest, EventResponse eventResponse)
		throws BridgeUninitializedException, BridgeException {

		if (initialized) {
			BridgePhase bridgePhase = bridgePhaseFactory.getBridgeEventPhase(eventRequest, eventResponse,
					portletConfig);
			bridgePhase.execute();
		}
		else {
			throw new BridgeUninitializedException();
		}

	}

	public void doFacesRequest(RenderRequest renderRequest, RenderResponse renderResponse)
		throws BridgeDefaultViewNotSpecifiedException, BridgeUninitializedException, BridgeException {

		if (initialized) {
			BridgePhase bridgePhase = bridgePhaseFactory.getBridgeRenderPhase(renderRequest, renderResponse,
					portletConfig);
			bridgePhase.execute();
		}
		else {
			throw new BridgeUninitializedException();
		}

	}

	public void doFacesRequest(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws BridgeUninitializedException, BridgeException {

		if (initialized) {
			BridgePhase bridgePhase = bridgePhaseFactory.getBridgeResourcePhase(resourceRequest, resourceResponse,
					portletConfig);
			bridgePhase.execute();
		}
		else {
			throw new BridgeUninitializedException();
		}

	}

	public void init(PortletConfig portletConfig) throws BridgeException {
		logger.trace("init(PortletConfig) this=[{0}]", this);
		this.initialized = true;
		this.portletConfig = portletConfig;
		BridgeFactoryFinder.setPortletConfig(portletConfig);
		bridgePhaseFactory = (BridgePhaseFactory) BridgeFactoryFinder.getFactory(BridgePhaseFactory.class);
	}
}
