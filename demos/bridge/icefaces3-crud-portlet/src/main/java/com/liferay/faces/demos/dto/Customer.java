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
package com.liferay.faces.demos.dto;

import java.util.Date;


/**
 * @author  Neil Griffin
 */
public class Customer implements Cloneable {

	private int customerId;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private Date dateOfBirth;
	private String city;
	private int provinceId;
	private String postalCode;

	@Override
	public Object clone() throws CloneNotSupportedException {
		Customer cloned = (Customer) super.clone();
		cloned.setCustomerId(customerId);
		cloned.setFirstName(new String(firstName));
		cloned.setLastName(new String(lastName));
		cloned.setEmailAddress(new String(emailAddress));
		cloned.setPhoneNumber(new String(phoneNumber));
		cloned.setDateOfBirth(new Date(dateOfBirth.getTime()));
		cloned.setCity(new String(city));
		cloned.setProvinceId(provinceId);
		cloned.setPostalCode(new String(postalCode));

		return cloned;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}
}
