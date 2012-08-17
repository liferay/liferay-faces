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

import java.util.List;

import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ValueChangeEvent;

import org.icefaces.application.PushRenderer;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.service.CustomerService;
import com.liferay.faces.demos.util.PortletSessionUtil;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "customersModelBean")
@RequestScoped
public class CustomersModelBean {

	// Private Constants
	private static final String CUSTOMER_RENDER_GROUP = "CUSTOMER_RENDER_GROUP";

	// Injections
	@ManagedProperty(name = "customerService", value = "#{customerService}")
	private CustomerService customerService;

	public CustomersModelBean() {
		PushRenderer.addCurrentSession(CUSTOMER_RENDER_GROUP);
	}

	@PreDestroy
	public void preDestroy() {
		PushRenderer.removeCurrentSession(CUSTOMER_RENDER_GROUP);
	}

	public void valueChangeListener(ValueChangeEvent valueChangeEvent) {
		PushRenderer.render(CUSTOMER_RENDER_GROUP);
	}

	@SuppressWarnings("unchecked")
	public List<Customer> getAllCustomers() {
		List<Customer> allCustomers = (List<Customer>) PortletSessionUtil.getSharedSessionAttribute(
				PortletSessionUtil.CUSTOMER_LIST);

		if (allCustomers == null) {
			allCustomers = customerService.getAllCustomers();
			PortletSessionUtil.setSharedSessionAttribute(PortletSessionUtil.CUSTOMER_LIST, allCustomers);
		}

		return allCustomers;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {

		// Injected via ManagedProperty annotation
		this.customerService = customerService;
	}

	public Customer getSelected() {
		return (Customer) PortletSessionUtil.getSharedSessionAttribute(PortletSessionUtil.SELECTED_CUSTOMER);
	}

	public void setSelected(Customer customer) {
		PortletSessionUtil.setSharedSessionAttribute(PortletSessionUtil.SELECTED_CUSTOMER, customer);
		PushRenderer.render(CUSTOMER_RENDER_GROUP);
	}
}
