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

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import com.liferay.faces.demos.dto.Airport;
import com.liferay.faces.demos.dto.Booking;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
@ApplicationScoped
public class FlightServiceMockImpl implements FlightService {

	// Private Constants
	private static final int AVG_SPEED_KM_PER_HOUR = 900;
	private static final double PRICE_USD_PER_KM = 0.1D;

	@Inject
	AirportService airportService;

	public FlightServiceMockImpl() {

		Calendar departureCalendar = GregorianCalendar.getInstance();
		Calendar arrivalCalendar = (Calendar) departureCalendar.clone();
		arrivalCalendar.add(Calendar.HOUR, 5);
	}

	@Override
	public List<Booking> searchDirect(long departureAirportId, Date departureDate, long arrivalAirportId) {

		DateFormat dateFormat = new SimpleDateFormat();
		List<Booking> searchResults = new ArrayList<Booking>();

		Airport departureAirport = airportService.findById(departureAirportId);
		double departureLatitude = departureAirport.getLatitude();
		double departureLongitude = departureAirport.getLongitude();
		LatLng departureLatLong = new LatLng(departureLatitude, departureLongitude);
		Airport arrivalAirport = airportService.findById(arrivalAirportId);
		double arrivalLatitude = arrivalAirport.getLatitude();
		double arrivalLongitude = arrivalAirport.getLongitude();
		LatLng arrivalLatLong = new LatLng(arrivalLatitude, arrivalLongitude);
		double distanceInKilometers = LatLngTool.distance(departureLatLong, arrivalLatLong, LengthUnit.KILOMETER);
		double durationInHours = distanceInKilometers / AVG_SPEED_KM_PER_HOUR;
		double priceUSD = distanceInKilometers * PRICE_USD_PER_KM;
		Calendar departureCalendar = new GregorianCalendar();
		departureCalendar.setTime(departureDate);
		departureCalendar.set(Calendar.HOUR_OF_DAY, 6);

		Random random = new Random();

		while (departureCalendar.get(Calendar.HOUR_OF_DAY) < 18) {

			Calendar arrivalCalendar = (Calendar) departureCalendar.clone();
			arrivalCalendar.add(Calendar.HOUR_OF_DAY, (int) Math.round(durationInHours));

			Booking flight = new Booking();
			Date flightDepartureDate = departureCalendar.getTime();
			flight.setDepartureDate(flightDepartureDate);
			flight.setArrivalId(arrivalAirportId);

			Date flightArrivalDate = arrivalCalendar.getTime();
			flight.setArrivalDate(flightArrivalDate);
			flight.setDistance(distanceInKilometers);
			flight.setDuration(durationInHours);
			flight.setBookingId(Math.abs(random.nextLong()));

			String flightNumber = Integer.toString(Math.abs(random.nextInt()));
			flight.setLabel(flightNumber);

			StringBuilder description = new StringBuilder();
			description.append("Flight#");
			description.append(flightNumber);
			description.append(StringPool.SPACE);
			description.append("departing from");
			description.append(StringPool.SPACE);
			description.append(departureAirport.getCity());
			description.append(StringPool.SPACE);
			description.append(StringPool.OPEN_PARENTHESIS);
			description.append(departureAirport.getCode());
			description.append(StringPool.CLOSE_PARENTHESIS);
			description.append(StringPool.SPACE);
			description.append("on");
			description.append(StringPool.SPACE);
			description.append(dateFormat.format(flightDepartureDate));
			description.append(StringPool.SPACE);
			description.append("arriving at");
			description.append(StringPool.SPACE);
			description.append(arrivalAirport.getCity());
			description.append(StringPool.OPEN_PARENTHESIS);
			description.append(arrivalAirport.getCode());
			description.append(StringPool.CLOSE_PARENTHESIS);
			description.append(StringPool.SPACE);
			description.append("on");
			description.append(StringPool.SPACE);
			description.append(dateFormat.format(flightArrivalDate));
			flight.setDescription(description.toString());

			BigDecimal price = new BigDecimal(priceUSD);
			price.setScale(2, BigDecimal.ROUND_HALF_UP);
			flight.setPrice(price);
			departureCalendar.add(Calendar.MINUTE, 90);

			searchResults.add(flight);
		}

		return searchResults;
	}

	// TODO
	public int getTimeZoneOffset(double latitude, double longitude) {

		String url = "http://www.earthtools.org/timezone/" + latitude + "/" + longitude;
		System.err.println("!@#$ url=" + url);

		return 10;
	}
}
