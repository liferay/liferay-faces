/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.flow.FlowScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.liferay.faces.demos.dto.Booking;
import com.liferay.faces.demos.dto.Country;
import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.service.CountryService;
import com.liferay.faces.demos.service.FlightService;


/**
 * @author  Neil Griffin
 */
@Named
@FlowScoped("booking")
public class BookingFlowModelBean {

	@Inject
	private CountryService countryService;

	@Inject
	private FlightService flightService;

	// Private Data Members
	private long bookingArrivalId;
	private Date bookingDepartureDate;
	private long bookingDepartureId;
	private long bookingTypeId;
	private String bookingTypeName;
	private List<Booking> cartBookings;
	private Customer customer;
	private List<Booking> flights;

	public BookingFlowModelBean() {
		this.cartBookings = new ArrayList<Booking>();
		this.customer = new Customer();
		clearBooking();
	}

	public void clearBooking() {
		bookingArrivalId = 0L;
		bookingDepartureDate = null;
		bookingDepartureId = 0L;
		bookingTypeId = 0L;
		bookingTypeName = "none";
		flights = null;
	}

	@PostConstruct
	public void postContruct() {
		Country unitedStates = countryService.findByAbbreviation("US");
		customer.setCountryId(unitedStates.getCountryId());
	}

	@PreDestroy
	public void preDestroy() {
		System.err.println("!@#$ BookingFlowModelBean going OUT OF SCOPE!");
	}

	public long getBookingArrivalId() {
		return bookingArrivalId;
	}

	public void setBookingArrivalId(long bookingArrivalId) {
		this.bookingArrivalId = bookingArrivalId;
	}

	public Date getBookingDepartureDate() {
		return bookingDepartureDate;
	}

	public void setBookingDepartureDate(Date bookingDepartureDate) {
		this.bookingDepartureDate = bookingDepartureDate;
	}

	public long getBookingDepartureId() {
		return bookingDepartureId;
	}

	public void setBookingDepartureId(long bookingDepartureId) {
		this.bookingDepartureId = bookingDepartureId;
	}

	public long getBookingTypeId() {
		return bookingTypeId;
	}

	public void setBookingTypeId(long bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	public String getBookingTypeName() {
		return bookingTypeName;
	}

	public void setBookingTypeName(String bookingTypeName) {
		this.bookingTypeName = bookingTypeName;
	}

	public List<Booking> getCartBookings() {
		return cartBookings;
	}

	public void setCartBookings(List<Booking> cartBookings) {
		this.cartBookings = cartBookings;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Booking> getFlights() {

		if (flights == null) {
			flights = flightService.searchDirect(bookingDepartureId, bookingDepartureDate, bookingArrivalId);
		}

		return flights;
	}
}
