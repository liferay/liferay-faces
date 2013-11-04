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

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeException;

import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgePhaseResourceImpl extends BridgePhaseBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgePhaseResourceImpl.class);

	// Private Data Members
	private ResourceRequest resourceRequest;
	private ResourceResponse resourceResponse;

	public BridgePhaseResourceImpl(ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		PortletConfig portletConfig) {
		super(portletConfig);
		this.resourceRequest = resourceRequest;
		this.resourceResponse = resourceResponse;
	}

	public void execute() throws BridgeDefaultViewNotSpecifiedException, BridgeException {

		logger.debug(Logger.SEPARATOR);
		logger.debug("execute(ResourceRequest, ResourceResponse) portletName=[{0}]", portletName);

		try {

			init(resourceRequest, resourceResponse, Bridge.PortletPhase.RESOURCE_PHASE);

			// If the Faces resource handler indicates that this is a request for an image/javascript/css type of
			// resource, then
			if (isJSF2ResourceRequest(facesContext)) {

				logger.debug("Detected JSF2 resource request");

				// Ask the Faces resource handler to copy the contents of the resource to the response.
				handleJSF2ResourceRequest(facesContext);
			}
			else if ((resourceRequest.getResourceID() != null) &&
					!resourceRequest.getResourceID().equals(BridgeConstants.WSRP)) {

				logger.debug("Detected non-Faces resource");

				String resourceId = resourceRequest.getResourceID();
				PortletRequestDispatcher portletRequestDispatcher = portletContext.getRequestDispatcher(resourceId);
				portletRequestDispatcher.forward(resourceRequest, resourceResponse);
			}

			// Otherwise, must be an Ajax (partial-submit) request. Though technically a postback type of request,
			// Ajax requests also utilize the portlet RESOURCE_PHASE. Therefore treat it like a postback, and
			// execute the entire Faces lifecycle: RESTORE_VIEW, APPLY_REQUEST_VALUES, PROCESS_VALIDATIONS,
			// UPDATE_MODEL, INVOKE_APPLICATION.
			else {

				if (logger.isDebugEnabled()) {

					String facesAjaxParameter = bridgeContext.getRequestParameterMap().get(
							BridgeExt.FACES_AJAX_PARAMETER);

					if (BooleanHelper.isTrueToken(facesAjaxParameter)) {
						logger.debug("Detected Ajax ResourceRequest");
					}
					else {
						logger.debug("Detected Non-Ajax ResourceRequest");
					}
				}

				String viewId = bridgeContext.getFacesViewId();
				logger.debug("Running Faces lifecycle for viewId=[{0}]", viewId);

				// Attach the JSF 2.2 client window to the JSF lifecycle so that Faces Flows can be utilized.
				attachClientWindowToLifecycle(facesContext, facesLifecycle);

				// Execute the JSF lifecycle.
				facesLifecycle.execute(facesContext);

				// Also execute the RENDER_RESPONSE phase of the Faces lifecycle, which will ultimately return a
				// DOM-update back to the jsf.js Javascript code that issued the XmlHttpRequest in the first place.
				facesLifecycle.render(facesContext);

				// If the {@link BridgeConfigConstants#PARAM_BRIDGE_REQUEST_SCOPE_AJAX_ENABLED} feature is enabled, then
				if (bridgeRequestScope != null) {

					// PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-202
					bridgeRequestScope.setPortletMode(resourceRequest.getPortletMode());

					// TCK TestPage071: nonFacesResourceTest
					// TCK TestPage073: scopeAfterRedisplayResourcePPRTest -- Preserve the non-excluded request
					// attributes in the BridgeRequestScope so that they can be restored in subsequent render requests.
					bridgeRequestScope.saveState(facesContext);
					maintainBridgeRequestScope(resourceRequest, resourceResponse,
						BridgeRequestScope.Transport.PORTLET_SESSION_ATTRIBUTE);
				}

				// Spec 6.6 (Namespacing)
				indicateNamespacingToConsumers(facesContext.getViewRoot(), resourceResponse);
			}
		}
		catch (Throwable t) {
			throw new BridgeException(t);
		}
		finally {
			cleanup();
		}

		logger.debug(Logger.SEPARATOR);
	}
}
