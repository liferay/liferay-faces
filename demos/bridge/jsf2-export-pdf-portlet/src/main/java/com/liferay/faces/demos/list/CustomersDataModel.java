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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.ListDataModel;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.resource.CustomerExportResource;
import com.liferay.faces.demos.service.CustomerService;


/**
 * @author  Neil Griffin
 */
public class CustomersDataModel extends ListDataModel<CustomerWrapper> implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 1639576409231493573L;

	public CustomersDataModel(CustomerService customerService) {

		List<Customer> customers = customerService.getAllCustomers();
		List<CustomerWrapper> wrappedData = new ArrayList<CustomerWrapper>(customers.size());

		if (customers != null) {

			for (Customer customer : customers) {
				CustomerExportResource customerExportResource = new CustomerExportResource(customer);
				String exportResourceURL = customerExportResource.getRequestPath();
				wrappedData.add(new CustomerWrapper(customer, exportResourceURL));
			}

			setWrappedData(wrappedData);
		}
	}

}
