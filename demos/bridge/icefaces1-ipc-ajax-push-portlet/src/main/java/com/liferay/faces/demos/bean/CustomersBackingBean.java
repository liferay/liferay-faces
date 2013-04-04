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

import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

import com.liferay.faces.demos.dto.Customer;


/**
 * @author  Neil Griffin
 */
public class CustomersBackingBean {

	// Injections
	private CustomersModelBean customersModelBean;

	public void selectionListener(ActionEvent actionEvent) {
		UICommand uiCommand = (UICommand) actionEvent.getComponent();
		Customer customer = (Customer) uiCommand.getValue();
		customersModelBean.setSelected(customer);
	}

//  public void selectionListener(RowSelectorEvent rowSelectorEvent) {
//      Customer customer = customersModelBean.getAllCustomers().get(rowSelectorEvent.getRow());
//      customersModelBean.setSelected(customer);
//      System.err.println("!@#$ rowSelected!");
//  }

	public void setCustomersModelBean(CustomersModelBean customersModelBean) {

		// Injected via WEB-INF/faces-config.xml managed-property
		this.customersModelBean = customersModelBean;
	}
}
