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
package com.liferay.faces.demos.dto;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * @author  Neil Griffin
 */
public class Booking {

	private long bookingTypeId;
	private long customerId;
	private Date startDate;
	private Date finishDate;

	public Booking() {
		// Pluto requires a no-arg default constructor.
	}

	public Booking(long bookingTypeId, long customerId) {
		Calendar today = GregorianCalendar.getInstance();
		Calendar weekFromToday = (Calendar) today.clone();
		weekFromToday.add(Calendar.DATE, 7);
		this.bookingTypeId = bookingTypeId;
		this.customerId = customerId;
		startDate = new Date(today.getTimeInMillis());
		finishDate = new Date(weekFromToday.getTimeInMillis());
	}

	public Booking(long bookingTypeId, long customerId, Date startDate, Date finishDate) {
		this.bookingTypeId = bookingTypeId;
		this.customerId = customerId;
		this.startDate = startDate;
		this.finishDate = finishDate;
	}

	public long getBookingTypeId() {
		return bookingTypeId;
	}

	public void setBookingTypeId(long bookingTypeId) {
		this.bookingTypeId = bookingTypeId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}
