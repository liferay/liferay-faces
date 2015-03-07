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
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;


/**
 * @author  Neil Griffin
 */
@RequestScoped
@ManagedBean
public class MessageBackingBean {

	public void submit() {

		FacesMessage globalFacesMessage = new FacesMessage("Your request processed successfully.");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, globalFacesMessage);
	}

	public void valueChangeListener(ValueChangeEvent valueChangeEvent) {

		Object newValue = valueChangeEvent.getNewValue();
		int totalChars = 0;

		if (newValue != null) {
			totalChars = newValue.toString().length();
		}

		FacesMessage globalFacesMessage = new FacesMessage("You typed " + totalChars + " characters.");
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String componentClientId = valueChangeEvent.getComponent().getClientId();
		facesContext.addMessage(componentClientId, globalFacesMessage);
	}
}
