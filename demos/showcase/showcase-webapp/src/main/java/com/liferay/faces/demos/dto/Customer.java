/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
import java.util.Date;


/**
 * @author  Neil Griffin
 */
public class Customer implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 6155011527137371447L;

	// Private Data Members
	private long customerId;
	private long countryId;
	private Date dateOfBirth;
	private String firstName;
	private String lastName;

	public Customer(long customerId, long countryId, String firstName, String lastName, Date dateOfBirth) {
		this.customerId = customerId;
		this.countryId = countryId;
		this.dateOfBirth = dateOfBirth;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
