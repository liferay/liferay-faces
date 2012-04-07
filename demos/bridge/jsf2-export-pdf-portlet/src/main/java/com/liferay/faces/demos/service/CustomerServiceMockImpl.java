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

	// Public Constants
	public static final long ID_BRIAN_GREEN = 1;
	public static final long ID_LIZ_KESSLER = 2;
	public static final long ID_RICH_SHEARER = 3;

	// serialVersionUID
	private static final long serialVersionUID = 6515562063250005643L;

	// Private Data Members
	private ArrayList<Customer> allCustomers;

	@PostConstruct
	public void postConstruct() {
		allCustomers = new ArrayList<Customer>();

		Customer customer = new Customer(ID_BRIAN_GREEN, "Brian", "Green");
		allCustomers.add(customer);
		customer = new Customer(ID_LIZ_KESSLER, "Liz", "Kessler");
		allCustomers.add(customer);
		customer = new Customer(ID_RICH_SHEARER, "Rich", "Shearer");
		allCustomers.add(customer);
	}

	public void save(Customer customer) {

		for (int i = 0; i < allCustomers.size(); i++) {

			if (allCustomers.get(i).getCustomerId() == customer.getCustomerId()) {
				allCustomers.set(i, customer);
			}
		}
	}

	public List<Customer> getAllCustomers() {
		return allCustomers;
	}

	public Customer getCustomer(long customerId) {

		Customer customer = null;
		List<Customer> customers = getAllCustomers();

		for (Customer curCustomer : customers) {

			if (curCustomer.getCustomerId() == customerId) {
				customer = curCustomer;

				break;
			}
		}

		return customer;
	}
}
