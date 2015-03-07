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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 */
@ManagedBean
@RequestScoped
public class InputSecretBackingBean {

	private static final Logger logger = LoggerFactory.getLogger(InputSecretBackingBean.class);

	@ManagedProperty(value = "#{inputSecretModelBean}")
	private InputSecretModelBean inputSecretModelBean;

	public void emailAddressValidator(FacesContext facesContext, UIComponent uiComponent, Object value)
		throws ValidatorException {

		if (value != null) {

			if (!value.toString().matches(".+[@].+[.].+")) {
				FacesMessage facesMessage = new FacesMessage();
				facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(facesMessage);
			}
		}
	}

	public void submit() {
		Object value = inputSecretModelBean.getPassword();

		if (value == null) {
			value = inputSecretModelBean.getDate();
		}

		logger.info("You entered: " + value);
	}

	public void submit1() {
		addGlobalInfoMessage("The alloy:inputSecret component was intentionally not re-rendered in the DOM.");
		submit();
	}

	public void submit2() {
		addGlobalInfoMessage("The entire form (including the alloy:inputSecret component) was re-rendered in the DOM.");
		submit();
	}

	public void valueChangeListener(ValueChangeEvent valueChangeEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		PhaseId phaseId = facesContext.getCurrentPhaseId();
		logger.debug("valueChangeListener: phaseId=[{0}]", phaseId.toString());

		String phaseName = phaseId.getName();
		FacesMessage facesMessage = new FacesMessage("The valueChangeListener method was called during the " +
				phaseName + " phase of the JSF lifecycle.");
		facesContext.addMessage(null, facesMessage);
	}

	protected void addGlobalInfoMessage(String summary) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
		facesMessage.setSummary(summary);
		facesContext.addMessage(null, facesMessage);
	}

	public void setInputSecretModelBean(InputSecretModelBean inputSecretModelBean) {
		this.inputSecretModelBean = inputSecretModelBean;
	}
}
