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
package com.liferay.faces.demos.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.service.CustomerService;


/**
 * @author  Neil Griffin
 */
@RequestScoped
@ManagedBean
public class DataTableModelBean {

	// Injections
	@ManagedProperty(value = "#{customerService}")
	private CustomerService customerService;

	// Private Data Members
	private List<Customer> customers;

	@PostConstruct
	public void postConstruct() {
		customers = customerService.getAllCustomers();
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
}
