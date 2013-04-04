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
package com.liferay.faces.bridge.component.html;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class AttributesWrapper implements Map<String, Object>, Wrapper<Map<String, Object>> {

	public void clear() {
		getWrapped().clear();
	}

	public boolean containsKey(Object key) {
		return getWrapped().containsKey(key);
	}

	public boolean containsValue(Object value) {
		return getWrapped().containsValue(value);
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return getWrapped().entrySet();
	}

	public Object get(Object key) {
		return getWrapped().get(key);
	}

	public Set<String> keySet() {
		return getWrapped().keySet();
	}

	public Object put(String key, Object value) {
		return getWrapped().put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Object> values) {
		getWrapped().putAll(values);
	}

	public Object remove(Object key) {
		return getWrapped().remove(key);
	}

	public int size() {
		return getWrapped().size();
	}

	public Collection<Object> values() {
		return getWrapped().values();
	}

	public boolean isEmpty() {
		return getWrapped().isEmpty();
	}

}
