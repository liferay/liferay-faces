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

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.component.UICommand;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.portlet.ActionResponse;
import javax.xml.namespace.QName;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class CustomersBackingBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2920712441012786321L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CustomersBackingBean.class);

	@PostConstruct
	public void postConstruct() {
		logger.trace("@PostConstruct annotation worked");
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("@PreDestroy annotation worked");
	}

	public void selectionListener(ActionEvent actionEvent) {
		UICommand uiCommand = (UICommand) actionEvent.getComponent();
		Customer customer = (Customer) uiCommand.getValue();
		QName qName = new QName("http://liferay.com/events", "ipc.customerSelected");
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ActionResponse actionResponse = (ActionResponse) externalContext.getResponse();
		actionResponse.setEvent(qName, customer);
	}
}
