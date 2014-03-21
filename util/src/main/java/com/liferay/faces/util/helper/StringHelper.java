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
package com.liferay.faces.util.helper;

/**
 * @author  Neil Griffin
 */
public class StringHelper {

	public static String toString(Object value, String defaultValue) {

		if (value != null) {
			return value.toString();
		}
		else {
			return defaultValue;
		}
	}

	public static String[] append(String[]... arrays) {
		int length = 0;

		for (String[] array : arrays) {
			length += array.length;
		}

		String[] newArray = new String[length];

		int previousLength = 0;

		for (String[] array : arrays) {
			System.arraycopy(array, 0, newArray, previousLength, array.length);

			previousLength += array.length;
		}

		return newArray;
	}

	public static String[] append(String[] array, String... value) {
		String[] newArray = new String[array.length + value.length];

		System.arraycopy(array, 0, newArray, 0, array.length);
		System.arraycopy(value, 0, newArray, array.length, array.length);

		return newArray;
	}
}
