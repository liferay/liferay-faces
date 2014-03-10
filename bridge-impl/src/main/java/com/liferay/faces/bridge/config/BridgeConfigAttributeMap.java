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
package com.liferay.faces.bridge.config;

import java.util.HashMap;

import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class BridgeConfigAttributeMap extends HashMap<String, Object> {

	// serialVersionUID
	private static final long serialVersionUID = 7385067508147506114L;

	@Override
	public Object get(Object key) {
		Object value = super.get(key);

		if (value == null) {
			value = ProductMap.getInstance().get(key);
		}

		return value;
	}
}
