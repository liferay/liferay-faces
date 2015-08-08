/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context.internal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceResponse;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowStateException;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeInvalidViewPathException;
import javax.portlet.faces.GenericFacesPortlet;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.application.internal.BridgeNavigationUtil;
import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.internal.BridgeConfigAttributeMap;
import com.liferay.faces.bridge.config.internal.PortletConfigParam;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.BridgePortalContext;
import com.liferay.faces.bridge.context.IncongruityContext;
import com.liferay.faces.bridge.context.map.internal.ContextMapFactory;
import com.liferay.faces.bridge.context.map.internal.RequestHeaderMap;
import com.liferay.faces.bridge.context.map.internal.RequestHeaderValuesMap;
import com.liferay.faces.bridge.context.url.BridgeResourceURL;
import com.liferay.faces.bridge.context.url.BridgeURI;
import com.liferay.faces.bridge.context.url.BridgeURIFactory;
import com.liferay.faces.bridge.context.url.BridgeURL;
import com.liferay.faces.bridge.context.url.BridgeURLFactory;
import com.liferay.faces.bridge.internal.BridgeExt;
import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeContextImpl extends BridgeContextCompatImpl {

	/** Portlet-API request attribute that contains an instance of javax.portlet.PortletRequest */
	private static final String REQUEST_ATTR_PORTLET_REQUEST = "javax.portlet.request";

	/** Servlet-API request attribute that indicates the query part of the URL requested by the user-agent */
	private static final String REQUEST_ATTR_QUERY_STRING = "javax.servlet.forward.query_string";

	// Private Constants
	private static final String DIRECT_LINK_EQUALS_TRUE = Bridge.DIRECT_LINK + "=true";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeContextImpl.class);

	// Private Data Members
	private Map<String, Object> attributeMap = new HashMap<String, Object>();
	private BridgeConfig bridgeConfig;
	private BridgeRequestScope bridgeRequestScope;
	private BridgeURIFactory bridgeURIFactory;
	private BridgeURLFactory bridgeURLFactory;
	private List<String> configuredSuffixes;
	private List<ConfiguredServletMapping> configuredFacesServletMappings;
	private ContextMapFactory contextMapFactory;
	private String defaultRenderKitId;
	private Map<String, String> defaultViewIdMap;
	private FacesView facesView;
	private IncongruityContext incongruityContext;
	private List<String> preFacesRequestAttrNames;
	private PortletConfig portletConfig;
	private PortletContext portletContext;
	private PortletRequest portletRequest;
	private Bridge.PortletPhase portletPhase;
	private PortletResponse portletResponse;
	private Boolean preserveActionParams;
	private Map<String, String[]> preservedActionParams;
	private boolean processingAfterViewContent;
	private boolean renderRedirect;
	private boolean renderRedirectAfterDispatch;
	private Boolean renderRedirectEnabled;
	private BridgeURL renderRedirectURL;
	private Map<String, String> requestHeaderMap;
	private Map<String, String[]> requestHeaderValuesMap;
	private Map<String, String> requestParameterMap;
	private Map<String, String[]> requestParameterValuesMap;
	private StringWrapper requestPathInfo;
	private String requestServletPath;
	private String requestQueryString;
	private String requestURL;
	private Writer responseOutputWriter;
	private String savedViewState;
	private String viewIdAndQueryString;

	@SuppressWarnings("unchecked")
	public BridgeContextImpl(BridgeConfig bridgeConfig, BridgeRequestScope bridgeRequestScope,
		PortletConfig portletConfig, PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse, Bridge.PortletPhase portletPhase, IncongruityContext incongruityContext) {

		this.bridgeConfig = bridgeConfig;
		this.configuredFacesServletMappings = (List<ConfiguredServletMapping>) bridgeConfig.getAttributes().get(
				BridgeConfigAttributeMap.CONFIGURED_FACES_SERVLET_MAPPINGS);
		this.configuredSuffixes = (List<String>) bridgeConfig.getAttributes().get(
				BridgeConfigAttributeMap.CONFIGURED_SUFFIXES);
		this.bridgeRequestScope = bridgeRequestScope;
		this.portletConfig = portletConfig;
		this.portletContext = portletContext;
		this.portletRequest = portletRequest;
		this.portletResponse = portletResponse;
		this.portletPhase = portletPhase;
		this.incongruityContext = incongruityContext;

		// Get the BridgeURLFactory instance.
		this.bridgeURIFactory = (BridgeURIFactory) BridgeFactoryFinder.getFactory(BridgeURIFactory.class);
		this.bridgeURLFactory = (BridgeURLFactory) BridgeFactoryFinder.getFactory(BridgeURLFactory.class);
		this.contextMapFactory = (ContextMapFactory) BridgeFactoryFinder.getFactory(ContextMapFactory.class);

		logger.debug("User-Agent requested URL=[{0}]", getRequestURL());

		setCurrentInstance(this);
	}

	@Override
	public BridgeURL encodeActionURL(String url) {

		logger.debug("encodeActionURL fromURL=[{0}]", url);

		BridgeURL bridgeActionURL = null;

		// Determine the target of the specified URL, which could be a Faces-View or a
		// Non-Faces-View.
		try {
			BridgeURI bridgeURI = bridgeURIFactory.getBridgeURI(url);
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			FacesContext facesContext = FacesContext.getCurrentInstance();
			UIViewRoot viewRoot = facesContext.getViewRoot();
			String currentFacesViewId = viewRoot.getViewId();
			bridgeActionURL = bridgeURLFactory.getBridgeActionURL(bridgeContext, bridgeURI, currentFacesViewId);

			// If the specified URL starts with "portlet:", then
			Map<String, String[]> parameterMap = bridgeActionURL.getParameterMap();

			if (bridgeURI.isPortletScheme()) {

				// If the "_jsfBridgeViewId" URL parameter is equal to "_jsfBridgeCurrentView" then the
				// URL is self-referencing and the "_jsfBridgeViewId" parameter muse be removed from the
				// URL.
				String facesViewIdParameter = bridgeActionURL.getParameter(Bridge.FACES_VIEW_ID_PARAMETER);

				if (Bridge.FACES_USE_CURRENT_VIEW_PARAMETER.equals(facesViewIdParameter)) {
					bridgeActionURL.setSelfReferencing(true);
					parameterMap.remove(Bridge.FACES_VIEW_ID_PARAMETER);
				}

				// If the "_jsfBridgeViewPath" URL parameter is equal to "_jsfBridgeCurrentView" then
				// the URL is self-referencing and the "_jsfBridgeViewPath" parameter muse be removed
				// from the URL.
				String facesViewPathParameter = bridgeActionURL.getParameter(Bridge.FACES_VIEW_PATH_PARAMETER);

				if (Bridge.FACES_USE_CURRENT_VIEW_PARAMETER.equals(facesViewPathParameter)) {
					bridgeActionURL.setSelfReferencing(true);
					parameterMap.remove(Bridge.FACES_VIEW_PATH_PARAMETER);
				}
			}

			// Otherwise, the specified URL must be for a path-encoded URL (either a Faces-View or
			// Non-Faces-View)
			else {

				// If the specified URL has a "javax.portlet.faces.DirectLink" parameter with a value of
				// "false", then remove it from the map of parameters as required by the Bridge Spec.
				String directLinkParam = bridgeActionURL.getParameter(Bridge.DIRECT_LINK);

				if (BooleanHelper.isFalseToken(directLinkParam)) {
					parameterMap.remove(Bridge.DIRECT_LINK);
				}

				String contextRelativeViewPath = null;

				if (!bridgeURI.isExternal()) {
					String contextPath = bridgeContext.getPortletRequest().getContextPath();
					contextRelativeViewPath = bridgeURI.getContextRelativePath(contextPath);

					if (contextRelativeViewPath == null) {
						contextRelativeViewPath = viewRoot.getViewId();
					}
				}

				FacesView targetFacesView = new FacesViewImpl(contextRelativeViewPath, configuredSuffixes,
						configuredFacesServletMappings);

				if (!bridgeURI.isAbsolute() && !targetFacesView.isExtensionMapped() &&
						!targetFacesView.isPathMapped() && !url.startsWith("#")) {
					bridgeActionURL.setParameter(Bridge.NONFACES_TARGET_PATH_PARAMETER, contextRelativeViewPath);
				}

			}
		}
		catch (URISyntaxException e) {
			logger.error(e);
		}

		return bridgeActionURL;
	}

	@Override
	public BridgeURL encodeBookmarkableURL(String baseURL, Map<String, List<String>> parameters) {

		logger.debug("encodeBookmarkableURL fromURL=[{0}]", baseURL);

		String viewId = baseURL;

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		PortletRequest portletRequest = bridgeContext.getPortletRequest();
		String requestContextPath = portletRequest.getContextPath();

		if (baseURL.startsWith(requestContextPath)) {
			viewId = baseURL.substring(requestContextPath.length());
		}

		try {
			BridgeURI bridgeURI = bridgeURIFactory.getBridgeURI(baseURL);

			return bridgeURLFactory.getBridgeBookmarkableURL(bridgeContext, bridgeURI, parameters, viewId);
		}
		catch (URISyntaxException e) {
			throw new FacesException(e);
		}
	}

	@Override
	public BridgeURL encodePartialActionURL(String url) {

		logger.debug("encodePartialActionURL fromURL=[{0}]", url);

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		String currentFacesViewId = viewRoot.getViewId();

		try {
			BridgeURI bridgeURI = bridgeURIFactory.getBridgeURI(url);
			BridgeURL bridgePartialActionURL = bridgeURLFactory.getBridgePartialActionURL(bridgeContext, bridgeURI,
					currentFacesViewId);
			bridgePartialActionURL.setParameter(BridgeExt.FACES_AJAX_PARAMETER, Boolean.TRUE.toString());

			return bridgePartialActionURL;
		}
		catch (URISyntaxException e) {
			throw new FacesException(e);
		}
	}

	@Override
	public BridgeURL encodeRedirectURL(String baseUrl, Map<String, List<String>> parameters) {

		logger.debug("encodeRedirectURL fromURL=[{0}]", baseUrl);

		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (isJSF2PartialRequest(facesContext)) {

			ArrayList<String> bridgeAjaxRedirect = new ArrayList<String>();
			bridgeAjaxRedirect.add("true");
			parameters.put("_bridgeAjaxRedirect", bridgeAjaxRedirect);
		}

		try {

			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			BridgeURI bridgeURI = bridgeURIFactory.getBridgeURI(baseUrl);
			String redirectViewId = null;

			if (!bridgeURI.isExternal()) {
				String contextPath = bridgeContext.getPortletRequest().getContextPath();
				redirectViewId = bridgeURI.getContextRelativePath(contextPath);
			}

			return bridgeURLFactory.getBridgeRedirectURL(bridgeContext, bridgeURI, parameters, redirectViewId);
		}
		catch (URISyntaxException e) {
			throw new FacesException(e);
		}
	}

	@Override
	public BridgeResourceURL encodeResourceURL(String url) {

		logger.debug("encodeResourceURL fromURL=[{0}]", url);

		String currentFacesViewId = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIViewRoot uiViewRoot = facesContext.getViewRoot();

		if (uiViewRoot != null) {
			currentFacesViewId = uiViewRoot.getViewId();
		}

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		try {
			BridgeURI bridgeURI = bridgeURIFactory.getBridgeURI(url);
			BridgeResourceURL bridgeResourceURL = bridgeURLFactory.getBridgeResourceURL(bridgeContext, bridgeURI,
					currentFacesViewId);

			// If the "javax.portlet.faces.ViewLink" parameter is found and set to "true", then
			String viewLinkParam = bridgeResourceURL.getParameter(Bridge.VIEW_LINK);
			Map<String, String[]> parameterMap = bridgeResourceURL.getParameterMap();

			if (BooleanHelper.isTrueToken(viewLinkParam)) {

				// Since this is going to be a URL that represents navigation to a different viewId,
				// need to remove the "javax.portlet.faces.ViewLink" parameter as required by the Bridge
				// Spec.
				parameterMap.remove(Bridge.VIEW_LINK);

				// Set a flag indicating that this is a view-link type of navigation.
				bridgeResourceURL.setViewLink(true);

				// If the "javax.portlet.faces.BackLink" parameter is found, then replace it's value
				// with a URL that can cause navigation back to the current view.
				if (bridgeResourceURL.getParameter(Bridge.BACK_LINK) != null) {
					bridgeResourceURL.replaceBackLinkParameter(facesContext);
				}
			}

			// If the specified URL is opaque, meaning it starts with something like "portlet:" or "mailto:" and
			// doesn't have the double-forward-slash like "http://" does, then
			if (bridgeURI.isOpaque()) {

				// If the specified URL starts with "portlet:", then
				if (bridgeURI.isPortletScheme()) {

					// If the "_jsfBridgeViewId" URL parameter is equal to "_jsfBridgeCurrentView" then the
					// URL is self-referencing and the "_jsfBridgeViewId" parameter muse be removed from the
					// URL.
					String facesViewIdParameter = bridgeResourceURL.getParameter(Bridge.FACES_VIEW_ID_PARAMETER);

					if (Bridge.FACES_USE_CURRENT_VIEW_PARAMETER.equals(facesViewIdParameter)) {
						bridgeResourceURL.setSelfReferencing(true);
						parameterMap.remove(Bridge.FACES_VIEW_ID_PARAMETER);
					}

					// If the "_jsfBridgeViewPath" URL parameter is equal to "_jsfBridgeCurrentView" then
					// the URL is self-referencing and the "_jsfBridgeViewPath" parameter muse be removed
					// from the URL.
					String facesViewPathParameter = bridgeResourceURL.getParameter(Bridge.FACES_VIEW_PATH_PARAMETER);

					if (Bridge.FACES_USE_CURRENT_VIEW_PARAMETER.equals(facesViewPathParameter)) {
						bridgeResourceURL.setSelfReferencing(true);
						parameterMap.remove(Bridge.FACES_VIEW_PATH_PARAMETER);
					}
				}
			}

			// Otherwise, if the specified URL is hierarchical and targets an external resource, then
			else if (bridgeURI.isHierarchical() && bridgeURI.isExternal()) {

				// If the "javax.portlet.faces.BackLink" parameter is found, then replace it's value with
				// a URL that can cause navigation back to the current view.
				if (bridgeResourceURL.getParameter(Bridge.BACK_LINK) != null) {
					bridgeResourceURL.replaceBackLinkParameter(facesContext);
				}
			}

			// Otherwise, if the specified URL is hierarchical and targets a resource internal to this
			// application, then
			else if (bridgeURI.isHierarchical() && !bridgeURI.isExternal()) {

				// If the "javax.portlet.faces.BackLink" parameter is found, then replace it's value with a URL
				// that can cause navigation back to the current view.
				if (bridgeResourceURL.getParameter(Bridge.BACK_LINK) != null) {
					bridgeResourceURL.replaceBackLinkParameter(facesContext);
				}

				// If the "javax.portlet.faces.InProtocolResourceLink" parameter is found, then
				if ((bridgeResourceURL.getParameter(Bridge.IN_PROTOCOL_RESOURCE_LINK) != null)) {
					bridgeResourceURL.setInProtocol(true);

					// Since an in-protocol-resource URL must be a ResourceURL, the
					// "javax.portlet.faces.PortletMode" and "javax.portlet.faces.WindowState" parameters must
					// be removed from the URL (if present) because you can change a PortletMode or WindowState
					// in a ResourceRequest.
					parameterMap.remove(Bridge.PORTLET_MODE_PARAMETER);
					parameterMap.remove(Bridge.PORTLET_WINDOWSTATE_PARAMETER);

					// The Bridge Spec indicates that the "javax.portlet.faces.Secure" parameter must be
					// removed but must also be used to set the security of the ResourceURL below.
					String secureParam = bridgeResourceURL.getParameter(Bridge.PORTLET_SECURE_PARAMETER);
					bridgeResourceURL.setSecure(BooleanHelper.isTrueToken(secureParam));
					parameterMap.remove(Bridge.PORTLET_SECURE_PARAMETER);
				}
			}

			return bridgeResourceURL;
		}
		catch (URISyntaxException e) {
			throw new FacesException(e);
		}
	}

	@Override
	public void redirect(String url) throws IOException {

		if (url != null) {
			logger.debug("redirect url=[{0}]", url);

			// If currently executing the ACTION_PHASE, EVENT_PHASE, or RENDER_PHASE of the portlet lifecycle, then
			if ((portletPhase == Bridge.PortletPhase.ACTION_PHASE) ||
					(portletPhase == Bridge.PortletPhase.EVENT_PHASE) ||
					(portletPhase == Bridge.PortletPhase.RENDER_PHASE)) {

				// If the specified URL starts with a "#" character, is external to this application, or has a
				// "javax.portlet.faces.DirectLink" parameter value of "true", then
				try {
					BridgeURI bridgeURI = bridgeURIFactory.getBridgeURI(url);
					String queryString = bridgeURI.getQuery();
					boolean directLink = (queryString != null) && queryString.contains(DIRECT_LINK_EQUALS_TRUE);

					if ((portletPhase == Bridge.PortletPhase.ACTION_PHASE) &&
							(url.startsWith("#") || bridgeURI.isExternal() || directLink)) {

						bridgeRequestScope.setRedirectOccurred(true);

						// TestPage039-requestNoScopeOnRedirectTest.
						ActionResponse actionResponse = (ActionResponse) portletResponse;
						actionResponse.sendRedirect(url);
					}

					// Otherwise,
					else {

						FacesContext facesContext = FacesContext.getCurrentInstance();

						// String newViewId =
						// bridgeURI.getContextRelativePath(bridgeContext.getPortletRequest().getContextPath());

						// If running in the ACTION_PHASE of the portlet lifecycle and the portlet container has the
						// ability to create a render URL during the ACTION_PHASE, then assume that the specified URL is
						// an encoded RenderURL and issue a redirect.
						BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
						PortalContext portalContext = bridgeContext.getPortletRequest().getPortalContext();
						String createRenderUrlDuringActionPhaseSupport = portalContext.getProperty(
								BridgePortalContext.CREATE_RENDER_URL_DURING_ACTION_PHASE_SUPPORT);

						if ((portletPhase == Bridge.PortletPhase.ACTION_PHASE) &&
								(createRenderUrlDuringActionPhaseSupport != null) && bridgeURI.isAbsolute()) {

							// Redirect to the targeted view.
							// bridgeRedirectURL.setParameter(Bridge.FACES_VIEW_ID_PARAMETER, newViewId);
							ActionResponse actionResponse = (ActionResponse) getPortletResponse();
							actionResponse.sendRedirect(bridgeURI.toString());
						}

						// Otherwise, if running in the ACTION_PHASE or EVENT_PHASE of the portlet lifecycle, then
						// assume that the specified URL is NOT an encoded RenderURL, but rather a simple context-path
						// based string like "/my-portlet/views/myView.faces" that contains a context-relative JSF view
						// id an potentially some URL parameters that indicate the portlet mode and window state. In
						// this case, all that can be done is to simply navigate to the target view id (not an actual
						// redirect).
						else if ((portletPhase == Bridge.PortletPhase.ACTION_PHASE) ||
								(portletPhase == Bridge.PortletPhase.EVENT_PHASE)) {

							// TCK NOTE: The TCK will invoke this condition during the
							// TestPage039-requestNoScopeOnRedirectTest and TestPage176-redirectActionTest.
							String contextPath = bridgeContext.getPortletRequest().getContextPath();
							String newViewId = bridgeURI.getContextRelativePath(contextPath);

							// If redirecting to a different view, then create the target view and place it into the
							// FacesContext.
							UIViewRoot viewRoot = facesContext.getViewRoot();
							String currentFacesViewId = viewRoot.getViewId();

							if (!currentFacesViewId.equals(newViewId)) {

								ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
								UIViewRoot newViewRoot = viewHandler.createView(facesContext, newViewId);
								facesContext.setViewRoot(newViewRoot);
							}

							// Update the PartialViewContext.
							partialViewContextRenderAll(facesContext);

							// Set the response as "complete" in the FacesContext.
							facesContext.responseComplete();

							// Set a flag on the {@link BridgeRequestScope} indicating that a <redirect />
							// occurred which means that the request attributes should not be preserved.
							getBridgeRequestScope().setRedirectOccurred(true);

							// Apply the PortletMode, WindowState, etc. that may be present in the URL to the response.
							try {
								StateAwareResponse stateAwareResponse = (StateAwareResponse) portletResponse;
								BridgeURL bridgeRedirectURL = bridgeURLFactory.getBridgeRedirectURL(bridgeContext,
										bridgeURI, null, null);
								BridgeNavigationUtil.navigate(portletRequest, stateAwareResponse, bridgeRequestScope,
									bridgeRedirectURL);
							}
							catch (PortletModeException e) {
								logger.error(e.getMessage());
							}
							catch (WindowStateException e) {
								logger.error(e.getMessage());
							}
						}

						// Otherwise, if currently executing the RENDER_PHASE of the portlet lifecycle, then
						else if (portletPhase == Bridge.PortletPhase.RENDER_PHASE) {

							// If the specified URL is for a JSF viewId, then prepare for a render-redirect.
							BridgeURL bridgeRedirectURL = bridgeURLFactory.getBridgeRedirectURL(bridgeContext,
									bridgeURI, null, null);

							if (bridgeRedirectURL.isFacesViewTarget()) {
								renderRedirect = true;
								renderRedirectURL = bridgeRedirectURL;
							}

							// Otherwise,
							else {

								// If there is a URL parameter specifying a JSF viewId, then prepare for a
								// render-redirect.
								String viewIdRenderParameterName = bridgeConfig.getViewIdRenderParameterName();
								String viewIdRenderParameterValue = bridgeRedirectURL.getParameter(
										viewIdRenderParameterName);

								if (viewIdRenderParameterValue != null) {

									// TCK TestPage 179: redirectRenderPRP1Test
									renderRedirect = true;
									viewIdRenderParameterValue = URLDecoder.decode(viewIdRenderParameterValue, "UTF-8");

									BridgeURI redirectURI = bridgeURIFactory.getBridgeURI(viewIdRenderParameterValue);
									UIViewRoot viewRoot = facesContext.getViewRoot();
									String currentFacesViewId = viewRoot.getViewId();
									bridgeRedirectURL = bridgeURLFactory.getBridgeRedirectURL(bridgeContext,
											redirectURI, null, currentFacesViewId);
									renderRedirectURL = bridgeRedirectURL;
								}

								// Otherwise, throw an IllegalStateException according to Section 6.1.3.1 of the Spec.
								else {
									throw new IllegalStateException(
										"6.1.3.1: Unable to redirect to a non-Faces view during the RENDER_PHASE.");
								}
							}
						}
					}
				}
				catch (URISyntaxException e) {
					logger.error(e);
				}
			}

			// Otherwise, since executing the RESOURCE_PHASE of the portlet lifecycle:
			else {

				// NOTE: The Bridge Spec indicates that the redirect is to be ignored, but JSF 2 has the ability to
				// redirect during Ajax.
				FacesContext facesContext = FacesContext.getCurrentInstance();

				if (isJSF2PartialRequest(facesContext)) {
					BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

					try {
						BridgeURI bridgeURI = bridgeURIFactory.getBridgeURI(url);
						BridgeURL bridgeRedirectURL = bridgeURLFactory.getBridgeRedirectURL(bridgeContext, bridgeURI,
								null, null);
						redirectJSF2PartialResponse(facesContext, (ResourceResponse) portletResponse,
							bridgeRedirectURL.toString());
					}
					catch (Exception e) {
						logger.error(e);
						throw new IOException(e.getMessage());
					}
				}
				else {
					throw new UnsupportedEncodingException(
						"Can only redirect during RESOURCE_PHASE if a JSF partial/Ajax request has been triggered");
				}
			}
		}
		else {
			logger.error("redirect url=null");
		}
	}

	@Override
	public void release() {
		this.attributeMap = null;
		this.bridgeConfig = null;
		this.bridgeRequestScope = null;
		this.bridgeURLFactory = null;
		this.configuredFacesServletMappings = null;
		this.configuredSuffixes = null;
		this.contextMapFactory = null;
		this.defaultRenderKitId = null;
		this.defaultViewIdMap = null;
		this.facesView = null;
		this.preFacesRequestAttrNames = null;
		this.portletConfig = null;
		this.portletContext = null;
		this.portletRequest = null;
		this.portletPhase = null;
		this.portletResponse = null;
		this.preserveActionParams = null;
		this.preservedActionParams = null;
		this.renderRedirect = false;
		this.renderRedirectAfterDispatch = false;
		this.renderRedirectURL = null;
		this.requestHeaderMap = null;
		this.requestHeaderValuesMap = null;
		this.requestParameterMap = null;
		this.requestParameterValuesMap = null;
		this.requestPathInfo = null;
		this.requestServletPath = null;
		this.requestQueryString = null;
		this.requestURL = null;
		this.savedViewState = null;
		this.viewIdAndQueryString = null;
		setCurrentInstance(null);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributeMap;
	}

	@Override
	public BridgeConfig getBridgeConfig() {
		return bridgeConfig;
	}

	@Override
	public BridgeRequestScope getBridgeRequestScope() {
		return bridgeRequestScope;
	}

	@Override
	public String getDefaultRenderKitId() {

		if (defaultRenderKitId == null) {
			String attributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletConfig.getPortletName() + "." +
				Bridge.DEFAULT_RENDERKIT_ID;
			defaultRenderKitId = (String) portletContext.getAttribute(attributeName);
		}

		return defaultRenderKitId;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> getDefaultViewIdMap() {

		if (defaultViewIdMap == null) {
			String portletName = portletConfig.getPortletName();
			String attrNameDefaultViewIdMap = Bridge.BRIDGE_PACKAGE_PREFIX + portletName + "." +
				Bridge.DEFAULT_VIEWID_MAP;
			defaultViewIdMap = (Map<String, String>) portletConfig.getPortletContext().getAttribute(
					attrNameDefaultViewIdMap);
		}

		return defaultViewIdMap;
	}

	/**
	 * Returns an instance of {@link FacesView} that represents the target view (and optional query string) as described
	 * in section 5.2.3 of the Bridge Spec titled "Determining the Target View".
	 *
	 * @throws  {@link BridgeDefaultViewNotSpecifiedException} when the default view is not specified in the
	 *            WEB-INF/portlet.xml descriptor.
	 * @throws  {@link BridgeInvalidViewPathException} when the {@link Bridge#VIEW_PATH} request attribute contains an
	 *            invalid path such that the target view cannot be determined.
	 */
	protected FacesView getFacesView() throws BridgeDefaultViewNotSpecifiedException, BridgeInvalidViewPathException {

		if (facesView == null) {
			String fullViewId = getFacesViewIdAndQueryString();
			String viewId = null;
			String navigationQueryString = null;

			if (fullViewId != null) {
				int pos = fullViewId.indexOf("?");

				if (pos > 0) {
					navigationQueryString = fullViewId.substring(pos + 1);
					viewId = fullViewId.substring(0, pos);
				}
				else {
					viewId = fullViewId;
				}
			}

			facesView = new FacesViewImpl(viewId, navigationQueryString, configuredSuffixes,
					configuredFacesServletMappings);
		}

		return facesView;
	}

	@Override
	public String getFacesViewId() throws BridgeDefaultViewNotSpecifiedException, BridgeInvalidViewPathException {
		return getFacesView().getViewId();
	}

	/**
	 * <p>This method returns the target view (and optional query string) as described in section 5.2.3 of the Bridge
	 * Spec titled "Determining the Target View".</p>
	 *
	 * <p>Try#1: Get the viewId from the {@link Bridge#VIEW_ID} (javax.portlet.faces.viewId) request attribute. As
	 * described in sections 3.4 and 4.2.5 of the bridge spec, this attribute is set by the {@link GenericFacesPortlet}
	 * when it encounters the {@link Bridge#FACES_VIEW_ID_PARAMETER} request parameter.</p>
	 *
	 * <p>Try#2: Get the viewId from the {@link Bridge#VIEW_PATH} (javax.portlet.faces.viewPath) request attribute. As
	 * described in sections 3.4 and 4.2.5 of the bridge spec, this attribute is set by the {@link GenericFacesPortlet}
	 * when it encounters the {@link Bridge#FACES_VIEW_PATH_PARAMETER} request parameter. If the viewId cannot be
	 * determined, then {@link BridgeInvalidViewPathException} is thrown.</p>
	 *
	 * <p>Try#3: Get the viewId from a prior render-redirect (if one has occurred).</p>
	 *
	 * <p>Try#4: Get the viewId from a request parameter, the name of which is dynamic depending on the {@link
	 * Bridge.PortletPhase}.</p>
	 *
	 * <p>Try#5:Get the viewId from the init-param value in the portlet.xml descriptor according the current {@link
	 * PortletMode}.</p>
	 *
	 * @throws  {@link BridgeDefaultViewNotSpecifiedException} when the default view is not specified in the
	 *            WEB-INF/portlet.xml descriptor.
	 * @throws  {@link BridgeInvalidViewPathException} when the {@link Bridge#VIEW_PATH} request attribute contains an
	 *            invalid path such that the target view cannot be determined.
	 */
	protected String getFacesViewIdAndQueryString() throws BridgeDefaultViewNotSpecifiedException,
		BridgeInvalidViewPathException {

		if (viewIdAndQueryString == null) {

			// Try#1: Get the viewId the "javax.portlet.faces.viewId" request attribute.
			viewIdAndQueryString = getFacesViewIdRequestAttribute(Bridge.VIEW_ID);

			if (viewIdAndQueryString == null) {

				// Try#2: Get the viewId from the "javax.portlet.faces.viewPath" request attribute.
				String viewPath = getFacesViewIdRequestAttribute(Bridge.VIEW_PATH);

				if (viewPath != null) {

					// If present, remove the query string from the specified viewPath.
					int pos = viewPath.indexOf("?");

					if (pos > 0) {
						viewPath = viewPath.substring(0, pos);
					}

					// If present, remove everything up to (and including) the context path from the viewPath.
					String contextPath = portletRequest.getContextPath();
					pos = viewPath.indexOf(contextPath);

					if (pos >= 0) {
						viewPath = viewPath.substring(pos + contextPath.length());
					}

					viewIdAndQueryString = getFacesViewIdFromPath(viewPath);

					if (viewIdAndQueryString == null) {
						throw new BridgeInvalidViewPathException();
					}
				}

				if (viewIdAndQueryString == null) {

					// Try #3: Get the viewId from a prior render-redirect (if one has occurred). Note that this logic
					// depends on the BridgePhaseRenderImpl calling the setRenderRedirectURL(BridgeRedirectURL) method
					// on this class instance when a render-redirect takes place.
					if (renderRedirectURL != null) {
						viewIdAndQueryString = renderRedirectURL.toString();
					}

					if (viewIdAndQueryString == null) {

						// Try#4: Get the viewId from a request parameter, the name of which is dynamic depending on
						// the portlet phase.
						String requestParameterName;

						if (portletPhase == Bridge.PortletPhase.RESOURCE_PHASE) {
							requestParameterName = bridgeConfig.getViewIdResourceParameterName();
						}
						else {
							requestParameterName = bridgeConfig.getViewIdRenderParameterName();
						}

						viewIdAndQueryString = getFacesViewIdRequestParameter(requestParameterName);

						if (viewIdAndQueryString == null) {

							// Try#5: Get the viewId from the init-param value in the portlet.xml descriptor according
							// to the current portlet mode.
							PortletMode currentPortletMode = portletRequest.getPortletMode();
							viewIdAndQueryString = getDefaultViewIdMap().get(currentPortletMode.toString());
							logger.debug("portlet.xml viewId=[{0}] portletMode=[{1}]", viewIdAndQueryString,
								currentPortletMode);

							if (viewIdAndQueryString == null) {
								throw new BridgeDefaultViewNotSpecifiedException();
							}
						}
						else {
							logger.debug("request parameter {0}=[{1}]", requestParameterName, viewIdAndQueryString);
						}
					}
					else {
						logger.debug("redirect viewId=[{0}]", viewIdAndQueryString);
					}
				}
			}
			else {
				logger.debug("javax.portlet.faces.viewId=[{0}]", viewIdAndQueryString);
			}
		}

		return viewIdAndQueryString;
	}

	@Override
	public String getFacesViewIdFromPath(String viewPath) {
		return getFacesViewIdFromPath(viewPath, true);
	}

	@Override
	public String getFacesViewIdFromPath(String viewPath, boolean mustExist) {

		String facesViewId = null;

		// Try to determine the viewId by examining the servlet-mapping entries for the Faces Servlet.
		// For each servlet-mapping:
		for (ConfiguredServletMapping configuredFacesServletMapping : configuredFacesServletMappings) {

			// If the curent servlet-mapping matches the viewPath, then
			logger.debug("Attempting to determine the facesViewId from {0}=[{1}]", Bridge.VIEW_PATH, viewPath);

			if (configuredFacesServletMapping.isMatch(viewPath)) {

				// If the servlet-mapping is extension mapped (like *.faces or *.jsf), then
				if (configuredFacesServletMapping.isExtensionMapped()) {

					// Iterate through each of the valid extensions (.jsp, .jspx, etc.) that the developer
					// may have specified in the web.xml descriptor. For each extension, see if file exists
					// within the filesystem of this context.
					for (String defaultSuffix : configuredSuffixes) {

						int pos = viewPath.lastIndexOf(".");

						if (pos > 0) {

							if (mustExist) {
								String resourcePath = viewPath.substring(0, pos) + defaultSuffix;

								try {
									URL resourceURL = getPortletContext().getResource(resourcePath);

									// If the file exists, then we've determined the viewId from the viewPath.
									if (resourceURL != null) {
										facesViewId = viewPath;

										break;
									}

								}
								catch (MalformedURLException e) {
									logger.error(e);
								}
							}
							else {
								facesViewId = viewPath;

								break;
							}
						}
					}

					if (facesViewId == null) {
						logger.error(
							"Matched EXTENSION MAPPING for for urlPattern=[{0}] and viewPath=[{1}] but unable to find a facesViewId with extensions[{2}]",
							configuredFacesServletMapping.getUrlPattern(), viewPath, configuredSuffixes);
					}
				}

				// Otherwise, if the servlet-mapping is path-mapped, then
				else if (configuredFacesServletMapping.isPathMapped()) {
					facesViewId = viewPath;
				}

				if (facesViewId != null) {
					break;
				}
			}
		}

		return facesViewId;
	}

	protected String getFacesViewIdRequestAttribute(String name) {

		String value = (String) getPortletRequest().getAttribute(name);

		if ((value != null) && (value.contains(":") || value.contains("%"))) {

			logger.warn("Invalid character in request attribute {0}=[{1}]", name, value);
			value = null;
		}

		return value;
	}

	protected String getFacesViewIdRequestParameter(String name) {

		String value = getPortletRequest().getParameter(name);

		if ((value != null) && (value.contains(":") || value.contains("%"))) {

			logger.warn("Invalid character in request parameter {0}=[{1}]", name, value);
			value = null;
		}

		return value;
	}

	@Override
	public String getFacesViewQueryString() {
		return getFacesView().getQueryString();
	}

	@Override
	public boolean isRenderRedirectAfterDispatch() {
		return renderRedirectAfterDispatch;
	}

	@Override
	public IncongruityContext getIncongruityContext() {
		return incongruityContext;
	}

	@Override
	public String getInitParameter(String name) {

		String initParameter = portletConfig.getInitParameter(name);

		if (initParameter == null) {
			initParameter = portletContext.getInitParameter(name);
		}

		return initParameter;
	}

	@Override
	public PortletConfig getPortletConfig() {
		return portletConfig;
	}

	@Override
	public PortletContext getPortletContext() {
		return portletContext;
	}

	@Override
	public PortletRequest getPortletRequest() {
		return portletRequest;
	}

	@Override
	public void setPortletRequest(PortletRequest portletRequest) {
		this.portletRequest = portletRequest;
		this.requestParameterMap = null;
		this.requestParameterValuesMap = null;
		this.requestHeaderMap = null;
		this.requestHeaderValuesMap = null;
	}

	@Override
	public Bridge.PortletPhase getPortletRequestPhase() {
		return portletPhase;
	}

	@Override
	public PortletResponse getPortletResponse() {
		return portletResponse;
	}

	@Override
	public void setPortletResponse(PortletResponse portletResponse) {
		this.portletResponse = portletResponse;
	}

	@Override
	public List<String> getPreFacesRequestAttrNames() {
		return preFacesRequestAttrNames;
	}

	public void setPreFacesRequestAttrNames(List<String> preFacesRequestAttrNames) {
		this.preFacesRequestAttrNames = preFacesRequestAttrNames;
	}

	@Override
	public Map<String, String[]> getPreservedActionParams() {

		if (preservedActionParams == null) {
			preservedActionParams = new HashMap<String, String[]>();
		}

		return preservedActionParams;
	}

	public void setPreservedActionParams(Map<String, String[]> preservedActionParams) {

		// TODO: This never actually gets called anywhere in the bridge.
		this.preservedActionParams = preservedActionParams;
	}

	@Override
	public void setProcessingAfterViewContent(boolean processingAfterViewContent) {
		this.processingAfterViewContent = processingAfterViewContent;
	}

	@Override
	public void setRenderRedirectAfterDispatch(boolean renderRedirectAfterDispatch) {
		this.renderRedirectAfterDispatch = renderRedirectAfterDispatch;
	}

	@Override
	public BridgeURL getRenderRedirectURL() {
		return renderRedirectURL;
	}

	@Override
	public void setRenderRedirectURL(BridgeURL renderRedirectURL) {
		this.renderRedirectURL = renderRedirectURL;
	}

	@Override
	public Map<String, String> getRequestHeaderMap() {

		if (requestHeaderMap == null) {
			requestHeaderMap = Collections.unmodifiableMap(new RequestHeaderMap(getRequestHeaderValuesMap()));
		}

		return requestHeaderMap;
	}

	@Override
	public Map<String, String[]> getRequestHeaderValuesMap() {

		if (requestHeaderValuesMap == null) {
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			requestHeaderValuesMap = Collections.unmodifiableMap(new RequestHeaderValuesMap(bridgeContext));
		}

		return requestHeaderValuesMap;
	}

	@Override
	public Map<String, String> getRequestParameterMap() {

		if (requestParameterMap == null) {
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			requestParameterMap = contextMapFactory.getRequestParameterMap(bridgeContext);
		}

		return requestParameterMap;
	}

	@Override
	public Map<String, String[]> getRequestParameterValuesMap() {

		if (requestParameterValuesMap == null) {
			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			requestParameterValuesMap = contextMapFactory.getRequestParameterValuesMap(bridgeContext);
		}

		return requestParameterValuesMap;
	}

	@Override
	public String getRequestPathInfo() {

		String returnValue = null;

		if (requestPathInfo == null) {

			FacesView facesView = getFacesView();
			String viewId = facesView.getViewId();

			// If the facesView is extension-mapped (like *.faces), then return a null value as required by Section
			// 6.1.3.1 of the spec.
			if (facesView.isExtensionMapped()) {
				logger.debug("requestPathInfo=[null] EXTENSION=[{1}] viewId=[{2}]", facesView.getExtension(), viewId);
			}

			// Otherwise, if the facesViewId (like /faces/foo/bar/test.jspx) is path-mapped (like /faces/*), then return
			// the /foo/bar/test.jspx part as the reqestPathInfo. This is the way the path-mapped feature works -- it
			// treats the /faces/* part as a "virtual" path used to match the url-pattern of the servlet-mapping. But it
			// has to be removed from the requestPathInfo in order to provide a context-relative path to a file resource
			// that can be found by a RequestDispatcher (or in the case of portlets, a PortletRequestDispatcher).
			else if (facesView.isPathMapped()) {

				returnValue = viewId.substring(facesView.getServletPath().length());
				logger.debug("requestPathInfo=[{0}] PATH=[{1}] viewId=[{2}]", returnValue, facesView.getServletPath(),
					viewId);
			}

			// Otherwise, since it is neither extension-mapped nor path-mapped, simply return the viewId. This typically
			// occurs in a Facelets environment.
			else {
				returnValue = facesView.getViewId();
				logger.debug("requestPathInfo=[{0}] servletMapping=[NONE] viewId=[{1}]", returnValue, viewId);
			}

			// The StringWrapper is used to support the lazy-initialization of technique of this method but still
			// have the ability to return a null value.
			requestPathInfo = new StringWrapper(returnValue);
		}
		else {
			returnValue = requestPathInfo.getValue();
		}

		return returnValue;
	}

	protected String getRequestQueryString(PortletRequest portletRequest) {

		if (requestQueryString == null) {

			requestQueryString = (String) portletRequest.getAttribute(REQUEST_ATTR_QUERY_STRING);

			if (requestQueryString == null) {

				// Some portlet bridges wrap the portal's PortletRequest implementation instance (which prevents us from
				// getting the query_string). As a workaround, we might still be able to get  the original
				// PortletRequest instance, because the Portlet spec says it must be stored in the
				// "javax.portlet.request" attribute.
				Object portletRequestAsObject = portletRequest.getAttribute(REQUEST_ATTR_PORTLET_REQUEST);

				if ((portletRequestAsObject != null) && (portletRequestAsObject instanceof PortletRequest)) {
					portletRequest = (PortletRequest) portletRequestAsObject;
					requestQueryString = (String) portletRequest.getAttribute(REQUEST_ATTR_QUERY_STRING);
				}
			}
		}

		return requestQueryString;
	}

	@Override
	public String getRequestServletPath() {

		if (requestServletPath == null) {

			FacesView facesView = getFacesView();
			String viewId = facesView.getViewId();

			// If the facesView is extension-mapped (like *.faces), then simply return the viewId as required by Section
			// 6.1.3.1 of the spec. This also conforms to the behavior of the HttpServletRequest#getServletPath()
			// method.
			if (facesView.isExtensionMapped()) {
				requestServletPath = facesView.getViewId();
				logger.debug("requestServletPath=[{0}] extensionMapped=[{1}] viewId=[{2}]", requestServletPath,
					facesView.getExtension(), viewId);
			}

			// If the facesView is path-mapped (like /faces/*) then return everything up until the last forward-slash as
			// required by Section 6.1.3.1 of the spec. This also conforms to the behavior of the
			// HttpServletRequest#getServletPath() method.
			else if (facesView.isPathMapped()) {
				requestServletPath = facesView.getViewId();

				int pos = requestServletPath.lastIndexOf("/*");

				if (pos >= 0) {
					requestServletPath = requestServletPath.substring(0, pos);
				}

				logger.debug("requestServletPath=[{0}] pathMapped=[{1}] viewId=[{2}]", requestServletPath,
					facesView.getServletPath(), viewId);
			}

			// Otherwise, since there is no servlet-mapping, return an empty string. This is not required by the spec
			// but seems to work in a Facelets environment where there is no servlet-mapping.
			else {
				requestServletPath = "";
				logger.debug("requestServletPath=[{0}] servletMapping=[NONE] viewId=[{1}]", requestServletPath, viewId);
			}

		}

		return requestServletPath;
	}

	protected String getRequestURL() {

		if (requestURL == null) {

			// Note that this is an approximation (best guess) of the original URL.
			StringBuilder buf = new StringBuilder();
			PortletRequest portletRequest = getPortletRequest();
			buf.append(portletRequest.getScheme());
			buf.append("://");
			buf.append(portletRequest.getServerName());
			buf.append(":");
			buf.append(portletRequest.getServerPort());
			buf.append(portletRequest.getContextPath());
			buf.append("?");
			buf.append(getRequestQueryString(portletRequest));
			requestURL = buf.toString();
		}

		return requestURL;
	}

	@Override
	public Writer getResponseOutputWriter() throws IOException {

		if (responseOutputWriter == null) {

			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			if (portletPhase == Bridge.PortletPhase.RENDER_PHASE) {

				if (renderRedirectEnabled == null) {
					renderRedirectEnabled = PortletConfigParam.RenderRedirectEnabled.getBooleanValue(portletConfig);
				}

				if (renderRedirectEnabled) {
					responseOutputWriter = new RenderRedirectWriterImpl(mimeResponse.getWriter());
				}
				else {
					responseOutputWriter = mimeResponse.getWriter();
				}

			}
			else {
				responseOutputWriter = mimeResponse.getWriter();
			}

		}

		return responseOutputWriter;
	}

	@Override
	public boolean isPreserveActionParams() {

		if (preserveActionParams == null) {
			String initParamName = Bridge.BRIDGE_PACKAGE_PREFIX + portletConfig.getPortletName() + "." +
				Bridge.PRESERVE_ACTION_PARAMS;
			Object initParamValue = portletContext.getAttribute(initParamName);

			if ((initParamValue != null) && (initParamValue instanceof Boolean)) {
				preserveActionParams = (Boolean) initParamValue;
			}
			else {
				preserveActionParams = Boolean.FALSE;
			}
		}

		return preserveActionParams;
	}

	@Override
	public String getSavedViewState() {
		return savedViewState;
	}

	@Override
	public void setSavedViewState(String savedViewState) {
		this.savedViewState = savedViewState;
	}

	@Override
	public boolean isProcessingAfterViewContent() {
		return processingAfterViewContent;
	}

	@Override
	public boolean isRenderRedirect() {
		return renderRedirect;
	}

	protected class StringWrapper {

		private String value;

		public StringWrapper(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

}
