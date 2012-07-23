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
package com.liferay.faces.bridge.context;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.portlet.ClientDataRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.ResourceResponse;
import javax.portlet.StateAwareResponse;
import javax.portlet.faces.BridgeWriteBehindResponse;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.application.view.BridgeAfterViewContentRequest;
import com.liferay.faces.bridge.application.view.BridgeAfterViewContentResponse;
import com.liferay.faces.bridge.application.view.BridgeWriteBehindResponseFactory;
import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.bridge.context.map.ApplicationMap;
import com.liferay.faces.bridge.context.map.InitParameterMap;
import com.liferay.faces.bridge.context.map.RequestAttributeMap;
import com.liferay.faces.bridge.context.map.RequestCookieMap;
import com.liferay.faces.bridge.context.map.SessionMap;
import com.liferay.faces.bridge.lifecycle.CongruousTask;
import com.liferay.faces.bridge.lifecycle.LifecycleIncongruityManager;
import com.liferay.faces.bridge.lifecycle.LifecycleIncongruityMap;
import com.liferay.faces.bridge.util.LocaleIterator;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ExternalContextImpl extends ExternalContextCompatImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ExternalContextImpl.class);

	// Constructor-initialized objects
	private boolean manageIncongruities;

	// Pre-initialized Data Members
	private ApplicationMap applicationMap;
	private BridgeContext bridgeContext;
	private Map<String, Object> requestAttributeMap;
	private Iterator<Locale> requestLocales;
	private Map<String, Object> sessionMap;

	// Lazily-Initialized Data Members
	private String authType;
	private Map<String, String> initParameterMap;
	private String remoteUser;
	private Map<String, Object> requestCookieMap;
	private Locale requestLocale;
	private String responseContentType;
	private Principal userPrincipal;

	public ExternalContextImpl(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		super(portletContext, portletRequest, portletResponse);

		try {
			boolean requestChanged = false;
			boolean responseChanged = false;
			preInitializeObjects(requestChanged, responseChanged);
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void dispatch(String path) throws IOException {
		bridgeContext.dispatch(path);
	}

	@Override
	public String encodeActionURL(String url) {

		if (isEncodingFormWithPrimeFacesAjaxFileUpload()) {
			return encodePartialActionURL(url);
		}
		else {
			return bridgeContext.encodeActionURL(url).toString();
		}
	}

	/**
	 * @see  ExternalContext#encodeNamespace(String)
	 */
	@Override
	public String encodeNamespace(String name) {
		return bridgeContext.getResponseNamespace() + name;
	}

	/**
	 * @see  ExternalContext#encodeResourceURL(String)
	 */
	@Override
	public String encodeResourceURL(String url) {
		return bridgeContext.encodeResourceURL(url).toString();
	}

	@Override
	public void log(String message) {
		portletContext.log(message);
	}

	@Override
	public void log(String message, Throwable exception) {
		portletContext.log(message, exception);
	}

	@Override
	public void redirect(String url) throws IOException {
		bridgeContext.redirect(url);
	}

	/**
	 * In order to increase runtime performance, this method caches values of objects that are typically called more
	 * than once during the JSF lifecycle. Other values will be cached lazily, or might not be cached since their getter
	 * methods may never get called.
	 *
	 * @param  requestChanged   Flag indicating that this method is being called because {@link #setRequest(Object)} was
	 *                          called.
	 * @param  responseChanged  Flag indicating that this method is being called because {@link #setResponse(Object)}
	 *                          was called.
	 */
	protected void preInitializeObjects(boolean requestChanged, boolean responseChanged) {

		// Get the BridgeContext.
		bridgeContext = BridgeContext.getCurrentInstance();

		// Determines whether or not lifecycle incongruities should be managed.
		manageIncongruities = BooleanHelper.toBoolean(getInitParameter(
					BridgeConfigConstants.PARAM_MANAGE_INCONGRUITIES), true);

		if (requestChanged) {
			bridgeContext.setPortletRequest(portletRequest);
		}

		if (responseChanged) {
			bridgeContext.setPortletResponse(portletResponse);
		}

		// Retrieve the portlet lifecycle phase.
		portletPhase = bridgeContext.getPortletRequestPhase();

		// Determines whether or not methods annotated with the &#064;PreDestroy annotation are preferably invoked
		// over the &#064;BridgePreDestroy annotation.
		String preferPreDestroyInitParam = getInitParameter(BridgeConfigConstants.PARAM_PREFER_PRE_DESTROY1);

		if (preferPreDestroyInitParam == null) {

			// Backward compatibility
			preferPreDestroyInitParam = getInitParameter(BridgeConfigConstants.PARAM_PREFER_PRE_DESTROY2);
		}

		boolean preferPreDestroy = BooleanHelper.toBoolean(preferPreDestroyInitParam, true);

		// Initialize the application map.
		applicationMap = new ApplicationMap(portletContext, preferPreDestroy);

		// Initialize the request attribute map.
		requestAttributeMap = new RequestAttributeMap(portletRequest, preferPreDestroy);

		// Initialize the session map.
		sessionMap = new SessionMap(portletRequest.getPortletSession(), PortletSession.PORTLET_SCOPE, preferPreDestroy);

		// Initialize the map for storing portlet lifecycle incongruities.
		lifecycleIncongruityMap = new LifecycleIncongruityMap(applicationMap, manageIncongruities);

		// Initialize the portlet lifecycle incogruity manager.
		lifecycleIncongruityManager = new LifecycleIncongruityManager(lifecycleIncongruityMap, manageIncongruities);

		// Initialize the init parameter map.
		initParameterMap = Collections.unmodifiableMap(new InitParameterMap(portletContext));

		// Initialize the request context path.
		requestContextPath = portletRequest.getContextPath();

		// Initialize the request locales.
		requestLocales = new LocaleIterator(portletRequest.getLocales());

		// Initialize the response content type.
		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			responseContentType = mimeResponse.getContentType();
		}
	}

	@Override
	public Map<String, Object> getApplicationMap() {
		return applicationMap;
	}

	@Override
	public String getAuthType() {

		if (authType == null) {
			authType = portletRequest.getAuthType();
		}

		return authType;
	}

	@Override
	public Object getContext() {
		return portletContext;
	}

	@Override
	public boolean isUserInRole(String role) {
		return portletRequest.isUserInRole(role);
	}

	@Override
	public String getInitParameter(String name) {
		return bridgeContext.getInitParameter(name);
	}

	@Override
	public Map<String, String> getInitParameterMap() {
		return initParameterMap;
	}

	@Override
	public String getRemoteUser() {

		if (remoteUser == null) {
			remoteUser = portletRequest.getRemoteUser();
		}

		return remoteUser;
	}

	@Override
	public Object getRequest() {

		// If JSP AFTER_VIEW_CONTENT processing has been activated by the
		// JspViewDeclarationLanguage#buildView(FacesContext, UIViewRoot) method, then return a ServletRequest that
		// wraps/decorates the current PortletRequest. This is necessary because the MyFaces
		// JspViewDeclarationLanguage#buildView(FacesContext, UIViewRoot) method has a Servlet API dependency due to
		// explicit casts to HttpServletRequest.
		if (bridgeContext.isProcessingAfterViewContent()) {

			logger.debug("Detected JSP AFTER_VIEW_CONTENT processing as activated");

			return new BridgeAfterViewContentRequest(portletRequest);
		}
		else {
			return portletRequest;
		}
	}

	@Override
	public void setRequest(Object request) {
		this.portletRequest = (PortletRequest) request;

		try {
			boolean requestChanged = true;
			boolean responseChanged = false;
			preInitializeObjects(requestChanged, responseChanged);
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public String getRequestCharacterEncoding() {
		String requestCharacterEncoding = null;

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;
			requestCharacterEncoding = clientDataRequest.getCharacterEncoding();
			lifecycleIncongruityMap.putRequestCharacterEncoding(requestCharacterEncoding);
		}
		else {
			requestCharacterEncoding = lifecycleIncongruityMap.getRequestCharacterEncoding();
		}

		return requestCharacterEncoding;
	}

	@Override
	public void setRequestCharacterEncoding(String encoding) throws UnsupportedEncodingException,
		IllegalStateException {

		// Although the JSF API's ViewHandler.initView(FacesContext) method will call this method during the portlet
		// RENDER_PHASE, the RenderRequest does not implement the ClientDataRequest interface, which means it does not
		// have a setCharacterEncoding(String) method, therefore this should only be done in the case of a
		// ClientDataRequest.
		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			try {
				clientDataRequest.setCharacterEncoding(encoding);
			}
			catch (IllegalStateException e) {
				// TestPage141: setRequestCharacterEncodingActionTest -- exception is to be ignored
			}
		}
		else {
			lifecycleIncongruityManager.addCongruousTask(CongruousTask.SET_REQUEST_CHARACTER_ENCODING);
		}

		lifecycleIncongruityMap.putRequestCharacterEncoding(encoding);
	}

	@Override
	public String getRequestContentType() {
		String requestContentType = null;

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			// If using ICEfaces 3.0.x/2.0.x then need to return the legacy value.
			// http://issues.liferay.com/browse/FACES-1228
			if (isICEfacesLegacyMode(clientDataRequest)) {
				requestContentType = clientDataRequest.getResponseContentType();
			}
			else {
				requestContentType = clientDataRequest.getContentType();
			}

			lifecycleIncongruityMap.putRequestContentType(requestContentType);
		}
		else {
			requestContentType = lifecycleIncongruityMap.getRequestContentType();
		}

		return requestContentType;
	}

	@Override
	public String getRequestContextPath() {
		return requestContextPath;
	}

	@Override
	public Map<String, Object> getRequestCookieMap() {

		if (requestCookieMap == null) {
			requestCookieMap = new RequestCookieMap(portletRequest.getCookies());
		}

		return requestCookieMap;
	}

	@Override
	public Map<String, String> getRequestHeaderMap() {
		return bridgeContext.getRequestHeaderMap();
	}

	@Override
	public Map<String, String[]> getRequestHeaderValuesMap() {
		return bridgeContext.getRequestHeaderValuesMap();
	}

	@Override
	public Locale getRequestLocale() {

		if (requestLocale == null) {
			requestLocale = portletRequest.getLocale();
		}

		return requestLocale;
	}

	@Override
	public Iterator<Locale> getRequestLocales() {
		return requestLocales;
	}

	@Override
	public Map<String, Object> getRequestMap() {
		return requestAttributeMap;
	}

	@Override
	public Map<String, String> getRequestParameterMap() {
		return bridgeContext.getRequestParameterMap();
	}

	@Override
	public Iterator<String> getRequestParameterNames() {
		return getRequestParameterMap().keySet().iterator();
	}

	@Override
	public Map<String, String[]> getRequestParameterValuesMap() {
		return bridgeContext.getRequestParameterValuesMap();
	}

	/**
	 * This method returns the relative path to the viewId that is to be rendered.
	 *
	 * @see  javax.faces.context.ExternalContext#getRequestPathInfo()
	 */
	@Override
	public String getRequestPathInfo() {
		return bridgeContext.getRequestPathInfo();
	}

	/**
	 * Section 6.1.3.1 of the JSR 329 spec describes the logic for this method.
	 */
	@Override
	public String getRequestServletPath() {
		return bridgeContext.getRequestServletPath();
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		return portletContext.getResource(path);
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		return portletContext.getResourceAsStream(path);
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		return portletContext.getResourcePaths(path);
	}

	@Override
	public Object getResponse() {

		// If JSP AFTER_VIEW_CONTENT processing has been activated by the
		// JspViewDeclarationLanguage#buildView(FacesContext, UIViewRoot) method, then return a ServletResponse that is
		// able to handle the AFTER_VIEW_CONTENT feature. This is necessary because the Mojarra
		// JspViewHandlingStrategy#getWrapper(ExternalContext) method has a Servlet API dependency due to explicit casts
		// to HttpServletResponse. Additionally, the MyFaces JspViewDeclarationLanguage#buildView(FacesContext,
		// UIViewRoot) method has an explicit cast to HttpServletResponse.
		if (bridgeContext.isProcessingAfterViewContent()) {

			logger.debug("Detected JSP AFTER_VIEW_CONTENT feature as activated");

			if (facesImplementationServletResponse == null) {
				return new BridgeAfterViewContentResponse(portletResponse, getRequestLocale());
			}
			else {
				return facesImplementationServletResponse;
			}
		}
		else {

			if (isBridgeFlashServletResponseRequired()) {
				return createFlashHttpServletResponse();
			}
			else {
				return portletResponse;
			}
		}
	}

	@Override
	public void setResponse(Object response) {

		// Assume that the JSP_AFTER_VIEW_CONTENT feature is deactivated.
		facesImplementationServletResponse = null;

		// If JSP AFTER_VIEW_CONTENT processing has been activated by the bridge's
		// ViewDeclarationLanguageJspImpl#buildView(FacesContext, UIViewRoot) method, then wrap the specified response
		// object with a ServletResponse that is able to handle the AFTER_VIEW_CONTENT feature. This is necessary
		// because the Mojarra JspViewHandlingStrategy#getWrapper(ExternalContext) method has a Servlet API dependency
		// due to explicit casts to HttpServletResponse.
		if (bridgeContext.isProcessingAfterViewContent()) {

			// If the specified response is of type HttpServletResponseWrapper, then it is almost certain that Mojarra's
			// JspViewHandlingStrategy#executePageToBuildView(FacesContext, UIViewRoot) method is attempting to wrap the
			// bridge's response object (that it originally got by calling the ExternalContext#getResponse() method)
			// with it's ViewHandlerResponseWrapper, which extends HttpServletResponseWrapper.
			if (response instanceof HttpServletResponseWrapper) {

				this.facesImplementationServletResponse = (ServletResponse) response;

				HttpServletResponseWrapper httpServletResponseWrapper = (HttpServletResponseWrapper) response;
				ServletResponse wrappedServletResponse = httpServletResponseWrapper.getResponse();

				if (wrappedServletResponse instanceof BridgeAfterViewContentResponse) {
					BridgeAfterViewContentResponse bridgeAfterViewContentPreResponse = (BridgeAfterViewContentResponse)
						wrappedServletResponse;
					PortletResponse wrappedPortletResponse = bridgeAfterViewContentPreResponse.getWrapped();

					BridgeWriteBehindResponseFactory bridgeWriteBehindResponseFactory =
						(BridgeWriteBehindResponseFactory) BridgeFactoryFinder.getFactory(
							BridgeWriteBehindResponseFactory.class);
					BridgeWriteBehindResponse bridgeWriteBehindResponse =
						bridgeWriteBehindResponseFactory.getBridgeWriteBehindResponse((MimeResponse)
							wrappedPortletResponse, facesImplementationServletResponse);

					// Note: See comments in BridgeContextImpl#dispatch(String) regarding Liferay's inability to
					// accept a wrapped response. This is indeed supported in Pluto.
					this.portletResponse = (PortletResponse) bridgeWriteBehindResponse;
				}
				else {

					// Since we're unable to determine the wrapped PortletResponse, the following line will throw an
					// intentional ClassCastException. Note that this case should never happen.
					this.portletResponse = (PortletResponse) response;
				}
			}

			// Otherwise, the specified response is of type BridgeAfterViewContentResponse, then Mojarra's
			// JspViewHandlingStrategy#executePageToBuildView(FacesContext, UIViewRoot) method is trying to restore the
			// bridge's response object that it originally got from calling the ExternalContext#getResponse() method
			// prior to wrapping with it's ViewHandlerResponseWrapper.
			else if (response instanceof BridgeAfterViewContentResponse) {
				BridgeAfterViewContentResponse bridgeAfterViewContentResponse = (BridgeAfterViewContentResponse)
					response;
				this.portletResponse = bridgeAfterViewContentResponse.getWrapped();
			}

			// Otherwise, assume that the specified response is a PortletResponse.
			else {
				this.portletResponse = (PortletResponse) response;
			}

		}

		// Otherwise, since the JSF AFTER_VIEW_CONTENT feature is not activated, assume that the specified response is
		// a PortletResponse.
		else {
			this.portletResponse = (PortletResponse) response;
		}

		try {
			boolean requestChanged = false;
			boolean responseChanged = true;
			preInitializeObjects(requestChanged, responseChanged);
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public String getResponseCharacterEncoding() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			String characterEncoding = mimeResponse.getCharacterEncoding();
			lifecycleIncongruityMap.putResponseCharacterEncoding(characterEncoding);

			return characterEncoding;
		}
		else {

			if (manageIncongruities) {
				return lifecycleIncongruityMap.getResponseCharacterEncoding();
			}
			else {

				if (portletResponse instanceof StateAwareResponse) {

					// TCK TestPage169: getResponseCharacterEncodingActionTest
					// TCK TestPage180: getResponseCharacterEncodingEventTest
					throw new IllegalStateException();
				}
				else {
					return null;
				}
			}
		}
	}

	/**
	 * @see  ExternalContext#setResponseCharacterEncoding(String)
	 */
	@Override
	public void setResponseCharacterEncoding(String encoding) {

		if (encoding != null) {

			if (portletResponse instanceof ResourceResponse) {
				ResourceResponse resourceResponse = (ResourceResponse) portletResponse;
				resourceResponse.setCharacterEncoding(encoding);
			}
			else {
				lifecycleIncongruityManager.addCongruousTask(CongruousTask.SET_RESPONSE_CHARACTER_ENCODING);
			}

			lifecycleIncongruityMap.putResponseCharacterEncoding(encoding);
		}
	}

	/**
	 * @see  javax.faces.context.ExternalContext#getResponseContentType()
	 */
	@Override
	public String getResponseContentType() {

		if (portletResponse instanceof StateAwareResponse) {

			// TCK TestPage173: getResponseContentTypeActionTest
			// TCK TestPage174: getResponseContentTypeEventTest
			throw new IllegalStateException();
		}
		else {
			return responseContentType;
		}
	}

	/**
	 * @see  {@link ExternalContext#getSession(boolean)}
	 */
	@Override
	public Object getSession(boolean create) {
		return portletRequest.getPortletSession(create);
	}

	@Override
	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}

	@Override
	public Principal getUserPrincipal() {

		if (userPrincipal == null) {
			userPrincipal = portletRequest.getUserPrincipal();
		}

		return userPrincipal;
	}
}
