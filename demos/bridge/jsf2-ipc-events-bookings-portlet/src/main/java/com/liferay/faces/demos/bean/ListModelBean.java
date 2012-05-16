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

import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.liferay.faces.demos.dto.BookingType;
import com.liferay.faces.demos.service.BookingTypeService;


/**
 * @author  Neil Griffin
 */
@ApplicationScoped
@ManagedBean(name = "listModelBean")
public class ListModelBean {

	// Private Data Memebers
	private List<BookingType> bookingTypes;

	// Injections
	@ManagedProperty(name = "bookingTypeService", value = "#{bookingTypeService}")
	private BookingTypeService bookingTypeService;

	public List<BookingType> getBookingTypes() {

		if (bookingTypes == null) {
			bookingTypes = bookingTypeService.getAllBookingTypes();
		}

		return bookingTypes;
	}

	public BookingTypeService getBookingTypeService() {
		return bookingTypeService;
	}

	public void setBookingTypeService(BookingTypeService bookingTypeService) {

		// Injected via @ManagedProperty annotation
		this.bookingTypeService = bookingTypeService;
	}
}
