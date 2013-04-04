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
package com.liferay.faces.demos.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author  Neil Griffin
 */
public class Customer implements Serializable {

	private static final long serialVersionUID = 6155011527137171447L;

	private List<Booking> bookings;
	private long customerId;
	private String firstName;
	private String lastName;
	private boolean selected;

	public Customer(long customerId, String firstName, String lastName) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public List<Booking> getBookings() {

		if (bookings == null) {
			bookings = new ArrayList<Booking>();
		}

		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public boolean isSelected() {
		return selected;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
