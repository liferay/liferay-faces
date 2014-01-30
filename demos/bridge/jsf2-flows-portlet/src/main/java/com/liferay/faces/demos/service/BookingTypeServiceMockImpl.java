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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.liferay.faces.demos.dto.BookingType;


/**
 * @author  Neil Griffin
 */
@Named("bookingTypeService")
@ApplicationScoped
public class BookingTypeServiceMockImpl implements BookingTypeService {

	// Public Constants
	public static final long TYPE_ID_FLIGHT = 1;
	public static final long TYPE_ID_CRUISE = 2;
	public static final long TYPE_ID_HOTEL = 3;
	public static final long TYPE_ID_RESTAURANT = 4;
	public static final long TYPE_ID_RENTAL_CAR = 5;
	public static final long TYPE_ID_TRAIN = 6;

	// Private Data Members
	private Map<Long, BookingType> bookingTypeMap;
	private List<BookingType> bookingTypes;

	public BookingTypeServiceMockImpl() {

		bookingTypeMap = new HashMap<Long, BookingType>();
		bookingTypeMap.put(TYPE_ID_FLIGHT, new BookingType(TYPE_ID_FLIGHT, "flight"));
		bookingTypeMap.put(TYPE_ID_CRUISE, new BookingType(TYPE_ID_CRUISE, "cruise"));
		bookingTypeMap.put(TYPE_ID_HOTEL, new BookingType(TYPE_ID_HOTEL, "hotel"));
		bookingTypeMap.put(TYPE_ID_RESTAURANT, new BookingType(TYPE_ID_RESTAURANT, "restaurant"));
		bookingTypeMap.put(TYPE_ID_RENTAL_CAR, new BookingType(TYPE_ID_RENTAL_CAR, "rental-car"));
		bookingTypeMap.put(TYPE_ID_TRAIN, new BookingType(TYPE_ID_TRAIN, "train"));

		bookingTypes = new ArrayList<BookingType>();

		Set<Entry<Long, BookingType>> entrySet = bookingTypeMap.entrySet();

		for (Entry<Long, BookingType> mapEntry : entrySet) {
			BookingType bookingType = mapEntry.getValue();
			bookingTypes.add(bookingType);
		}
	}

	public List<BookingType> getBookingTypes() {
		return bookingTypes;
	}

	@Override
	public String getName(long bookingTypeId) {

		String name = null;
		BookingType bookingType = bookingTypeMap.get(bookingTypeId);

		if (bookingType != null) {
			name = bookingType.getBookingTypeName();
		}

		return name;
	}
}
