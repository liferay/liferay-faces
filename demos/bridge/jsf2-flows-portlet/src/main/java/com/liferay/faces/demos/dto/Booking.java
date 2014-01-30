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
package com.liferay.faces.demos.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author  Neil Griffin
 */
public class Booking implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2384129997014505129L;

	// Private Data Members
	private Date arrivalDate;
	private long arrivalId;
	private long bookingId;
	private long bookingTypeId;
	private long customerId;
	private long departureId;
	private String description;
	private double distance;
	private double duration;
	private Date departureDate;
	private String label;
	private BigDecimal price;

	public Booking() {
		// Pluto requires a no-arg default constructor.
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public long getArrivalId() {
		return arrivalId;
	}

	public void setArrivalId(long arrivalId) {
		this.arrivalId = arrivalId;
	}

	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
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

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public long getDepartureId() {
		return departureId;
	}

	public void setDepartureId(long departureId) {
		this.departureId = departureId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
