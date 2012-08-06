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

import java.lang.reflect.Method;

import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides access to the com.liferay.portal.util.PortalUtil static utility class via reflection in order to
 * avoid a compile-time dependency.
 */
public class LiferayPortalUtil {

	// Private Constants
	private static final String PORTAL_UTIL_FQCN = "com.liferay.portal.util.PortalUtil";
	private static final String METHOD_NAME_GET_PORTLET_ID = "getPortletId";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayPortalUtil.class);

	public static String getPortletId(PortletRequest portletRequest) {
		String portletId = null;

		try {
			Class<?> portalUtilClass = Class.forName(PORTAL_UTIL_FQCN);
			Method method = portalUtilClass.getMethod(METHOD_NAME_GET_PORTLET_ID, new Class[] { PortletRequest.class });
			portletId = (String) method.invoke(null, new Object[] { portletRequest });

			if (portletRequest.getParameter(BridgeConstants.WSRP) != null) {

				// For some reason, when running as a WSRP producer, the underscores are missing from the beginning
				// and end...
				portletId = StringPool.UNDERLINE + portletId + StringPool.UNDERLINE;
			}
		}
		catch (Exception e) {
			logger.error(e);
		}

		return portletId;
	}

}
