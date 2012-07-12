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
package com.liferay.faces.demos.service.mock;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.demos.dto.City;
import com.liferay.faces.demos.service.CityService;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "cityService")
@ApplicationScoped
public class CityServiceMockImpl implements CityService {

	private static final Logger logger = LoggerFactory.getLogger(CityServiceMockImpl.class);

	private List<City> allCities;

	public List<City> getCities() {

		if (allCities == null) {

			try {

				// Obtain a SAX Parser Factory.
				SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
				boolean validating = false;
				saxParserFactory.setValidating(validating);
				saxParserFactory.setNamespaceAware(true);

				// Obtain a SAX Parser from the factory.
				SAXParser saxParser = saxParserFactory.newSAXParser();

				// Define a SAX 2 callback event handler for the SAX Parser.
				CityEventHandler saxEventHandler = new CityEventHandler();

				// First, parse all of the META-INF/faces-config.xml files found in the classpath. At this time, the JSF
				// 2.0 <ordering> element is not supported.
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				URL xmlFileURL = classLoader.getResource("cities.xml");

				if (xmlFileURL != null) {

					InputStream inputStream = xmlFileURL.openStream();

					saxParser.parse(inputStream, saxEventHandler);
					inputStream.close();
					saxParser.reset();
					allCities = saxEventHandler.getCities();
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return allCities;
	}

	private class CityEventHandler extends BaseSAXEventHandler {

		private boolean parsingCityId;
		private boolean parsingCityName;
		private boolean parsingProvinceId;
		private boolean parsingPostalCode;
		private boolean parsingRow;
		private City city;
		private List<City> cities = new ArrayList<City>();

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {

			if (parsingCityId) {
				city.setCityId(Integer.parseInt(getContent().toString()));
				parsingCityId = false;
			}
			else if (parsingProvinceId) {
				city.setProvinceId(Integer.parseInt(getContent().toString()));
				parsingProvinceId = false;
			}
			else if (parsingCityName) {
				city.setCityName(getContent().toString());
				parsingCityName = false;
			}
			else if (parsingPostalCode) {
				city.setPostalCode(getContent().toString());
				parsingPostalCode = false;
			}
			else if (parsingRow) {
				cities.add(city);
				parsingRow = false;
			}

			setContent(null);
		}

		@Override
		public void startElement(String uri, String localName, String elementName, Attributes attributes)
			throws SAXException {
			logger.trace("elementName=[]", elementName);

			setContent(new StringBuilder());

			if (elementName.equals("row")) {
				parsingRow = true;
				city = new City();
			}
			else if (elementName.equals("column")) {
				String columnName = attributes.getValue(0);

				if (columnName.equals("cityId")) {
					parsingCityId = true;
				}
				else if (columnName.equals("provinceId")) {
					parsingProvinceId = true;
				}
				else if (columnName.equals("cityName")) {
					parsingCityName = true;
				}
				else if (columnName.equals("postalCode")) {
					parsingPostalCode = true;
				}
			}
		}

		public List<City> getCities() {
			return cities;
		}
	}
}
