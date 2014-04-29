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
package com.liferay.faces.demos.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;

import com.liferay.faces.demos.dto.Customer;


/**
 * @author  Kyle Stiemann
 */
@ManagedBean
@RequestScoped
public class CommandButtonBackingBean {

	// Injections
	@ManagedProperty(value = "#{commandButtonModelBean}")
	private CommandButtonModelBean commandButtonModelBean;

	public void selectionListener(ActionEvent actionEvent) {

		UICommand uiCommand = (UICommand) actionEvent.getComponent();
		Customer customer = (Customer) uiCommand.getValue();
		commandButtonModelBean.setSelectedCustomer(customer);
	}

	public void setCommandButtonModelBean(CommandButtonModelBean commandButtonModelBean) {
		this.commandButtonModelBean = commandButtonModelBean;
	}
}
