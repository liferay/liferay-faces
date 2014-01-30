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

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletResponse;


/**
 * @author  Neil Griffin
 */
public abstract class ExternalContextCompat_2_0_FlashImpl extends ExternalContextCompat_1_2_Impl {

	public ExternalContextCompat_2_0_FlashImpl(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {
		super(portletContext, portletRequest, portletResponse);
	}

	protected HttpServletResponse createFlashHttpServletResponse() {

		// no-op for JSF 1.2
		return null;
	}

	protected boolean isBridgeFlashServletResponseRequired() {

		// no-op for JSF 1.2
		return false;
	}
}
