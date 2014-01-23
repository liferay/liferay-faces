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
package com.liferay.faces.alloy.util;

import com.liferay.faces.util.helper.BooleanHelper;


/**
 * @author      Neil Griffin
 * @deprecated  Use {@link com.liferay.faces.util.helper.BooleanHelper BooleanHelper} instead.
 */
public class GetterUtil {

	/**
	 * @deprecated  Use {@link com.liferay.faces.util.helper.BooleanHelper#toBoolean(String, boolean) BooleanHelper's
	 *              toBoolean method} instead.
	 */
	public static boolean getBoolean(Object value, boolean defaultValue) {
		return BooleanHelper.toBoolean(value.toString(), defaultValue);
	}

	/**
	 * @deprecated  Use {@link com.liferay.faces.util.helper.BooleanHelper#toBoolean(String, boolean) BooleanHelper's
	 *              toBoolean method} instead.
	 */
	public static boolean getBoolean(String value, boolean defaultValue) {
		return BooleanHelper.toBoolean(value, defaultValue);
	}

	/**
	 * @deprecated  No replacement.
	 */
	public static String getString(Object value, String defaultValue) {

		if (value != null) {
			return value.toString();
		}
		else {
			return defaultValue;
		}
	}
}
