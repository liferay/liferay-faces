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
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.portlet.ClientDataRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.ResourceResponse;
import javax.portlet.StateAwareResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeWriteBehindResponse;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponseWrapper;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.application.view.BridgeAfterViewContentRequest;
import com.liferay.faces.bridge.application.view.BridgeAfterViewContentResponse;
import com.liferay.faces.bridge.application.view.BridgeWriteBehindResponseFactory;
import com.liferay.faces.bridge.component.primefaces.PrimeFacesFileUpload;
import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.bridge.config.BridgeConfigFactory;
import com.liferay.faces.bridge.config.Product;
import com.liferay.faces.bridge.config.ProductMap;
import com.liferay.faces.bridge.context.flash.BridgeFlash;
import com.liferay.faces.bridge.context.flash.BridgeFlashFactory;
import com.liferay.faces.bridge.context.flash.FlashHttpServletResponse;
import com.liferay.faces.bridge.context.map.ApplicationMap;
import com.liferay.faces.bridge.context.map.InitParameterMap;
import com.liferay.faces.bridge.context.map.RequestAttributeMap;
import com.liferay.faces.bridge.context.map.RequestCookieMap;
import com.liferay.faces.bridge.context.map.SessionMap;
import com.liferay.faces.bridge.helper.BooleanHelper;
import com.liferay.faces.bridge.lifecycle.CongruousTask;
import com.liferay.faces.bridge.lifecycle.LifecycleIncongruityManager;
import com.liferay.faces.bridge.lifecycle.LifecycleIncongruityMap;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.bridge.util.FileNameUtil;
import com.liferay.faces.bridge.util.LocaleIterator;


/**
 * @author  Neil Griffin
 */
public class ExternalContextImpl extends ExternalContext {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ExternalContextImpl.class);

	// Private Constants
	private static final String COOKIE_PROPERTY_COMMENT = "comment";
	private static final String COOKIE_PROPERTY_DOMAIN = "domain";
	private static final String COOKIE_PROPERTY_MAX_AGE = "maxAge";
	private static final String COOKIE_PROPERTY_PATH = "path";
	private static final String COOKIE_PROPERTY_SECURE = "secure";

	// Private Data Members
	private PortletContext portletContext;
	private PortletRequest portletRequest;
	private PortletResponse portletResponse;

	// Constructor-initialized objects
	private boolean manageIncongruities;

	// Pre-initialized objects
	private ApplicationMap applicationMap;
	private BridgeConfig bridgeConfig;
	private BridgeContext bridgeContext;
	private LifecycleIncongruityMap lifecycleIncongruityMap;
	private LifecycleIncongruityManager lifecycleIncongruityManager;
	private Bridge.PortletPhase portletPhase;
	private Map<String, Object> requestAttributeMap;
	private String requestContextPath;
	private Iterator<Locale> requestLocales;
	private Map<String, Object> sessionMap;

	// Lazily-initialized objects
	private ServletResponse facesImplementationServletResponse;
	private String authType;
	private BridgeFlash bridgeFlash;
	private Boolean iceFacesLegacyMode;
	private Map<String, String> initParameterMap;
	private String portletContextName;
	private String remoteUser;
	private Map<String, Object> requestCookieMap;
	private Locale requestLocale;
	private String responseContentType;
	private Principal userPrincipal;

	public ExternalContextImpl(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {
		this.portletContext = portletContext;
		this.portletRequest = portletRequest;
		this.portletResponse = portletResponse;

		try {

			// Get the bridge configuration.
			BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
					BridgeConfigFactory.class);
			bridgeConfig = bridgeConfigFactory.getBridgeConfig();

			boolean requestChanged = false;
			boolean responseChanged = false;
			preInitializeObjects(requestChanged, responseChanged);
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void addResponseCookie(String name, String value, Map<String, Object> properties) {
		Cookie cookie = new Cookie(name, value);

		if ((properties != null) && !properties.isEmpty()) {

			try {
				cookie.setComment((String) properties.get(COOKIE_PROPERTY_COMMENT));
				cookie.setDomain((String) properties.get(COOKIE_PROPERTY_DOMAIN));
				cookie.setMaxAge((Integer) properties.get(COOKIE_PROPERTY_MAX_AGE));
				cookie.setPath((String) properties.get(COOKIE_PROPERTY_PATH));
				cookie.setSecure((Boolean) properties.get(COOKIE_PROPERTY_SECURE));
			}
			catch (ClassCastException e) {
				logger.error(e.getMessage(), e);
			}
		}

		portletResponse.addProperty(cookie);
	}

	/**
	 * @see  ExternalContext#addResponseHeader(String, String)
	 */
	@Override
	public void addResponseHeader(String name, String value) {

		if (portletResponse instanceof ResourceResponse) {
			ResourceResponse resourceResponse = (ResourceResponse) portletResponse;
			resourceResponse.addProperty(name, value);
		}
		else {
			logger.warn("Unable to call {0} for portletResponse=[{1}] because it is not a ResourceResponse.",
				"portletResponse.addProperty(String, String)", portletResponse.getClass().getName());
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

	@Override
	public String encodeBookmarkableURL(String baseUrl, Map<String, List<String>> parameters) {

		String renderURL = null;

		if (baseUrl != null) {
			String viewId = baseUrl;

			if (baseUrl.startsWith(requestContextPath)) {
				viewId = baseUrl.substring(requestContextPath.length());
			}

			try {

				if ((portletPhase == Bridge.PortletPhase.RENDER_PHASE) ||
						(portletPhase == Bridge.PortletPhase.RESOURCE_PHASE)) {
					PortletURL portletRenderURL = bridgeContext.getPortletContainer().createRenderURL(baseUrl);
					portletRenderURL.setParameter(bridgeConfig.getViewIdRenderParameterName(), viewId);

					if (parameters != null) {

						for (Map.Entry<String, List<String>> parameter : parameters.entrySet()) {
							String name = parameter.getKey();

							if (name != null) {
								List<String> values = parameter.getValue();

								if (values != null) {
									int size = values.size();

									if (size > 0) {

										if (size == 1) {
											String value = values.get(0);

											if (value != null) {
												portletRenderURL.setParameter(name, value);
											}
										}
										else {
											logger.warn("Unable to append multiple values for parameter name=[{0]",
												name);
										}
									}
								}
							}
						}
					}

					renderURL = portletRenderURL.toString();
				}
				else {
					logger.error(
						"Unable to encode bookmarkable URL during Bridge.PortletPhase.[{0}] -- you should call BridgeUtil.getPortletRequestPhase() and check for Bridge.PortletPhase.RENDER_PHASE or Bridge.PortletPhase.RESOURCE_PHASE before calling ExternalContext.encodeBookmarkableURL(...).",
						portletPhase);
				}
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		else {
			logger.warn("Unable to encode RenderURL for url=[null]");
		}

		return renderURL;
	}

	/**
	 * @see  ExternalContext#encodeNamespace(String)
	 */
	@Override
	public String encodeNamespace(String name) {
		return bridgeContext.getResponseNamespace() + name;
	}

	/**
	 * @see  ExternalContext#encodePartialActionURL(String)
	 */
	@Override
	public String encodePartialActionURL(String url) {
		return bridgeContext.encodePartialActionURL(url).toString();
	}

	@Override
	public String encodeRedirectURL(String baseUrl, Map<String, List<String>> parameters) {
		return bridgeContext.encodeRedirectURL(baseUrl, parameters).toString();
	}

	/**
	 * @see  ExternalContext#encodeResourceURL(String)
	 */
	@Override
	public String encodeResourceURL(String url) {
		return bridgeContext.encodeResourceURL(url).toString();
	}

	@Override
	public void invalidateSession() {
		portletRequest.getPortletSession().invalidate();
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

	@Override
	public void responseFlushBuffer() throws IOException {

		if (portletResponse instanceof MimeResponse) {

			if (facesImplementationServletResponse != null) {

				// This happens when Mojarra's JspViewHandlingStrategy#buildView(FacesContext context, UIViewRoot)
				// executes.
				facesImplementationServletResponse.flushBuffer();
			}
			else {
				MimeResponse mimeResponse = (MimeResponse) portletResponse;
				mimeResponse.flushBuffer();
			}
		}
		else {
			lifecycleIncongruityManager.addCongruousTask(CongruousTask.RESPONSE_FLUSH_BUFFER);
		}
	}

	@Override
	public void responseReset() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			mimeResponse.reset();
		}
		else {
			lifecycleIncongruityManager.addCongruousTask(CongruousTask.RESPONSE_RESET);
		}
	}

	/**
	 * The Portlet API does not have an equivalent to {@link HttpServletResponse.sendError(int, String)}. Since the
	 * Mojarra JSF implementation basically only calls this when a Facelet is not found, better in a portlet environment
	 * to simply log an error and throw an IOException up the call stack so that the portlet will give the portlet
	 * container a chance to render an error message.
	 */
	@Override
	public void responseSendError(int statusCode, String message) throws IOException {
		String errorMessage = "Status code " + statusCode + ": " + message;
		logger.error(errorMessage);
		throw new IOException(errorMessage);
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

	// NOTE: PROPOSED-FOR-JSR344-API
	// http://java.net/jira/browse/JAVASERVERFACES_SPEC_PUBLIC-1070
	// NOTE: PROPOSED-FOR-BRIDGE3-API (Called by BridgeRequestScope in order to restore the Flash scope)
	// https://issues.apache.org/jira/browse/PORTLETBRIDGE-207
	public void setBridgeFlash(BridgeFlash bridgeFlash) {
		this.bridgeFlash = bridgeFlash;
	}

	@Override
	public Object getContext() {
		return portletContext;
	}

	@Override
	public String getContextName() {

		if (portletContextName == null) {
			portletContextName = portletContext.getPortletContextName();
		}

		return portletContextName;
	}

	@Override
	public boolean isResponseCommitted() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			boolean responseCommitted = mimeResponse.isCommitted();
			lifecycleIncongruityMap.putResponseCommitted(responseCommitted);

			return responseCommitted;
		}
		else {
			return lifecycleIncongruityMap.isResponseCommitted();
		}
	}

	protected boolean isEncodingFormWithPrimeFacesAjaxFileUpload() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (facesContext.getAttributes().get(PrimeFacesFileUpload.AJAX_FILE_UPLOAD) != null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isUserInRole(String role) {
		return portletRequest.isUserInRole(role);
	}

	protected boolean isICEfacesLegacyMode(ClientDataRequest clientDataRequest) {

		if (iceFacesLegacyMode == null) {

			iceFacesLegacyMode = Boolean.FALSE;

			String requestContentType = clientDataRequest.getContentType();

			if ((requestContentType != null) &&
					requestContentType.toLowerCase().startsWith(BridgeConstants.MULTIPART_CONTENT_TYPE_PREFIX)) {

				Product iceFaces = ProductMap.getInstance().get(BridgeConstants.ICEFACES);

				if (iceFaces.isDetected() &&
						((iceFaces.getMajorVersion() == 2) ||
							((iceFaces.getMajorVersion() == 3) && (iceFaces.getMinorVersion() == 0)))) {

					iceFacesLegacyMode = Boolean.TRUE;
				}
			}
		}

		return iceFacesLegacyMode;
	}

	@Override
	public Flash getFlash() {

		if (bridgeFlash == null) {
			bridgeFlash = BridgeFlashFactory.getBridgeFlash(this);
		}

		return bridgeFlash;
	}

	@Override
	public String getInitParameter(String name) {
		return bridgeContext.getInitParameter(name);
	}

	@Override
	public Map<String, String> getInitParameterMap() {
		return initParameterMap;
	}

	/**
	 * @see  ExternalContext#getMimeType(String)
	 */
	@Override
	public String getMimeType(String fileName) {
		String mimeType = portletContext.getMimeType(fileName);

		if ((mimeType == null) || (mimeType.length() == 0)) {
			mimeType = FileNameUtil.getFileNameMimeType(fileName);
		}

		return mimeType;
	}

	@Override
	public String getRealPath(String path) {
		return portletContext.getRealPath(path);
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
	public int getRequestContentLength() {

		int requestContentLength = -1;

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;
			requestContentLength = clientDataRequest.getContentLength();
			lifecycleIncongruityMap.putRequestContentLength(requestContentLength);
		}
		else {
			requestContentLength = lifecycleIncongruityMap.getRequestContentLength();
		}

		return requestContentLength;
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

	@Override
	public String getRequestScheme() {
		return portletRequest.getScheme();
	}

	@Override
	public String getRequestServerName() {
		return portletRequest.getServerName();
	}

	@Override
	public int getRequestServerPort() {
		return portletRequest.getServerPort();
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

			if ((bridgeFlash != null) && bridgeFlash.isServletResponseRequired()) {
				return new FlashHttpServletResponse(portletResponse, getRequestLocale());
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
	public int getResponseBufferSize() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			int responseBufferSize = mimeResponse.getBufferSize();
			lifecycleIncongruityMap.putResponseBufferSize(responseBufferSize);

			return responseBufferSize;
		}
		else {
			return lifecycleIncongruityMap.getResponseBufferSize();
		}
	}

	/**
	 * @see  javax.faces.context.ExternalContext#setResponseBufferSize(int)
	 */
	@Override
	public void setResponseBufferSize(int size) {

		if (bridgeContext.getPortletContainer().isAbleToSetResourceResponseBufferSize()) {

			if (portletResponse instanceof ResourceResponse) {
				ResourceResponse resourceResponse = (ResourceResponse) portletResponse;
				resourceResponse.setBufferSize(size);
			}
			else {
				lifecycleIncongruityManager.addCongruousTask(CongruousTask.SET_RESPONSE_BUFFER_SIZE);
			}
		}

		lifecycleIncongruityMap.putResponseBufferSize(size);
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
	 * @see  javax.faces.context.ExternalContext#setResponseContentLength(int)
	 */
	@Override
	public void setResponseContentLength(int length) {

		if (portletResponse instanceof ResourceResponse) {
			ResourceResponse resourceResponse = (ResourceResponse) portletResponse;
			resourceResponse.setContentLength(length);
		}
		else {
			lifecycleIncongruityManager.addCongruousTask(CongruousTask.SET_RESPONSE_CONTENT_LENGTH);
		}

		lifecycleIncongruityMap.putResponseContentLength(length);
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
	 * @see  ExternalContext#setResponseContentType(String)
	 */
	@Override
	public void setResponseContentType(String contentType) {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			bridgeContext.getPortletContainer().setMimeResponseContentType(mimeResponse, contentType);
		}
		else {
			lifecycleIncongruityManager.addCongruousTask(CongruousTask.SET_RESPONSE_CONTENT_TYPE);
		}

		lifecycleIncongruityMap.putResponseContentType(contentType);
	}

	/**
	 * @see  ExternalContext#setResponseHeader(String, String)
	 */
	@Override
	public void setResponseHeader(String name, String value) {
		addResponseHeader(name, value);
	}

	/**
	 * @see  javax.faces.context.ExternalContext#getResponseOutputStream()
	 */
	@Override
	public OutputStream getResponseOutputStream() throws IOException {

		if (portletResponse instanceof MimeResponse) {

			if (facesImplementationServletResponse != null) {
				logger.debug("Delegating to AFTER_VIEW_CONTENT servletResponse=[{0}]",
					facesImplementationServletResponse);

				return facesImplementationServletResponse.getOutputStream();
			}
			else {
				MimeResponse mimeResponse = (MimeResponse) portletResponse;

				return mimeResponse.getPortletOutputStream();
			}
		}
		else {
			lifecycleIncongruityManager.addCongruousTask(CongruousTask.WRITE_RESPONSE_OUTPUT_STREAM);

			return lifecycleIncongruityMap.getResponseOutputStream();
		}
	}

	/**
	 * @see  javax.faces.context.ExternalContext#getResponseOutputWriter()
	 */
	@Override
	public Writer getResponseOutputWriter() throws IOException {

		if (portletResponse instanceof MimeResponse) {

			if (facesImplementationServletResponse != null) {
				logger.debug("Delegating to AFTER_VIEW_CONTENT servletResponse=[{0}]",
					facesImplementationServletResponse);

				return facesImplementationServletResponse.getWriter();
			}
			else {
				return bridgeContext.getResponseOutputWriter();
			}

		}
		else {
			lifecycleIncongruityManager.addCongruousTask(CongruousTask.WRITE_RESPONSE_OUTPUT_WRITER);

			return lifecycleIncongruityMap.getResponseOutputWriter();
		}
	}

	/**
	 * Sets the status of the portlet response to the specified status code. Note that this is only possible for a
	 * portlet request of type PortletResponse because that is the only type of portlet response that is delivered
	 * directly back to the client (without additional markup added by the portlet container).
	 *
	 * @see  ExternalContext#setResponseStatus(int)
	 */
	@Override
	public void setResponseStatus(int statusCode) {

		if (portletResponse instanceof ResourceResponse) {
			ResourceResponse resourceResponse = (ResourceResponse) portletResponse;
			resourceResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, Integer.toString(statusCode));
		}

		lifecycleIncongruityMap.putResponseStatus(statusCode);
	}

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
