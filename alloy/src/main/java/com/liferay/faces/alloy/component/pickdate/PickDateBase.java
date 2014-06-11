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
package com.liferay.faces.alloy.component.pickdate;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIComponentBase;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PickDateBase extends UIComponentBase implements Styleable, ClientComponent {

	// Public Constants
	public static final String AUTO_HIDE = "autoHide";
	public static final String CLIENT_KEY = "clientKey";
	public static final String DATE_PATTERN = "datePattern";
	public static final String DATE_SEPARATOR = "dateSeparator";
	public static final String FOR = "for";
	public static final String LOCALE = "locale";
	public static final String MAXIMUM_DATE = "maximumDate";
	public static final String MINIMUM_DATE = "minimumDate";
	public static final String PANES = "panes";
	public static final String SELECTION_MODE = "selectionMode";
	public static final String STYLE = "style";
	public static final String STYLE_CLASS = "styleClass";
	public static final String Z_INDEX = "zIndex";

	public Boolean isAutoHide() {
		return (Boolean) getStateHelper().eval(AUTO_HIDE, null);
	}

	public void setAutoHide(Boolean autoHide) {
		getStateHelper().put(AUTO_HIDE, autoHide);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(CLIENT_KEY, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(CLIENT_KEY, clientKey);
	}

	public String getDatePattern() {
		return (String) getStateHelper().eval(DATE_PATTERN, null);
	}

	public void setDatePattern(String datePattern) {
		getStateHelper().put(DATE_PATTERN, datePattern);
	}

	public String getDateSeparator() {
		return (String) getStateHelper().eval(DATE_SEPARATOR, null);
	}

	public void setDateSeparator(String dateSeparator) {
		getStateHelper().put(DATE_SEPARATOR, dateSeparator);
	}

	public String getFor() {
		return (String) getStateHelper().eval(FOR, null);
	}

	public void setFor(String for_) {
		getStateHelper().put(FOR, for_);
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

	public Integer getPanes() {
		return (Integer) getStateHelper().eval(PANES, null);
	}

	public void setPanes(Integer panes) {
		getStateHelper().put(PANES, panes);
	}

	public String getSelectionMode() {
		return (String) getStateHelper().eval(SELECTION_MODE, null);
	}

	public void setSelectionMode(String selectionMode) {
		getStateHelper().put(SELECTION_MODE, selectionMode);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(STYLE, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(STYLE, style);
	}

	@Override
	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}

	public Integer getzIndex() {
		return (Integer) getStateHelper().eval(Z_INDEX, null);
	}

	public void setzIndex(Integer zIndex) {
		getStateHelper().put(Z_INDEX, zIndex);
	}
}
//J+
