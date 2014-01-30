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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.ClientWindow;
import javax.faces.render.ResponseStateManager;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.servlet.http.Cookie;

import com.liferay.faces.bridge.servlet.BridgeSessionListener;
import com.liferay.faces.util.lang.StringPool;


/**
 * This class provides a compatibility layer that isolates differences between JSF 2.1 and JSF 2.2.
 *
 * @author  Neil Griffin
 */
public abstract class ExternalContextCompat_2_2_Impl extends ExternalContextCompat_2_1_Impl {

	// Private Constants
	private static final String COOKIE_PROPERTY_HTTP_ONLY = "httpOnly";

	// Private Data Members
	private ClientWindow clientWindow;

	public ExternalContextCompat_2_2_Impl(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {
		super(portletContext, portletRequest, portletResponse);
	}

	/**
	 * @since  JSF 1.0
	 */
	@Override
	public String encodeActionURL(String url) {

		// JSF 2.2 requires that the ClientWindow ID and associated query string parameters be added to the URL.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		url = encodeClientWindowURL(facesContext, url);

		return super.encodeActionURL(url);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public String encodeBookmarkableURL(String baseUrl, Map<String, List<String>> parameters) {

		// JSF 2.2 requires that the ClientWindow ID and associated query string parameters be added to the URL.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		baseUrl = encodeClientWindowURL(facesContext, baseUrl);

		return super.encodeBookmarkableURL(baseUrl, parameters);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public String encodePartialActionURL(String url) {

		// JSF 2.2 requires that the ClientWindow ID and associated query string parameters be added to the URL.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		url = encodeClientWindowURL(facesContext, url);

		return super.encodePartialActionURL(url);
	}

	/**
	 * @since  JSF 2.0
	 */
	@Override
	public String encodeRedirectURL(String baseUrl, Map<String, List<String>> parameters) {

		// JSF 2.2 requires that the ClientWindow ID and associated query string parameters be added to the URL.
		FacesContext facesContext = FacesContext.getCurrentInstance();
		baseUrl = encodeClientWindowURL(facesContext, baseUrl);

		return super.encodeRedirectURL(baseUrl, parameters);
	}

	@Override
	protected Cookie createCookie(String name, String value, Map<String, Object> properties) {

		Cookie cookie = super.createCookie(name, value, properties);

		if ((properties != null) && !properties.isEmpty()) {

			Boolean httpOnly = (Boolean) properties.get(COOKIE_PROPERTY_HTTP_ONLY);

			if (httpOnly != null) {
				cookie.setHttpOnly(httpOnly);
			}

		}

		return cookie;
	}

	/**
	 * This method returns an encoded URL that contains the ClientWindow ID and associated query string parameters
	 * according to the requirements specified in {@link ExternalContext#encodeActionURL(String)}.
	 */
	protected String encodeClientWindowURL(FacesContext facesContext, String url) {

		if ((url != null) && (url.indexOf(ResponseStateManager.CLIENT_WINDOW_PARAM) < 0)) {

			ClientWindow clientWindow = getClientWindow();

			if ((clientWindow != null) && clientWindow.isClientWindowRenderModeEnabled(facesContext)) {

				StringBuilder urlBuilder = new StringBuilder(url);

				int queryPos = url.indexOf(StringPool.QUESTION);

				if (queryPos > 0) {
					urlBuilder.append(StringPool.AMPERSAND);
				}
				else {
					urlBuilder.append(StringPool.QUESTION);
				}

				urlBuilder.append(ResponseStateManager.CLIENT_WINDOW_PARAM);
				urlBuilder.append(StringPool.EQUAL);
				urlBuilder.append(clientWindow.getId());

				Map<String, String> queryURLParameters = clientWindow.getQueryURLParameters(facesContext);

				if ((queryURLParameters != null) && (queryURLParameters.size() > 0)) {

					Set<Entry<String, String>> entrySet = queryURLParameters.entrySet();

					for (Map.Entry<String, String> mapEntry : entrySet) {

						urlBuilder.append(mapEntry.getKey());
						urlBuilder.append(StringPool.EQUAL);
						urlBuilder.append(mapEntry.getValue());
					}
				}

				url = urlBuilder.toString();
			}
		}

		return url;
	}

	/**
	 * @see    {@link ExternalContext#getApplicationContextPath()}
	 * @since  JSF 2.2
	 */
	@Override
	public String getApplicationContextPath() {
		return BridgeSessionListener.getServletContextPath();
	}

	/**
	 * @see    {@link ExternalContext#getClientWindow()}
	 * @since  JSF 2.2
	 */
	@Override
	public ClientWindow getClientWindow() {
		return clientWindow;
	}

	/**
	 * @see    {@link ExternalContext#setClientWindow(ClientWindow)}
	 * @since  JSF 2.2
	 */
	@Override
	public void setClientWindow(ClientWindow clientWindow) {
		this.clientWindow = clientWindow;
	}

	/**
	 * @since  JSF 2.2
	 */
	@Override
	public String getSessionId(boolean create) {

		String sessionId = null;

		PortletSession portletSession = (PortletSession) getSession(create);

		if ((portletSession == null) && (!create)) {

			// JSF 2.2 requires the empty string to be returned in this case.
			sessionId = StringPool.BLANK;
		}
		else {
			sessionId = portletSession.getId();
		}

		return sessionId;
	}
}
