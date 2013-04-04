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

import java.io.IOException;
import java.util.List;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.service.CustomerService;
import com.liferay.faces.util.model.LazyDataModel;


/**
 * @author  Neil Griffin
 */
public class CustomerLazyDataModel extends LazyDataModel<Customer> {

	private CustomerService customerService;
	private Integer totalRows;

	public CustomerLazyDataModel(CustomerService customerService, int rowsPerPage) {
		this.customerService = customerService;
		setRowsPerPage(rowsPerPage);
	}

	@Override
	public int countRows() {

		if (totalRows == null) {
			totalRows = customerService.countCustomers();
		}

		return totalRows;
	}

	@Override
	public void deleteRow(Object customerAsObject) throws IOException {
		Customer customer = (Customer) customerAsObject;
		customerService.removeCustomer(customer.getCustomerId());
	}

	@Override
	public List<Customer> findRows(int startRow, int finishRow) {
		return customerService.getCustomers(startRow, finishRow, getSortColumn(), isSortAscending());
	}

	@Override
	public Object getPrimaryKey(Customer customer) {
		return customer.getCustomerId();
	}

}
