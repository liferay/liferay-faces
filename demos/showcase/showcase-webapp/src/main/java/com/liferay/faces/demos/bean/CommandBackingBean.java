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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
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

		String phaseName = phaseId.getName();
		FacesMessage facesMessage = new FacesMessage("The actionListener method was called during the " + phaseName +
				" phase of the JSF lifecycle.");
		facesContext.addMessage(null, facesMessage);
	}

	public void ajaxListener(AjaxBehaviorEvent ajaxBehaviorEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("ajaxListener: phaseId=[{0}]", phaseId.toString());

		String phaseName = phaseId.getName();
		FacesMessage facesMessage = new FacesMessage("The ajaxListener method was called during the " + phaseName +
				" phase of the JSF lifecycle.");
		facesContext.addMessage(null, facesMessage);
	}

	public void attributeActionListener(ActionEvent actionEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("actionListener: phaseId=[{0}]", phaseId.toString());

		String value = (String) actionEvent.getComponent().getAttributes().get("attribute");

		String phaseName = phaseId.getName();
		FacesMessage facesMessage = new FacesMessage("The actionListener method was called during the " + phaseName +
				" phase of the JSF lifecycle.The attribute value is " + value);
		facesContext.addMessage(null, facesMessage);
	}

	public void attributesActionListener(ActionEvent actionEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("actionListener: phaseId=[{0}]", phaseId.toString());

		Map<String, Object> attributes = actionEvent.getComponent().getAttributes();
		String value1 = (String) attributes.get("attribute1");
		String value2 = (String) attributes.get("attribute2");

		String phaseName = phaseId.getName();
		FacesMessage facesMessage = new FacesMessage("The actionListener method was called during the " + phaseName +
				" phase of the JSF lifecycle.The attributes value are " + value1 + " and " + value2);
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

	public void parameterActionListener() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("actionListener: phaseId=[{0}]", phaseId.toString());

		Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
		String value = requestParameterMap.get("parameter");

		String phaseName = phaseId.getName();
		FacesMessage facesMessage = new FacesMessage("The actionListener method was called during the " + phaseName +
				" phase of the JSF lifecycle.The parameter value is " + value);
		facesContext.addMessage(null, facesMessage);
	}

	public void selectionListener(ActionEvent actionEvent) {

		UICommand uiCommand = (UICommand) actionEvent.getComponent();
		Customer customer = (Customer) uiCommand.getValue();
		commandModelBean.setSelectedCustomer(customer);
	}

	public Map<String, Object> getAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("attribute1", "value1");
		attributes.put("attribute2", "value2");

		return attributes;
	}

	public void setCommandModelBean(CommandModelBean commandModelBean) {
		this.commandModelBean = commandModelBean;
	}
}
