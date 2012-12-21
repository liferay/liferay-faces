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
package com.liferay.faces.demos.list;

import com.liferay.portal.kernel.util.OrderByComparator;


/**
 * @author  Neil Griffin, ported to Primefaces by Jacques Champliaud
 */
public class DocumentComparatorFactory {

	public static OrderByComparator getComparator(String columnName, boolean sortAscending) {

		if (columnName.equals("readCount")) {
			return new FileEntryReadCountComparator(sortAscending);
		}
		else if (columnName.equals("size")) {
			return new FileEntrySizeComparator(sortAscending);
		}
		else if (columnName.equals("userName")) {
			return new FileEntryUserNameComparator(sortAscending);
		}
		else {
			return new FileEntryTitleComparator(sortAscending);
		}
	}
}
