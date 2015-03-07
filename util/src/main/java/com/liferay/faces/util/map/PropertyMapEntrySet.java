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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;


/**
 * @author  Neil Griffin
 */
public class PropertyMapEntrySet<V> extends HashSet<Map.Entry<String, V>> {

	// serialVersionUID
	private static final long serialVersionUID = 6500855053442038977L;

	@Override
	public Iterator<Map.Entry<String, V>> iterator() {
		return new IteratorWrapper(super.iterator());
	}

	public class IteratorWrapper implements Iterator<Map.Entry<String, V>> {

		private Map.Entry<String, V> currentEntry;
		private Iterator<Map.Entry<String, V>> wrappedIterator;

		public IteratorWrapper(Iterator<Map.Entry<String, V>> iterator) {
			this.wrappedIterator = iterator;
		}

		public boolean hasNext() {
			return wrappedIterator.hasNext();
		}

		public Map.Entry<String, V> next() {
			currentEntry = wrappedIterator.next();

			return currentEntry;
		}

		public void remove() {

			if (currentEntry != null) {

				if (currentEntry instanceof AbstractPropertyMapEntry) {
					AbstractPropertyMapEntry<V> propertyMapEntry = (AbstractPropertyMapEntry<V>) currentEntry;
					propertyMapEntry.remove();
				}
			}

			wrappedIterator.remove();
		}

	}
}
