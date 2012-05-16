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

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.demos.dto.Booking;
import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.event.CustomerSelectedEventHandler;


/**
 * This class is a JSF model managed-bean that contains the selected customer. First, the Portlet 2.0 EVENT_PHASE
 * broadcasts the IPC events. At that point, the bridge executes the RESTORE_VIEW phase of the JSF lifecycle so that the
 * CustomerSelectedEventHandler.handleEvent(FacesContext, Event) method can handle the "ipc.customerSelected" event as
 * defined in the WEB-INF/portlet.xml descriptor. Then, the Portlet 2.0 RENDER_PHASE will cause the RENDER_RESPONSE
 * phase of the JSF lifecycle to be executed. Note that this class has to be {@link SessionScoped} instead of {@link
 * ViewScoped} because the {@link Customer} received in the {@link CustomerSelectedEventHandler} (which is injected into
 * this bean) has to survive the navigation from the noCustomerSelected.xhtml view to the customers.xhtml view due to
 * the navigation-rule in the WEB-INF/faces-config.xml descriptor. Also, it needs to be {@link SessionScoped} instead of
 * {@link RequestScoped} because otherwise the bookings form data submitted in customers.xhtml won't have a
 * correctly-sized List&lt;Booking&gt; for the Faces APPLY_REQUEST_VALUES phase to postback into.
 *
 * @author  Neil Griffin
 */
@SessionScoped
@ManagedBean(name = "bookingsModelBean")
public class BookingsModelBean {

	// Private Constants
	private static final Logger logger = LoggerFactory.getLogger(BookingsModelBean.class);

	// Private Bean Properties
	Customer customer;

	@PostConstruct
	public void postConstruct() {
		logger.trace("@PostConstruct annotation worked");
		customer = new Customer();
		customer.setBookings(new ArrayList<Booking>(5));
	}

	@PreDestroy
	public void preDestroy() {
		logger.trace("@PreDestroy annotation worked");
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {

		// Called by the CustomerEditedEventHandler
		this.customer = customer;
	}
}
