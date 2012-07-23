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
package com.liferay.faces.bridge.application;

import java.util.Map;

import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletResponse;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowStateException;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeActionURL;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeNavigationHandlerImpl extends BridgeNavigationHandler {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeNavigationHandlerImpl.class);

	// Private Data Members
	private NavigationHandler wrappedNavigationHandler;

	public BridgeNavigationHandlerImpl(NavigationHandler navigationHandler) {
		this.wrappedNavigationHandler = navigationHandler;
	}

	@Override
	public void handleNavigation(FacesContext facesContext, String fromAction, String outcome) {

		logger.debug("fromAction=[{0}] outcome=[{1}]", fromAction, outcome);

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		UIViewRoot uiViewRoot = facesContext.getViewRoot();
		String fromViewId = uiViewRoot.getViewId();

		// Ask the wrapped NavigationHandler to perform the navigation.
		wrappedNavigationHandler.handleNavigation(facesContext, fromAction, outcome);

		uiViewRoot = facesContext.getViewRoot();

		String toViewId = uiViewRoot.getViewId();

		if (!fromViewId.equals(toViewId)) {

			// If the navigation-case is NOT a redirect, then directly encode the {@link PortletMode} and {@link
			// WindowState} to the response. Don't need to worry about the redirect case here because that's handled in
			// the BridgeContext#redirect(String) method. It would be nice to handle the redirect case here but it needs
			// to stay in BridgeContext#redirect(String) since it's possible for developers to call
			// ExternalContext.redirect(String) directly from their application.
			boolean navigationCaseRedirect = bridgeContext.getBridgeRequestScope().isRedirectOccurred();

			if (!navigationCaseRedirect) {

				PortletResponse portletResponse = bridgeContext.getPortletResponse();

				if (portletResponse instanceof StateAwareResponse) {

					BridgeActionURL bridgeActionURL = bridgeContext.encodeActionURL(toViewId);

					try {

						BridgeNavigationCase bridgeNavigationCase = new BridgeNavigationCaseImpl(toViewId);
						String portletMode = bridgeNavigationCase.getPortletMode();

						if (portletMode != null) {
							bridgeActionURL.setParameter(Bridge.PORTLET_MODE_PARAMETER, portletMode);
						}

						String windowState = bridgeNavigationCase.getWindowState();

						if (windowState != null) {
							bridgeActionURL.setParameter(Bridge.PORTLET_WINDOWSTATE_PARAMETER, windowState);
						}

						bridgeActionURL.applyToResponse((StateAwareResponse) portletResponse);
					}
					catch (PortletModeException e) {
						logger.error(e.getMessage());
					}
					catch (WindowStateException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
	}

	@Override
	public void handleNavigation(FacesContext facesContext, PortletMode fromPortletMode, PortletMode toPortletMode) {

		if ((fromPortletMode != null) && !fromPortletMode.equals(toPortletMode)) {
			logger.debug("fromPortletMode=[{0}] toPortletMode=[{1}]", fromPortletMode, toPortletMode);

			String currentViewId = facesContext.getViewRoot().getViewId();
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			Map<String, String> defaultViewIdMap = bridgeContext.getDefaultViewIdMap();
			String portletModeViewId = defaultViewIdMap.get(toPortletMode.toString());

			if ((currentViewId != null) && (portletModeViewId != null)) {

				if (!currentViewId.equals(portletModeViewId)) {

					logger.debug("Navigating to viewId=[{0}]", portletModeViewId);

					ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
					UIViewRoot viewRoot = viewHandler.createView(facesContext, portletModeViewId);

					if (viewRoot != null) {
						facesContext.setViewRoot(viewRoot);
					}
				}
			}
		}
	}
}
