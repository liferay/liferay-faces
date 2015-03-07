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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 * @author  Kyle Stiemann
 */
@ManagedBean
@RequestScoped
public class InputDateModelBean {

	private Date birthday;
	private Locale locale;
	private Date maxDate;
	private Date minDate;

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getFormattedMaxDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		return dateFormat.format(getMaxDate());
	}

	public String getFormattedMinDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

		return dateFormat.format(getMinDate());
	}

	public Locale getLocale() {

		if (locale == null) {
			locale = new Locale("ja", "JA");
		}

		return locale;
	}

	public Date getMaxDate() {

		if (maxDate == null) {

			Calendar calendar = new GregorianCalendar();
			calendar.add(Calendar.MONTH, 2);
			maxDate = calendar.getTime();
		}

		return maxDate;
	}

	public Date getMinDate() {

		if (minDate == null) {

			Calendar calendar = new GregorianCalendar();
			calendar.add(Calendar.MONTH, -2);
			minDate = calendar.getTime();
		}

		return minDate;
	}
}
