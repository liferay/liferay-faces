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

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.service.CustomerService;

/**
 * @author  Neil Griffin
 */
//JSF 2+ @ManagedBean
//JSF 2+ @RequestScoped
public class DataTableBacking {

	private CustomerService customerService;

	// Private Data Members
	private List<Customer> customerDataModel;
	
	public List<Customer> getCustomerDataModel() {

		if (customerDataModel == null) {
			customerDataModel = customerService.getAllCustomers();
		}

		return customerDataModel;
	}

	public void setCustomerService(CustomerService customerService) {

		this.customerService = customerService;
	}
	
	public CustomerService getCustomerService() {
		if (this.customerService == null) {
			this.customerService = getCService(FacesContext.getCurrentInstance());
		}

		return this.customerService;
	}
	
	protected CustomerService getCService(FacesContext facesContext) {

		Application application = facesContext.getApplication();
		ELResolver elResolver = application.getELResolver();
		ELContext elContext = facesContext.getELContext();

		return (CustomerService) elResolver.getValue(elContext, null, "customerService");
	}
}
