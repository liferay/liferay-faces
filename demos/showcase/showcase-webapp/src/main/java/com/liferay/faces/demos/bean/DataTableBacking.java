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

import com.liferay.faces.alloy.component.datatable.DataTable;
import com.liferay.faces.alloy.component.datatable.RowDeselectEvent;
import com.liferay.faces.alloy.component.datatable.RowDeselectRangeEvent;
import com.liferay.faces.alloy.component.datatable.RowSelectEvent;
import com.liferay.faces.alloy.component.datatable.RowSelectRangeEvent;
import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.model.CustomerOnDemandDataModel;
import com.liferay.faces.demos.service.CustomerService;


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
	private DataModel<Customer> customerOnDemandDataModel;
	private int rowsPerPage = 10;
	private List<Customer> selectedCustomers;

	private String selectionMode = "checkbox";
	private String summaryPosition = "bottom";

	public void determineSelectedCustomers(ActionEvent actionEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		selectedCustomers = new ArrayList<Customer>();

		UICommand commandButton = (UICommand) actionEvent.getSource();
		DataTable customerDataTable = (DataTable) commandButton.findComponent("customers");
		String selectedRowIndexes = customerDataTable.getSelectedRowIndexes();

		if ((selectedRowIndexes != null) && (selectedRowIndexes.length() > 0)) {

			int originalRowIndex = customerDataTable.getRowIndex();
			String[] selectedRowIndexArray = selectedRowIndexes.split(",");

			StringBuilder facesMessageText = new StringBuilder();

			for (String selectedRowIndex : selectedRowIndexArray) {

				int rowIndex = Integer.parseInt(selectedRowIndex);
				customerDataTable.setRowIndex(rowIndex);

				Customer customer = (Customer) customerDataTable.getRowData();
				selectedCustomers.add(customer);

				if (facesMessageText.length() > 0) {
					facesMessageText.append(", ");
				}

				facesMessageText.append(customer.getFirstName());
				facesMessageText.append(" ");
				facesMessageText.append(customer.getLastName());
			}

			if (facesMessageText.length() > 0) {
				FacesMessage facesMessage = new FacesMessage(facesMessageText.toString());
				facesContext.addMessage(null, facesMessage);
			}

			customerDataTable.setRowIndex(originalRowIndex);
		}
		else {
			FacesMessage facesMessage = new FacesMessage("No Customers Selected");
			facesContext.addMessage(null, facesMessage);
		}
	}

	public void modeSwitch(ValueChangeEvent valueChangeEvent) {
		UISelectOne selectOneMenu = (UISelectOne) valueChangeEvent.getSource();
		DataTable customerDataTable = (DataTable) selectOneMenu.findComponent("customers");
		customerDataTable.setSelectedRowIndexes(null);
	}

	public void rowDeselectListener(AjaxBehaviorEvent ajaxBehaviorEvent) {

		// When using JSF 2.2, this cast is unnecessary, and the method can take the RowDeselectEvent directly.
		RowDeselectEvent rowDeselectEvent = (RowDeselectEvent) ajaxBehaviorEvent;
		addFacesMessage(rowDeselectEvent, rowDeselectEvent.getRowIndex(), (Customer) rowDeselectEvent.getRowData());
	}

	public void rowDeselectRangeListener(AjaxBehaviorEvent ajaxBehaviorEvent) {

		// When using JSF 2.2, this cast is unnecessary, and the method can take the RowDeselectRangeEvent directly.
		RowDeselectRangeEvent rowDeselectRangeEvent = (RowDeselectRangeEvent) ajaxBehaviorEvent;
		addFacesMessage(rowDeselectRangeEvent, rowDeselectRangeEvent.getRowIndexes(),
			rowDeselectRangeEvent.getRowDataList());
	}

	public void rowSelectListener(AjaxBehaviorEvent ajaxBehaviorEvent) {

		// When using JSF 2.2, this cast is unnecessary, and the method can take the RowSelectEvent directly.
		RowSelectEvent rowSelectEvent = (RowSelectEvent) ajaxBehaviorEvent;
		addFacesMessage(rowSelectEvent, rowSelectEvent.getRowIndex(), (Customer) rowSelectEvent.getRowData());
	}

	public void rowSelectRangeListener(AjaxBehaviorEvent ajaxBehaviorEvent) {

		// When using JSF 2.2, this cast is unnecessary, and the method can take the RowSelectRangeEvent directly.
		RowSelectRangeEvent rowSelectRangeEvent = (RowSelectRangeEvent) ajaxBehaviorEvent;
		addFacesMessage(rowSelectRangeEvent, rowSelectRangeEvent.getRowIndexes(), rowSelectRangeEvent.getRowDataList());
	}

	protected void addFacesMessage(FacesEvent facesEvent, int rowIndex, Customer customer) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		StringBuilder messageText = new StringBuilder();
		messageText.append("Received ");
		messageText.append(facesEvent.getClass().getSimpleName());
		messageText.append(" for rowIndex=[");
		messageText.append(rowIndex);
		messageText.append("] customer=[");
		messageText.append(customer.getFirstName());
		messageText.append(" ");
		messageText.append(customer.getLastName());
		messageText.append("] in the ");
		messageText.append(facesEvent.getPhaseId().toString());
		messageText.append(" phase.");

		FacesMessage facesMessage = new FacesMessage(messageText.toString());
		facesContext.addMessage(null, facesMessage);
	}

	protected void addFacesMessage(FacesEvent facesEvent, int[] rowIndexes, List<Object> customerList) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		StringBuilder messageText = new StringBuilder();
		messageText.append("Received ");
		messageText.append(facesEvent.getClass().getSimpleName());
		messageText.append(" for rowIndexes=");
		messageText.append(Arrays.toString(rowIndexes));

		if (customerList != null) {
			messageText.append(" Customers=[");

			for (int i = 0; i < customerList.size(); i++) {

				if (i > 0) {
					messageText.append(", ");
				}

				Customer customer = (Customer) customerList.get(i);
				messageText.append(customer.getFirstName());
				messageText.append(" ");
				messageText.append(customer.getLastName());
			}

			messageText.append("]");
		}

		messageText.append(" in the ");
		messageText.append(facesEvent.getPhaseId().toString());
		messageText.append(" phase.");

		FacesMessage facesMessage = new FacesMessage(messageText.toString());
		facesContext.addMessage(null, facesMessage);
	}

	public List<Customer> getCustomerDataModel() {

		if (customerDataModel == null) {
			customerDataModel = customerService.getAllCustomers();
		}

		return customerDataModel;
	}

	public DataModel getCustomerOnDemandDataModel() {

		if (customerOnDemandDataModel == null) {
			customerOnDemandDataModel = new CustomerOnDemandDataModel();
		}

		return customerOnDemandDataModel;
	}

	public void setCustomerService(CustomerService customerService) {

		// Injected via @ManagedProperty annotation.
		this.customerService = customerService;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public List<Customer> getSelectedCustomers() {
		return selectedCustomers;
	}

	public String getSelectionMode() {
		return selectionMode;
	}

	public void setSelectionMode(String selectionMode) {
		this.selectionMode = selectionMode;
	}

	public String getSummaryPosition() {
		return summaryPosition;
	}

	public void setSummaryPosition(String summaryPosition) {
		this.summaryPosition = summaryPosition;
	}
}
