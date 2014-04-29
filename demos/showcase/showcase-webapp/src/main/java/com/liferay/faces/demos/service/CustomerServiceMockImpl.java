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
package com.liferay.faces.demos.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.liferay.faces.demos.dto.Customer;


/**
 * @author  Neil Griffin
 */
@ApplicationScoped
@ManagedBean(name = "customerService")
public class CustomerServiceMockImpl implements CustomerService, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2241487919972957504L;

	// Private Data Members
	private List<Customer> allCustomers;

	@PostConstruct
	public void postConstruct() {
		allCustomers = new ArrayList<Customer>();

		Customer customer = new Customer(1, "Brian Green");
		allCustomers.add(customer);
		customer = new Customer(2, "Liz Kessler");
		allCustomers.add(customer);
		customer = new Customer(3, "Rich Shearer");
		allCustomers.add(customer);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return allCustomers;
	}
}
