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
package com.liferay.faces.bridge.container.liferay;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class LiferayPortletResponse {

	// Private Constants
	private static final String METHOD_NAME_CREATE_RENDER_URL = "createRenderURL";
	private static final String METHOD_NAME_SET_REDIRECT_LOCATION = "setRedirectLocation";

	// Logger
	Logger logger = LoggerFactory.getLogger(LiferayPortletResponse.class);

	// Private Data Members
	private PortletResponse wrappedPortletResponse;

	public LiferayPortletResponse(PortletResponse portletResponse) {
		this.wrappedPortletResponse = portletResponse;
	}

	public PortletURL createRenderURL() {

		PortletURL renderURL = null;

		try {
			Method method = wrappedPortletResponse.getClass().getMethod(METHOD_NAME_CREATE_RENDER_URL, (Class[]) null);

			renderURL = (PortletURL) method.invoke(wrappedPortletResponse, (Object[]) null);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return renderURL;
	}

	/**
	 * The Liferay ActionResponseImpl.sendRedirect(String) method throws exceptions under some circumstances that occur
	 * during the execution of the JSF lifecycle, so this method is necessary in order to bypass all those exceptions
	 * and to call the ActionResponseImpl.sendRedirect.setRedirectLocation(String) method reflectively in order to have
	 * the redirect work.
	 */
	public void sendRedirect(String url) throws IOException {

		try {
			Method method = wrappedPortletResponse.getClass().getMethod(METHOD_NAME_SET_REDIRECT_LOCATION,
					new Class[] { String.class });
			method.invoke(wrappedPortletResponse, new Object[] { url });
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}
}
