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
package com.liferay.faces.alloy.util;

import java.lang.reflect.Method;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class LiferayPortletUtil {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayPortletUtil.class);

	// Private Constants
	private static final Method GET_PORTLET_ID_METHOD;

	static {
		Method getPortletIdMethod = null;

		try {
			Class<?> portletClass = Class.forName("com.liferay.portal.model.Portlet");
			getPortletIdMethod = portletClass.getMethod("getPortletId", new Class[] {});
		}
		catch (Exception e) {
			// ignore
		}

		GET_PORTLET_ID_METHOD = getPortletIdMethod;
	}

	public static String getPortletId(Object portlet) {

		String portletId = null;

		if ((GET_PORTLET_ID_METHOD != null) && (portlet != null)) {

			try {
				portletId = (String) GET_PORTLET_ID_METHOD.invoke(portlet, new Object[] {});
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return portletId;
	}
}
