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
package com.liferay.faces.demos.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.liferay.faces.demos.list.CustomersDataModel;
import com.liferay.faces.demos.service.CustomerService;


/**
 * This is a model managed bean that manages customer data.
 *
 * @author  "Neil Griffin"
 */
@ManagedBean(name = "customersModelBean")
@ViewScoped
public class CustomersModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 7459638254337818761L;

	// Injections
	@ManagedProperty(value = "#{customerService}")
	private CustomerService customerService;

	// Private Data Members
	private CustomersDataModel customers;

	public CustomersDataModel getCustomers() {

		if (customers == null) {
			customers = new CustomersDataModel(customerService);
		}

		return customers;
	}

	public void setCustomerService(CustomerService customerService) {

		// Injected via ManagedProperty annotation.
		this.customerService = customerService;
	}
}
