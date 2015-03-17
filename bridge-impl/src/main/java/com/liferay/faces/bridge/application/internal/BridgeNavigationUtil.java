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
package com.liferay.faces.bridge.application.internal;

import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.context.url.BridgeURL;
import com.liferay.faces.bridge.scope.BridgeRequestScope;


/**
 * @author  Neil Griffin
 */
public class BridgeNavigationUtil {

	/**
	 * Convenience method that applies the parameters found in the specified URL to the specified {@link
	 * StateAwareResponse} by calling methods such as {@link
	 * StateAwareResponse#setPortletMode(javax.portlet.PortletMode)} and {@link
	 * StateAwareResponse#setWindowState(javax.portlet.WindowState)}, etc.
	 */
	public static void navigate(PortletRequest portletRequest, StateAwareResponse stateAwareResponse,
		BridgeRequestScope bridgeRequestScope, BridgeURL bridgeURL) throws PortletModeException, WindowStateException {

		// For each parameter found in the encoded <to-view-id> Action-URL:
		Set<String> urlParameterNames = bridgeURL.getParameterMap().keySet();

		for (String urlParameterName : urlParameterNames) {

			String parameterValue = bridgeURL.getParameter(urlParameterName);

			// If the URL contains the "javax.portlet.faces.PortletMode" parameter, then set the
			// PortletMode on the ActionResponse.
			if (Bridge.PORTLET_MODE_PARAMETER.equals(urlParameterName)) {
				PortletMode portletMode = new PortletMode(parameterValue);

				if (bridgeRequestScope != null) {

					if (!portletRequest.getPortletMode().equals(portletMode) &&
							portletRequest.isPortletModeAllowed(portletMode)) {
						stateAwareResponse.setPortletMode(portletMode);
						bridgeRequestScope.setPortletModeChanged(true);
					}
				}
			}

			// Otherwise, if the URL contains the "javax.portlet.faces.WindowState" parameter, then
			// set the WindowState on the ActionResponse.
			else if (Bridge.PORTLET_WINDOWSTATE_PARAMETER.equals(urlParameterName)) {

				WindowState windowState = new WindowState(parameterValue);

				if (portletRequest.isWindowStateAllowed(windowState)) {
					stateAwareResponse.setWindowState(windowState);
				}
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
