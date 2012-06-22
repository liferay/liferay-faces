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
package com.liferay.faces.bridge.context.url;

import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.scope.BridgeRequestScope;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeResponseURLImpl extends BridgeURLBaseImpl implements BridgeResponseURL {

	public BridgeResponseURLImpl(String url, String currentFacesViewId, BridgeContext bridgeContext) {
		super(url, currentFacesViewId, bridgeContext);
	}

	public void applyToResponse(StateAwareResponse stateAwareResponse) throws PortletModeException,
		WindowStateException {

		// For each parameter found in the encoded <to-view-id> Action-URL:
		Set<String> urlParameterNames = getParameterNames();

		for (String urlParameterName : urlParameterNames) {

			String parameterValue = getParameter(urlParameterName);

			// If the URL contains the "javax.portlet.faces.PortletMode" parameter, then set the
			// PortletMode on the ActionResponse.
			if (Bridge.PORTLET_MODE_PARAMETER.equals(urlParameterName)) {
				PortletMode portletMode = new PortletMode(parameterValue);
				BridgeRequestScope bridgeRequestScope = bridgeContext.getBridgeRequestScope();
				if (bridgeRequestScope != null) {
					PortletRequest portletRequest = bridgeContext.getPortletRequest();
					if (!portletRequest.getPortletMode().equals(portletMode)) {
						stateAwareResponse.setPortletMode(portletMode);
						bridgeRequestScope.setPortletModeChanged(true);
					}
				}
			}

			// Otherwise, if the URL contains the "javax.portlet.faces.WindowState" parameter, then
			// set the WindowState on the ActionResponse.
			else if (Bridge.PORTLET_WINDOWSTATE_PARAMETER.equals(urlParameterName)) {
				stateAwareResponse.setWindowState(new WindowState(parameterValue));
			}

			// Otherwise, if the URL contains the "_jsfBridgeNonFacesView" parameter, then set a
			// render parameter so that the Non-Faces-View will get picked up by the GenericFacesPortlet for
			// dispatch.
			else if (Bridge.NONFACES_TARGET_PATH_PARAMETER.equals(urlParameterName)) {
				stateAwareResponse.setRenderParameter(Bridge.NONFACES_TARGET_PATH_PARAMETER, parameterValue);
			}

			// Otherwise, it's not a special parameter recognized by the bridge. Regardless, set a render
			// parameter so that it can be picked up by the RENDER_RESPONSE phase if necessary.
			else {
				stateAwareResponse.setRenderParameter(urlParameterName, parameterValue);
			}
		}
	}
}
