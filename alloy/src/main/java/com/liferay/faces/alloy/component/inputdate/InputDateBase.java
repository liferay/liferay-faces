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
package com.liferay.faces.alloy.component.inputdate;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.inputdatetime.InputDateTimeBase;

import com.liferay.faces.util.component.Styleable;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputDateBase extends InputDateTimeBase implements Styleable {

	// Public Constants
	private static final String DATE_PATTERN = "datePattern";
	private static final String LOCALE = "locale";
	private static final String MAXIMUM_DATE = "maximumDate";
	private static final String MINIMUM_DATE = "minimumDate";
	private static final String PANES = "panes";
	private static final String STYLE = "style";
	private static final String STYLE_CLASS = "styleClass";
	private static final String Z_INDEX = "zIndex";

	public String getDatePattern() {
		return (String) getStateHelper().eval(DATE_PATTERN, null);
	}

	public void setDatePattern(String datePattern) {
		getStateHelper().put(DATE_PATTERN, datePattern);
	}

	public Object getLocale() {
		return (Object) getStateHelper().eval(LOCALE, null);
	}

	public void setLocale(Object locale) {
		getStateHelper().put(LOCALE, locale);
	}

	public Object getMaximumDate() {
		return (Object) getStateHelper().eval(MAXIMUM_DATE, null);
	}

	public void setMaximumDate(Object maximumDate) {
		getStateHelper().put(MAXIMUM_DATE, maximumDate);
	}

	public Object getMinimumDate() {
		return (Object) getStateHelper().eval(MINIMUM_DATE, null);
	}

	public void setMinimumDate(Object minimumDate) {
		getStateHelper().put(MINIMUM_DATE, minimumDate);
	}

	public Object getPanes() {
		return (Object) getStateHelper().eval(PANES, null);
	}

	public void setPanes(Object panes) {
		getStateHelper().put(PANES, panes);
	}

	public String getStyle() {
		return (String) getStateHelper().eval(STYLE, null);
	}

	public void setStyle(String style) {
		getStateHelper().put(STYLE, style);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}

	public Object getzIndex() {
		return (Object) getStateHelper().eval(Z_INDEX, null);
	}

	public void setzIndex(Object zIndex) {
		getStateHelper().put(Z_INDEX, zIndex);
	}
}
//J+
