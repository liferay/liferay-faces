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

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.demos.dto.Customer;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "customersBackingBean")
@RequestScoped
public class CustomersBackingBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2920712441012786321L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CustomersBackingBean.class);

	// Injections
	@ManagedProperty(name = "customersModelBean", value = "#{customersModelBean}")
	private CustomersModelBean customersModelBean;

	@PostConstruct
	public void postConstruct() {
		logger.trace("@PostConstruct annotation worked");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("@PreDestroy annotation worked");
	}

	/**
	 * This method is a JSF action listener that is called when the user clicks on a customer. By calling {@link
	 * CustomersModelBean#setSelectedCustomerId(String)} it may seem that we are simply recording the selected
	 * customerId in the model. However, there is more than meets the eye. I the WEB-INF/faces-config.xml descriptor
	 * there is an EL expression of #{customersPortlet:customersModelBean.selectedCustomerId} which means that the
	 * bridge will be monitoring the status of that model bean property. If the value changes in the model bean, then
	 * the bridge will automatically call {@link BookingsModelBean#setSelectedCustomerId(String)} because of the other
	 * model-el Expression #{bookingsPortlet:bookingsModelBean.selectedCustomerId} found in the WEB-INF/faces-config.xml
	 * descriptor.
	 */
	public void selectionListener(ActionEvent actionEvent) {
		UICommand uiCommand = (UICommand) actionEvent.getComponent();
		Customer customer = (Customer) uiCommand.getValue();
		logger.debug("User click! selectedCustomerId=[{0}]", customer.getCustomerId());
		customersModelBean.setSelectedCustomerId(Long.toString(customer.getCustomerId()));
	}

	public void setCustomersModelBean(CustomersModelBean customersModelBean) {

		// Injected via ManagedProperty annotation.
		this.customersModelBean = customersModelBean;
	}
}
