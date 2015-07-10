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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;


/**
 * @author  Juan Gonzalez
 */
@ManagedBean
@RequestScoped
public class ValidationModelBean {

	@Past
	private Date birthDay;
	@Size(max = 5)
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
