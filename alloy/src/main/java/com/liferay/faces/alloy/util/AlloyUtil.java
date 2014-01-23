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

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author      Neil Griffin
 * @deprecated  Use {@link com.liferay.faces.util.component.ComponentUtil ComponentUtil} instead.
 */
public class AlloyUtil {

	/**
	 * @deprecated  Use {@link com.liferay.faces.util.component.ComponentUtil#appendToCssClasses(String cssClass, String
	 *              suffix) ComponentUtil's appendToCssClasses method} instead.
	 */
	public static String appendToCssClasses(String cssClass, String suffix) {
		return ComponentUtil.appendToCssClasses(cssClass, suffix);
	}

	/**
	 * @deprecated  Use {@link com.liferay.faces.util.component.ComponentUtil#escapeClientId(String) ComponentUtil's
	 *              escapeClientId method} instead.
	 */
	public static String escapeClientId(String clientId) {
		return ComponentUtil.escapeClientId(clientId);
	}
}
