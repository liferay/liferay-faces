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
package com.liferay.faces.bridge.filter.internal;

import javax.portlet.ActionRequest;
import javax.portlet.EventRequest;
import javax.portlet.PortalContext;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;

import com.liferay.faces.bridge.context.BridgePortalContext;
import com.liferay.faces.bridge.context.internal.BridgePortalContextImpl;
import com.liferay.faces.bridge.context.internal.BridgePortalContextLiferayImpl;
import com.liferay.faces.bridge.filter.BridgePortletRequestFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class BridgePortletRequestFactoryImpl extends BridgePortletRequestFactory {

	// Private Constants
	private static final Product LIFERAY_PORTAL = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL);
	private static final boolean LIFERAY_PORTAL_DETECTED = LIFERAY_PORTAL.isDetected();

	@Override
	public ActionRequest getActionRequest(ActionRequest actionRequest) {

		PortalContext portalContext = actionRequest.getPortalContext();
		BridgePortalContext bridgePortalContext;

		if (LIFERAY_PORTAL_DETECTED) {
			bridgePortalContext = new BridgePortalContextLiferayImpl(portalContext, actionRequest, LIFERAY_PORTAL);
		}
		else {
			bridgePortalContext = new BridgePortalContextImpl(portalContext);
		}

		return new ActionRequestBridgeImpl(actionRequest, bridgePortalContext);
	}

	@Override
	public EventRequest getEventRequest(EventRequest eventRequest) {

		PortalContext portalContext = eventRequest.getPortalContext();
		BridgePortalContext bridgePortalContext;

		if (LIFERAY_PORTAL_DETECTED) {
			bridgePortalContext = new BridgePortalContextLiferayImpl(portalContext, eventRequest, LIFERAY_PORTAL);
		}
		else {
			bridgePortalContext = new BridgePortalContextImpl(portalContext);
		}

		return new EventRequestBridgeImpl(eventRequest, bridgePortalContext);
	}

	@Override
	public RenderRequest getRenderRequest(RenderRequest renderRequest) {

		PortalContext portalContext = renderRequest.getPortalContext();
		BridgePortalContext bridgePortalContext;

		if (LIFERAY_PORTAL_DETECTED) {
			bridgePortalContext = new BridgePortalContextLiferayImpl(portalContext, renderRequest, LIFERAY_PORTAL);
		}
		else {
			bridgePortalContext = new BridgePortalContextImpl(portalContext);
		}

		return new RenderRequestBridgeImpl(renderRequest, bridgePortalContext);
	}

	@Override
	public ResourceRequest getResourceRequest(ResourceRequest resourceRequest) {

		PortalContext portalContext = resourceRequest.getPortalContext();
		BridgePortalContext bridgePortalContext;

		if (LIFERAY_PORTAL_DETECTED) {
			bridgePortalContext = new BridgePortalContextLiferayImpl(portalContext, resourceRequest, LIFERAY_PORTAL);
		}
		else {
			bridgePortalContext = new BridgePortalContextImpl(portalContext);
		}

		return new ResourceRequestBridgeImpl(resourceRequest, bridgePortalContext);
	}

	@Override
	public BridgePortletRequestFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}
}
