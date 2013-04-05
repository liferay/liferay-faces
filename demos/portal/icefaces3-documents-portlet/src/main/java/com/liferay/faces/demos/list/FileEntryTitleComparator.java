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
package com.liferay.faces.demos.list;

import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.portlet.documentlibrary.model.DLFileEntry;


/**
 * @author  Neil Griffin
 */
public class FileEntryTitleComparator extends OrderByComparator {

	// serialVersionUID
	private static final long serialVersionUID = 3230208812916238433L;

	// Private Constants
	private static final String ORDER_BY_ASC = "title ASC";
	private static final String ORDER_BY_DESC = "title DESC";
	private static final String[] ORDER_BY_FIELDS = { "title" };

	// Private Data Members
	private boolean ascending;

	public FileEntryTitleComparator() {
		this(true);
	}

	public FileEntryTitleComparator(boolean ascending) {
		this.ascending = ascending;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		DLFileEntry fileEntry1 = (DLFileEntry) obj1;
		DLFileEntry fileEntry2 = (DLFileEntry) obj2;

		int value = fileEntry1.getTitle().compareTo(fileEntry2.getTitle());

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
