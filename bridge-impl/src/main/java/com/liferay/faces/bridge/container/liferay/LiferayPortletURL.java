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
package com.liferay.faces.bridge.container.liferay;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;


/**
 * @author  Neil Griffin
 */
public abstract class LiferayPortletURL extends LiferayBaseURL implements PortletURL {

	// Private Data Members
	private PortletMode portletMode;
	private String toStringValue;
	private WindowState windowState;

	public LiferayPortletURL(ParsedPortletURL parsedPortletURL, String responseNamespace) {
		super(parsedPortletURL, responseNamespace);
		portletMode = parsedPortletURL.getPortletMode();
		windowState = parsedPortletURL.getWindowState();
	}

	public void removePublicRenderParameter(String name) {
		// Ignore
	}

	@Override
	public void resetToString() {
		super.resetToString();
		toStringValue = null;
	}

	@Override
	public String toString() {

		if (toStringValue == null) {
			StringBuilder url = new StringBuilder(super.toString());

			// Always add the p_p_mode parameter.
			boolean firstParameter = false;
			String parameterValue = null;

			FacesContext facesContext = FacesContext.getCurrentInstance();
			Map<String, Object> applicationMap = facesContext.getExternalContext().getApplicationMap();
			if (getPortletMode() == null) {
				parameterValue = (String) applicationMap.get(getResponseNamespace() + LiferayConstants.P_P_MODE);
			}
			else {
				parameterValue = getPortletMode().toString();
			}

			appendParameterToURL(firstParameter, LiferayConstants.P_P_MODE, parameterValue, url);

			// Always add the p_p_state parameter.
			if (getWindowState() == null) {
				parameterValue = (String) applicationMap.get(getResponseNamespace() + LiferayConstants.P_P_STATE);
			}
			else {
				parameterValue = getWindowState().toString();
			}

			appendParameterToURL(firstParameter, LiferayConstants.P_P_STATE, parameterValue, url);

			toStringValue = url.toString();
		}

		return toStringValue;
	}

	public PortletMode getPortletMode() {
		return portletMode;
	}

	public void setPortletMode(PortletMode portletMode) throws PortletModeException {
		this.portletMode = portletMode;
		resetToString();
	}

	public WindowState getWindowState() {
		return windowState;
	}

	public void setWindowState(WindowState windowState) throws WindowStateException {
		this.windowState = windowState;
		resetToString();
	}

}
