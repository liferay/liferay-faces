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
package com.liferay.faces.demos.service.mock;

import java.util.Comparator;

import com.liferay.faces.demos.dto.Customer;


/**
 * @author  Neil Griffin
 */
public class CustomerSortComparator implements Comparator<Customer> {

	public String sortColumn;
	public boolean sortAscending;

	public CustomerSortComparator(String sortColumn, Boolean sortAscending) {

		if (sortColumn == null) {
			this.sortColumn = "firstName";
		}
		else {
			this.sortColumn = sortColumn;
		}

		this.sortAscending = ((sortAscending != null) && sortAscending);
	}

	public int compare(Customer customer1, Customer customer2) {

		int value = 0;

		if (sortColumn.equals("firstName")) {
			value = customer1.getFirstName().compareTo(customer2.getFirstName());
		}
		else if (sortColumn.equals("lastName")) {
			value = customer1.getLastName().compareTo(customer2.getLastName());
		}
		else if (sortColumn.equals("emailAddress")) {
			value = customer1.getEmailAddress().compareTo(customer2.getEmailAddress());
		}

		if (!sortAscending) {
			value = value * -1;
		}

		return value;
	}

}
