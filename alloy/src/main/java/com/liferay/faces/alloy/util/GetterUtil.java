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
import com.liferay.faces.util.helper.StringHelper;


/**
 * @author      Neil Griffin
 * @deprecated  Use {@link BooleanHelper} and {@link ShortHelper} from the Liferay Faces Util project instead.
 */
@Deprecated
public class GetterUtil {

	/**
	 * @deprecated  Use {@link BooleanHelper#toBoolean(String, boolean)} instead.
	 */
	public static boolean getBoolean(Object value, boolean defaultValue) {
		return BooleanHelper.toBoolean(value.toString(), defaultValue);
	}

	/**
	 * @deprecated  Use {@link BooleanHelper#toBoolean(String, boolean)} instead.
	 */
	public static boolean getBoolean(String value, boolean defaultValue) {
		return BooleanHelper.toBoolean(value, defaultValue);
	}

	/**
	 * @deprecated  Use {@link StringHelper#toString(Object, String)} instead.
	 */
	public static String getString(Object value, String defaultValue) {
		return StringHelper.toString(value, defaultValue);
	}
}
