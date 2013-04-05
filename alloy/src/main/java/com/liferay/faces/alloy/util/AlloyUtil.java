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

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class AlloyUtil {

	// Private Constants
	private static final String REGEX_COLON = "[:]";
	private static final String DOUBLE_BACKSLASH_COLON = "\\\\\\\\:";

	public static String appendToCssClasses(String cssClass, String suffix) {

		String value = cssClass;

		if (value != null) {
			value = value.trim();

			if (value.length() > 0) {
				StringBuilder buf = new StringBuilder();
				String[] cssClasses = cssClass.trim().split(StringPool.SPACE);
				boolean firstClass = true;

				for (String curCssClass : cssClasses) {

					if (firstClass) {
						firstClass = false;
					}
					else {
						buf.append(StringPool.SPACE);
					}

					buf.append(curCssClass);
					buf.append(suffix);
				}

				value = buf.toString();
			}
		}

		return value;
	}

	public static String escapeClientId(String clientId) {
		String escapedClientId = clientId;

		if (escapedClientId != null) {

			// JSF clientId values contain colons, which must be preceeded by double backslashes in order to have them
			// work with JavaScript functions like AUI.one(String). http://yuilibrary.com/projects/yui3/ticket/2528057
			escapedClientId = escapedClientId.replaceAll(REGEX_COLON, DOUBLE_BACKSLASH_COLON);
		}

		return escapedClientId;
	}
}
