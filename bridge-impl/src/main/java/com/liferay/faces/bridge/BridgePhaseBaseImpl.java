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

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.webapp.FacesServlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.annotation.PortletNamingContainer;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigFactory;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.BridgeContextFactory;
import com.liferay.faces.bridge.helper.PortletModeHelper;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScope;


/**
 * @author  Neil Griffin
 */
public abstract class BridgePhaseBaseImpl implements BridgePhase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgePhaseBaseImpl.class);

	// Protected Data Members
	protected BridgeConfig bridgeConfig;
	protected BridgeContext bridgeContext = null;
	protected BridgeContextFactory bridgeContextFactory;
	protected BridgeRequestScope bridgeRequestScope = null;
	protected FacesContext facesContext = null;
	protected Lifecycle facesLifecycle;
	protected PortletConfig portletConfig;
	protected PortletContext portletContext;
	protected String portletName;

	// Private Data Members
	private FacesContextFactory facesContextFactory;
	private String facesLifecycleId;

	public BridgePhaseBaseImpl(PortletConfig portletConfig) {
		this.portletConfig = portletConfig;
		this.portletContext = portletConfig.getPortletContext();
		this.portletName = portletConfig.getPortletName();

		BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
				BridgeConfigFactory.class);
		this.bridgeConfig = bridgeConfigFactory.getBridgeConfig();
		this.bridgeContextFactory = (BridgeContextFactory) BridgeFactoryFinder.getFactory(BridgeContextFactory.class);
	}

	protected void cleanup(PortletRequest portletRequest) {

		// Cleanup request attributes.
		portletRequest.removeAttribute(BridgeExt.BRIDGE_CONTEXT_ATTRIBUTE);
		portletRequest.removeAttribute(Bridge.PORTLET_LIFECYCLE_PHASE);
	}

	protected void indicateNamespacingToConsumers(UIViewRoot uiViewRoot, PortletResponse portletResponse) {

		if (uiViewRoot != null) {

			// This method helps satisfy the namespacing requirements of Section 6.6 of the specification. It might be
			// the case that the consumer (portal engine / portlet container) needs to know if all of the form fields
			// have been namespaced properly. If that's the case, then it can check for the existence of the
			// "X-JAVAX-PORTLET-FACES-NAMESPACED-RESPONSE" property on the response, which will be set to "true" if the
			// UIViewRoot is annotated with {@link PortletNamingContainer}.
			if (uiViewRoot.getClass().getAnnotation(PortletNamingContainer.class) != null) {
				portletResponse.addProperty(Bridge.PORTLET_NAMESPACED_RESPONSE_PROPERTY, Boolean.TRUE.toString());
			}
		}
		else {

			// http://issues.liferay.com/browse/FACES-267 Sometimes there are requests that the bridge may see as valid
			// ResourceRequests (e.g. related to Ajax Push) where a ViewRoot might not be available -- this is not an
			// error.
			logger.debug("UIViewRoot is null -- might be push related");
		}
	}

	protected void init(PortletRequest portletRequest, PortletResponse portletResponse,
		Bridge.PortletPhase portletPhase) {

		// Null check required by the TCK.
		if (portletRequest == null) {
			throw new NullPointerException("portletRequest was null");
		}

		// Null check required by the TCK.
		if (portletResponse == null) {
			throw new NullPointerException("portletResponse was null");
		}

		// Save the Bridge.PortletPhase as a request attribute so that it can be picked up by the
		// BridgeRequestAttributeListener.
		portletRequest.setAttribute(Bridge.PORTLET_LIFECYCLE_PHASE, portletPhase);

		// Get the BridgeContext, BridgeRequestScopeManager, and BridgeRequestScope.
		bridgeContext = bridgeContextFactory.getBridgeContext(bridgeConfig, portletConfig, portletContext,
				portletRequest, portletResponse, portletPhase);
		bridgeRequestScope = bridgeContext.getBridgeRequestScope();

		// Save the BridgeContext as a request attribute so that it can be picked up by the
		// ExternalContextImpl during construction, which will happen as a consequence of getting the
		// FacesContext below.
		portletRequest.setAttribute(BridgeExt.BRIDGE_CONTEXT_ATTRIBUTE, bridgeContext);

		// Get the FacesContext.
		facesContext = getFacesContext(portletRequest, portletResponse);

		// Save the BridgeContext as a FacesContext attribute so that it can be picked up elsewhere in the
		// bridge. PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-206
		facesContext.getAttributes().put(BridgeExt.BRIDGE_CONTEXT_ATTRIBUTE, bridgeContext);

		// If not set by a previous request, then set the default viewIdHistory for the portlet modes.
		for (String portletMode : PortletModeHelper.PORTLET_MODE_NAMES) {
			StringBuilder buf = new StringBuilder();
			buf.append(Bridge.VIEWID_HISTORY);
			buf.append(BridgeConstants.CHAR_PERIOD);
			buf.append(portletMode);

			String attributeName = buf.toString();
			PortletSession portletSession = portletRequest.getPortletSession();

			if (portletSession.getAttribute(attributeName) == null) {
				portletSession.setAttribute(attributeName, bridgeContext.getDefaultViewIdMap().get(portletMode));
			}
		}
	}

	protected FacesContext getFacesContext(PortletRequest portletRequest, PortletResponse portletResponse)
		throws FacesException {
		return getFacesContextFactory().getFacesContext(portletContext, portletRequest, portletResponse,
				getFacesLifecycle());
	}

	protected FacesContextFactory getFacesContextFactory() throws FacesException {

		if (facesContextFactory == null) {
			facesContextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
		}

		return facesContextFactory;
	}

	protected Lifecycle getFacesLifecycle() throws FacesException {

		if (facesLifecycle == null) {
			LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(
					FactoryFinder.LIFECYCLE_FACTORY);
			facesLifecycle = lifecycleFactory.getLifecycle(getFacesLifecycleId());
		}

		return facesLifecycle;
	}

	protected String getFacesLifecycleId() {

		if (facesLifecycleId == null) {
			facesLifecycleId = portletContext.getInitParameter(FacesServlet.LIFECYCLE_ID_ATTR);

			if (facesLifecycleId == null) {
				facesLifecycleId = LifecycleFactory.DEFAULT_LIFECYCLE;
			}
		}

		return facesLifecycleId;
	}
}
