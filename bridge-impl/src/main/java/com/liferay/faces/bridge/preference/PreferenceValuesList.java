/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.preference;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.portlet.PortletPreferences;


/**
 * @author  Neil Griffin
 */
public class PreferenceValuesList implements List<String> {

	private PortletPreferences portletPreferences;
	private String name;

	public PreferenceValuesList(PortletPreferences portletPreferences, String name) {
		this.portletPreferences = portletPreferences;
		this.name = name;
	}

	public boolean add(String value) {
		throw new UnsupportedOperationException();
	}

	public void add(int index, String value) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(Collection<? extends String> values) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(int index, Collection<? extends String> values) {
		throw new UnsupportedOperationException();
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public boolean contains(Object value) {

		if (value != null) {
			String[] values = portletPreferences.getValues(name, null);

			if (values != null) {

				for (String currentValue : values) {

					if (value.equals(currentValue)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	public boolean containsAll(Collection<?> values) {
		boolean allFound = true;

		if (values != null) {
			Collection<String> stringCollection = (Collection<String>) values;

			for (String value : stringCollection) {
				allFound = (allFound && contains(value));

				if (!allFound) {
					break;
				}
			}
		}

		return allFound;
	}

	public String get(int index) {
		String value = null;
		String[] values = portletPreferences.getValues(name, null);

		if ((values != null) && (values.length > index)) {
			value = values[index];
		}

		return value;
	}

	public int indexOf(Object value) {
		int index = 0;
		String[] values = portletPreferences.getValues(name, null);

		if ((values != null)) {

			for (int i = 0; i < values.length; i++) {
				String currentValue = values[i];

				if ((currentValue != null) && (currentValue.equals(value))) {
					index = i;

					break;
				}
			}
		}

		return index;
	}

	public Iterator<String> iterator() {
		return new PreferenceValueIterator(portletPreferences.getNames());
	}

	public int lastIndexOf(Object value) {
		throw new UnsupportedOperationException();
	}

	public ListIterator<String> listIterator() {
		throw new UnsupportedOperationException();
	}

	public ListIterator<String> listIterator(int arg0) {
		throw new UnsupportedOperationException();
	}

	public boolean remove(Object arg0) {
		throw new UnsupportedOperationException();
	}

	public String remove(int arg0) {
		throw new UnsupportedOperationException();
	}

	public boolean removeAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	public boolean retainAll(Collection<?> arg0) {
		throw new UnsupportedOperationException();
	}

	public String set(int arg0, String arg1) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		int size = 0;
		String[] values = portletPreferences.getValues(name, null);

		if (values != null) {
			size = values.length;
		}

		return size;
	}

	public List<String> subList(int arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	public Object[] toArray() {
		return portletPreferences.getValues(name, null);
	}

	public <T> T[] toArray(T[] arg0) {
		throw new UnsupportedOperationException();
	}

	public boolean isEmpty() {
		String[] values = portletPreferences.getValues(name, null);

		return ((values == null) || (values.length == 0));
	}
}
