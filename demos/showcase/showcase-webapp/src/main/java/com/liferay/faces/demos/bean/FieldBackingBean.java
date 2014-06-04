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
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;


/**
 * @author  Kyle Stiemann
 */
@ManagedBean
@RequestScoped
public class FieldBackingBean {

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

	public void warningValidator(FacesContext facesContext, UIComponent uiComponent, Object value)
		throws ValidatorException {

		FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
		facesMessage.setDetail("This is an warning message.");
		facesContext.addMessage(uiComponent.getClientId(), facesMessage);
	}
}
