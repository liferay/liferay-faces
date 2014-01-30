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

import javax.faces.context.ExternalContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;


/**
 * This class provides a compatibility layer that isolates differences between JSF 2.0 and JSF 2.1.
 *
 * @author  Neil Griffin
 */
public abstract class ExternalContextCompat_2_1_Impl extends ExternalContextCompat_2_0_Impl {

	public ExternalContextCompat_2_1_Impl(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {
		super(portletContext, portletRequest, portletResponse);
	}

	/**
	 * @see    {@link ExternalContext#isSecure()}
	 * @since  JSF 2.1
	 */
	@Override
	public boolean isSecure() {
		return portletRequest.isSecure();
	}

	/**
	 * @see    {@link ExternalContext#getSessionMaxInactiveInterval()}
	 * @since  JSF 2.1
	 */
	@Override
	public int getSessionMaxInactiveInterval() {

		PortletSession portletSession = (PortletSession) getSession(true);

		return portletSession.getMaxInactiveInterval();
	}

	/**
	 * @see    {@link ExternalContext#setSessionMaxInactiveInterval(int)}
	 * @since  JSF 2.1
	 */
	@Override
	public void setSessionMaxInactiveInterval(int sessionMaxInactiveInterval) {
		PortletSession portletSession = (PortletSession) getSession(true);
		portletSession.setMaxInactiveInterval(sessionMaxInactiveInterval);
	}
}
