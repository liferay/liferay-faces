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
package com.liferay.faces.demos.list;

import javax.faces.FacesWrapper;

import com.liferay.faces.demos.dto.Customer;


/**
 * @author  Neil Griffin
 */
public class CustomerWrapper extends Customer implements FacesWrapper<Customer> {

	// serialVersionUID
	private static final long serialVersionUID = 2212060479044678860L;

	// Private Data Members
	private Customer wrappedCustomer;
	private String exportResourceURL;

	public CustomerWrapper(Customer wrappedCustomer, String exportURL) {
		this.wrappedCustomer = wrappedCustomer;
		this.exportResourceURL = exportURL;
	}

	@Override
	public long getCustomerId() {
		return getWrapped().getCustomerId();
	}

	@Override
	public void setCustomerId(long customerId) {
		getWrapped().setCustomerId(customerId);
	}

	public String getExportResourceURL() {
		return exportResourceURL;
	}

	public void setExportResourceURL(String exportResourceURL) {
		this.exportResourceURL = exportResourceURL;
	}

	@Override
	public String getFirstName() {
		return getWrapped().getFirstName();
	}

	@Override
	public void setFirstName(String firstName) {
		getWrapped().setFirstName(firstName);
	}

	@Override
	public String getLastName() {
		return getWrapped().getLastName();
	}

	@Override
	public void setLastName(String lastName) {
		getWrapped().setLastName(lastName);
	}

	public Customer getWrapped() {
		return wrappedCustomer;
	}

}
