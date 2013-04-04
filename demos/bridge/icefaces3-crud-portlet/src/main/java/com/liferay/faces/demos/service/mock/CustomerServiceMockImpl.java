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
package com.liferay.faces.demos.service.mock;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.liferay.faces.demos.dto.Customer;
import com.liferay.faces.demos.service.CustomerService;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "customerService")
@ApplicationScoped
public class CustomerServiceMockImpl implements CustomerService, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 8524082963779934947L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceMockImpl.class);

	// Private Data Memebers
	private transient List<Customer> allCustomers;
	private transient int nextCustomerId;

	public Customer addCustomer(Customer customer) {
		customer.setCustomerId(nextCustomerId++);
		allCustomers.add(customer);

		return customer;
	}

	public int countCustomers() {
		List<Customer> customers = getCustomers(null, null);

		return customers.size();
	}

	public Customer removeCustomer(int customerId) {

		Customer customer = null;
		Iterator<Customer> itr = allCustomers.iterator();

		while (itr.hasNext()) {
			Customer curCustomer = itr.next();

			if (curCustomer.getCustomerId() == customerId) {
				customer = curCustomer;
				itr.remove();
			}
		}

		return customer;
	}

	public Customer updateCustomer(Customer customer) {

		int totalCustomers = allCustomers.size();

		for (int i = 0; i < totalCustomers; i++) {

			if (allCustomers.get(i).getCustomerId() == customer.getCustomerId()) {
				allCustomers.set(i, customer);

				break;
			}
		}

		return customer;
	}

	public List<Customer> getCustomers() {
		return getCustomers(null, null);
	}

	public List<Customer> getCustomers(String sortColumn, Boolean sortAscending) {
		return getCustomers(CustomerService.ALL_POS, CustomerService.ALL_POS, sortColumn, sortAscending);
	}

	public List<Customer> getCustomers(int startRow, int finishRow, String sortColumn, Boolean sortAscending) {

		if (allCustomers == null) {

			try {

				// Obtain a SAX Parser Factory.
				SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
				boolean validating = false;
				saxParserFactory.setValidating(validating);
				saxParserFactory.setNamespaceAware(true);

				// Obtain a SAX Parser from the factory.
				SAXParser saxParser = saxParserFactory.newSAXParser();

				// Define a SAX 2 callback event handler for the SAX Parser.
				CustomerEventHandler saxEventHandler = new CustomerEventHandler();

				// First, parse all of the META-INF/faces-config.xml files found in the classpath. At this time, the JSF
				// 2.0 <ordering> element is not supported.
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				URL xmlFileURL = classLoader.getResource("customers.xml");

				if (xmlFileURL != null) {

					InputStream inputStream = xmlFileURL.openStream();

					saxParser.parse(inputStream, saxEventHandler);
					inputStream.close();
					saxParser.reset();
					allCustomers = saxEventHandler.getCustomers();
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		int totalCustomers = allCustomers.size();
		List<Customer> customers = new ArrayList<Customer>(totalCustomers);

		for (Customer customer : allCustomers) {

			try {
				customers.add((Customer) customer.clone());
			}
			catch (CloneNotSupportedException e) {
				// ignore
			}
		}

		if ((sortColumn != null) && (sortAscending != null)) {
			CustomerSortComparator customerSortComparator = new CustomerSortComparator(sortColumn, sortAscending);
			Collections.sort(customers, customerSortComparator);
		}

		if ((startRow > CustomerService.ALL_POS) && (finishRow > CustomerService.ALL_POS)) {
			customers = customers.subList(startRow, finishRow + 1);
		}

		return customers;
	}

	private class CustomerEventHandler extends BaseSAXEventHandler {

		private boolean parsingRow;
		private boolean parsingCustomerId;
		private boolean parsingFirstName;
		private boolean parsingLastName;
		private boolean parsingEmailAddress;
		private boolean parsingPhoneNumber;
		private boolean parsingDateOfBirth;
		private boolean parsingCity;
		private boolean parsingProvinceId;
		private boolean parsingPostalCode;
		private Customer customer;
		private List<Customer> customers = new ArrayList<Customer>();

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {

			if (parsingCustomerId) {
				int customerId = Integer.parseInt(getContent().toString());
				customer.setCustomerId(customerId);
				parsingCustomerId = false;

				if (customerId >= nextCustomerId) {
					nextCustomerId = customerId + 1;
				}
			}
			else if (parsingFirstName) {
				customer.setFirstName(getContent().toString());
				parsingFirstName = false;
			}
			else if (parsingLastName) {
				customer.setLastName(getContent().toString());
				parsingLastName = false;
			}
			else if (parsingEmailAddress) {
				customer.setEmailAddress(getContent().toString());
				parsingEmailAddress = false;
			}
			else if (parsingPhoneNumber) {
				customer.setPhoneNumber(getContent().toString());
				parsingPhoneNumber = false;
			}
			else if (parsingDateOfBirth) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

				try {
					customer.setDateOfBirth(dateFormat.parse(getContent().toString()));
				}
				catch (Exception e) {
					logger.error(e);
				}

				parsingDateOfBirth = false;
			}
			else if (parsingCity) {
				customer.setCity(getContent().toString());
				parsingCity = false;
			}
			else if (parsingProvinceId) {
				customer.setProvinceId(Integer.parseInt(getContent().toString()));
				parsingProvinceId = false;
			}
			else if (parsingPostalCode) {
				customer.setPostalCode(getContent().toString());
				parsingPostalCode = false;
			}
			else if (parsingRow) {
				customers.add(customer);
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
				customer = new Customer();
			}
			else if (elementName.equals("column")) {
				String columnName = attributes.getValue(0);

				if (columnName.equals("customerId")) {
					parsingCustomerId = true;
				}
				else if (columnName.equals("firstName")) {
					parsingFirstName = true;
				}
				else if (columnName.equals("lastName")) {
					parsingLastName = true;
				}
				else if (columnName.equals("emailAddress")) {
					parsingEmailAddress = true;
				}
				else if (columnName.equals("phoneNumber")) {
					parsingPhoneNumber = true;
				}
				else if (columnName.equals("dateOfBirth")) {
					parsingDateOfBirth = true;
				}
				else if (columnName.equals("city")) {
					parsingCity = true;
				}
				else if (columnName.equals("provinceId")) {
					parsingProvinceId = true;
				}
				else if (columnName.equals("postalCode")) {
					parsingPostalCode = true;
				}
			}
		}

		public List<Customer> getCustomers() {
			return customers;
		}
	}
}
