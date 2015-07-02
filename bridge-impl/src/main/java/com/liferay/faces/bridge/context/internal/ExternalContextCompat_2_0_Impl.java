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
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.portlet.ClientDataRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceResponse;
import javax.portlet.faces.Bridge;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;

import com.liferay.faces.bridge.context.BridgePortalContext;
import com.liferay.faces.bridge.internal.BridgeConstants;
import com.liferay.faces.bridge.util.internal.FileNameUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * This class provides a compatibility layer that isolates differences between JSF 1.2 and JSF 2.0.
 *
 * @author  Neil Griffin
 */
public abstract class ExternalContextCompat_2_0_Impl extends ExternalContextCompat_2_0_FlashImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ExternalContextCompat_2_0_Impl.class);

	// Private Constants
	private static final String COOKIE_PROPERTY_COMMENT = "comment";
	private static final String COOKIE_PROPERTY_DOMAIN = "domain";
	private static final String COOKIE_PROPERTY_MAX_AGE = "maxAge";
	private static final String COOKIE_PROPERTY_PATH = "path";
	private static final String COOKIE_PROPERTY_SECURE = "secure";

	// Lazy-Initialized Data Members
	private Boolean iceFacesLegacyMode;
	private String portletContextName;

	// Protected Data Members
	protected ServletResponse facesImplementationServletResponse;
	protected Bridge.PortletPhase portletPhase;

	public ExternalContextCompat_2_0_Impl(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		super(portletContext, portletRequest, portletResponse);
	}

	/**
	 * @see    {@link ExternalContext#addResponseCookie(String, String, Map)}
	 * @since  JSF 2.0
	 */
	@Override
	public void addResponseCookie(String name, String value, Map<String, Object> properties) {

		Cookie cookie = createCookie(name, value, properties);
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
		return bridgeContext.encodeBookmarkableURL(baseUrl, parameters).toString();
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
	 * The Portlet API does not have an equivalent to {@link javax.servlet.http.HttpServletResponse#sendError(int,
	 * String)}. Since the Mojarra JSF implementation basically only calls this when a Facelet is not found, better in a
	 * portlet environment to simply log an error and throw an IOException up the call stack so that the portlet will
	 * give the portlet container a chance to render an error message.
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

	protected Cookie createCookie(String name, String value, Map<String, Object> properties) {

		Cookie cookie = new Cookie(name, value);

		if ((properties != null) && !properties.isEmpty()) {

			try {
				String comment = (String) properties.get(COOKIE_PROPERTY_COMMENT);

				if (comment != null) {
					cookie.setComment(comment);
				}

				String domain = (String) properties.get(COOKIE_PROPERTY_DOMAIN);

				if (domain != null) {
					cookie.setDomain(domain);
				}

				Integer maxAge = (Integer) properties.get(COOKIE_PROPERTY_MAX_AGE);

				if (maxAge != null) {
					cookie.setMaxAge(maxAge);
				}

				String path = (String) properties.get(COOKIE_PROPERTY_PATH);

				if (path != null) {
					cookie.setPath(path);
				}

				Boolean secure = (Boolean) properties.get(COOKIE_PROPERTY_SECURE);

				if (secure != null) {
					cookie.setSecure(secure);
				}
			}
			catch (ClassCastException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return cookie;
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

	protected boolean isICEfacesLegacyMode(ClientDataRequest clientDataRequest) {

		if (iceFacesLegacyMode == null) {

			iceFacesLegacyMode = Boolean.FALSE;

			String requestContentType = clientDataRequest.getContentType();

			if ((requestContentType != null) &&
					requestContentType.toLowerCase().startsWith(BridgeConstants.MULTIPART_CONTENT_TYPE_PREFIX)) {

				Product iceFaces = ProductMap.getInstance().get(ProductConstants.ICEFACES);

				if (iceFaces.isDetected() &&
						((iceFaces.getMajorVersion() == 2) ||
							((iceFaces.getMajorVersion() == 3) && (iceFaces.getMinorVersion() == 0)))) {

					iceFacesLegacyMode = Boolean.TRUE;
				}
			}
		}

		return iceFacesLegacyMode;
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

		int requestContentLength;

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

			PortalContext portalContext = portletRequest.getPortalContext();
			String setResponseBufferSizeSupport = portalContext.getProperty(
					BridgePortalContext.SET_RESOURCE_RESPONSE_BUFFER_SIZE_SUPPORT);

			if (setResponseBufferSizeSupport != null) {
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
			mimeResponse.setContentType(contentType);
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
