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
package com.liferay.faces.alloy.component.autocomplete.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * @author  Kyle Stiemann
 */
class AutoCompleteFilterCharMatchImpl implements AutoCompleteFilter {

	@Override
	public List<String> doFilter(String query, List<String> items, boolean caseSensitive, Locale locale) {

		List<String> results = new ArrayList<String>();
		boolean add;

		if (!caseSensitive) {
			query = query.toLowerCase(locale);
		}

		int size = items.size();

		for (int i = 0; i < size; i++) {

			String item = items.get(i);

			if (!caseSensitive) {
				item = item.toLowerCase(locale);
			}

			add = true;

			char[] queryCharArray = query.toCharArray();

			for (Character queryChar : queryCharArray) {

				if (item.indexOf(queryChar) < 0) {
					add = false;

					break;
				}
			}

			if (add) {

				// Add the original item in case the item was changed to lower case.
				results.add(items.get(i));
			}
		}

		return results;
	}
}
