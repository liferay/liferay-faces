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

import javax.faces.application.NavigationHandler;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeEventHandler;
import javax.portlet.faces.BridgeException;
import javax.portlet.faces.event.EventNavigationResult;

import com.liferay.faces.bridge.event.IPCPhaseListener;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgePhaseEventImpl extends BridgePhaseCompatImpl {

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

			// If there is a bridgeEventHandler registered in portlet.xml, then
			String bridgeEventHandlerAttributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletName + "." +
				Bridge.BRIDGE_EVENT_HANDLER;
			BridgeEventHandler bridgeEventHandler = (BridgeEventHandler) portletContext.getAttribute(
					bridgeEventHandlerAttributeName);

			init(eventRequest, eventResponse, Bridge.PortletPhase.EVENT_PHASE);

			if (bridgeEventHandler != null) {

				// Restore the BridgeRequestScope that may have started during the ACTION_PHASE.
				bridgeRequestScope.restoreState(facesContext);

				// PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-202
				bridgeRequestScope.setPortletMode(eventRequest.getPortletMode());

				// Execute the JSF lifecycle so that ONLY the RESTORE_VIEW phase executes (note that this this is
				// accomplished by the IPCPhaseListener).
				facesLifecycle.execute(facesContext);

				// If there were any "handled" exceptions queued, then throw a BridgeException.
				Throwable handledException = getJSF2HandledException(facesContext);

				if (handledException != null) {
					throw new BridgeException(handledException);
				}

				// Otherwise, if there were any "unhandled" exceptions queued, then throw a BridgeException.
				Throwable unhandledException = getJSF2UnhandledException(facesContext);

				if (unhandledException != null) {
					throw new BridgeException(unhandledException);
				}

				// Set a flag on the bridge request scope indicating that the Faces Lifecycle has executed.
				bridgeRequestScope.setFacesLifecycleExecuted(true);

				// If there is a bridgeEventHandler registered in portlet.xml, then invoke the handler so that it
				// can process the event payload.

				logger.debug("Invoking {0} for class=[{1}]", bridgeEventHandlerAttributeName,
					bridgeEventHandler.getClass());

				EventNavigationResult eventNavigationResult = bridgeEventHandler.handleEvent(facesContext,
						eventRequest.getEvent());

				if (eventNavigationResult != null) {
					String oldViewId = facesContext.getViewRoot().getViewId();
					String fromAction = eventNavigationResult.getFromAction();
					String outcome = eventNavigationResult.getOutcome();
					logger.debug("Invoking navigationHandler fromAction=[{0}] outcome=[{1}]", fromAction, outcome);

					NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
					navigationHandler.handleNavigation(facesContext, fromAction, outcome);

					String newViewId = facesContext.getViewRoot().getViewId();
					bridgeRequestScope.setNavigationOccurred(!oldViewId.equals(newViewId));
				}

				// Save the faces view root and any messages in the faces context so that they can be restored during
				// the RENDER_PHASE of the portlet lifecycle.
				bridgeRequestScope.saveState(facesContext);
				maintainBridgeRequestScope(eventRequest, eventResponse);

				// Process the outgoing public render parameters.
				// TCK TestPage064: eventControllerTest
				processOutgoingPublicRenderParameters(facesLifecycle);
			}

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

	protected void processOutgoingPublicRenderParameters(Lifecycle lifecycle) {

		PhaseListener[] phaseListeners = lifecycle.getPhaseListeners();

		for (PhaseListener phaseListener : phaseListeners) {

			if (phaseListener instanceof IPCPhaseListener) {
				IPCPhaseListener ipcPhaseListener = (IPCPhaseListener) phaseListener;
				ipcPhaseListener.processOutgoingPublicRenderParameters(bridgeContext, facesContext);

				break;
			}
		}
	}
}
