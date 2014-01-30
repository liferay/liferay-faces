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
package com.liferay.faces.demos.service;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.liferay.faces.demos.dto.BookingType;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "bookingTypeService")
@ApplicationScoped
public class BookingTypeServiceMockImpl implements BookingTypeService {

	// Public Constants
	public static final long TYPE_ID_AIRFARE = 1;
	public static final long TYPE_ID_CRUISE = 2;
	public static final long TYPE_ID_HOTEL = 3;
	public static final long TYPE_ID_PLAY = 4;
	public static final long TYPE_ID_RENTAL_CAR = 5;
	public static final long TYPE_ID_THEME_PARK = 6;
	public static final long TYPE_ID_TRAIN = 7;

	// Private Data Members
	private List<BookingType> allBookingTypes;

	public List<BookingType> getAllBookingTypes() {

		if (allBookingTypes == null) {
			allBookingTypes = new ArrayList<BookingType>();
			allBookingTypes.add(new BookingType(TYPE_ID_AIRFARE, "Airfare"));
			allBookingTypes.add(new BookingType(TYPE_ID_CRUISE, "Cruise"));
			allBookingTypes.add(new BookingType(TYPE_ID_HOTEL, "Hotel"));
			allBookingTypes.add(new BookingType(TYPE_ID_PLAY, "Play/Theatre"));
			allBookingTypes.add(new BookingType(TYPE_ID_RENTAL_CAR, "Rental Car"));
			allBookingTypes.add(new BookingType(TYPE_ID_THEME_PARK, "Theme Park"));
			allBookingTypes.add(new BookingType(TYPE_ID_TRAIN, "Train"));
		}

		return allBookingTypes;
	}
}
