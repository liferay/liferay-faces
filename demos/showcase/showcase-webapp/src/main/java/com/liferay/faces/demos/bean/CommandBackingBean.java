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

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 * @author  Kyle Stiemann
 */
//JSF 2+ @ManagedBean
//JSF 2+ @RequestScoped
public class CommandBackingBean {

	private static final Logger logger = LoggerFactory.getLogger(CommandBackingBean.class);

	// Private Data Members
	private boolean ajax;

	public void actionListener(ActionEvent actionEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		FacesMessage facesMessage = new FacesMessage("The actionListener method was called.");
		facesContext.addMessage(null, facesMessage);
	}

	public void attributeActionListener(ActionEvent actionEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		String value = (String) actionEvent.getComponent().getAttributes().get("attribute");

		FacesMessage facesMessage = new FacesMessage("The actionListener method was called." +
				"The attribute value is " + value);
		facesContext.addMessage(null, facesMessage);
	}

	public void parameterActionListener(ActionEvent actionEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
		String value = requestParameterMap.get("parameter");

		FacesMessage facesMessage = new FacesMessage("The actionListener method was called." +
				"The parameter value is " + value);
		facesContext.addMessage(null, facesMessage);
	}	
}
