/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.portal;

/**
 * @author  Kyle Stiemann
 */
public class LiferayThemeDisplayUtil {

	public static boolean isIsolated(Object themeDisplayObject) {

		boolean isolated = false;

		if (themeDisplayObject instanceof com.liferay.portal.theme.ThemeDisplay) {
			com.liferay.portal.theme.ThemeDisplay themeDislpay = (com.liferay.portal.theme.ThemeDisplay)
				themeDisplayObject;
			isolated = themeDislpay.isIsolated();
		}

		return isolated;
	}

	public static boolean isStateExclusive(Object themeDisplayObject) {

		boolean stateExclusive = false;

		if (themeDisplayObject instanceof com.liferay.portal.theme.ThemeDisplay) {
			com.liferay.portal.theme.ThemeDisplay themeDislpay = (com.liferay.portal.theme.ThemeDisplay)
				themeDisplayObject;
			stateExclusive = themeDislpay.isStateExclusive();
		}

		return stateExclusive;
	}
}
