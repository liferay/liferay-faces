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
package com.liferay.faces.util.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Set;


/**
 * This class wraps a {@link java.util.HashMap} that represents rows that are marked, typically for deletion. It also
 * extends the {@link java.util.Observable} class so that observers can be notified when the map has changed, according
 * to the {@link NotificationEvent} enumeration.
 *
 * @author  Neil Griffin
 */
public class RowMarks extends Observable implements Map<Object, Boolean> {

	/**
	 * @author  Neil Griffin
	 */
	public enum NotificationEvent {
		ALL_ROWS_CLEARED, ALL_ROWS_MARKED, ALL_ROWS_REPLACED, ALL_ROWS_UNMARKED, ROW_MARKED, ROW_REMOVED, ROW_UNMARKED
	}

	private Map<Object, Boolean> rowMarks = new HashMap<Object, Boolean>();

	public void clear() {
		rowMarks.clear();
		setChanged();
		notifyObservers(NotificationEvent.ALL_ROWS_CLEARED);
	}

	public boolean containsKey(Object key) {
		return rowMarks.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return rowMarks.containsValue(value);
	}

	public Set<java.util.Map.Entry<Object, Boolean>> entrySet() {
		return rowMarks.entrySet();
	}

	public Boolean get(Object key) {
		return rowMarks.get(key);
	}

	public Set<Object> keySet() {
		return rowMarks.keySet();
	}

	/**
	 * Sets all the entries in the map with a value of {@link Boolean#TRUE}.
	 */
	public void markAll() {
		Set<Entry<Object, Boolean>> entrySet = this.entrySet();
		Iterator<Map.Entry<Object, Boolean>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<Object, Boolean> mapEntry = iterator.next();
			mapEntry.setValue(Boolean.TRUE);
		}

		setChanged();
		notifyObservers(NotificationEvent.ALL_ROWS_MARKED);
	}

	public Boolean put(Object key, Boolean value) {
		Boolean putValue = rowMarks.put(key, value);
		setChanged();

		if (value) {
			notifyObservers(NotificationEvent.ROW_MARKED);
		}
		else {
			notifyObservers(NotificationEvent.ROW_UNMARKED);
		}

		return putValue;
	}

	public void putAll(Map<? extends Object, ? extends Boolean> t) {
		rowMarks.putAll(t);
		setChanged();
		notifyObservers(NotificationEvent.ALL_ROWS_REPLACED);
	}

	public Boolean remove(Object key) {
		Boolean removedValue = rowMarks.remove(key);
		setChanged();
		notifyObservers(NotificationEvent.ROW_REMOVED);

		return removedValue;
	}

	public int size() {
		return rowMarks.size();
	}

	/**
	 * Sets all the entries in the map with a value of {@link Boolean#FALSE}.
	 */
	public void unmarkAll() {
		Set<Entry<Object, Boolean>> entrySet = this.entrySet();
		Iterator<Map.Entry<Object, Boolean>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<Object, Boolean> mapEntry = iterator.next();
			mapEntry.setValue(Boolean.FALSE);
		}

		setChanged();
		notifyObservers(NotificationEvent.ALL_ROWS_UNMARKED);
	}

	public Collection<Boolean> values() {
		return rowMarks.values();
	}

	/**
	 * Returns the number of entries in the map that have a value of true.
	 */
	public int getCount() {
		int count = 0;
		Set<Entry<Object, Boolean>> entrySet = this.entrySet();
		Iterator<Map.Entry<Object, Boolean>> iterator = entrySet.iterator();

		while (iterator.hasNext()) {
			Map.Entry<Object, Boolean> mapEntry = iterator.next();

			if (mapEntry.getValue()) {
				count++;
			}
		}

		return count;
	}

	public boolean isEmpty() {
		return rowMarks.isEmpty();
	}

}
