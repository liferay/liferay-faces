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

import javax.faces.application.ResourceHandler;
import javax.faces.event.PhaseListener;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeException;

import com.liferay.faces.bridge.event.IPCPhaseListener;
import com.liferay.faces.bridge.event.ManagedBeanScopePhaseListener;
import com.liferay.faces.bridge.helper.BooleanHelper;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgePhaseResourceImpl extends BridgePhaseBaseImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgePhaseResourceImpl.class);

	// Private Data Members
	ResourceRequest resourceRequest;
	ResourceResponse resourceResponse;

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
			ResourceHandler resourceHandler = facesContext.getApplication().getResourceHandler();

			if (resourceHandler.isResourceRequest(facesContext)) {
				logger.debug("Detected JSF2 resource request");

				// Ask the Faces resource handler to copy the contents of the resource to the response.
				resourceHandler.handleResourceRequest(facesContext);
			}
			else if (resourceRequest.getResourceID() != null) {

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

					if (BooleanHelper.isTrueToken(resourceRequest.getParameter(BridgeExt.FACES_AJAX_PARAMETER))) {
						logger.debug("Detected Ajax ResourceRequest");
					}
					else {
						logger.debug("Detected Non-Ajax ResourceRequest");
					}
				}

				String viewId = bridgeContext.getFacesViewId();
				logger.debug("Running Faces lifecycle for viewId=[{0}]", viewId);

				// Section 5.2.4 of the JSR 329 Spec requires that a phase listener be registered in order to
				// handle Portlet 2.0 Public Render Parameters after the RESTORE_VIEW phase of the JSF lifecycle
				// executes. The IPCPhaseListener satisfies this requirement.
				PhaseListener ipcPhaseListener = new IPCPhaseListener(bridgeConfig, portletContext, portletName,
						resourceRequest, resourceResponse);
				facesLifecycle.addPhaseListener(ipcPhaseListener);
				facesLifecycle.execute(facesContext);
				facesLifecycle.removePhaseListener(ipcPhaseListener);

				// Also execute the RENDER_RESPONSE phase of the Faces lifecycle, which will ultimately return a
				// DOM-update back to the jsf.js Javascript code that issued the XmlHttpRequest in the first place.
				// Note that we need to add the ManagedBeanScopePhaseListener so that after the RENDER_RESPONSE
				// phase, the managed-beans in request scope will go out-of-scope which will in turn cause any
				// annotated PreDestroy methods to be called. Note that there is no concept of "bridgeRequestScope"
				// in a ResourceRequest, so we refer to it as plain-old "request" scope.
				PhaseListener managedBeanScopePhaseListener = new ManagedBeanScopePhaseListener();
				facesLifecycle.addPhaseListener(managedBeanScopePhaseListener);
				facesLifecycle.render(facesContext);
				facesLifecycle.removePhaseListener(managedBeanScopePhaseListener);

				// Spec 6.6 (Namespacing)
				indicateNamespacingToConsumers(facesContext.getViewRoot(), resourceResponse);
			}

			cleanup(resourceRequest);
		}
		catch (Exception e) {
			throw new BridgeException(e);
		}
		finally {

			if (facesContext != null) {
				facesContext.release();
			}
		}

		logger.debug(Logger.SEPARATOR);
	}

}
