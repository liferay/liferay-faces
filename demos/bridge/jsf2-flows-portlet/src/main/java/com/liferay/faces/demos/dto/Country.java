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
package com.liferay.faces.demos.dto;

import java.io.Serializable;


/**
 * @author  Neil Griffin
 */
public class Country implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 9135922470541735463L;

	// Private Data Members
	private long countryId;
	private String countryName;
	private String countryAbbreviation;

	public Country() {
		// Pluto requires a no-arg default constructor.
	}

	public Country(long countryId, String countryName, String countryAbbreviation) {
		this.countryId = countryId;
		this.countryName = countryName;
		this.countryAbbreviation = countryAbbreviation;
	}

	public String getCountryAbbreviation() {
		return countryAbbreviation;
	}

	public void setCountryAbbreviation(String countryAbbreviation) {
		this.countryAbbreviation = countryAbbreviation;
	}

	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
