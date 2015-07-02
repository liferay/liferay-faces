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
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 * @author  Kyle Stiemann
 */
@ManagedBean
@ViewScoped
public class InputTimeModelBean {

	private String highlighterType = "charMatch";
	private Date time;
	private Locale locale;

	public String getHighlighterType() {
		return highlighterType;
	}

	public void setHighlighterType(String highlighterType) {
		this.highlighterType = highlighterType;
	}

	public Locale getLocale() {

		if (locale == null) {
			locale = new Locale("ja", "JA");
		}

		return locale;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
