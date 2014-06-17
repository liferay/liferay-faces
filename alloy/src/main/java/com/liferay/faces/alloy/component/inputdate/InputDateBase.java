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
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputDateBase extends InputDateTimeBase implements Styleable {

	// Protected Enumerations
	protected enum InputDatePropertyKeys {
		autoHide,
		datePattern,
		locale,
		maximumDate,
		minimumDate,
		panes,
		zIndex
	}

	public Boolean isAutoHide() {
		return (Boolean) getStateHelper().eval(InputDatePropertyKeys.autoHide, null);
	}

	public void setAutoHide(Boolean autoHide) {
		getStateHelper().put(InputDatePropertyKeys.autoHide, autoHide);
	}

	public String getDatePattern() {
		return (String) getStateHelper().eval(InputDatePropertyKeys.datePattern, null);
	}

	public void setDatePattern(String datePattern) {
		getStateHelper().put(InputDatePropertyKeys.datePattern, datePattern);
	}

	public Object getLocale() {
		return (Object) getStateHelper().eval(InputDatePropertyKeys.locale, null);
	}

	public void setLocale(Object locale) {
		getStateHelper().put(InputDatePropertyKeys.locale, locale);
	}

	public Object getMaximumDate() {
		return (Object) getStateHelper().eval(InputDatePropertyKeys.maximumDate, null);
	}

	public void setMaximumDate(Object maximumDate) {
		getStateHelper().put(InputDatePropertyKeys.maximumDate, maximumDate);
	}

	public Object getMinimumDate() {
		return (Object) getStateHelper().eval(InputDatePropertyKeys.minimumDate, null);
	}

	public void setMinimumDate(Object minimumDate) {
		getStateHelper().put(InputDatePropertyKeys.minimumDate, minimumDate);
	}

	public Integer getPanes() {
		return (Integer) getStateHelper().eval(InputDatePropertyKeys.panes, null);
	}

	public void setPanes(Integer panes) {
		getStateHelper().put(InputDatePropertyKeys.panes, panes);
	}

	public Integer getzIndex() {
		return (Integer) getStateHelper().eval(InputDatePropertyKeys.zIndex, null);
	}

	public void setzIndex(Integer zIndex) {
		getStateHelper().put(InputDatePropertyKeys.zIndex, zIndex);
	}
}
//J+
