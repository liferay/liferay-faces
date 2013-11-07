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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;

import com.liferay.faces.demos.dto.Customer;


/**
 * @author  Neil Griffin
 */
@ApplicationScoped
public class CustomerServiceMockImpl implements CustomerService, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 6241863951014091848L;

	// Private Data Members
	private Map<Long, Customer> customerMap;

	public CustomerServiceMockImpl() {
		this.customerMap = new HashMap<Long, Customer>();
	}

	@Override
	public void save(Customer customer) {
		customerMap.put(customer.getCustomerId(), customer);
	}
}
