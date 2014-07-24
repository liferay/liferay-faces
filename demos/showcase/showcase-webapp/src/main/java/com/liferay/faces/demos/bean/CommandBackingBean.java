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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UICommand;
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

	public void selectionListener(ActionEvent actionEvent) {

		UICommand uiCommand = (UICommand) actionEvent.getComponent();
		Customer customer = (Customer) uiCommand.getValue();
		commandModelBean.setSelectedCustomer(customer);
	}

	public void setCommandModelBean(CommandModelBean commandModelBean) {
		this.commandModelBean = commandModelBean;
	}
}
