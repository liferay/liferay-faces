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
package com.liferay.faces.bridge.filter.internal;

import javax.portlet.ActionResponse;
import javax.portlet.EventResponse;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceResponse;

import com.liferay.faces.bridge.filter.BridgePortletResponseFactory;


/**
 * @author  Neil Griffin
 */
public class BridgePortletResponseFactoryImpl extends BridgePortletResponseFactory {

	@Override
	public ActionResponse getActionResponse(ActionResponse actionResponse) {

		if (PortletContainerDetector.isLiferayPortletResponse(actionResponse)) {
			return new ActionResponseBridgeLiferayImpl(actionResponse);
		}
		else {
			return new ActionResponseBridgeImpl(actionResponse);
		}
	}

	@Override
	public EventResponse getEventResponse(EventResponse eventResponse) {

		if (PortletContainerDetector.isLiferayPortletResponse(eventResponse)) {
			return new EventResponseBridgeLiferayImpl(eventResponse);
		}
		else {
			return new EventResponseBridgeImpl(eventResponse);
		}
	}

	@Override
	public RenderResponse getRenderResponse(RenderResponse renderResponse) {

		if (PortletContainerDetector.isLiferayPortletResponse(renderResponse)) {
			return new RenderResponseBridgeLiferayImpl(renderResponse);
		}
		else if (PortletContainerDetector.isPlutoPortletResponse(renderResponse)) {
			return new RenderResponseBridgePlutoImpl(renderResponse);
		}
		else {
			return new RenderResponseBridgeImpl(renderResponse);
		}
	}

	@Override
	public ResourceResponse getResourceResponse(ResourceResponse resourceResponse) {

		if (PortletContainerDetector.isLiferayPortletResponse(resourceResponse)) {
			return new ResourceResponseBridgeLiferayImpl(resourceResponse);
		}
		else if (PortletContainerDetector.isPlutoPortletResponse(resourceResponse)) {
			return new ResourceResponseBridgePlutoImpl(resourceResponse);
		}
		else {
			return new ResourceResponseBridgeImpl(resourceResponse);
		}
	}

	@Override
	public BridgePortletResponseFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}
}
