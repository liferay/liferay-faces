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
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.portlet.ClientDataRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.ResourceResponse;
import javax.portlet.StateAwareResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeWriteBehindResponse;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.application.internal.ViewHandlerImpl;
import com.liferay.faces.bridge.application.view.internal.BridgeAfterViewContentRequest;
import com.liferay.faces.bridge.application.view.internal.BridgeAfterViewContentResponse;
import com.liferay.faces.bridge.application.view.internal.BridgeWriteBehindSupportFactory;
import com.liferay.faces.bridge.context.map.internal.ContextMapFactory;
import com.liferay.faces.bridge.util.internal.LocaleIterator;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class ExternalContextImpl extends ExternalContextCompat_2_2_Impl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ExternalContextImpl.class);

	// Private Constants
	private static final boolean RICHFACES_DETECTED = ProductMap.getInstance().get(ProductConstants.RICHFACES)
		.isDetected();
	private static final String ORG_RICHFACES_EXTENSION = "org.richfaces.extension";

	// Pre-initialized Data Members
	private Map<String, Object> applicationMap;
	private ContextMapFactory contextMapFactory;
	private Map<String, Object> requestAttributeMap;
	private String requestContextPath;
	private Map<String, Object> sessionMap;

	// Lazily-Initialized Data Members
	private String authType;
	private BridgeAfterViewContentRequest bridgeAfterViewContentRequest;
	private BridgeAfterViewContentResponse bridgeAfterViewContentResponse;
	private Map<String, String> initParameterMap;
	private String remoteUser;
	private Map<String, Object> requestCookieMap;
	private Locale requestLocale;
	private Principal userPrincipal;

	public ExternalContextImpl(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		super(portletContext, portletRequest, portletResponse);

		this.contextMapFactory = (ContextMapFactory) BridgeFactoryFinder.getFactory(ContextMapFactory.class);

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

		// Indicate that JSP AFTER_VIEW_CONTENT processing has been de-activated. This will make it possible for the
		// Mojarra JspViewHandlingStrategy#executePageToBuildView(FacesContext, UIViewRoot) method to successfully call
		// {@link ExternalContextImpl#setResponse(Object)} in order to undo the ViewHandlerResponseWrapper and restore
		// the original PortletResponse.
		bridgeContext.setProcessingAfterViewContent(false);

		logger.debug("De-activated JSP AFTER_VIEW_CONTENT");

		logger.debug("Acquiring dispatcher for JSP path=[{0}]", path);

		PortletRequestDispatcher portletRequestDispacher = portletContext.getRequestDispatcher(path);

		try {

			if (portletRequestDispacher != null) {

				// If a render-redirect has occurred after dispatching to a JSP, that means that the previous
				// dispatch called PortletRequestDispatcher#forward(String) which marked the response as "complete",
				// thereby making it impossible to forward again. In such cases, need to "include" instead of
				// "forward".
				if (bridgeContext.isRenderRedirectAfterDispatch()) {
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
			else {
				throw new IOException("Unable to acquire PortletRequestDispatcher for path=[" + path + "]");
			}
		}
		catch (PortletException e) {
			logger.error(e);
			throw new IOException(e.getMessage());
		}
	}

	/**
	 * @see  ExternalContext#encodeNamespace(String)
	 */
	@Override
	public String encodeNamespace(String name) {

		if (name == null) {
			return portletResponse.getNamespace();
		}
		else if (RICHFACES_DETECTED && (name.equals(ORG_RICHFACES_EXTENSION))) {

			// http://issues.liferay.com/browse/FACES-1416
			return name;
		}
		else {
			return portletResponse.getNamespace() + name;
		}
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

		if (requestChanged) {
			bridgeContext.setPortletRequest(portletRequest);
		}

		if (responseChanged) {
			bridgeContext.setPortletResponse(portletResponse);
		}

		// Retrieve the portlet lifecycle phase.
		portletPhase = bridgeContext.getPortletRequestPhase();

		// Initialize the application map.
		applicationMap = contextMapFactory.getApplicationScopeMap(bridgeContext);

		// Initialize the request attribute map.
		requestAttributeMap = contextMapFactory.getRequestScopeMap(bridgeContext);

		// Initialize the session map.
		sessionMap = contextMapFactory.getSessionScopeMap(bridgeContext, PortletSession.PORTLET_SCOPE);

		// Initialize the init parameter map.
		initParameterMap = contextMapFactory.getInitParameterMap(portletContext);

		// Initialize the request context path.
		requestContextPath = portletRequest.getContextPath();
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

			if ((bridgeAfterViewContentRequest == null) ||
					(bridgeAfterViewContentRequest.getWrapped() != portletRequest)) {

				BridgeWriteBehindSupportFactory bridgeWriteBehindSupportFactory = (BridgeWriteBehindSupportFactory)
					BridgeFactoryFinder.getFactory(BridgeWriteBehindSupportFactory.class);
				bridgeAfterViewContentRequest = bridgeWriteBehindSupportFactory.getBridgeAfterViewContentRequest(
						portletRequest);
			}

			return bridgeAfterViewContentRequest;
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

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;
			String requestCharacterEncoding = clientDataRequest.getCharacterEncoding();

			if (manageIncongruities) {

				try {
					incongruityContext.setRequestCharacterEncoding(requestCharacterEncoding);
				}
				catch (Exception e) {
					logger.error(e);
				}
			}

			return requestCharacterEncoding;
		}
		else {

			if (manageIncongruities) {
				return incongruityContext.getRequestCharacterEncoding();
			}
			else {

				// The Mojarra 2.x {@link MultiViewHandler#initView(FacesContext)} method expects a null value to be
				// returned, so throwing an IllegalStateException is not an option.
				return null;
			}
		}
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

			if (manageIncongruities) {
				incongruityContext.setRequestCharacterEncoding(encoding);
			}
			else {
				// TestPage140: setRequestCharacterEncodingRenderTest expects this to be a no-op so throwing an
				// IllegalStateException is not an option.
			}
		}
	}

	@Override
	public String getRequestContentType() {

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;

			// If using ICEfaces 3.0.x/2.0.x then need to return the legacy value.
			// http://issues.liferay.com/browse/FACES-1228
			String requestContentType;

			if (isICEfacesLegacyMode(clientDataRequest)) {
				requestContentType = clientDataRequest.getResponseContentType();
			}
			else {
				requestContentType = clientDataRequest.getContentType();
			}

			if (manageIncongruities) {
				incongruityContext.setRequestContentType(requestContentType);
			}

			return requestContentType;
		}
		else {

			if (manageIncongruities) {
				return incongruityContext.getRequestContentType();
			}
			else {

				// TestPage166: getRequestContentTypeEventTest expects this condition to return null so throwing an
				// IllegalStateException is not an option.
				return null;
			}
		}
	}

	@Override
	public String getRequestContextPath() {
		return requestContextPath;
	}

	@Override
	public Map<String, Object> getRequestCookieMap() {

		if (requestCookieMap == null) {
			requestCookieMap = contextMapFactory.getRequestCookieMap(bridgeContext);
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
		return new LocaleIterator(portletRequest.getLocales());
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

			if (facesImplementationServletResponse == null) {

				if ((bridgeAfterViewContentResponse == null) ||
						(bridgeAfterViewContentResponse.getWrapped() != portletResponse)) {
					BridgeWriteBehindSupportFactory bridgeWriteBehindSupportFactory = (BridgeWriteBehindSupportFactory)
						BridgeFactoryFinder.getFactory(BridgeWriteBehindSupportFactory.class);
					bridgeAfterViewContentResponse = bridgeWriteBehindSupportFactory.getBridgeAfterViewContentResponse(
							portletResponse, getRequestLocale());
				}

				return bridgeAfterViewContentResponse;
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

					BridgeWriteBehindSupportFactory bridgeWriteBehindSupportFactory = (BridgeWriteBehindSupportFactory)
						BridgeFactoryFinder.getFactory(BridgeWriteBehindSupportFactory.class);
					BridgeWriteBehindResponse bridgeWriteBehindResponse =
						bridgeWriteBehindSupportFactory.getBridgeWriteBehindResponse((MimeResponse)
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

			if (manageIncongruities) {
				incongruityContext.setResponseCharacterEncoding(characterEncoding);
			}

			return characterEncoding;
		}
		else {

			if (manageIncongruities) {
				return incongruityContext.getResponseCharacterEncoding();
			}
			else {

				if (portletResponse instanceof StateAwareResponse) {

					String characterEncoding = (String) bridgeContext.getAttributes().get(
							ViewHandlerImpl.RESPONSE_CHARACTER_ENCODING);

					if (characterEncoding != null) {

						// Workaround for patch applied to Mojarra in JAVASERVERFACES-3023
						return characterEncoding;
					}
					else {

						// TCK TestPage169: getResponseCharacterEncodingActionTest
						// TCK TestPage180: getResponseCharacterEncodingEventTest
						throw new IllegalStateException();
					}
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

				if (manageIncongruities) {
					incongruityContext.setResponseCharacterEncoding(encoding);
				}
				else {
					// TestPage196: setResponseCharacterEncodingTest expects this to be a no-op so throwing
					// IllegalStateException is not an option.
				}
			}
		}
	}

	/**
	 * @see  {@link ExternalContext#getResponseContentType()}
	 */
	@Override
	public String getResponseContentType() {

		if (portletResponse instanceof MimeResponse) {

			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			String responseContentType = mimeResponse.getContentType();

			if (responseContentType == null) {
				responseContentType = portletRequest.getResponseContentType();
			}

			return responseContentType;
		}
		else {

			// TCK TestPage173: getResponseContentTypeActionTest
			// TCK TestPage174: getResponseContentTypeEventTest
			throw new IllegalStateException();
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
