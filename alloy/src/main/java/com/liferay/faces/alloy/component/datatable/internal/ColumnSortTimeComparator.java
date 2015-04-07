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

import java.util.Comparator;

import com.liferay.faces.alloy.component.column.Column;


/**
 * @author  Neil Griffin
 */
public class ColumnSortTimeComparator implements Comparator<Column> {

	@Override
	public int compare(Column column1, Column column2) {

		Long sortTimeColumn1 = (Long) column1.getAttributes().get("sortTime");
		Long sortTimeColumn2 = (Long) column2.getAttributes().get("sortTime");

		if (sortTimeColumn1 == null) {
			return 1;
		}
		else if (sortTimeColumn2 == null) {
			return -1;
		}
		else {

			if (sortTimeColumn1 < sortTimeColumn2) {
				return -1;
			}
			else if (sortTimeColumn1 > sortTimeColumn2) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}
}
