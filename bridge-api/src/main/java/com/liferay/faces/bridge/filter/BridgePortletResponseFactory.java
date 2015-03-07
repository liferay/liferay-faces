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
package com.liferay.faces.bridge.filter;

import javax.faces.FacesWrapper;
import javax.portlet.ActionResponse;
import javax.portlet.EventResponse;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceResponse;


/**
 * @author  Neil Griffin
 */
public abstract class BridgePortletResponseFactory implements FacesWrapper<BridgePortletResponseFactory> {

	public abstract ActionResponse getActionResponse(ActionResponse actionResponse);

	public abstract EventResponse getEventResponse(EventResponse eventResponse);

	public abstract RenderResponse getRenderResponse(RenderResponse renderResponse);

	public abstract ResourceResponse getResourceResponse(ResourceResponse resourceResponse);
}
