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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.util.FacesMessageUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "customersBackingBean")
@RequestScoped
public class CustomersBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CustomersBackingBean.class);

	// Injections
	@ManagedProperty(name = "customersModelBean", value = "#{customersModelBean}")
	private CustomersModelBean customersModelBean;

	@ManagedProperty(name = "customersViewBean", value = "#{customersViewBean}")
	private CustomersViewBean customersViewBean;

	public void addNew(ActionEvent actionEvent) {
		customersModelBean.setSelected(new Customer());
		customersViewBean.setMasterRendered(false);
	}

	public void cancel(ActionEvent actionEvent) {
		customersViewBean.setMasterRendered(true);
	}

	public void deleteChecked(ActionEvent actionEvent) {

		try {
			customersModelBean.deleteChecked();
		}
		catch (Exception e) {
			logger.error(e);
			FacesMessageUtil.addGlobalUnexpectedErrorMessage(FacesContext.getCurrentInstance());
		}
	}

	public void save(ActionEvent actionEvent) {

		try {

			if (customersModelBean.getSelected().getCustomerId() == 0) {
				customersModelBean.addSelected();
			}
			else {
				customersModelBean.updateSelected();
			}
		}
		catch (Exception e) {
			logger.error(e);
			FacesMessageUtil.addGlobalUnexpectedErrorMessage(FacesContext.getCurrentInstance());
		}

		customersViewBean.setMasterRendered(true);
	}

	public void select(ActionEvent actionEvent) {
		UICommand uiCommand = (UICommand) actionEvent.getComponent();
		Customer selected = (Customer) uiCommand.getValue();

		try {
			customersModelBean.setSelected((Customer) selected.clone());
		}
		catch (CloneNotSupportedException e) {
			// ignore
		}

		customersViewBean.setMasterRendered(false);
	}

	public void setCustomersModelBean(CustomersModelBean customersModelBean) {

		// Injected via ManagedProperty annotation
		this.customersModelBean = customersModelBean;
	}

	public void setCustomersViewBean(CustomersViewBean customersViewBean) {

		// Injected via ManagedProperty annotation
		this.customersViewBean = customersViewBean;
	}

}
