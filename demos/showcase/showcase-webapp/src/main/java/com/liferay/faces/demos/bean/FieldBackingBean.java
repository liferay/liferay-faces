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
import javax.faces.validator.ValidatorException;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Kyle Stiemann
 */
@ManagedBean
@RequestScoped
public class FieldBackingBean {

	private static final Logger logger = LoggerFactory.getLogger(FieldBackingBean.class);

	@ManagedProperty(value = "#{fieldModelBean}")
	private FieldModelBean fieldModelBean;

	public void errorValidator(FacesContext facesContext, UIComponent uiComponent, Object value)
		throws ValidatorException {

		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		facesMessage.setDetail("This is an error message.");
		facesContext.addMessage(uiComponent.getClientId(), facesMessage);
	}

	public void infoValidator(FacesContext facesContext, UIComponent uiComponent, Object value)
		throws ValidatorException {

		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
		facesMessage.setDetail("This is an info message.");
		facesContext.addMessage(uiComponent.getClientId(), facesMessage);
	}

	public void submit() {

		FacesMessage globalFacesMessage = new FacesMessage("Your request processed successfully.");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, globalFacesMessage);

		logger.info("submit: firstName = " + fieldModelBean.getFirstName());
	}

	public void warningValidator(FacesContext facesContext, UIComponent uiComponent, Object value)
		throws ValidatorException {

		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
		facesMessage.setDetail("This is a warning message.");
		facesContext.addMessage(uiComponent.getClientId(), facesMessage);
	}

	public void setFieldModelBean(FieldModelBean fieldModelBean) {
		this.fieldModelBean = fieldModelBean;
	}
}
