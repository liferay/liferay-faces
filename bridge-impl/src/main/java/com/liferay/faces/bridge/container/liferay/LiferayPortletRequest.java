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
package com.liferay.faces.bridge.container.liferay;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.filter.PortletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * This class wraps an instance of com.liferay.portlet.PortletRequestImpl and provides decorator methods that access the
 * wrapped instance via reflection in order to avoid a compile-time dependency.
 */
public class LiferayPortletRequest {

	// Private Constants
	private static final String METHOD_NAME_GET_ORIGINAL_HTTP_SERVLET_REQUEST = "getOriginalHttpServletRequest";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayPortletRequest.class);

	// Private Data Members
	private PortletRequest wrappedPortletRequest;
	private OriginalHttpServletRequest originalHttpServletRequest;
	private ThemeDisplay themeDisplay;

	public LiferayPortletRequest(PortletRequest portletRequest) {

		if (portletRequest != null) {

			while (portletRequest instanceof PortletRequestWrapper) {
				PortletRequestWrapper portletRequestWrapper = (PortletRequestWrapper) portletRequest;
				portletRequest = portletRequestWrapper.getRequest();
			}
		}

		this.wrappedPortletRequest = portletRequest;

		try {
			Method method = wrappedPortletRequest.getClass().getMethod(METHOD_NAME_GET_ORIGINAL_HTTP_SERVLET_REQUEST,
					(Class[]) null);
			this.originalHttpServletRequest = new OriginalHttpServletRequest((HttpServletRequest) method.invoke(
						wrappedPortletRequest, (Object[]) null));
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public long getDateHeader(String name) {
		return originalHttpServletRequest.getDateHeader(name);
	}

	public String[] getHeader(String name) {
		String[] values = null;
		Enumeration<?> headers = originalHttpServletRequest.getHeaders(name);

		if (headers != null) {
			List<String> valueList = new ArrayList<String>();

			while (headers.hasMoreElements()) {
				valueList.add((String) headers.nextElement());
			}

			values = valueList.toArray(new String[valueList.size()]);
		}

		return values;
	}

	public ThemeDisplay getThemeDisplay() {

		if (themeDisplay == null) {
			themeDisplay = (ThemeDisplay) wrappedPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		}

		return themeDisplay;
	}
}
