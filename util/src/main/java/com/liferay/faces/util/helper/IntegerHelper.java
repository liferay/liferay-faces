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
 * This class provides additional methods that operate against the javax.lang.Integer system class.
 */
public class IntegerHelper {

	public static int toInteger(String value) {
		return toInteger(value, 0);
	}

	public static int toInteger(String value, int defaultValue) {

		int valueAsInt = defaultValue;

		try {
			valueAsInt = Integer.parseInt(value);
		}
		catch (NumberFormatException e) {
			// ignore
		}

		return valueAsInt;
	}
}
