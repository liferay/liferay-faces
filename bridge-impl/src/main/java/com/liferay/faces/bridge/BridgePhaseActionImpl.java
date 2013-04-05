/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeException;

import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgePhaseActionImpl extends BridgePhaseCompatImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgePhaseActionImpl.class);

	// Private Data Members
	private ActionRequest actionRequest;
	private ActionResponse actionResponse;

	public BridgePhaseActionImpl(ActionRequest actionRequest, ActionResponse actionResponse,
		PortletConfig portletConfig) {
		super(portletConfig);
		this.actionRequest = actionRequest;
		this.actionResponse = actionResponse;
	}

	public void execute() throws BridgeDefaultViewNotSpecifiedException, BridgeException {

		logger.debug(Logger.SEPARATOR);
		logger.debug("execute(ActionRequest, ActionResponse) portletName=[{0}]", portletName);

		try {

			init(actionRequest, actionResponse, Bridge.PortletPhase.ACTION_PHASE);

			// PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-202
			bridgeRequestScope.setPortletMode(actionRequest.getPortletMode());

			// If the "javax.portlet.faces.PortletMode" request parameter has a value, then the developer probably
			// specified a URL like <h:outputLink value="portlet:render"> using f:param to set the request parameter
			// for switching modes. This is one of the tests in the TCK.
			String portletModeParam = actionRequest.getParameter(Bridge.PORTLET_MODE_PARAMETER);

			if (portletModeParam != null) {

				try {
					actionResponse.setPortletMode(new PortletMode(portletModeParam));
				}
				catch (PortletModeException e) {
					logger.error("Invalid parameter value {0}=[{1}]}", Bridge.PORTLET_MODE_PARAMETER, portletModeParam);
				}
			}

			// Execute all the phases of the JSF lifecycle except for RENDER_RESPONSE since that can only be executed
			// during the RENDER_PHASE of the portlet lifecycle.
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

			// Save the faces view root and any messages in the faces context so that they can be restored during
			// the RENDER_PHASE of the portlet lifecycle.
			bridgeRequestScope.saveState(facesContext);
			maintainBridgeRequestScope(actionRequest, actionResponse, BridgeRequestScope.Transport.RENDER_PARAMETER);

			// Spec 6.6 (Namespacing)
			indicateNamespacingToConsumers(facesContext.getViewRoot(), actionResponse);
		}
		catch (Throwable t) {
			throw new BridgeException(t);
		}
		finally {
			cleanup();
		}
	}
}
