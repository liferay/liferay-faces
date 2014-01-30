/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowStateException;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeDefaultViewNotSpecifiedException;
import javax.portlet.faces.BridgeInvalidViewPathException;
import javax.portlet.faces.GenericFacesPortlet;

import com.liferay.faces.bridge.BridgeExt;
import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.bridge.config.ServletMapping;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.map.RequestHeaderMap;
import com.liferay.faces.bridge.context.map.RequestHeaderValuesMap;
import com.liferay.faces.bridge.context.map.RequestParameterMapFactory;
import com.liferay.faces.bridge.context.url.BridgeActionURL;
import com.liferay.faces.bridge.context.url.BridgePartialActionURL;
import com.liferay.faces.bridge.context.url.BridgePartialActionURLImpl;
import com.liferay.faces.bridge.context.url.BridgeRedirectURL;
import com.liferay.faces.bridge.context.url.BridgeResourceURL;
import com.liferay.faces.bridge.context.url.BridgeURLFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeContextImpl extends BridgeContextCompatImpl {

	// Public Constants
	public static final String ATTR_RESPONSE_NAMESPACE = "com.liferay.faces.bridge.responseNamespace";

	// Private Constants
	private static final String NON_NUMERIC_NAMESPACE_PREFIX = "A";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeContextImpl.class);

	// Private Data Members
	private Map<String, Object> attributeMap = new HashMap<String, Object>();
	private BridgeConfig bridgeConfig;
	private Boolean bridgeRequestScopePreserved;
	private BridgeRequestScope bridgeRequestScope;
	private BridgeURLFactory bridgeURLFactory;
	private String defaultRenderKitId;
	private Map<String, String> defaultViewIdMap;
	private FacesView facesView;
	private IncongruityContext incongruityContext;
	private List<String> preFacesRequestAttrNames;
	private PortletConfig portletConfig;
	private PortletContainer portletContainer;
	private PortletContext portletContext;
	private PortletRequest portletRequest;
	private Bridge.PortletPhase portletPhase;
	private PortletResponse portletResponse;
	private Boolean preserveActionParams;
	private Map<String, String[]> preservedActionParams;
	private boolean processingAfterViewContent;
	private boolean renderRedirect;
	private boolean renderRedirectAfterDispatch;
	private BridgeRedirectURL renderRedirectURL;
	private Boolean renderRedirectEnabled;
	private Map<String, String> requestHeaderMap;
	private Map<String, String[]> requestHeaderValuesMap;
	private Map<String, String> requestParameterMap;
	private RequestParameterMapFactory requestParameterMapFactory;
	private Map<String, String[]> requestParameterValuesMap;
	private StringWrapper requestPathInfo;
	private String requestServletPath;
	private String responseNamespace;
	private Writer responseOutputWriter;
	private String savedViewState;
	private String viewIdAndQueryString;

	public BridgeContextImpl(BridgeConfig bridgeConfig, BridgeRequestScope bridgeRequestScope,
		PortletConfig portletConfig, PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse, Bridge.PortletPhase portletPhase, PortletContainer portletContainer,
		IncongruityContext incongruityContext) {

		this.bridgeConfig = bridgeConfig;
		this.bridgeRequestScope = bridgeRequestScope;
		this.portletConfig = portletConfig;
		this.portletContext = portletContext;
		this.portletRequest = portletRequest;
		this.portletResponse = portletResponse;
		this.portletPhase = portletPhase;
		this.portletContainer = portletContainer;
		this.incongruityContext = incongruityContext;

		// Get the BridgeURLFactory instance.
		this.bridgeURLFactory = (BridgeURLFactory) BridgeFactoryFinder.getFactory(BridgeURLFactory.class);

		setCurrentInstance(this);
	}

	@Override
	public void dispatch(String path) throws IOException {

		logger.debug("Acquiring dispatcher for JSP path=[{0}]", path);

		PortletRequestDispatcher portletRequestDispacher = portletContext.getRequestDispatcher(path);

		try {

			if (portletRequestDispacher != null) {

				// If the underlying portlet container has the ability to forward (like Pluto), then
				if (portletContainer.isAbleToForwardOnDispatch()) {

					// If a render-redirect has occurred after dispatching to a JSP, that means that the previous
					// dispatch called PortletRequestDispatcher#forward(String) which marked the response as "complete",
					// thereby making it impossible to forward again. In such cases, need to "include" instead of
					// "forward".
					if (renderRedirectAfterDispatch) {
						portletRequestDispacher.include(portletRequest, portletResponse);
					}

					// Otherwise,
					else {

						// If running in the RESOURCE_PHASE of the portlet lifecycle, then need to "include" instead of
						// "forward" or else the markup will not be properly rendered to the ResourceResponse.
						if (portletPhase == Bridge.PortletPhase.RESOURCE_PHASE) {
							portletRequestDispacher.include(portletRequest, portletResponse);
						}

						// Otherwise, "forward" to the specified path.
						else {
							portletRequestDispacher.forward(portletRequest, portletResponse);
						}
					}
				}

				// Otherwise, must be a portlet container like Liferay, and so need to "include" the specified path.
				else {

					// Note: Liferay does not have the ability to wrap/decorate the PortletRequest and PortletResponse.
					// This makes it impossible for Liferay to support the BridgeWriteBehindResponse feature of the
					// bridge so that AFTER_VIEW_CONTENT (markup that appears after the closing </f:view> component tag)
					// renders in the correct location. It only works in Pluto.
					portletRequestDispacher.include(portletRequest, portletResponse);
				}
			}
			else {
				throw new IOException("Unable to acquire PortletRequestDispatcher for path=[" + path + "]");
			}
		}
		catch (PortletException e) {
			logger.error(e);
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public BridgeActionURL encodeActionURL(String url) {

		logger.debug("encodeActionURL fromURL=[{0}]", url);

		String currentFacesViewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();

		BridgeActionURL bridgeActionURL = bridgeURLFactory.getBridgeActionURL(url, currentFacesViewId, this);

		// Determine the target of the specified URL, which could be a Faces-View or a
		// Non-Faces-View.
		String contextRelativeViewPath = bridgeActionURL.getContextRelativePath();
		List<String> defaultSuffixes = bridgeConfig.getConfiguredExtensions();
		FacesView targetFacesView = new FacesViewImpl(contextRelativeViewPath, defaultSuffixes);

		// If the specified URL starts with "portlet:", then
		if (bridgeActionURL.isPortletScheme()) {

			// If the "_jsfBridgeViewId" URL parameter is equal to "_jsfBridgeCurrentView" then the
			// URL is self-referencing and the "_jsfBridgeViewId" parameter muse be removed from the
			// URL.
			String facesViewIdParameter = bridgeActionURL.getParameter(Bridge.FACES_VIEW_ID_PARAMETER);

			if (Bridge.FACES_USE_CURRENT_VIEW_PARAMETER.equals(facesViewIdParameter)) {
				bridgeActionURL.setSelfReferencing(true);
				bridgeActionURL.removeParameter(Bridge.FACES_VIEW_ID_PARAMETER);
			}

			// If the "_jsfBridgeViewPath" URL parameter is equal to "_jsfBridgeCurrentView" then
			// the URL is self-referencing and the "_jsfBridgeViewPath" parameter muse be removed
			// from the URL.
			String facesViewPathParameter = bridgeActionURL.getParameter(Bridge.FACES_VIEW_PATH_PARAMETER);

			if (Bridge.FACES_USE_CURRENT_VIEW_PARAMETER.equals(facesViewPathParameter)) {
				bridgeActionURL.setSelfReferencing(true);
				bridgeActionURL.removeParameter(Bridge.FACES_VIEW_PATH_PARAMETER);
			}
		}

		// Otherwise, the specified URL must be for a path-encoded URL (either a Faces-View or
		// Non-Faces-View)
		else {

			// If the specified URL has a "javax.portlet.faces.DirectLink" parameter with a value of
			// "false", then remove it from the map of parameters as required by the Bridge Spec.
			String directLinkParam = bridgeActionURL.getParameter(Bridge.DIRECT_LINK);

			if (BooleanHelper.isFalseToken(directLinkParam)) {
				bridgeActionURL.removeParameter(Bridge.DIRECT_LINK);
			}

			if (!bridgeActionURL.isAbsolute() && !targetFacesView.isExtensionMapped() &&
					!targetFacesView.isPathMapped() && !url.startsWith(StringPool.POUND)) {
				bridgeActionURL.setParameter(Bridge.NONFACES_TARGET_PATH_PARAMETER, contextRelativeViewPath);
			}

		}

		return bridgeActionURL;
	}

	@Override
	public BridgePartialActionURL encodePartialActionURL(String url) {

		logger.debug("encodePartialActionURL fromURL=[{0}]", url);

		String currentFacesViewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();

		BridgePartialActionURL bridgePartialActionURL = new BridgePartialActionURLImpl(url, currentFacesViewId, this);

		bridgePartialActionURL.setParameter(BridgeExt.FACES_AJAX_PARAMETER, Boolean.TRUE.toString());

		return bridgePartialActionURL;
	}

	@Override
	public BridgeRedirectURL encodeRedirectURL(String baseUrl, Map<String, List<String>> parameters) {

		logger.debug("encodeRedirectURL fromURL=[{0}]", baseUrl);

		String currentFacesViewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();

		return bridgeURLFactory.getBridgeRedirectURL(baseUrl, parameters, currentFacesViewId, this);
	}

	@Override
	public BridgeResourceURL encodeResourceURL(String url) {

		logger.debug("encodeResourceURL fromURL=[{0}]", url);

		String currentFacesViewId = null;
		UIViewRoot uiViewRoot = FacesContext.getCurrentInstance().getViewRoot();

		if (uiViewRoot != null) {
			currentFacesViewId = uiViewRoot.getViewId();
		}

		BridgeResourceURL bridgeResourceURL = bridgeURLFactory.getBridgeResourceURL(url, currentFacesViewId, this);

		// If the "javax.portlet.faces.ViewLink" parameter is found and set to "true", then
		String viewLinkParam = bridgeResourceURL.getParameter(Bridge.VIEW_LINK);

		if (BooleanHelper.isTrueToken(viewLinkParam)) {

			// Since this is going to be a URL that represents navigation to a different viewId,
			// need to remove the "javax.portlet.faces.ViewLink" parameter as required by the Bridge
			// Spec.
			bridgeResourceURL.removeParameter(Bridge.VIEW_LINK);

			// Set a flag indicating that this is a view-link type of navigation.
			bridgeResourceURL.setViewLink(true);

			// If the "javax.portlet.faces.BackLink" parameter is found, then replace it's value
			// with a URL that can cause navigation back to the current view.
			if (bridgeResourceURL.getParameter(Bridge.BACK_LINK) != null) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				bridgeResourceURL.replaceBackLinkParameter(facesContext);
			}
		}

		// If the specified URL is opaque, meaning it starts with something like "portlet:" or "mailto:" and
		// doesn't have the double-forward-slash like "http://" does, then
		if (bridgeResourceURL.isOpaque()) {

			// If the specified URL starts with "portlet:", then
			if (bridgeResourceURL.isPortletScheme()) {

				// If the "_jsfBridgeViewId" URL parameter is equal to "_jsfBridgeCurrentView" then the
				// URL is self-referencing and the "_jsfBridgeViewId" parameter muse be removed from the
				// URL.
				String facesViewIdParameter = bridgeResourceURL.getParameter(Bridge.FACES_VIEW_ID_PARAMETER);

				if (Bridge.FACES_USE_CURRENT_VIEW_PARAMETER.equals(facesViewIdParameter)) {
					bridgeResourceURL.setSelfReferencing(true);
					bridgeResourceURL.removeParameter(Bridge.FACES_VIEW_ID_PARAMETER);
				}

				// If the "_jsfBridgeViewPath" URL parameter is equal to "_jsfBridgeCurrentView" then
				// the URL is self-referencing and the "_jsfBridgeViewPath" parameter muse be removed
				// from the URL.
				String facesViewPathParameter = bridgeResourceURL.getParameter(Bridge.FACES_VIEW_PATH_PARAMETER);

				if (Bridge.FACES_USE_CURRENT_VIEW_PARAMETER.equals(facesViewPathParameter)) {
					bridgeResourceURL.setSelfReferencing(true);
					bridgeResourceURL.removeParameter(Bridge.FACES_VIEW_PATH_PARAMETER);
				}
			}
		}

		// Otherwise, if the specified URL is hierarchical and targets an external resource, then
		else if (bridgeResourceURL.isHierarchical() && bridgeResourceURL.isExternal()) {

			// If the "javax.portlet.faces.BackLink" parameter is found, then replace it's value with
			// a URL that can cause navigation back to the current view.
			if (bridgeResourceURL.getParameter(Bridge.BACK_LINK) != null) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				bridgeResourceURL.replaceBackLinkParameter(facesContext);
			}
		}

		// Otherwise, if the specified URL is hierarchical and targets a resource internal to this
		// application, then
		else if (bridgeResourceURL.isHierarchical() && !bridgeResourceURL.isExternal()) {

			// If the "javax.portlet.faces.BackLink" parameter is found, then replace it's value with a URL
			// that can cause navigation back to the current view.
			if (bridgeResourceURL.getParameter(Bridge.BACK_LINK) != null) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				bridgeResourceURL.replaceBackLinkParameter(facesContext);
			}

			// If the "javax.portlet.faces.InProtocolResourceLink" parameter is found, then
			if ((bridgeResourceURL.getParameter(Bridge.IN_PROTOCOL_RESOURCE_LINK) != null)) {
				bridgeResourceURL.setInProtocol(true);

				// Since an in-protocol-resource URL must be a ResourceURL, the
				// "javax.portlet.faces.PortletMode" and "javax.portlet.faces.WindowState" parameters must
				// be removed from the URL (if present) because you can change a PortletMode or WindowState
				// in a ResourceRequest.
				bridgeResourceURL.removeParameter(Bridge.PORTLET_MODE_PARAMETER);
				bridgeResourceURL.removeParameter(Bridge.PORTLET_WINDOWSTATE_PARAMETER);

				// The Bridge Spec indicates that the "javax.portlet.faces.Secure" parameter must be
				// removed but must also be used to set the security of the ResourceURL below.
				String secureParam = bridgeResourceURL.getParameter(Bridge.PORTLET_SECURE_PARAMETER);
				bridgeResourceURL.setSecure(BooleanHelper.isTrueToken(secureParam));
				bridgeResourceURL.removeParameter(Bridge.PORTLET_SECURE_PARAMETER);
			}
		}

		return bridgeResourceURL;
	}

	@Override
	public void redirect(String url) throws IOException {

		if (url != null) {
			logger.debug("redirect url=[{0}]", url);

			String currentFacesViewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();

			BridgeRedirectURL bridgeRedirectURL = bridgeURLFactory.getBridgeRedirectURL(url, null, currentFacesViewId,
					this);

			// If currently executing the ACTION_PHASE, EVENT_PHASE, or RENDER_PHASE of the portlet lifecycle, then
			if ((portletPhase == Bridge.PortletPhase.ACTION_PHASE) ||
					(portletPhase == Bridge.PortletPhase.EVENT_PHASE) ||
					(portletPhase == Bridge.PortletPhase.RENDER_PHASE)) {

				// If the specified URL starts with a "#" character, is external to this application, or has a
				// "javax.portlet.faces.DirectLink" parameter value of "true", then
				if ((portletPhase == Bridge.PortletPhase.ACTION_PHASE) &&
						(url.startsWith(StringPool.POUND) || bridgeRedirectURL.isExternal() ||
							BooleanHelper.isTrueToken(bridgeRedirectURL.getParameter(Bridge.DIRECT_LINK)))) {

					bridgeRequestScope.setRedirectOccurred(true);

					// TCK NOTE: The TCK does not appear to have a test that invokes this condition.
					portletContainer.redirect(bridgeRedirectURL.toString());
				}

				// Otherwise,
				else {

					// If currently executing the ACTION_PHASE or EVENT_PHASE of the portlet lifecycle, then
					if ((portletPhase == Bridge.PortletPhase.ACTION_PHASE) ||
							(portletPhase == Bridge.PortletPhase.EVENT_PHASE)) {

						// TCK NOTE: The TCK will invoke this condition during the
						// TestPage039-requestNoScopeOnRedirectTest and TestPage176-redirectActionTest.
						FacesContext facesContext = FacesContext.getCurrentInstance();
						String oldViewId = facesContext.getViewRoot().getViewId();
						String newViewId = bridgeRedirectURL.getContextRelativePath();

						// NOTE: Since the Portlet API assumes that the portlet container implements the
						// post-redirect-get pattern, need to treat this as a navigation from one Faces view to another.
						// In this case the client does not redirect, rather the new Faces View is rendered. Note that
						// Liferay Portal does not implement the post-redirect-get pattern, rather, the entire portlet
						// lifecycle occurs within the POST operation. So currently, for Liferay, the <redirect/> is
						// effectively ignored. :-(
						if (!oldViewId.equals(newViewId)) {

							// Create the new view and place it into the FacesContext.
							ViewHandler viewHandler = facesContext.getApplication().getViewHandler();
							UIViewRoot newViewRoot = viewHandler.createView(facesContext, newViewId);
							facesContext.setViewRoot(newViewRoot);

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
								bridgeRedirectURL.applyToResponse(stateAwareResponse);
							}
							catch (PortletModeException e) {
								logger.error(e.getMessage());
							}
							catch (WindowStateException e) {
								logger.error(e.getMessage());
							}
						}
					}

					// Otherwise, if currently executing the RENDER_PHASE of the portlet lifecycle, then
					else if (portletPhase == Bridge.PortletPhase.RENDER_PHASE) {

						// If the specified URL is for a JSF viewId, then prepare for a render-redirect.
						if (bridgeRedirectURL.isFacesViewTarget()) {
							renderRedirect = true;
							renderRedirectURL = bridgeRedirectURL;
						}

						// Otherwise,
						else {

							// If there is a URL parameter specifying a JSF viewId, then prepare for a render-redirect.
							String viewIdRenderParameterName = bridgeConfig.getViewIdRenderParameterName();
							String viewIdRenderParameterValue = bridgeRedirectURL.getParameter(
									viewIdRenderParameterName);

							if (viewIdRenderParameterValue != null) {

								// TCK TestPage 179: redirectRenderPRP1Test
								renderRedirect = true;
								viewIdRenderParameterValue = URLDecoder.decode(viewIdRenderParameterValue,
										StringPool.UTF8);
								bridgeRedirectURL = bridgeURLFactory.getBridgeRedirectURL(viewIdRenderParameterValue,
										null, currentFacesViewId, this);
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

			// Otherwise, since executing the RESOURCE_PHASE of the portlet lifecycle:
			else {

				// NOTE: The Bridge Spec indicates that the redirect is to be ignored, but JSF 2 has the ability to
				// redirect during Ajax.
				portletContainer.redirect(bridgeRedirectURL.toString());
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
		this.bridgeRequestScopePreserved = null;
		this.bridgeRequestScope = null;
		this.bridgeURLFactory = null;
		this.defaultRenderKitId = null;
		this.defaultViewIdMap = null;
		this.facesView = null;
		this.preFacesRequestAttrNames = null;
		this.portletConfig = null;
		this.portletContainer = null;
		this.portletContext = null;
		this.portletRequest = null;
		this.portletPhase = null;
		this.portletResponse = null;
		this.preserveActionParams = null;
		this.preservedActionParams = null;
		this.renderRedirect = false;
		this.renderRedirectAfterDispatch = false;
		this.renderRedirectURL = null;
		this.renderRedirectEnabled = null;
		this.requestHeaderMap = null;
		this.requestHeaderValuesMap = null;
		this.requestParameterMap = null;
		this.requestParameterMapFactory = null;
		this.requestParameterValuesMap = null;
		this.requestPathInfo = null;
		this.requestServletPath = null;
		this.responseNamespace = null;
		this.responseOutputWriter = null;
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

	public void setBridgeRequestScopePreserved(boolean bridgeRequestScopePreserved) {
		this.bridgeRequestScopePreserved = bridgeRequestScopePreserved;
	}

	@Override
	public boolean isBridgeRequestScopePreserved() {

		if (bridgeRequestScopePreserved == null) {

			// NOTE: The defaultValue of false deviates from the proposed Spec which has a default of true.
			// See: http://issues.liferay.com/browse/FACES-219
			boolean defaultValue = false;
			String initParam = getInitParameter(BridgeConfigConstants.PARAM_BRIDGE_REQUEST_SCOPE_PRESERVED1);

			if (initParam == null) {

				// Backwards compatibility
				initParam = getInitParameter(BridgeConfigConstants.PARAM_BRIDGE_REQUEST_SCOPE_PRESERVED2);
			}

			bridgeRequestScopePreserved = BooleanHelper.toBoolean(initParam, defaultValue);
		}

		return bridgeRequestScopePreserved;
	}

	@Override
	public String getDefaultRenderKitId() {

		if (defaultRenderKitId == null) {
			String attributeName = Bridge.BRIDGE_PACKAGE_PREFIX + portletConfig.getPortletName() + StringPool.PERIOD +
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
				int pos = fullViewId.indexOf(StringPool.QUESTION);

				if (pos > 0) {
					navigationQueryString = fullViewId.substring(pos + 1);
					viewId = fullViewId.substring(0, pos);
				}
				else {
					viewId = fullViewId;
				}
			}

			facesView = new FacesViewImpl(viewId, navigationQueryString, bridgeConfig.getConfiguredExtensions());
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
			viewIdAndQueryString = (String) portletRequest.getAttribute(Bridge.VIEW_ID);

			if (viewIdAndQueryString == null) {

				// Try#2: Get the viewId from the "javax.portlet.faces.viewPath" request attribute.
				String viewPath = (String) portletRequest.getAttribute(Bridge.VIEW_PATH);

				if (viewPath != null) {

					// If present, remove the query string from the specified viewPath.
					int pos = viewPath.indexOf(StringPool.QUESTION);

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
						String requestParameterName = null;

						if (portletPhase == Bridge.PortletPhase.RESOURCE_PHASE) {
							requestParameterName = bridgeConfig.getViewIdResourceParameterName();
						}
						else {
							requestParameterName = bridgeConfig.getViewIdRenderParameterName();
						}

						viewIdAndQueryString = portletRequest.getParameter(requestParameterName);

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
		List<ServletMapping> servletMappings = bridgeConfig.getFacesServletMappings();

		// For each servlet-mapping:
		for (ServletMapping servletMapping : servletMappings) {

			// If the curent servlet-mapping matches the viewPath, then
			logger.debug("Attempting to determine the facesViewId from {0}=[{1}]", Bridge.VIEW_PATH, viewPath);

			if (servletMapping.isMatch(viewPath)) {

				// If the servlet-mapping is extension mapped (like *.faces or *.jsf), then
				if (servletMapping.isExtensionMapped()) {

					// Iterate through each of the valid extensions (.jsp, .jspx, etc.) that the developer
					// may have specified in the web.xml descriptor. For each extension, see if file exists
					// within the filesystem of this context.
					List<String> defaultSuffixes = bridgeConfig.getConfiguredExtensions();

					for (String defaultSuffix : defaultSuffixes) {

						int pos = viewPath.lastIndexOf(StringPool.PERIOD);

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
							"Matched EXTENSION MAPPING for for urlPattern=[{0}] and viewPath=[{1}] but unable to find a facesViewId with extensions[{3}]",
							servletMapping.getUrlPattern(), viewPath, defaultSuffixes);
					}
				}

				// Otherwise, if the servlet-mapping is path-mapped, then
				else if (servletMapping.isPathMapped()) {
					facesViewId = viewPath;
				}

				if (facesViewId != null) {
					break;
				}
			}
		}

		return facesViewId;
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
	public PortletContainer getPortletContainer() {
		return portletContainer;
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
		this.requestParameterMapFactory = null;
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
	public BridgeRedirectURL getRenderRedirectURL() {
		return renderRedirectURL;
	}

	@Override
	public void setRenderRedirectURL(BridgeRedirectURL renderRedirectURL) {
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
			requestHeaderValuesMap = Collections.unmodifiableMap(new RequestHeaderValuesMap(this,
						getRequestParameterMap()));
		}

		return requestHeaderValuesMap;
	}

	@Override
	public Map<String, String> getRequestParameterMap() {

		if (requestParameterMap == null) {
			requestParameterMap = getRequestParameterMapFactory().getRequestParameterMap();
		}

		return requestParameterMap;
	}

	protected RequestParameterMapFactory getRequestParameterMapFactory() {

		if (requestParameterMapFactory == null) {
			requestParameterMapFactory = new RequestParameterMapFactory(this);
		}

		return requestParameterMapFactory;
	}

	@Override
	public Map<String, String[]> getRequestParameterValuesMap() {

		if (requestParameterValuesMap == null) {
			requestParameterValuesMap = getRequestParameterMapFactory().getRequestParameterValuesMap();
		}

		return requestParameterValuesMap;
	}

	@Override
	public String getRequestPathInfo() {

		String returnValue;

		if (requestPathInfo == null) {

			FacesView facesView = getFacesView();
			String viewId = facesView.getViewId();

			// If the facesView is extension-mapped (like *.faces), then return a null value as required by Section
			// 6.1.3.1 of the spec.
			if (facesView.isExtensionMapped()) {
				returnValue = null;
				logger.debug("requestPathInfo=[{0}] EXTENSION=[{1}] viewId=[{2}]", returnValue,
					facesView.getExtension(), viewId);
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

				int pos = requestServletPath.lastIndexOf(StringPool.FORWARD_SLASH + StringPool.STAR);

				if (pos >= 0) {
					requestServletPath = requestServletPath.substring(0, pos);
				}

				logger.debug("requestServletPath=[{0}] pathMapped=[{1}] viewId=[{2}]", requestServletPath,
					facesView.getServletPath(), viewId);
			}

			// Otherwise, since there is no servlet-mapping, return an empty string. This is not required by the spec
			// but seems to work in a Facelets environment where there is no servlet-mapping.
			else {
				requestServletPath = StringPool.BLANK;
				logger.debug("requestServletPath=[{0}] servletMapping=[NONE] viewId=[{1}]", requestServletPath, viewId);
			}

		}

		return requestServletPath;
	}

	@Override
	public String getResponseNamespace() {

		if (responseNamespace == null) {

			// If the namespace should be optimized (minimized), then perform the optimization.
			String optimizePortletNamespaceInitParam = getInitParameter(
					BridgeConfigConstants.PARAM_OPTIMIZE_PORTLET_NAMESPACE1);

			if (optimizePortletNamespaceInitParam == null) {

				// Backward compatibility
				optimizePortletNamespaceInitParam = getInitParameter(
						BridgeConfigConstants.PARAM_OPTIMIZE_PORTLET_NAMESPACE2);
			}

			// Initialize the response namespace.
			responseNamespace = portletContainer.getResponseNamespace();

			boolean optimizePortletNamespace = BooleanHelper.toBoolean(optimizePortletNamespaceInitParam, false);

			if (optimizePortletNamespace) {

				// Since the namespace is going to appear in every single clientId and name attribute of the rendered
				// view, this needs to be shortened as much as possible -- four characters should be enough to keep the
				// namespace unique.
				int hashCode = responseNamespace.hashCode();

				if (hashCode < 0) {
					hashCode = hashCode * -1;
				}

				String namespaceHashCode = Integer.toString(hashCode);
				int namespaceHashCodeLength = namespaceHashCode.length();

				if (namespaceHashCodeLength > 4) {

					// FACES-67: Taking the last four characters is more likely to force uniqueness than the first four.
					namespaceHashCode = namespaceHashCode.substring(namespaceHashCodeLength - 4);
				}

				if (namespaceHashCode.length() < responseNamespace.length()) {

					// Note that unless we prepend the hash namespace with some non-numeric string, IE might encounter
					// JavaScript problems with ICEfaces. http://issues.liferay.com/browse/FACES-12
					responseNamespace = NON_NUMERIC_NAMESPACE_PREFIX + namespaceHashCode;
				}
			}
		}

		return responseNamespace;
	}

	@Override
	public Writer getResponseOutputWriter() throws IOException {

		if (responseOutputWriter == null) {

			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			if (portletPhase == Bridge.PortletPhase.RENDER_PHASE) {

				if (renderRedirectEnabled == null) {
					renderRedirectEnabled = BooleanHelper.isTrueToken(BridgeContext.getCurrentInstance()
							.getInitParameter(BridgeConfigConstants.PARAM_RENDER_REDIRECT_ENABLED));
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
			String initParamName = Bridge.BRIDGE_PACKAGE_PREFIX + portletConfig.getPortletName() + StringPool.PERIOD +
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
