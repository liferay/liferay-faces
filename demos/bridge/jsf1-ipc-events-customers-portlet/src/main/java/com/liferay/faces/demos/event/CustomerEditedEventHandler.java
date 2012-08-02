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
package com.liferay.faces.demos.event;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.portlet.Event;
import javax.portlet.faces.BridgeEventHandler;
import javax.portlet.faces.event.EventNavigationResult;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.service.CustomerService;


/**
 * @author  Neil Griffin
 */
public class CustomerEditedEventHandler implements BridgeEventHandler {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CustomerEditedEventHandler.class);

	public EventNavigationResult handleEvent(FacesContext facesContext, Event event) {
		EventNavigationResult eventNavigationResult = null;
		String eventQName = event.getQName().toString();

		if (eventQName.equals("{http://liferay.com/events}ipc.customerEdited")) {
			Customer customer = (Customer) event.getValue();
			getCustomerService(facesContext).save(customer);
			logger.debug("Received event ipc.customerEdited for customerId=[{0}] firstName=[{1}] lastName=[{2}]",
				new Object[] { customer.getCustomerId(), customer.getFirstName(), customer.getLastName() });
		}

		return eventNavigationResult;
	}

	protected CustomerService getCustomerService(FacesContext facesContext) {
		String elExpression = "#{customerService}";
		ELContext elContext = facesContext.getELContext();
		ValueExpression valueExpression = facesContext.getApplication().getExpressionFactory().createValueExpression(
				elContext, elExpression, CustomerService.class);

		return (CustomerService) valueExpression.getValue(elContext);
	}
}
