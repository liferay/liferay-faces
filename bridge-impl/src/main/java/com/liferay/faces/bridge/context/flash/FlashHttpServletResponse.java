/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context.flash;

import java.util.Locale;

import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.bridge.context.ExternalContextImpl;
import com.liferay.faces.bridge.filter.HttpServletResponseAdapter;


/**
 * This class provides the ability to trick the JSF implementation into thinking that the current {@link
 * PortletResponse} is actually an {@link HttpServletResponse}. It serves as a marker class that simply extends {@link
 * HttpServletResponseAdapter}. It's only purpose is to make the code in {@link ExternalContextImpl} easier to follow by
 * providing a meaningful (self documenting) name.
 *
 * @author  Neil Griffin
 */
public class FlashHttpServletResponse extends HttpServletResponseAdapter {

	public FlashHttpServletResponse(PortletResponse portletResponse, Locale requestLocale) {
		super(portletResponse, requestLocale);
	}

}
