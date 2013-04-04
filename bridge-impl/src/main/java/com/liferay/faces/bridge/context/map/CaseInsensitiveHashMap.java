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

import java.util.HashMap;


/**
 * @author  Neil Griffin
 */
public class CaseInsensitiveHashMap<V> extends HashMap<String, V> {

	// serialVersionUID
	private static final long serialVersionUID = 7989809010272499963L;

	@Override
	public boolean containsKey(Object key) {
		return super.containsKey(getLowerCaseKey(key));
	}

	@Override
	public V get(Object key) {

		return super.get(getLowerCaseKey(key));
	}

	@Override
	public V put(String key, V value) {
		return super.put(getLowerCaseKey(key), value);
	}

	protected String getLowerCaseKey(Object key) {
		String lowerCaseKey = null;

		if (key != null) {
			lowerCaseKey = key.toString().toLowerCase();
		}

		return lowerCaseKey;
	}
}
