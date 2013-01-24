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

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.GenericFacesPortlet;

import com.liferay.portal.kernel.portlet.LiferayPortletMode;


/**
 * This class provides useful extensions to the {@link GenericFacesPortlet} found in the Bridge API.
 *
 * @author  Neil Griffin
 */
public class GenericLiferayFacesPortlet extends GenericFacesPortlet {

	@Override
	protected void doDispatch(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		PortletMode portletMode = renderRequest.getPortletMode();

		if (portletMode.equals(PortletMode.VIEW) || portletMode.equals(PortletMode.EDIT) ||
				portletMode.equals(PortletMode.HELP)) {
			super.doDispatch(renderRequest, renderResponse);
		}
		else if (portletMode.equals(LiferayPortletMode.ABOUT)) {
			doView(renderRequest, renderResponse);
		}
		else if (portletMode.equals(LiferayPortletMode.CONFIG)) {
			doView(renderRequest, renderResponse);
		}
		else if (portletMode.equals(LiferayPortletMode.EDIT_DEFAULTS)) {
			doView(renderRequest, renderResponse);
		}
		else if (portletMode.equals(LiferayPortletMode.EDIT_GUEST)) {
			doView(renderRequest, renderResponse);
		}
		else if (portletMode.equals(LiferayPortletMode.PREVIEW)) {
			doView(renderRequest, renderResponse);
		}
		else if (portletMode.equals(LiferayPortletMode.PRINT)) {
			doView(renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected void doHeaders(RenderRequest renderRequest, RenderResponse renderResponse) {

		try {

			// Streaming portals like WebSphere (as opposed to buffered portals like Liferay) will set the
			// javax.portlet.render_part request attribute to a value of "RENDER_HEADERS" which will cause
			// javax.portlet.GenericPortlet (the superclass of this class) to call this doHeaders(RenderRequest,
			// RenderResponse) method, but will not in turn call GenericPortlet.doDispatch(RenderRequest,
			// RenderResponse). That also means that that the doView(RenderRequest, RenderResponse) will not be called
			// in this class. So if the attribute is set, we call the Bridge.doFacesRequest(RenderRequest,
			// RenderResponse) method here, so that the Faces lifecycle can be run, and resources added to
			// h:head can be retrieved. Note that it is the responsibility of the bridge to check for this
			// attribute as well, because at this point the bridge should not render any JSF views to the response.
			Object renderPartAttribute = renderRequest.getAttribute(RenderRequest.RENDER_PART);

			if ((renderPartAttribute != null) && renderPartAttribute.equals(RenderRequest.RENDER_HEADERS)) {
				Bridge facesBridge = getFacesBridge(renderRequest, renderResponse);
				facesBridge.doFacesRequest(renderRequest, renderResponse);
			}
		}
		catch (PortletException e) {

			// Unfortunately the signature for GenericPortlet.doHeaders(RenderRequest, RenderResponse) does not throw
			// an exception, so we have no choice but to simply report any exceptions by printing the stacktrace.
			e.printStackTrace();
		}
	}
}
