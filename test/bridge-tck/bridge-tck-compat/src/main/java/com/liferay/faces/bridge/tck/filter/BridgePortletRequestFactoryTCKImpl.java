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
package com.liferay.faces.bridge.tck.filter;

import javax.portlet.ActionRequest;
import javax.portlet.EventRequest;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.filter.PortletRequestWrapper;

import com.liferay.faces.bridge.filter.BridgePortletRequestFactory;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class BridgePortletRequestFactoryTCKImpl extends BridgePortletRequestFactory {

	// Private Constants
	private static final boolean RESIN_DETECTED = ProductMap.getInstance().get(ProductConstants.RESIN).isDetected();

	// Private Data Members
	private BridgePortletRequestFactory wrappedBridgePortletRequestFactory;

	public BridgePortletRequestFactoryTCKImpl(BridgePortletRequestFactory bridgePortletRequestFactory) {
		this.wrappedBridgePortletRequestFactory = bridgePortletRequestFactory;
	}

	@Override
	public ActionRequest getActionRequest(ActionRequest actionRequest) {
		return getWrapped().getActionRequest(actionRequest);
	}

	@Override
	public EventRequest getEventRequest(EventRequest eventRequest) {
		return getWrapped().getEventRequest(eventRequest);
	}

	@Override
	public RenderRequest getRenderRequest(RenderRequest renderRequest) {

		renderRequest = getWrapped().getRenderRequest(renderRequest);

		if (RESIN_DETECTED) {

			// Workaround for FACES-1629
			renderRequest = new RenderRequestResinImpl(renderRequest);
		}

		return renderRequest;
	}

	@Override
	public ResourceRequest getResourceRequest(ResourceRequest resourceRequest) {

		if (isPlutoPortletRequest(resourceRequest)) {
			resourceRequest = getWrapped().getResourceRequest(resourceRequest);

			return new ResourceRequestPlutoTCKImpl(resourceRequest);
		}
		else {
			resourceRequest = getWrapped().getResourceRequest(resourceRequest);
		}

		return resourceRequest;
	}

	/**
	 * Determines whether or not the specified {@link javax.portlet.PortletRequest} is one created by Liferay Portal. If
	 * the specified {@link javax.portlet.PortletRequest} is an instance of {@link
	 * javax.portlet.filter.PortletRequestWrapper} then it will work with the wrapped {@link
	 * javax.portlet.PortletRequest}.
	 *
	 * @param   portletRequest  The current {@link javax.portlet.PortletRequest}.
	 *
	 * @return  true if the specified portletRequest was created by Pluto.
	 */
	protected boolean isPlutoPortletRequest(PortletRequest portletRequest) {

		if (portletRequest != null) {

			while (portletRequest instanceof PortletRequestWrapper) {
				PortletRequestWrapper portletRequestWrapper = (PortletRequestWrapper) portletRequest;
				portletRequest = portletRequestWrapper.getRequest();
			}

			return portletRequest.getClass().getName().startsWith("org.apache.pluto");
		}
		else {
			return false;
		}
	}

	@Override
	public BridgePortletRequestFactory getWrapped() {
		return wrappedBridgePortletRequestFactory;
	}
}
