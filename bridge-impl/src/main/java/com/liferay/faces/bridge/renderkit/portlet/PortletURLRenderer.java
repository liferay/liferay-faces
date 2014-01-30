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
package com.liferay.faces.bridge.renderkit.portlet;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import com.liferay.faces.bridge.component.PortletActionURL;
import com.liferay.faces.bridge.component.PortletRenderURL;


/**
 * This is the abstract base renderer for the rendering that is common to the {@link PortletActionURL} and {@link
 * PortletRenderURL} components.
 *
 * @author  Neil Griffin
 */
public abstract class PortletURLRenderer extends BaseURLRenderer {

	protected void encodeEnd(FacesContext facesContext, UIComponent uiComponent, PortletURL portletURL)
		throws IOException {

		Map<String, Object> attributes = uiComponent.getAttributes();
		String windowState = (String) attributes.get("windowState");
		String portletMode = (String) attributes.get("portletMode");
		Boolean copyCurrentRenderParameters = (Boolean) attributes.get("copyCurrentRenderParameters");

		try {

			if (windowState != null) {
				portletURL.setWindowState(new WindowState(windowState));
			}

			if (portletMode != null) {
				portletURL.setPortletMode(new PortletMode(portletMode));
			}

			if ((copyCurrentRenderParameters != null) && copyCurrentRenderParameters) {
				PortletRequest portletRequest = (PortletRequest) facesContext.getExternalContext().getRequest();
				Map<String, String[]> currentRenderParametersMap = portletRequest.getPublicParameterMap();
				portletURL.setParameters(currentRenderParametersMap);
			}

			super.encodeEnd(facesContext, uiComponent, portletURL);
		}
		catch (Exception e) {
			throw new IOException();
		}
	}
}
