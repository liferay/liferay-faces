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
package com.liferay.faces.portlet.component.renderurl.internal;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.BaseURL;
import javax.portlet.MimeResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import com.liferay.faces.portlet.component.renderurl.RenderURLBase;


/**
 * @author  Kyle Stiemann
 */
public class AbstractRenderURLRenderer extends RenderURLRendererBase {

	@Override
	protected BaseURL getBaseURL(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		MimeResponse mimeResponse = (MimeResponse) externalContext.getResponse();
		PortletURL renderURL = getPortletURL(mimeResponse, uiComponent);
		RenderURLBase renderURLcomponent = (RenderURLBase) uiComponent;
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();

		if (renderURLcomponent.isCopyCurrentRenderParameters()) {

			Map<String, String[]> currentRenderParameters = portletRequest.getParameterMap();
			String namespace = facesContext.getExternalContext().encodeNamespace("");

			for (Map.Entry<String, String[]> param : currentRenderParameters.entrySet()) {

				String name = param.getKey();

				if (name.contains(namespace)) {
					name = name.substring(namespace.length());
				}

				renderURL.setParameter(name, param.getValue());
			}
		}

		String portletModeString = renderURLcomponent.getPortletMode();
		PortletMode portletMode;

		if (portletModeString != null) {
			portletMode = new PortletMode(portletModeString);
		}
		else {
			portletMode = portletRequest.getPortletMode();
		}

		try {
			renderURL.setPortletMode(portletMode);
		}
		catch (PortletModeException e) {
			throw new IOException(e);
		}

		String windowStateString = renderURLcomponent.getWindowState();
		WindowState windowState;

		if (windowStateString != null) {
			windowState = new WindowState(windowStateString);
		}
		else {
			windowState = portletRequest.getWindowState();
		}

		try {
			renderURL.setWindowState(windowState);
		}
		catch (WindowStateException e) {
			throw new IOException(e);
		}

		return renderURL;
	}

	protected PortletURL getPortletURL(MimeResponse mimeResponse, UIComponent uiComponent) {
		return mimeResponse.createRenderURL();
	}
}
