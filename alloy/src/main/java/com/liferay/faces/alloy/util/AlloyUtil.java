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
package com.liferay.faces.alloy.util;

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author      Neil Griffin
 * @deprecated  Use {@link ComponentUtil} from the Liferay Faces Util project instead.
 */
@Deprecated
public class AlloyUtil {

	/**
	 * @deprecated  Use {@link ComponentUtil#appendToCssClasses(String, String)} instead.
	 */
	public static String appendToCssClasses(String cssClass, String suffix) {
		return ComponentUtil.appendToCssClasses(cssClass, suffix);
	}

	/**
	 * @deprecated  Use {@link ComponentUtil#escapeClientId(String)} instead.
	 */
	public static String escapeClientId(String clientId) {
		return ComponentUtil.escapeClientId(clientId);
	}
}
