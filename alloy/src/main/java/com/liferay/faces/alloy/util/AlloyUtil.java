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
public class AlloyUtil {

	public static String appendToCssClasses(String cssClass, String suffix) {

		String value = cssClass;

		if (value != null) {
			value = value.trim();

			if (value.length() > 0) {
				StringBuilder buf = new StringBuilder();
				String[] cssClasses = cssClass.trim().split(" ");
				boolean firstClass = true;

				for (String curCssClass : cssClasses) {

					if (firstClass) {
						firstClass = false;
					}
					else {
						buf.append(" ");
					}

					buf.append(curCssClass);
					buf.append(suffix);
				}

				value = buf.toString();
			}
		}

		return value;
	}
}
