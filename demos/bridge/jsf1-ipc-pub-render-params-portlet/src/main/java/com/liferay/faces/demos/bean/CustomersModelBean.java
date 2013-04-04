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
package com.liferay.faces.demos.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.service.CustomerService;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class CustomersModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2241487919972557504L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CustomersModelBean.class);

	// Injections
	private CustomerService customerService;

	// Private Bean Properties
	private List<Customer> allCustomers;
	private String selectedCustomerId;

	@PostConstruct
	public void postConstruct() {
		logger.trace("@PostConstruct annotation worked");
		allCustomers = customerService.getAllCustomers();
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("@PreDestroy annotation worked");
	}

	public List<Customer> getAllCustomers() {
		return allCustomers;
	}

	public void setCustomerService(CustomerService customerService) {

		// Injected via ManagedProperty annotation
		this.customerService = customerService;
	}

	public String getSelectedCustomerId() {
		return selectedCustomerId;
	}

	public void setSelectedCustomerId(String selectedCustomerId) {
		this.selectedCustomerId = selectedCustomerId;
	}
}
