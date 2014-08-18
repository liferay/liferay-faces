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
package com.liferay.faces.alloy.component.inputfile.internal;

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class InputFileTemplateUtil {

	public static String toJavaScriptArray(String[] items) {

		StringBuilder javaScriptArray = new StringBuilder(StringPool.OPEN_BRACKET);

		if (items != null) {

			for (int i = 0; i < items.length; i++) {

				if (i > 0) {
					javaScriptArray.append(StringPool.COMMA);
				}

				javaScriptArray.append(StringPool.APOSTROPHE);
				javaScriptArray.append(items[i]);
				javaScriptArray.append(StringPool.APOSTROPHE);
			}
		}

		javaScriptArray.append(StringPool.CLOSE_BRACKET);

		return javaScriptArray.toString();
	}
}
