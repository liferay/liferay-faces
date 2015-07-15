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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UICommand;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;

import com.liferay.faces.demos.comparator.CustomerComparator;
import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.service.CustomerService;
import com.liferay.faces.util.model.OnDemandDataModel;
import com.liferay.faces.util.model.SortCriterion;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@ViewScoped
public class DataTableBacking implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 1715081848553221866L;

	// Injections
	@ManagedProperty(value = "#{customerService}")
	private transient CustomerService customerService;

	// Private Data Members
	private List<Customer> customerDataModel;
	
	public List<Customer> getCustomerDataModel() {

		if (customerDataModel == null) {
			customerDataModel = customerService.getAllCustomers();
		}

		return customerDataModel;
	}

	public void setCustomerService(CustomerService customerService) {

		// Injected via @ManagedProperty annotation.
		this.customerService = customerService;
	}
}
