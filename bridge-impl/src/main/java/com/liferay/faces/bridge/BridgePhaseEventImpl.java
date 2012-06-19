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
package com.liferay.faces.bridge;

import javax.faces.FactoryFinder;
import javax.faces.application.NavigationHandler;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeEventHandler;
import javax.portlet.faces.BridgeException;
import javax.portlet.faces.event.EventNavigationResult;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgePhaseEventImpl extends BridgePhaseBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgePhaseEventImpl.class);

	// Private Data Members
	private EventRequest eventRequest;
	private EventResponse eventResponse;

	public BridgePhaseEventImpl(EventRequest eventRequest, EventResponse eventResponse, PortletConfig portletConfig) {
		super(portletConfig);
		this.eventRequest = eventRequest;
		this.eventResponse = eventResponse;
	}

	public void execute() throws BridgeDefaultViewNotSpecifiedException, BridgeException {

		logger.debug(Logger.SEPARATOR);
		logger.debug("execute(EventRequest, EventResponse) portletName=[{0}]", portletName);

		try {

			// Get the JSF lifecycle instance that is designed to be used with the EVENT_PHASE of the portlet
			// lifecycle.
			LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(
					FactoryFinder.LIFECYCLE_FACTORY);
			Lifecycle eventPhaseFacesLifecycle = lifecycleFactory.getLifecycle(Bridge.PortletPhase.EVENT_PHASE.name());

			init(eventRequest, eventResponse, Bridge.PortletPhase.EVENT_PHASE, eventPhaseFacesLifecycle);

			// PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-202
			bridgeRequestScope.setPortletMode(eventRequest.getPortletMode());

			// Execute the JSF lifecycle so that ONLY the RESTORE_VIEW phase executes (this is a feature of the
			// lifecycle implementation for the EVENT_PHASE of the portlet lifecycle).
			eventPhaseFacesLifecycle.execute(facesContext);

			// If there is a bridgeEventHandler registered in portlet.xml, then invoke the handler so that it
			// can process the event payload.
			String bridgeEventHandlerAttributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletName + "." +
				Bridge.BRIDGE_EVENT_HANDLER;
			BridgeEventHandler bridgeEventHandler = (BridgeEventHandler) portletContext.getAttribute(
					bridgeEventHandlerAttributeName);

			if (bridgeEventHandler != null) {
				logger.debug("Invoking {0} for class=[{1}]", bridgeEventHandlerAttributeName,
					bridgeEventHandler.getClass());

				EventNavigationResult eventNavigationResult = bridgeEventHandler.handleEvent(facesContext,
						eventRequest.getEvent());

				if (eventNavigationResult != null) {
					String fromAction = eventNavigationResult.getFromAction();
					String outcome = eventNavigationResult.getOutcome();
					logger.debug("Invoking navigationHandler fromAction=[{0}] outcome=[{1}]", fromAction, outcome);

					NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
					navigationHandler.handleNavigation(facesContext, fromAction, outcome);
				}
			}

			// Save the faces view root and any messages in the faces context so that they can be restored during
			// the RENDER_PHASE of the portlet lifecycle.
			bridgeRequestScope.preserveScopedData(facesContext);

			// PROPOSED-FOR-JSR344-API
			// http://java.net/jira/browse/JAVASERVERFACES_SPEC_PUBLIC-1070
			// PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-201
			bridgeRequestScope.setFlash(facesContext.getExternalContext().getFlash());

			// Maintain the render parameters set in the ACTION_PHASE so that they carry over to the RENDER_PHASE.
			bridgeContext.getPortletContainer().maintainRenderParameters(eventRequest, eventResponse);

			// Spec 6.6 (Namespacing)
			indicateNamespacingToConsumers(facesContext.getViewRoot(), eventResponse);
		}
		catch (Throwable t) {
			throw new BridgeException(t);
		}
		finally {
			cleanup();
		}
	}

}
