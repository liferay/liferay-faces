/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
import javax.faces.bean.ViewScoped;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.service.CustomerService;


/**
 * @author  Vernon Singleton
 * @author  Kyle Stiemann
 */
@ManagedBean
@ViewScoped
public class CommandModelBean {

	// Injections
	@ManagedProperty(value = "#{customerService}")
	private CustomerService customerService;

	// Private Data Members
	private boolean ajax;
	private Customer selectedCustomer;
	private List<Customer> customers;

	@PostConstruct
	public void postConstruct() {
		customers = customerService.getCustomers(0, 5);
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public Customer getSelectedCustomer() {
		return selectedCustomer;
	}

	public void setSelectedCustomer(Customer selectedCustomer) {
		this.selectedCustomer = selectedCustomer;
	}

	public boolean isAjax() {
		return ajax;
	}
}
