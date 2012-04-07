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
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletConfig;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeEventHandler;
import javax.portlet.faces.BridgeException;
import javax.portlet.faces.event.EventNavigationResult;

import com.liferay.faces.bridge.event.IPCPhaseListener;
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

			init(eventRequest, eventResponse, Bridge.PortletPhase.EVENT_PHASE);

			// PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-202
			bridgeRequestScope.setPortletMode(eventRequest.getPortletMode());

			// Execute the JSF lifecycle so that the RESTORE_VIEW phase executes. Section 5.2.5 of the JSR 329 Spec
			// requires that there be a phase listener registered that has the ability to short-circuit the JSF
			// lifecycle. Section 5.3.3 requires that there be a phase listener registered that processes outgoing
			// Public Render Parameters during the ACTION_PHASE and RENDER_PHASE of the portlet lifecycle. Both
			// of these requirements are handled by the IPCPhaseListener.
			PhaseListener ipcPhaseListener = new IPCPhaseListener(bridgeConfig, portletContext, portletName,
					eventRequest, eventResponse);
			facesLifecycle.addPhaseListener(ipcPhaseListener);
			facesLifecycle.execute(facesContext);
			facesLifecycle.removePhaseListener(ipcPhaseListener);

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

			// Spec 6.6 (Namespacing)
			indicateNamespacingToConsumers(facesContext.getViewRoot(), eventResponse);
		}
		catch (Exception e) {
			throw new BridgeException(e);
		}
		finally {

			if (facesContext != null) {
				facesContext.release();
			}

			cleanup(eventRequest);
		}
	}

}
