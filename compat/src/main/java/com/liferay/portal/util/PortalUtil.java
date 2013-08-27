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
package com.liferay.portal.util;

import java.lang.reflect.Method;

import javax.portlet.PortletRequest;
import javax.portlet.filter.PortletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * Since the Liferay CDI Portlet Bridge has a dependency on the {@link PortalUtil#getHttpServletRequest(PortletRequest)}
 * method, this class is necessary to provide a runtime compatibility layer.
 *
 * @author  Neil Griffin
 */
public class PortalUtil {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PortalUtil.class);

	// Private Constants
	private static final String GET_HTTP_SERVLET_REQUEST = "getHttpServletRequest";
	private static final String COM_LIFERAY_PORTLET = "com.liferay.portlet";

	public static HttpServletRequest getHttpServletRequest(PortletRequest portletRequest) {

		HttpServletRequest httpServletRequest = null;

		if (portletRequest instanceof PortletRequestWrapper) {
			PortletRequestWrapper portletRequestWrapper = (PortletRequestWrapper) portletRequest;
			portletRequest = portletRequestWrapper.getRequest();
		}

		Class<?> portletRequestClass = portletRequest.getClass();

		if (portletRequestClass.getName().startsWith(COM_LIFERAY_PORTLET)) {

			try {
				Method getHttpServletRequestMethod = portletRequestClass.getMethod(GET_HTTP_SERVLET_REQUEST,
						new Class[] {});
				httpServletRequest = (HttpServletRequest) getHttpServletRequestMethod.invoke(portletRequest,
						new Object[] {});
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return httpServletRequest;
	}

}
