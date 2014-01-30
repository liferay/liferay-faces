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
 * This is a bean that represents a Province, and implements the Transfer Object (formerly known as ValueObject/VO)
 * design pattern.
 *
 * @author  "Neil Griffin"
 */
public class Province implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 4055995992262228819L;

	// JavaBean Properties
	private long provinceId;
	private long countryId;
	private String provinceName;
	private String provinceAbbreviation;

	public Province() {
		// Pluto requires a no-arg default constructor.
	}

	public Province(long provinceId, long countryId, String provinceName, String provinceAbbreviation) {
		this.provinceId = provinceId;
		this.setCountryId(countryId);
		this.provinceName = provinceName;
		this.provinceAbbreviation = provinceAbbreviation;
	}

	public long getCountryId() {
		return countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public String getProvinceAbbreviation() {
		return provinceAbbreviation;
	}

	public void setProvinceAbbreviation(String provinceAbbreviation) {
		this.provinceAbbreviation = provinceAbbreviation;
	}

	public long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
}
