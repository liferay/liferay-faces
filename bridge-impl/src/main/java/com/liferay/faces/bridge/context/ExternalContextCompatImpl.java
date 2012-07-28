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
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.portlet.ClientDataRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceResponse;
import javax.portlet.faces.Bridge;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.component.primefaces.PrimeFacesFileUpload;
import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.bridge.config.BridgeConfigFactory;
import com.liferay.faces.bridge.config.Product;
import com.liferay.faces.bridge.config.ProductMap;
import com.liferay.faces.bridge.context.flash.BridgeFlash;
import com.liferay.faces.bridge.context.flash.BridgeFlashFactory;
import com.liferay.faces.bridge.context.flash.FlashHttpServletResponse;
import com.liferay.faces.bridge.util.FileNameUtil;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class ExternalContextCompatImpl extends ExternalContext {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ExternalContextCompatImpl.class);

	// Private Constants
	private static final String COOKIE_PROPERTY_COMMENT = "comment";
	private static final String COOKIE_PROPERTY_DOMAIN = "domain";
	private static final String COOKIE_PROPERTY_MAX_AGE = "maxAge";
	private static final String COOKIE_PROPERTY_PATH = "path";
	private static final String COOKIE_PROPERTY_SECURE = "secure";

	// Lazy-Initialized Data Members
	private BridgeFlash bridgeFlash;
	private Boolean iceFacesLegacyMode;
	private String portletContextName;

	// Private Data Members
	private BridgeConfig bridgeConfig;

	// Protected Data Members
	protected BridgeContext bridgeContext;
	protected ServletResponse facesImplementationServletResponse;
	protected IncongruityContext incongruityContext;
	protected boolean manageIncongruities;
	protected PortletContext portletContext;
	protected PortletRequest portletRequest;
	protected PortletResponse portletResponse;
	protected Bridge.PortletPhase portletPhase;
	protected String requestContextPath;

	public ExternalContextCompatImpl(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		this.portletContext = portletContext;
		this.portletRequest = portletRequest;
		this.portletResponse = portletResponse;

		// Get the bridge configuration.
		BridgeConfigFactory bridgeConfigFactory = (BridgeConfigFactory) BridgeFactoryFinder.getFactory(
				BridgeConfigFactory.class);
		this.bridgeConfig = bridgeConfigFactory.getBridgeConfig();

		// Get the BridgeContext.
		this.bridgeContext = BridgeContext.getCurrentInstance();

		this.incongruityContext = bridgeContext.getIncongruityContext();

		// Determine whether or not lifecycle incongruities should be managed.
		this.manageIncongruities = BooleanHelper.toBoolean(bridgeContext.getInitParameter(
					BridgeConfigConstants.PARAM_MANAGE_INCONGRUITIES), true);
	}

	/**
	 * @see    {@link ExternalContext#addResponseCookie(String, String, Map)}
	 * @since  JSF 2.0
	 */
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
	 * @see    {@link ExternalContext#addResponseHeader(String, String)}
	 * @since  JSF 2.0
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

	/**
	 * @see    {@link ExternalContext#encodeBookmarkableURL(String, Map)}
	 * @since  JSF 2.0
	 */
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
	 * @see    {@link ExternalContext#encodePartialActionURL(String)}
	 * @since  JSF 2.0
	 */
	@Override
	public String encodePartialActionURL(String url) {
		return bridgeContext.encodePartialActionURL(url).toString();
	}

	/**
	 * @see    {@link ExternalContext#encodeRedirectURL(String, Map)}
	 * @since  JSF 2.0
	 */
	@Override
	public String encodeRedirectURL(String baseUrl, Map<String, List<String>> parameters) {
		return bridgeContext.encodeRedirectURL(baseUrl, parameters).toString();
	}

	/**
	 * @see    {@link ExternalContext#invalidateSession()}
	 * @since  JSF 2.0
	 */
	@Override
	public void invalidateSession() {
		portletRequest.getPortletSession().invalidate();
	}

	/**
	 * @see    {@link ExternalContext#responseFlushBuffer()}
	 * @since  JSF 2.0
	 */
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

			if (manageIncongruities) {
				incongruityContext.responseFlushBuffer();
			}
			else {
				throw new IllegalStateException();
			}
		}
	}

	/**
	 * @see    {@link ExternalContext#responseReset()}
	 * @since  JSF 2.0
	 */
	@Override
	public void responseReset() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			mimeResponse.reset();
		}
		else {

			if (manageIncongruities) {
				incongruityContext.responseReset();
			}
			else {
				throw new IllegalStateException();
			}
		}
	}

	/**
	 * The Portlet API does not have an equivalent to {@link HttpServletResponse.sendError(int, String)}. Since the
	 * Mojarra JSF implementation basically only calls this when a Facelet is not found, better in a portlet environment
	 * to simply log an error and throw an IOException up the call stack so that the portlet will give the portlet
	 * container a chance to render an error message.
	 *
	 * @see    {@link ExternalContext#responseSendError(int, String)}
	 * @since  JSF 2.0
	 */
	@Override
	public void responseSendError(int statusCode, String message) throws IOException {
		String errorMessage = "Status code " + statusCode + ": " + message;
		logger.error(errorMessage);
		throw new IOException(errorMessage);
	}

	protected HttpServletResponse createFlashHttpServletResponse() {
		return new FlashHttpServletResponse(portletResponse, getRequestLocale());
	}

	// NOTE: PROPOSED-FOR-JSR344-API
	// http://java.net/jira/browse/JAVASERVERFACES_SPEC_PUBLIC-1070
	// NOTE: PROPOSED-FOR-BRIDGE3-API (Called by BridgeRequestScope in order to restore the Flash scope)
	// https://issues.apache.org/jira/browse/PORTLETBRIDGE-207
	public void setBridgeFlash(BridgeFlash bridgeFlash) {
		this.bridgeFlash = bridgeFlash;
	}

	/**
	 * @see    {@link ExternalContext#getContextName()}
	 * @since  JSF 2.0
	 */
	@Override
	public String getContextName() {

		if (portletContextName == null) {
			portletContextName = portletContext.getPortletContextName();
		}

		return portletContextName;
	}

	/**
	 * @see    {@link ExternalContext#isResponseCommitted()}
	 * @since  JSF 2.0
	 */
	@Override
	public boolean isResponseCommitted() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			boolean responseCommitted = mimeResponse.isCommitted();

			if (manageIncongruities) {
				incongruityContext.setResponseCommitted(responseCommitted);
			}

			return responseCommitted;
		}
		else {

			if (manageIncongruities) {
				return incongruityContext.isResponseCommitted();
			}
			else {
				throw new IllegalStateException();
			}
		}
	}

	protected boolean isBridgeFlashServletResponseRequired() {
		return ((bridgeFlash != null) && bridgeFlash.isServletResponseRequired());
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
			BridgeFlashFactory bridgeFlashFactory = (BridgeFlashFactory) BridgeFactoryFinder.getFactory(
					BridgeFlashFactory.class);
			bridgeFlash = bridgeFlashFactory.getBridgeFlash();
		}

		return bridgeFlash;
	}

	/**
	 * @see    {@link ExternalContext#getMimeType(String)}
	 * @since  JSF 2.0
	 */
	@Override
	public String getMimeType(String fileName) {
		String mimeType = portletContext.getMimeType(fileName);

		if ((mimeType == null) || (mimeType.length() == 0)) {
			mimeType = FileNameUtil.getFileNameMimeType(fileName);
		}

		return mimeType;
	}

	/**
	 * @see    {@link ExternalContext#getRealPath(String)}
	 * @since  JSF 2.0
	 */
	@Override
	public String getRealPath(String path) {
		return portletContext.getRealPath(path);
	}

	/**
	 * @see    {@link ExternalContext#getRequestContentLength()}
	 * @since  JSF 2.0
	 */
	@Override
	public int getRequestContentLength() {

		int requestContentLength = -1;

		if (portletRequest instanceof ClientDataRequest) {
			ClientDataRequest clientDataRequest = (ClientDataRequest) portletRequest;
			requestContentLength = clientDataRequest.getContentLength();

			if (manageIncongruities) {
				incongruityContext.setRequestContentLength(requestContentLength);
			}

			return requestContentLength;
		}
		else {

			if (manageIncongruities) {
				return incongruityContext.getRequestContentLength();
			}
			else {
				throw new IllegalStateException();
			}
		}
	}

	/**
	 * @see    {@link ExternalContext#getRequestScheme()}
	 * @since  JSF 2.0
	 */
	@Override
	public String getRequestScheme() {
		return portletRequest.getScheme();
	}

	/**
	 * @see    {@link ExternalContext#getRequestServerName()}
	 * @since  JSF 2.0
	 */
	@Override
	public String getRequestServerName() {
		return portletRequest.getServerName();
	}

	/**
	 * @see    {@link ExternalContext#getRequestServerPort()}
	 * @since  JSF 2.0
	 */
	@Override
	public int getRequestServerPort() {
		return portletRequest.getServerPort();
	}

	/**
	 * @see    {@link ExternalContext#getResponseBufferSize()}
	 * @since  JSF 2.0
	 */
	@Override
	public int getResponseBufferSize() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			int responseBufferSize = mimeResponse.getBufferSize();

			if (manageIncongruities) {
				incongruityContext.setResponseBufferSize(responseBufferSize);
			}

			return responseBufferSize;
		}
		else {

			if (manageIncongruities) {
				return incongruityContext.getResponseBufferSize();
			}
			else {
				throw new IllegalStateException();
			}
		}
	}

	/**
	 * @see    {@link ExternalContext#setResponseBufferSize(int)}
	 * @since  JSF 2.0
	 */
	@Override
	public void setResponseBufferSize(int size) {

		if (portletResponse instanceof ResourceResponse) {

			if (bridgeContext.getPortletContainer().isAbleToSetResourceResponseBufferSize()) {
				ResourceResponse resourceResponse = (ResourceResponse) portletResponse;
				resourceResponse.setBufferSize(size);
			}
		}
		else {

			if (manageIncongruities) {
				incongruityContext.setResponseBufferSize(size);
			}
			else {
				throw new IllegalStateException();
			}
		}
	}

	/**
	 * @see    {@link ExternalContext#setResponseContentLength(int)}
	 * @since  JSF 2.0
	 */
	@Override
	public void setResponseContentLength(int length) {

		if (portletResponse instanceof ResourceResponse) {
			ResourceResponse resourceResponse = (ResourceResponse) portletResponse;
			resourceResponse.setContentLength(length);
		}
		else {

			if (manageIncongruities) {
				incongruityContext.setResponseContentLength(length);
			}
			else {
				throw new IllegalStateException();
			}
		}
	}

	/**
	 * @see    {@link ExternalContext#setResponseContentType(String)}
	 * @since  JSF 2.0
	 */
	@Override
	public void setResponseContentType(String contentType) {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			bridgeContext.getPortletContainer().setMimeResponseContentType(mimeResponse, contentType);
		}
		else {

			if (manageIncongruities) {
				incongruityContext.setResponseContentType(contentType);
			}
			else {
				throw new IllegalStateException();
			}
		}
	}

	/**
	 * @see    {@link ExternalContext#setResponseHeader(String, String)}
	 * @since  JSF 2.0
	 */
	@Override
	public void setResponseHeader(String name, String value) {
		addResponseHeader(name, value);
	}

	/**
	 * @see    {@link ExternalContext#getResponseOutputStream()}
	 * @since  JSF 2.0
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

			if (manageIncongruities) {
				return incongruityContext.getResponseOutputStream();
			}
			else {
				throw new IllegalStateException();
			}
		}
	}

	/**
	 * @see    {@link ExternalContext#getResponseOutputWriter()}
	 * @since  JSF 2.0
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

			if (manageIncongruities) {
				return incongruityContext.getResponseOutputWriter();
			}
			else {
				throw new IllegalStateException();
			}
		}
	}

	/**
	 * Sets the status of the portlet response to the specified status code. Note that this is only possible for a
	 * portlet request of type PortletResponse because that is the only type of portlet response that is delivered
	 * directly back to the client (without additional markup added by the portlet container).
	 *
	 * @see    {@link ExternalContext#setResponseStatus(int)}
	 * @since  JSF 2.0
	 */
	@Override
	public void setResponseStatus(int statusCode) {

		if (portletResponse instanceof ResourceResponse) {
			ResourceResponse resourceResponse = (ResourceResponse) portletResponse;
			resourceResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, Integer.toString(statusCode));
		}
		else {

			if (manageIncongruities) {
				incongruityContext.setResponseStatus(statusCode);
			}
			else {
				// Mojarra will call this method if a runtime exception occurs during execution of the JSF lifecycle, so
                // must not throw an IllegalStateException.
			}
		}
	}
}
