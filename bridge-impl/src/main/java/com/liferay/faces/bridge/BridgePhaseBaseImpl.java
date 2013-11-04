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

import javax.el.ELContext;
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
import javax.portlet.StateAwareResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.annotation.PortletNamingContainer;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.bridge.config.BridgeConfigFactory;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.container.PortletContainerFactory;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.BridgeContextFactory;
import com.liferay.faces.bridge.context.IncongruityContext;
import com.liferay.faces.bridge.context.IncongruityContextFactory;
import com.liferay.faces.bridge.helper.PortletModeHelper;
import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.bridge.scope.BridgeRequestScopeCache;
import com.liferay.faces.bridge.scope.BridgeRequestScopeCacheFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScopeFactory;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public abstract class BridgePhaseBaseImpl extends BridgePhaseCompat_2_2_Impl implements BridgePhase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgePhaseBaseImpl.class);

	// Private Constants
	private static final String PARAM_BRIDGE_REQUEST_SCOPE_ID = "com.liferay.faces.bridge.bridgeRequestScopeId";

	// Protected Data Members
	protected BridgeConfig bridgeConfig;
	protected BridgeContext bridgeContext;
	protected BridgeRequestScope bridgeRequestScope;
	protected BridgeRequestScopeCache bridgeRequestScopeCache;
	protected FacesContext facesContext;
	protected IncongruityContext incongruityContext;
	protected Lifecycle facesLifecycle;
	protected PortletConfig portletConfig;
	protected PortletContext portletContext;
	protected String portletName;

	// Private Data Members
	private FacesContextFactory facesContextFactory;
	private String pathInfo;
	private String servletPath;

	public BridgePhaseBaseImpl(PortletConfig portletConfig) {
		this.portletConfig = portletConfig;
		this.portletContext = portletConfig.getPortletContext();
		this.portletName = portletConfig.getPortletName();

		BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
				BridgeConfigFactory.class);
		this.bridgeConfig = bridgeConfigFactory.getBridgeConfig();

		// Initialize the incongruity context implementation.
		IncongruityContextFactory incongruityContextFactory = (IncongruityContextFactory) BridgeFactoryFinder
			.getFactory(IncongruityContextFactory.class);
		this.incongruityContext = incongruityContextFactory.getIncongruityContext();

		// Get the bridge request scope cache from the factory.
		BridgeRequestScopeCacheFactory bridgeRequestScopeCacheFactory = (BridgeRequestScopeCacheFactory)
			BridgeFactoryFinder.getFactory(BridgeRequestScopeCacheFactory.class);
		this.bridgeRequestScopeCache = bridgeRequestScopeCacheFactory.getBridgeRequestScopeCache(portletContext);

		// Get the default lifecycle instance from the factory.
		LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(
				FactoryFinder.LIFECYCLE_FACTORY);
		String lifecycleId = this.portletContext.getInitParameter(FacesServlet.LIFECYCLE_ID_ATTR);

		if (lifecycleId == null) {
			lifecycleId = LifecycleFactory.DEFAULT_LIFECYCLE;
		}

		this.facesLifecycle = lifecycleFactory.getLifecycle(lifecycleId);
	}

	@SuppressWarnings("deprecation")
	protected void cleanup() {

		if (facesContext != null) {
			facesContext.release();
		}

		if (bridgeContext != null) {

			// Cleanup request attributes.
			PortletRequest portletRequest = bridgeContext.getPortletRequest();

			if (portletRequest != null) {
				portletRequest.removeAttribute(BridgeExt.BRIDGE_CONTEXT_ATTRIBUTE);
				portletRequest.removeAttribute(Bridge.PORTLET_LIFECYCLE_PHASE);

				// Restore the cached attributes.
				portletRequest.setAttribute(BridgeConstants.REQ_ATTR_PATH_INFO, pathInfo);
				portletRequest.setAttribute(BridgeConstants.REQ_ATTR_SERVLET_PATH, servletPath);
			}

			bridgeContext.release();
		}

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

	@SuppressWarnings("deprecation")
	protected void init(PortletRequest portletRequest, PortletResponse portletResponse,
		Bridge.PortletPhase portletPhase) {

		// Save the Bridge.PortletPhase as a request attribute so that it can be picked up by the
		// BridgeRequestAttributeListener.
		portletRequest.setAttribute(Bridge.PORTLET_LIFECYCLE_PHASE, portletPhase);

		// Initialize the portlet container implementation.
		PortletContainerFactory portletContainerFactory = (PortletContainerFactory) BridgeFactoryFinder.getFactory(
				PortletContainerFactory.class);
		PortletContainer portletContainer = portletContainerFactory.getPortletContainer(portletRequest, portletResponse,
				portletContext, bridgeConfig);

		// Initialize the bridge request scope.
		initBridgeRequestScope(portletRequest, portletResponse, portletPhase, portletContainer, incongruityContext);

		// Get the bridge context.
		BridgeContextFactory bridgeContextFactory = (BridgeContextFactory) BridgeFactoryFinder.getFactory(
				BridgeContextFactory.class);
		bridgeContext = bridgeContextFactory.getBridgeContext(bridgeConfig, bridgeRequestScope, portletConfig,
				portletContext, portletRequest, portletResponse, portletPhase, portletContainer, incongruityContext);

		// Save the BridgeContext as a request attribute for legacy versions of ICEfaces.
		portletRequest.setAttribute(BridgeExt.BRIDGE_CONTEXT_ATTRIBUTE, bridgeContext);

		// Get the FacesContext.
		facesContext = getFacesContext(portletRequest, portletResponse, facesLifecycle);

		// Some portlet containers (like the one provided by Liferay Portal) uses a servlet dispatcher when executing
		// the portlet lifecycle. This approach requires the portal to save some standard Servlet-API request attributes
		// like javax.servlet.include.path_info and javax.servlet.include.servlet_path. Unfortunately, some JSF
		// implementations (like Mojarra) assume a servlet (non-portlet) environment and check for attributes. In order
		// to prevent the JSF implementation from working with bad values, the attributes must be removed before the
		// Faces lifecycle is run, and then restored afterwards.
		pathInfo = (String) portletRequest.getAttribute(BridgeConstants.REQ_ATTR_PATH_INFO);
		portletRequest.removeAttribute(BridgeConstants.REQ_ATTR_PATH_INFO);
		servletPath = (String) portletRequest.getAttribute(BridgeConstants.REQ_ATTR_SERVLET_PATH);
		portletRequest.removeAttribute(BridgeConstants.REQ_ATTR_SERVLET_PATH);

		// If not set by a previous request, then set the default viewIdHistory for the portlet modes.
		for (String portletMode : PortletModeHelper.PORTLET_MODE_NAMES) {
			StringBuilder buf = new StringBuilder();
			buf.append(Bridge.VIEWID_HISTORY);
			buf.append(StringPool.PERIOD);
			buf.append(portletMode);

			String attributeName = buf.toString();
			PortletSession portletSession = portletRequest.getPortletSession();

			if (portletSession.getAttribute(attributeName) == null) {
				portletSession.setAttribute(attributeName, bridgeContext.getDefaultViewIdMap().get(portletMode));
			}
		}
	}

	protected void initBridgeRequestScope(PortletRequest portletRequest, PortletResponse portletResponse,
		Bridge.PortletPhase portletPhase, PortletContainer portletContainer, IncongruityContext incongruityContext) {

		boolean bridgeRequestScopeEnabled = true;

		if (portletPhase == Bridge.PortletPhase.RESOURCE_PHASE) {
			bridgeRequestScopeEnabled = BooleanHelper.toBoolean(getInitParameter(
						BridgeConfigConstants.PARAM_BRIDGE_REQUEST_SCOPE_AJAX_ENABLED), false);
		}

		if (bridgeRequestScopeEnabled) {

			// Determine if there is a bridge request scope "id" saved as a render parameter. Note that in order to
			// avoid collisions with bridge request scopes for other portlets, the render parameter name has to be
			// namespaced with the portlet name.
			String portletName = portletConfig.getPortletName();
			String bridgeRequestScopeKey = portletName + PARAM_BRIDGE_REQUEST_SCOPE_ID;

			// If there is a render parameter value found for the "id", then return the cached bridge request scope
			// associated with the "id".
			String bridgeRequestScopeId = (String) portletRequest.getParameter(bridgeRequestScopeKey);

			if (bridgeRequestScopeId != null) {

				bridgeRequestScope = bridgeRequestScopeCache.get(bridgeRequestScopeId);

				if (bridgeRequestScope != null) {
					logger.debug("Found render parameter name=[{0}] value=[{1}] and cached bridgeRequestScope=[{2}]",
						bridgeRequestScopeKey, bridgeRequestScopeId, bridgeRequestScope);
				}
				else {
					logger.error(
						"Found render parameter name=[{0}] value=[{1}] BUT bridgeRequestScope is NOT in the cache",
						bridgeRequestScopeKey, bridgeRequestScopeId);
				}
			}

			// Otherwise, if there is a portlet session attribute found for the "id", then return the cached bridge
			// request scope associated with the "id". Note: This occurs after an Ajax-based ResourceRequest so that
			// non-excluded request attributes can be picked up by a subsequent RenderRequest.
			if (bridgeRequestScope == null) {

				// TCK TestPage071: nonFacesResourceTest
				// TCK TestPage073: scopeAfterRedisplayResourcePPRTest
				PortletSession portletSession = portletRequest.getPortletSession();
				bridgeRequestScopeId = (String) portletSession.getAttribute(bridgeRequestScopeKey);

				if (bridgeRequestScopeId != null) {

					portletSession.removeAttribute(bridgeRequestScopeKey);

					bridgeRequestScope = bridgeRequestScopeCache.get(bridgeRequestScopeId);

					if (bridgeRequestScope != null) {

						logger.debug(
							"Found (and removed) session-attribute name=[{0}] value=[{1}] and cached bridgeRequestScope=[{2}]",
							bridgeRequestScopeKey, bridgeRequestScopeId, bridgeRequestScope);

						if (portletResponse instanceof StateAwareResponse) {
							logger.debug("Setting former session-attribute as render parameter name=[{0}] value=[{1}]",
								bridgeRequestScopeKey, bridgeRequestScopeId);

							StateAwareResponse stateAwareResponse = (StateAwareResponse) portletResponse;
							stateAwareResponse.setRenderParameter(bridgeRequestScopeKey, bridgeRequestScopeId);
						}
					}
					else {
						logger.error(
							"Found session attribute name=[{0}] value=[{1}] but bridgeRequestScope is not in the cache",
							bridgeRequestScopeKey, bridgeRequestScopeId);
					}
				}
			}

			// Otherwise, return a new factory created instance.
			if (bridgeRequestScope == null) {
				BridgeRequestScopeFactory bridgeRequestScopeFactory = (BridgeRequestScopeFactory) BridgeFactoryFinder
					.getFactory(BridgeRequestScopeFactory.class);
				bridgeRequestScope = bridgeRequestScopeFactory.getBridgeRequestScope(portletConfig, portletContext,
						portletRequest);
			}
		}
	}

	protected void maintainBridgeRequestScope(PortletRequest portletRequest, PortletResponse portletResponse,
		BridgeRequestScope.Transport bridgeRequestScopeTransport) {

		String bridgeRequestScopeId = bridgeRequestScope.getId();

		bridgeRequestScopeCache.put(bridgeRequestScopeId, bridgeRequestScope);

		String bridgeRequestScopeKey = portletName + PARAM_BRIDGE_REQUEST_SCOPE_ID;

		if (bridgeRequestScopeTransport == BridgeRequestScope.Transport.PORTLET_SESSION_ATTRIBUTE) {

			// TCK TestPage071: nonFacesResourceTest
			// TCK TestPage073: scopeAfterRedisplayResourcePPRTest
			PortletSession portletSession = portletRequest.getPortletSession(true);
			portletSession.setAttribute(bridgeRequestScopeKey, bridgeRequestScopeId);
		}
		else {

			if (portletResponse instanceof StateAwareResponse) {
				logger.debug("Setting render parameter name=[{0}] value=[{1}]", bridgeRequestScopeKey,
					bridgeRequestScopeId);

				try {
					StateAwareResponse stateAwareResponse = (StateAwareResponse) portletResponse;
					stateAwareResponse.setRenderParameter(bridgeRequestScopeKey, bridgeRequestScopeId);
				}
				catch (IllegalStateException e) {

					// If a redirect occurred, then swallow/ignore the IllegalStateException
					if (bridgeRequestScope.isRedirectOccurred()) {

						// The Portlet API JavaDocs indicate that StateAwareResponse.setRenderParameter(String, String)
						// must throw an IllegalStateException if ActionResponse.sendRedirect(String) was previously
						// called. The JSR 329 TCK TestPage039 (requestNoScopeOnRedirectTest) and TestPage176
						// (redirectActionTest) both perform pseudo-redirects (effectively treated like navigation-rules
						// from one JSF viewId to another). Since the tests don't actually call
						// ActionResponse.sendRedirect(String), this condition is never reached by the TCK. However,
						// this condition is a real-world use-case and so the IllegalStateException must be
						// swallowed/ignored here so that portlet lifecycle processing is able to continue. For more
						// information, see: http://issues.liferay.com/browse/FACES-1367
					}

					// Otherwise throw the IllegalStateException.
					else {
						throw e;
					}
				}
			}
		}
	}

	protected FacesContext getFacesContext(PortletRequest portletRequest, PortletResponse portletResponse,
		Lifecycle lifecycle) throws FacesException {

		FacesContext newFacesContext = getFacesContextFactory().getFacesContext(portletContext, portletRequest,
				portletResponse, lifecycle);

		// TCK TestPage203 (JSF_ELTest) ensure that the #{facesContext} implicit object is set to the current instance.
		ELContext elContext = newFacesContext.getELContext();
		elContext.putContext(FacesContext.class, newFacesContext);

		return newFacesContext;
	}

	protected FacesContextFactory getFacesContextFactory() throws FacesException {

		if (facesContextFactory == null) {
			facesContextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
		}

		return facesContextFactory;
	}

	protected String getInitParameter(String name) {
		String initParameter = portletConfig.getInitParameter(name);

		if (initParameter == null) {
			initParameter = portletContext.getInitParameter(name);
		}

		return initParameter;
	}
}
