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

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class wraps an instance of com.liferay.portal.theme.ThemeDisplay and provides decorator methods that access the
 * wrapped instance via reflection in order to avoid a compile-time dependency.
 */
public class LiferayThemeDisplay {

	// Private Constants
	private static final String METHOD_NAME_GET_PORTLETDISPLAY = "getPortletDisplay";
	private static final String METHOD_NAME_GET_URL_CURRENT = "getURLCurrent";
	private static final String METHOD_NAME_GET_URL_PORTAL = "getURLPortal";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayThemeDisplay.class);

	Object wrappedThemeDisplay;

	public LiferayThemeDisplay(Object wrappedThemeDisplay) {
		this.wrappedThemeDisplay = wrappedThemeDisplay;
	}

	public LiferayPortletDisplay getLiferayPortletDisplay() {
		Object liferayPortletDisplay = null;

		try {
			Method method = wrappedThemeDisplay.getClass().getMethod(METHOD_NAME_GET_PORTLETDISPLAY);
			liferayPortletDisplay = method.invoke(wrappedThemeDisplay, (Object[]) null);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (liferayPortletDisplay == null) {
			return null;
		}
		else {
			return new LiferayPortletDisplay(liferayPortletDisplay);
		}
	}

	protected String getMethodReturnValue(Object object, String methodName) {
		Object methodReturnValue = null;

		try {
			Method method = object.getClass().getMethod(methodName);
			methodReturnValue = method.invoke(object, (Object[]) null);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (methodReturnValue == null) {
			return null;
		}
		else {
			return methodReturnValue.toString();
		}
	}

	public String getURLCurrent() {
		return getMethodReturnValue(wrappedThemeDisplay, METHOD_NAME_GET_URL_CURRENT);
	}

	public String getURLPortal() {
		return getMethodReturnValue(wrappedThemeDisplay, METHOD_NAME_GET_URL_PORTAL);
	}
}
