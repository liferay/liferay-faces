/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.map;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author  Neil Griffin
 */
public abstract class AbstractPropertyMap<V> implements Map<String, V> {

	public void clear() {
		Enumeration<String> propertyNames = getPropertyNames();

		if (propertyNames != null) {

			while (propertyNames.hasMoreElements()) {
				removeProperty(propertyNames.nextElement());
			}
		}
	}

	public boolean containsKey(Object key) {
		boolean found = false;

		if (key != null) {
			String keyAsString = key.toString();

			// NOTE: This is an inefficient mechanism because getPropertyNames() can potentially be slow since it has to
			// return a new (non-cached) Enumeration each time it is called. Because of this, it is best to override
			// this containsKey method with optimizations when possible.
			Enumeration<String> propertyNames = getPropertyNames();

			if (propertyNames != null) {

				while (!found && propertyNames.hasMoreElements()) {
					String propertyName = propertyNames.nextElement();
					found = propertyName.equals(keyAsString);
				}
			}
		}

		return found;
	}

	public boolean containsValue(Object value) {
		boolean found = false;
		Enumeration<String> propertyNames = getPropertyNames();

		if (propertyNames != null) {

			while (!found && propertyNames.hasMoreElements()) {
				String propertyName = propertyNames.nextElement();
				Object propertyValue = getProperty(propertyName);

				if (propertyValue == null) {
					found = (value == null);
				}
				else {
					found = propertyValue.equals(value);
				}
			}
		}

		return found;
	}

	public Set<Map.Entry<String, V>> entrySet() {
		Set<Map.Entry<String, V>> entrySet = null;
		Enumeration<String> propertyNames = getPropertyNames();

		if (propertyNames != null) {
			entrySet = new PropertyMapEntrySet<V>();

			while (propertyNames.hasMoreElements()) {
				String name = propertyNames.nextElement();
				AbstractPropertyMapEntry<V> propertyMapEntry = createPropertyMapEntry(name);
				entrySet.add(propertyMapEntry);
			}
		}

		return entrySet;
	}

	public V get(Object key) {
		V value = null;

		if (key != null) {
			String keyAsString = key.toString();
			value = getProperty(keyAsString);
		}

		return value;
	}

	public Set<String> keySet() {
		Set<String> keySet = null;
		Enumeration<String> propertyNames = getPropertyNames();

		if (propertyNames != null) {
			keySet = new HashSet<String>();

			while (propertyNames.hasMoreElements()) {
				String propertyName = propertyNames.nextElement();
				keySet.add(propertyName);
			}
		}

		return keySet;
	}

	public V put(String key, V value) {
		V oldValue = getProperty(key);
		setProperty(key, value);

		return oldValue;
	}

	public void putAll(Map<? extends String, ? extends V> t) {

		if (t != null) {
			Set<? extends String> keySet = t.keySet();

			if (keySet != null) {

				for (String key : keySet) {
					setProperty(key, t.get(key));
				}
			}
		}
	}

	public V remove(Object key) {
		V oldValue = null;

		if (key != null) {
			String keyAsString = key.toString();
			oldValue = getProperty(keyAsString);
			removeProperty(keyAsString);
		}

		return oldValue;
	}

	public int size() {
		int size = 0;
		Enumeration<String> propertyNames = getPropertyNames();

		if (propertyNames != null) {

			while (propertyNames.hasMoreElements()) {
				size++;
				propertyNames.nextElement();
			}
		}

		return size;
	}

	public Collection<V> values() {
		Collection<V> values = null;
		Enumeration<String> propertyNames = getPropertyNames();

		if (propertyNames != null) {
			values = new HashSet<V>();

			while (propertyNames.hasMoreElements()) {
				String propertyName = propertyNames.nextElement();
				V value = getProperty(propertyName);
				values.add(value);
			}
		}

		return values;
	}

	protected abstract AbstractPropertyMapEntry<V> createPropertyMapEntry(String name);

	protected abstract void removeProperty(String name);

	protected abstract V getProperty(String name);

	protected abstract void setProperty(String name, V value);

	protected abstract Enumeration<String> getPropertyNames();

	public boolean isEmpty() {
		Enumeration<String> propertyNames = getPropertyNames();

		return ((propertyNames == null) || !propertyNames.hasMoreElements());
	}
}
