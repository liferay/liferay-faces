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
import java.util.GregorianCalendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;


/**
 * @author  Vernon Singleton
 */
@ManagedBean
@RequestScoped
public class InputHiddenModelBean {

	private Date date = new GregorianCalendar().getTime();
	private Date testDate = new GregorianCalendar(33, 3, 5).getTime();
	private String text;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTestDate1() {

		// Example 1 requires a date formatted by the user's locale.
		DateTimeConverter dateTimeConverter = new DateTimeConverter();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		dateTimeConverter.setLocale(externalContext.getRequestLocale());

		return dateTimeConverter.getAsString(facesContext, facesContext.getViewRoot(), testDate);
	}

	public String getTestDate2() {

		// Example 1 requires a date formatted by a specific date pattern.
		DateTimeConverter dateTimeConverter = new DateTimeConverter();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		dateTimeConverter.setLocale(externalContext.getRequestLocale());
		dateTimeConverter.setPattern("MM/dd/yyyy");

		return dateTimeConverter.getAsString(facesContext, facesContext.getViewRoot(), testDate);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
