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
package com.liferay.faces.util.helper;

/**
 * This class provides additional methods that operate against the javax.lang.Boolean system class.
 */
public class BooleanHelper {

	public static final String[] TRUE_VALUES = { "true", "1", "t", "y", "yes", "on", "checked" };
	public static final String[] FALSE_VALUES = { "false", "0", "f", "n", "no", "off", "unchecked" };

	public static boolean toBoolean(String value) {
		return toBoolean(value, false);
	}

	public static boolean toBoolean(Object value, boolean defaultValue) {

		String valueAsString = null;

		if (value != null) {
			valueAsString = value.toString();
		}

		return toBoolean(valueAsString, defaultValue);
	}

	public static boolean toBoolean(String value, boolean defaultValue) {

		if (value == null) {

			return defaultValue;
		}
		else {

			if (isTrueToken(value)) {
				return true;
			}
			else if (isFalseToken(value)) {
				return false;
			}
			else {
				return defaultValue;
			}

		}

	}

	public static boolean isBooleanToken(String value) {

		return isTrueToken(value) || isFalseToken(value);
	}

	public static boolean isFalseToken(String value) {

		boolean flag = false;

		if (value != null) {
			String trimmedValue = value.trim();
			flag = (trimmedValue.equalsIgnoreCase(FALSE_VALUES[0]) || trimmedValue.equalsIgnoreCase(FALSE_VALUES[1]) ||
					trimmedValue.equalsIgnoreCase(FALSE_VALUES[2]) || trimmedValue.equalsIgnoreCase(FALSE_VALUES[3]) ||
					trimmedValue.equalsIgnoreCase(FALSE_VALUES[3]) || trimmedValue.equalsIgnoreCase(FALSE_VALUES[4]) ||
					trimmedValue.equalsIgnoreCase(FALSE_VALUES[5]) || trimmedValue.equalsIgnoreCase(FALSE_VALUES[6]));
		}

		return flag;
	}

	public static boolean isTrueToken(String value) {

		boolean flag = false;

		if (value != null) {
			String trimmedValue = value.trim();
			flag = (trimmedValue.equalsIgnoreCase(TRUE_VALUES[0]) || trimmedValue.equalsIgnoreCase(TRUE_VALUES[1]) ||
					trimmedValue.equalsIgnoreCase(TRUE_VALUES[2]) || trimmedValue.equalsIgnoreCase(TRUE_VALUES[3]) ||
					trimmedValue.equalsIgnoreCase(TRUE_VALUES[3]) || trimmedValue.equalsIgnoreCase(TRUE_VALUES[4]) ||
					trimmedValue.equalsIgnoreCase(TRUE_VALUES[5]) || trimmedValue.equalsIgnoreCase(TRUE_VALUES[6]));
		}

		return flag;
	}
}
