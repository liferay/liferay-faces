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
package com.liferay.faces.demos.service;

import java.util.List;

import com.liferay.faces.demos.dto.Customer;


/**
 * @author  Neil Griffin
 */
public interface CustomerService {

	public static final int ALL_POS = -1;

	public Customer addCustomer(Customer customer);

	public int countCustomers();

	public Customer removeCustomer(int customerId);

	public Customer updateCustomer(Customer customer);

	public List<Customer> getCustomers();

	public List<Customer> getCustomers(String sortColumn, Boolean sortAscending);

	public List<Customer> getCustomers(int startRow, int finishRow, String sortColumn, Boolean sortAscending);
}
