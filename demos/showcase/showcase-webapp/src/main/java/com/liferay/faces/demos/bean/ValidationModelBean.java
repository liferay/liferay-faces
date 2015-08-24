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
package com.liferay.faces.demos.bean;

// JSF 2+ import javax.faces.bean.ManagedBean;
// JSF 2+ import javax.faces.bean.RequestScoped;
// JSF 2+ import javax.validation.constraints.Past;
// JSF 2+ import javax.validation.constraints.Size;


/**
 * @author  Juan Gonzalez
 */
// JSF 2+ @ManagedBean
// JSF 2+ @RequestScoped
public class ValidationModelBean {

	// JSF 2+ @Pattern(regexp = ".+[@].+[.].+")
	private String email;
	private Double doubleNumber;
	private Long longNumber;

	public Double getDoubleNumber() {
		return doubleNumber;
	}

	public void setDoubleNumber(Double doubleNumber) {
		this.doubleNumber = doubleNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getLongNumber() {
		return longNumber;
	}

	public void setLongNumber(Long longNumber) {
		this.longNumber = longNumber;
	}
}
