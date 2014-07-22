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
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PickDateBase extends UIComponentBase implements Styleable, ClientComponent {

	// Protected Enumerations
	protected enum PickDatePropertyKeys {
		autoHide,
		clientKey,
		datePattern,
		dateSeparator,
		for_,
		locale,
		maximumDate,
		minimumDate,
		panes,
		selectionMode,
		style,
		styleClass,
		timeZone,
		zIndex
	}

	public Boolean isAutoHide() {
		return (Boolean) getStateHelper().eval(PickDatePropertyKeys.autoHide, null);
	}

	public void setAutoHide(Boolean autoHide) {
		getStateHelper().put(PickDatePropertyKeys.autoHide, autoHide);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(PickDatePropertyKeys.clientKey, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(PickDatePropertyKeys.clientKey, clientKey);
	}

	public String getDatePattern() {
		return (String) getStateHelper().eval(PickDatePropertyKeys.datePattern, null);
	}

	public void setDatePattern(String datePattern) {
		getStateHelper().put(PickDatePropertyKeys.datePattern, datePattern);
	}

	public String getDateSeparator() {
		return (String) getStateHelper().eval(PickDatePropertyKeys.dateSeparator, null);
	}

	public void setDateSeparator(String dateSeparator) {
		getStateHelper().put(PickDatePropertyKeys.dateSeparator, dateSeparator);
	}

	public String getFor() {
		return (String) getStateHelper().eval(PickDatePropertyKeys.for_, null);
	}

	public void setFor(String for_) {
		getStateHelper().put(PickDatePropertyKeys.for_, for_);
	}

	public Object getLocale() {
		return (Object) getStateHelper().eval(PickDatePropertyKeys.locale, null);
	}

	public void setLocale(Object locale) {
		getStateHelper().put(PickDatePropertyKeys.locale, locale);
	}

	public Object getMaximumDate() {
		return (Object) getStateHelper().eval(PickDatePropertyKeys.maximumDate, null);
	}

	public void setMaximumDate(Object maximumDate) {
		getStateHelper().put(PickDatePropertyKeys.maximumDate, maximumDate);
	}

	public Object getMinimumDate() {
		return (Object) getStateHelper().eval(PickDatePropertyKeys.minimumDate, null);
	}

	public void setMinimumDate(Object minimumDate) {
		getStateHelper().put(PickDatePropertyKeys.minimumDate, minimumDate);
	}

	public Integer getPanes() {
		return (Integer) getStateHelper().eval(PickDatePropertyKeys.panes, null);
	}

	public void setPanes(Integer panes) {
		getStateHelper().put(PickDatePropertyKeys.panes, panes);
	}

	public String getSelectionMode() {
		return (String) getStateHelper().eval(PickDatePropertyKeys.selectionMode, null);
	}

	public void setSelectionMode(String selectionMode) {
		getStateHelper().put(PickDatePropertyKeys.selectionMode, selectionMode);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(PickDatePropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(PickDatePropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		return (String) getStateHelper().eval(PickDatePropertyKeys.styleClass, null);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(PickDatePropertyKeys.styleClass, styleClass);
	}

	public Object getTimeZone() {
		return (Object) getStateHelper().eval(PickDatePropertyKeys.timeZone, null);
	}

	public void setTimeZone(Object timeZone) {
		getStateHelper().put(PickDatePropertyKeys.timeZone, timeZone);
	}

	public Integer getzIndex() {
		return (Integer) getStateHelper().eval(PickDatePropertyKeys.zIndex, null);
	}

	public void setzIndex(Integer zIndex) {
		getStateHelper().put(PickDatePropertyKeys.zIndex, zIndex);
	}
}
//J+
