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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 * @author  Kyle Stiemann
 */
@ManagedBean
@RequestScoped
public class CommandBackingBean {

	private static final Logger logger = LoggerFactory.getLogger(CommandBackingBean.class);

	// Private Data Members
	private boolean ajax;

	// Injections
	@ManagedProperty(value = "#{commandModelBean}")
	private CommandModelBean commandModelBean;

	public void actionListener(ActionEvent actionEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("actionListener: phaseId=[{0}]", phaseId.toString());

		String phaseName = phaseId.toString();
		FacesMessage facesMessage = new FacesMessage("The actionListener method was called during the " + phaseName +
				" phase of the JSF lifecycle.");
		facesContext.addMessage(null, facesMessage);
	}

	public void feedbackListener(ActionEvent actionEvent) {

		String value = "";

		List<UIComponent> children = actionEvent.getComponent().getChildren();

		for (UIComponent uiComponent : children) {

			if (uiComponent instanceof UIOutput) {
				value = (String) ((UIOutput) uiComponent).getValue();
			}
		}

		FacesContext facesContext = FacesContext.getCurrentInstance();
		logger.debug("feedbackListener: You selected the '" + value + "' menu item.");

		FacesMessage facesMessage = new FacesMessage("You selected the '" + value + "' menu item.");
		facesContext.addMessage(null, facesMessage);
	}

	public void selectionListener(ActionEvent actionEvent) {

		UICommand uiCommand = (UICommand) actionEvent.getComponent();
		Customer customer = (Customer) uiCommand.getValue();
		commandModelBean.setSelectedCustomer(customer);
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}

	public void setCommandModelBean(CommandModelBean commandModelBean) {
		this.commandModelBean = commandModelBean;
	}

	public boolean isAjax() {
		return ajax;
	}
}
