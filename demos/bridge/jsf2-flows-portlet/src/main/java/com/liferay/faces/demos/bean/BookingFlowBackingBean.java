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

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import com.liferay.faces.demos.dto.Booking;
import com.liferay.faces.demos.service.BookingTypeService;


/**
 * @author  Neil Griffin
 */
@Named
@RequestScoped
public class BookingFlowBackingBean {

	@Inject
	BookingFlowModelBean bookingFlowModelBean;

	@Inject
	BookingTypeService bookingTypeService;

	public String addFlightToCart(long bookingId) {

		List<Booking> flights = bookingFlowModelBean.getFlights();

		for (Booking flight : flights) {

			if (flight.getBookingId() == bookingId) {
				bookingFlowModelBean.getCartBookings().add(flight);

				break;
			}
		}

		return "cart";
	}

	public String bookAdditionalTravel() {
		bookingFlowModelBean.clearBooking();

		return "booking";
	}

	public void bookingTypeIdChanged(ValueChangeEvent valueChangeEvent) {
		Long bookingTypeId = (Long) valueChangeEvent.getNewValue();
		String bookingTypeName = bookingTypeService.getName(bookingTypeId);
		bookingFlowModelBean.setBookingTypeName(bookingTypeName);
	}

	public void removeBooking(long bookingId) {

		List<Booking> cartBookings = bookingFlowModelBean.getCartBookings();

		Booking bookingToRemove = null;

		for (Booking cartBooking : cartBookings) {

			if (cartBooking.getBookingId() == bookingId) {
				bookingToRemove = cartBooking;

				break;
			}
		}

		if (bookingToRemove != null) {
			cartBookings.remove(bookingToRemove);
		}
	}
}
