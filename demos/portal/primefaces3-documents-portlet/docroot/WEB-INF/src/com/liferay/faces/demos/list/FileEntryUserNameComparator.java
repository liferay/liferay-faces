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

import com.liferay.portlet.documentlibrary.model.DLFileEntry;


/**
 * @author  Neil Griffin, ported to Primefaces by Jacques Champliaud
 */
public class FileEntryUserNameComparator extends OrderByComparator {

	// serialVersionUID
	private static final long serialVersionUID = 3230208812916238433L;

	// Private Constants
	private static final String ORDER_BY_ASC = "userName ASC";
	private static final String ORDER_BY_DESC = "userName DESC";
	private static final String[] ORDER_BY_FIELDS = { "userName" };

	// Private Data Members
	private boolean ascending;

	public FileEntryUserNameComparator() {
		this(true);
	}

	public FileEntryUserNameComparator(boolean ascending) {
		this.ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		DLFileEntry fileEntry1 = (DLFileEntry) obj1;
		DLFileEntry fileEntry2 = (DLFileEntry) obj2;

		int value = fileEntry1.getUserName().compareTo(fileEntry2.getUserName());

		if (ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public boolean isAscending() {
		return ascending;
	}

	@Override
	public String getOrderBy() {

		if (ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

}
