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
public class LiferayThemeDisplayUtil {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayThemeDisplayUtil.class);

	// Private Constants
	private static final Method IS_ISOLATED_METHOD;
	private static final Method IS_STATE_EXCLUSIVE_METHOD;

	static {
		Method isIsolatedMethod = null;
		Method isStateExclusiveMethod = null;

		try {
			Class<?> portletClass = Class.forName("com.liferay.portal.theme.ThemeDisplay");
			isIsolatedMethod = portletClass.getMethod("isIsolated", new Class[] {});
			isStateExclusiveMethod = portletClass.getMethod("isStateExclusive", new Class[] {});
		}
		catch (Exception e) {
			// ignore
		}

		IS_ISOLATED_METHOD = isIsolatedMethod;
		IS_STATE_EXCLUSIVE_METHOD = isStateExclusiveMethod;
	}

	public static boolean isIsolated(Object themeDisplay) {

		boolean isolated = false;

		if ((IS_ISOLATED_METHOD != null) && (themeDisplay != null)) {

			try {
				isolated = (Boolean) IS_ISOLATED_METHOD.invoke(themeDisplay, new Object[] {});
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return isolated;
	}

	public static boolean isStateExclusive(Object themeDisplay) {

		boolean stateExclusive = false;

		if ((IS_STATE_EXCLUSIVE_METHOD != null) && (themeDisplay != null)) {

			try {
				stateExclusive = (Boolean) IS_STATE_EXCLUSIVE_METHOD.invoke(themeDisplay, new Object[] {});
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return stateExclusive;
	}
}
