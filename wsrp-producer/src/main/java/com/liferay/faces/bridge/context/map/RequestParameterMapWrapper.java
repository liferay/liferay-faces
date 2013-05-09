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

import java.util.Collection;
import java.util.Map;
import java.util.Set;


/**
 * @author  Neil Griffin
 */
public abstract class RequestParameterMapWrapper implements Map<String, String> {

	public void clear() {
		getWrapped().clear();
	}

	public boolean containsKey(Object key) {
		return getWrapped().containsKey(key);
	}

	public boolean containsValue(Object value) {
		return getWrapped().containsValue(value);
	}

	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return getWrapped().entrySet();
	}

	public String get(Object key) {
		return getWrapped().get(key);
	}

	public Set<String> keySet() {
		return getWrapped().keySet();
	}

	public String put(String key, String value) {
		return getWrapped().put(key, value);
	}

	public void putAll(Map<? extends String, ? extends String> m) {
		getWrapped().putAll(m);
	}

	public String remove(Object key) {
		return getWrapped().remove(key);
	}

	public int size() {
		return getWrapped().size();
	}

	public Collection<String> values() {
		return getWrapped().values();
	}

	public abstract Map<String, String> getWrapped();

	public boolean isEmpty() {
		return getWrapped().isEmpty();
	}

}
