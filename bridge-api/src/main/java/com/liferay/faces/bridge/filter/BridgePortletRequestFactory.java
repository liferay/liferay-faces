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
import javax.portlet.ActionRequest;
import javax.portlet.EventRequest;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;


/**
 * @author  Neil Griffin
 */
public abstract class BridgePortletRequestFactory implements FacesWrapper<BridgePortletRequestFactory> {

	public abstract ActionRequest getActionRequest(ActionRequest actionRequest);

	public abstract EventRequest getEventRequest(EventRequest eventRequest);

	public abstract RenderRequest getRenderRequest(RenderRequest renderRequest);

	public abstract ResourceRequest getResourceRequest(ResourceRequest resourceRequest);
}
