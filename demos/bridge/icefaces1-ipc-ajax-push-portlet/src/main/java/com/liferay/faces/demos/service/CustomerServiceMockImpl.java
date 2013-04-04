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
package com.liferay.faces.demos.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.liferay.faces.demos.dto.Customer;


/**
 * @author  Neil Griffin
 */
public class CustomerServiceMockImpl implements CustomerService, Serializable {

	// Public Constants
	public static final long ID_BRIAN_GREEN = 1;
	public static final long ID_LIZ_KESSLER = 2;
	public static final long ID_RICH_SHEARER = 3;

	// serialVersionUID
	private static final long serialVersionUID = 4908847695433183418L;

	// Private Data Members
	private ArrayList<Customer> allCustomers;

	// Injections
	private BookingService bookingService;

	public List<Customer> getAllCustomers() {

		if (allCustomers == null) {
			allCustomers = new ArrayList<Customer>();

			BookingService bookingService = getBookingService();
			Customer customer = new Customer(ID_BRIAN_GREEN, "Brian", "Green");
			customer.setBookings(bookingService.getBookingsByCustomerId(ID_BRIAN_GREEN));
			allCustomers.add(customer);
			customer = new Customer(ID_LIZ_KESSLER, "Liz", "Kessler");
			customer.setBookings(bookingService.getBookingsByCustomerId(ID_LIZ_KESSLER));
			allCustomers.add(customer);
			customer = new Customer(ID_RICH_SHEARER, "Rich", "Shearer");
			customer.setBookings(bookingService.getBookingsByCustomerId(ID_RICH_SHEARER));
			allCustomers.add(customer);
		}

		return allCustomers;
	}

	public BookingService getBookingService() {
		return bookingService;
	}

	public void setBookingService(BookingService bookingService) {

		// Injected via @ManagedProperty annotation
		this.bookingService = bookingService;
	}
}
