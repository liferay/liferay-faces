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
package com.liferay.faces.alloy.util;

/**
 * @author  Neil Griffin
 */
public class GetterUtil {

	public static boolean getBoolean(Object value, boolean defaultValue) {

		if (value != null) {
			return getBoolean(value.toString(), defaultValue);
		}
		else {
			return defaultValue;
		}
	}

	public static boolean getBoolean(String value, boolean defaultValue) {

		if (value != null) {
			value = value.trim().toLowerCase();

			if (value.equals("true")) {
				return true;
			}
			else if (value.equals("false")) {
				return false;
			}
			else {
				return defaultValue;
			}
		}
		else {
			return defaultValue;
		}
	}

	public static String getString(Object value, String defaultValue) {

		if (value != null) {
			return value.toString();
		}
		else {
			return defaultValue;
		}
	}
}
