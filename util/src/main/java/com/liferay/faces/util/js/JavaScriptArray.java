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
package com.liferay.faces.util.js;

import com.liferay.faces.util.lang.StringPool;


/**
 * This is a simple marker class that wraps a String. It marks the fact that the wrapped string is a JavaScript array.
 *
 * @author  Neil Griffin
 */
public final class JavaScriptArray {

	// Private Data Members
	private String value;

	public JavaScriptArray() {
		this(null);
	}

	public JavaScriptArray(String[] items) {

		StringBuilder buf = new StringBuilder(StringPool.OPEN_BRACKET);

		if (items != null) {

			for (int i = 0; i < items.length; i++) {

				if (i > 0) {
					buf.append(StringPool.COMMA);
				}

				buf.append(StringPool.APOSTROPHE);
				buf.append(items[i]);
				buf.append(StringPool.APOSTROPHE);
			}
		}

		buf.append(StringPool.CLOSE_BRACKET);

		value = buf.toString();
	}

	@Override
	public String toString() {
		return value;
	}
}
