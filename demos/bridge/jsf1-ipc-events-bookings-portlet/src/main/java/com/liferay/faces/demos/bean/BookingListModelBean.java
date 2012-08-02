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
import java.util.List;

import javax.faces.model.SelectItem;

import com.liferay.faces.demos.dto.BookingType;
import com.liferay.faces.demos.service.BookingTypeService;


/**
 * @author  Neil Griffin
 */
public class BookingListModelBean {

	// Private Data Memebers
	private List<SelectItem> bookingTypes;

	// Injections
	private BookingTypeService bookingTypeService;

	public List<SelectItem> getBookingTypes() {

		if (bookingTypes == null) {
			List<BookingType> bookingTypeList = bookingTypeService.getAllBookingTypes();
			bookingTypes = new ArrayList<SelectItem>(bookingTypeList.size());

			for (BookingType bookingType : bookingTypeList) {
				SelectItem selectItem = new SelectItem(bookingType.getBookingTypeId(),
						bookingType.getBookingTypeName());
				bookingTypes.add(selectItem);
			}
		}

		return bookingTypes;
	}

	public BookingTypeService getBookingTypeService() {
		return bookingTypeService;
	}

	public void setBookingTypeService(BookingTypeService bookingTypeService) {

		// Injected via WEB-INF/faces-config.xml managed-property
		this.bookingTypeService = bookingTypeService;
	}
}
