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

import java.util.Arrays;
import java.util.HashMap;


/**
 * @author  Neil Griffin
 */
public class NamespacedParameterMap extends HashMap<String, String[]> {

	// serialVersionUID
	private static final long serialVersionUID = 3026454680441721818L;

	// Private Data Members
	private String namespace;

	public NamespacedParameterMap(String namespace) {
		this.namespace = namespace;
	}

	public String[] append(String key, String value) {

		String[] values = get(key);

		if (values == null) {
			values = new String[] { value };
		}
		else {
			values = Arrays.copyOf(values, values.length + 1);
			values[values.length] = value;
		}

		return super.put(key, values);
	}

	@Override
	public boolean containsKey(Object key) {

		boolean found = super.containsKey(key);

		if (!found) {
			found = super.containsKey(namespace + key);
		}

		return found;

	}

	@Override
	public String[] get(Object key) {

		String[] value = super.get(key);

		if (value == null) {
			value = super.get(namespace + key);
		}

		return value;
	}

	public String getFirst(Object key) {

		String value = null;

		String[] values = get(key);

		if ((values != null) && (values.length > 0)) {
			value = values[0];
		}

		return value;
	}

}
