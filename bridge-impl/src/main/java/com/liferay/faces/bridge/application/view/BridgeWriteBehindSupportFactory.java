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
package com.liferay.faces.bridge.application.view;

import java.util.Locale;

import javax.faces.FacesException;
import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.faces.BridgeWriteBehindResponse;
import javax.servlet.ServletResponse;

import com.liferay.faces.bridge.FactoryWrapper;


/**
 * @author  Neil Griffin
 */
@SuppressWarnings("deprecation")
public abstract class BridgeWriteBehindSupportFactory implements FactoryWrapper<BridgeWriteBehindSupportFactory> {

	public abstract BridgeAfterViewContentRequest getBridgeAfterViewContentRequest(PortletRequest portletRequest);

	public abstract BridgeAfterViewContentResponse getBridgeAfterViewContentResponse(PortletResponse portletResponse,
		Locale locale);

	public abstract BridgeWriteBehindResponse getBridgeWriteBehindResponse(MimeResponse mimeResponse,
		ServletResponse servletResponse) throws FacesException;
}
