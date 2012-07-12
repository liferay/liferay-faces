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

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.dto.Province;
import com.liferay.faces.demos.list.CustomerLazyDataModel;
import com.liferay.faces.demos.service.CustomerService;
import com.liferay.faces.demos.service.ProvinceService;
import com.liferay.faces.util.model.LazyDataModel;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "customersModelBean")
@ViewScoped
public class CustomersModelBean implements Serializable {

	// serialVersionUID Note: This class implements Serializable only to avoid extraneous stacktraces from being thrown
	// by Mojarra. All of the private data members are marked as transient, because it's not possible to de-serialize
	// instances of Liferay's User class due to classloader prolems.
	private static final long serialVersionUID = 7763212348647832200L;

	// Injections
	@ManagedProperty(value = "#{provinceService}")
	private ProvinceService provinceService;

	@ManagedProperty(value = "#{customerService}")
	private CustomerService customerService;

	// Private Data Members
	private transient CustomerLazyDataModel customerLazyDataModel;
	private transient List<Province> cities;
	private transient Customer selected;
	private transient String previousSortColumn;

	public void addSelected() {
		selected = customerService.addCustomer(selected);
		previousSortColumn = getDataModel().getSortColumn();
		forceListReload();
	}

	public int deleteChecked() throws IOException {
		int totalDeleted = customerLazyDataModel.deleteMarkedRows();
		forceListReload();

		return totalDeleted;
	}

	public void forceListReload() {
		customerLazyDataModel = null;
	}

	public void updateSelected() {
		selected = customerService.updateCustomer(selected);
		forceListReload();
	}

	public void setCustomerService(CustomerService customerService) {

		// Injected via ManagedProperty annotation
		this.customerService = customerService;
	}

	public LazyDataModel<Customer> getDataModel() {

		if (customerLazyDataModel == null) {

			int rowsPerPage = getPreferenceRowsPerPage();
			customerLazyDataModel = new CustomerLazyDataModel(customerService, rowsPerPage);
			if (previousSortColumn != null) {
				customerLazyDataModel.setSortColumn(previousSortColumn);
			}
		}

		return customerLazyDataModel;
	}

	protected int getPreferenceRowsPerPage() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		PortletRequest portletRequest = (PortletRequest) facesContext.getExternalContext().getRequest();
		PortletPreferences portletPreferences = portletRequest.getPreferences();

		return Integer.parseInt(portletPreferences.getValue("rowsPerPage", "20"));
	}

	public List<Province> getProvinces() {

		if (cities == null) {
			cities = provinceService.getProvinces();
		}

		return cities;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public Customer getSelected() {
		return selected;
	}

	public void setSelected(Customer selected) {
		this.selected = selected;
	}
}
