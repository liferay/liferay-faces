/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.User;


/**
 * @author  Neil Griffin
 */
public class UserOrderByComparator extends OrderByComparator {

	// serialVersionUID
	private static final long serialVersionUID = 8419864349610805447L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UserOrderByComparator.class);

	// Private Data Members
	private boolean ascending;
	private String columnName;
	private String orderBy;

	public UserOrderByComparator(String columnName, boolean ascending) {
		this.columnName = columnName;
		this.ascending = ascending;

		if (ascending) {
			this.orderBy = columnName + " ASC";
		}
		else {
			this.orderBy = columnName + " DESC";
		}
	}

	@Override
	public int compare(Object obj1, Object obj2) {

		int value = 0;
		User user1 = (User) obj1;
		User user2 = (User) obj2;

		String name1 = user1.getLastName().toLowerCase();
		String name2 = user2.getLastName().toLowerCase();

		value = name1.compareTo(name2);
		logger.debug("comparing name1=[" + name1 + "] name2=[" + name2 + "] value=" + value);

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

		return orderBy;
	}

	@Override
	public String[] getOrderByFields() {
		return new String[] { columnName };
	}

}
