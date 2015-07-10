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

import java.util.Date;

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

	// JSF 2+ @Past
	private Date birthDay;
	// JSF 2+ @Size(max = 5)
	private String text;
	private Double doubleRange;
	private String otherText;
	private Long longRange;

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Double getDoubleRange() {
		return doubleRange;
	}

	public void setDoubleRange(Double doubleRange) {
		this.doubleRange = doubleRange;
	}

	public Long getLongRange() {
		return longRange;
	}

	public void setLongRange(Long longRange) {
		this.longRange = longRange;
	}

	public String getOtherText() {
		return otherText;
	}

	public void setOtherText(String otherText) {
		this.otherText = otherText;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
