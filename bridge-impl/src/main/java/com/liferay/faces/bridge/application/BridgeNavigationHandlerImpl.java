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

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationCase;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletResponse;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowStateException;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeActionURL;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


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

		NavigationCase navigationCase = getNavigationCase(facesContext, fromAction, outcome);

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		// Ask the wrapped NavigationHandler to perform the navigation.
		wrappedNavigationHandler.handleNavigation(facesContext, fromAction, outcome);

		if (navigationCase != null) {

			// Hack for http://jira.icesoft.org/browse/ICE-7996
			Iterator<FacesMessage> itr = facesContext.getMessages();

			while (itr.hasNext()) {
				FacesMessage facesMessage = itr.next();

				if (facesMessage.getDetail().contains("Unable to find matching navigation case")) {
					logger.warn("Removed bogus FacesMessage caused by http://jira.icesoft.org/browse/ICE-7996");
					itr.remove();
				}
			}

			// If the navigation-case is NOT a redirect, then directly encode the {@link PortletMode} and {@link
			// WindowState} to the response. Don't need to worry about the redirect case here because that's handled in
			// the BridgeContext#redirect(String) method. It would be nice to handle the redirect case here but it needs
			// to stay in BridgeContext#redirect(String) since it's possible for developers to call
			// ExternalContext.redirect(String) directly from their application.
			if (!navigationCase.isRedirect()) {

				String toViewId = navigationCase.getToViewId(facesContext);

				if (toViewId != null) {

					PortletResponse portletResponse = bridgeContext.getPortletResponse();

					if (portletResponse instanceof StateAwareResponse) {

						BridgeActionURL bridgeActionURL = bridgeContext.encodeActionURL(toViewId);

						try {

							BridgeNavigationCase bridgeNavigationCase = new BridgeNavigationCaseImpl(navigationCase);
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
	}

	@Override
	public void handleNavigation(FacesContext facesContext, PortletMode fromPortletMode, PortletMode toPortletMode) {

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

					PartialViewContext partialViewContext = facesContext.getPartialViewContext();
					partialViewContext.setRenderAll(true);
				}
			}
		}
	}

	@Override
	public NavigationCase getNavigationCase(FacesContext facesContext, String fromAction, String outcome) {

		if (wrappedNavigationHandler instanceof ConfigurableNavigationHandler) {
			ConfigurableNavigationHandler wrappedConfigurableNavigationHandler = (ConfigurableNavigationHandler)
				wrappedNavigationHandler;

			return wrappedConfigurableNavigationHandler.getNavigationCase(facesContext, fromAction, outcome);
		}
		else {

			// So as not to reinvent the wheel, we currently rely on the default NavigationHandler provided by
			// Mojarra/MyFaces being an instance of ConfigurableNavigationHandler. If that's not the case for some
			// reason, then throw an exception.
			throw new UnsupportedOperationException(
				"JSF runtime does not provide an instance of ConfigurableNavigationHandler");
		}
	}

	@Override
	public Map<String, Set<NavigationCase>> getNavigationCases() {

		if (wrappedNavigationHandler instanceof ConfigurableNavigationHandler) {
			ConfigurableNavigationHandler wrappedConfigurableNavigationHandler = (ConfigurableNavigationHandler)
				wrappedNavigationHandler;

			return wrappedConfigurableNavigationHandler.getNavigationCases();
		}
		else {

			// So as not to reinvent the wheel, we currently rely on the default NavigationHandler provided by
			// Mojarra/MyFaces being an instance of ConfigurableNavigationHandler. If that's not the case for some
			// reason, then throw an exception.
			throw new UnsupportedOperationException(
				"JSF runtime does not provide an instance of ConfigurableNavigationHandler");
		}
	}

}
