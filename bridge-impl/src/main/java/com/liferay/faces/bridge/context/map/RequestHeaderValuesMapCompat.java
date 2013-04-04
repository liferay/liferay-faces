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
package com.liferay.faces.bridge.context.map;

import com.liferay.faces.bridge.container.PortletContainer;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class RequestHeaderValuesMapCompat extends CaseInsensitiveHashMap<String[]> {

	// serialVersionUID
	private static final long serialVersionUID = 5256297252491398013L;

	protected void addJSF1Headers(PortletContainer portletContainer) {

		// This is a no-op for JSF 2.x
	}
}
