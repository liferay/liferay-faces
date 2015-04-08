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
package com.liferay.faces.alloy.component.datatable.internal;

/**
 * This class provides a simple mechanism for cycling through a comma-delimited list of strings (such as the rowClasses
 * and columnClasses attributes of the DataTable). Each time the {@link #getNextItem()} method is called, the index
 * pointing to the next item is incremented. When the end of the list is reached, the {@link #getNextItem()} method
 * resets the index back to the beginning and returns the first item.
 *
 * @author  Neil Griffin
 */
public class ItemCycler {

	// Private Data Members
	private String[] items;
	private int nextItemIndex;

	public ItemCycler(String commaDelimitedItems) {

		if (commaDelimitedItems != null) {

			commaDelimitedItems = commaDelimitedItems.trim();

			if (commaDelimitedItems.length() > 0) {

				this.items = commaDelimitedItems.split(",");
			}
		}
	}

	public void reset() {
		nextItemIndex = 0;
	}

	public String getNextItem() {

		String nextItem = null;

		if (items != null) {

			nextItem = items[nextItemIndex];
			nextItemIndex++;

			if (nextItemIndex >= items.length) {
				nextItemIndex = 0;
			}
		}

		return nextItem;
	}
}
