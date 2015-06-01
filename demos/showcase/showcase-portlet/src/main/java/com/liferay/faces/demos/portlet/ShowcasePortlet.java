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
package com.liferay.faces.demos.portlet;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.faces.Bridge;


/**
 * @author  Neil Griffin
 */
public class ShowcasePortlet extends ActionURLDemoPortlet {

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException, IOException {

		String libraryName = resourceRequest.getParameter("ln");
		String resourceName = resourceRequest.getParameter("javax.faces.resource");

		if ("images".equals(libraryName) && "liferay-faces-logo.png".equals(resourceName)) {

			String resourcePath = "/resources/" + libraryName + "/" + resourceName;
			PortletRequestDispatcher portletRequestDispatcher = this.getPortletConfig().getPortletContext()
				.getRequestDispatcher(resourcePath);

			if (portletRequestDispatcher != null) {
				portletRequestDispatcher.forward(resourceRequest, resourceResponse);
			}
		}
		else {
			super.serveResource(resourceRequest, resourceResponse);
		}
	}

	@Override
	protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		String componentPrefix = renderRequest.getParameter("componentPrefix");
		String componentName = renderRequest.getParameter("componentName");

		if ((componentPrefix != null) && (componentName != null)) {
			String viewId = "/views/component.xhtml";
			renderRequest.setAttribute(Bridge.VIEW_ID, viewId);
		}

		super.doView(renderRequest, renderResponse);
	}
}
