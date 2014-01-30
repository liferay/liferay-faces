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

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.liferay.faces.demos.dto.Airport;
import com.liferay.faces.demos.service.AirportService;


/**
 * @author  Neil Griffin
 */
@Named
@ViewScoped
public class FlightSearchModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4976012166797843537L;

	@Inject
	AirportService airportService;

	@Inject
	BookingFlowModelBean bookingFlowModelBean;

	@Inject
	ScopeTrackingBean scopeTrackingBean;

	// Private Data Members
	private String arrivalAirportName;
	private String arrivalCity;
	private String departureAirportName;
	private String departureCity;

	@PostConstruct
	public void postConstruct() {

		long departureAirportId = bookingFlowModelBean.getBookingDepartureId();
		Airport departureAirport = airportService.findById(departureAirportId);
		this.departureAirportName = departureAirport.getName();
		this.departureCity = departureAirport.getCity();

		long arrivalAirportId = bookingFlowModelBean.getBookingArrivalId();
		Airport arrivalAirport = airportService.findById(arrivalAirportId);
		this.setArrivalAirportName(arrivalAirport.getName());
		this.setArrivalCity(arrivalAirport.getCity());

		scopeTrackingBean.setFlightSearchModelBeanInScope(true);
	}

	@PreDestroy
	public void preDestroy() {
		scopeTrackingBean.setFlightSearchModelBeanInScope(false);
	}

	public String getArrivalAirportName() {
		return arrivalAirportName;
	}

	public void setArrivalAirportName(String arrivalAirportName) {
		this.arrivalAirportName = arrivalAirportName;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public String getDepartureAirportName() {
		return departureAirportName;
	}

	public String getDepartureCity() {
		return departureCity;
	}
}
