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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.liferay.faces.demos.dto.Airport;
import com.liferay.faces.util.helper.LongHelper;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@Named("airportService")
@ApplicationScoped
public class AirportServiceMockImpl implements AirportService {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AirportServiceMockImpl.class);

	// Private Constants
	private static final String AIRPORTS_FILENAME = "airports.csv";
	private static final String AIRPORTS_URL =
		"http://sourceforge.net/p/openflights/code/757/tree/openflights/data/airports.dat?format=raw";
	private static final String CANADA = "Canada";
	private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
	private static final String UNITED_STATES = "United States";

	// Private Data Members
	private List<Airport> airports;
	private Map<Long, Airport> airportMap;

	public AirportServiceMockImpl() {

		this.airports = new ArrayList<Airport>();
		this.airportMap = new HashMap<Long, Airport>();

		String tempFolderPath = System.getProperty(JAVA_IO_TMPDIR);
		File tempFolder = new File(tempFolderPath);
		File airportsFile = new File(tempFolder, AIRPORTS_FILENAME);

		if (!airportsFile.exists()) {

			URL url = null;

			try {
				logger.info("Downloading url=[{0}]", url);

				url = new URL(AIRPORTS_URL);

				InputStream inputStream = url.openStream();
				OutputStream outputStream = new FileOutputStream(airportsFile);
				byte[] buffer = new byte[1024];
				int bytesRead = 0;

				while ((bytesRead = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, bytesRead);
				}

				outputStream.close();
				inputStream.close();
			}
			catch (Exception e) {
				logger.warn("Error '{0}' when trying to download url=[{1}]", e.getMessage(), url);
			}
		}

		try {
			FileReader fileReader = new FileReader(airportsFile);

			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String csvLine;

			while ((csvLine = bufferedReader.readLine()) != null) {

				if (csvLine != null) {
					csvLine = csvLine.replaceAll(", ", StringPool.SPACE);
					csvLine = csvLine.replaceAll(StringPool.QUOTE, StringPool.BLANK);

					String[] csvParts = csvLine.split(StringPool.COMMA);

					String country = csvParts[3];

					if (UNITED_STATES.equals(country) || CANADA.equals(country)) {

						Airport airport = new Airport();
						long airportId = LongHelper.toLong(csvParts[0]);
						airport.setAirportId(airportId);
						airport.setName(csvParts[1]);
						airport.setCity(csvParts[2]);
						airport.setCountry(country);
						airport.setCode(csvParts[4]);
						airport.setLatitude(Double.parseDouble(csvParts[6]));
						airport.setLongitude(Double.parseDouble(csvParts[7]));
						this.airports.add(airport);
						this.airportMap.put(airportId, airport);
					}
				}
			}

			bufferedReader.close();
			fileReader.close();
		}
		catch (Exception e) {
			logger.error(e);
		}

		Collections.sort(this.airports, new AirportComparator());
	}

	@Override
	public Airport findById(long airportId) {
		return airportMap.get(airportId);
	}

	@Override
	public List<Airport> getAirports() {
		return airports;
	}

	protected class AirportComparator implements Comparator<Airport> {

		@Override
		public int compare(Airport airport1, Airport airport2) {
			return airport1.getCity().compareTo(airport2.getCity());
		}
	}
}
