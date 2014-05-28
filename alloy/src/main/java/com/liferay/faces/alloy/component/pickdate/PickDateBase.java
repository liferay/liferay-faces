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
package com.liferay.faces.alloy.component.datepicker;
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
public abstract class DatePickerBase extends UIComponentBase implements Styleable, ClientComponent, DatePickerAlloy {

	@Override
	public Object getActiveInput() {
		return (Object) getStateHelper().eval(ACTIVE_INPUT, null);
	}

	@Override
	public void setActiveInput(Object activeInput) {
		getStateHelper().put(ACTIVE_INPUT, activeInput);
	}

	@Override
	public Boolean isAutoHide() {
		return (Boolean) getStateHelper().eval(AUTO_HIDE, null);
	}

	@Override
	public void setAutoHide(Boolean autoHide) {
		getStateHelper().put(AUTO_HIDE, autoHide);
	}

	@Override
	public Object getCalendar() {
		return (Object) getStateHelper().eval(CALENDAR, null);
	}

	@Override
	public void setCalendar(Object calendar) {
		getStateHelper().put(CALENDAR, calendar);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(CLIENT_KEY, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(CLIENT_KEY, clientKey);
	}

	@Override
	public Object getContainer() {
		return (Object) getStateHelper().eval(CONTAINER, null);
	}

	@Override
	public void setContainer(Object container) {
		getStateHelper().put(CONTAINER, container);
	}

	@Override
	public String getContent() {
		return (String) getStateHelper().eval(CONTENT, null);
	}

	@Override
	public void setContent(String content) {
		getStateHelper().put(CONTENT, content);
	}

	@Override
	public String getDatePattern() {
		return (String) getStateHelper().eval(DATE_PATTERN, null);
	}

	@Override
	public void setDatePattern(String datePattern) {
		getStateHelper().put(DATE_PATTERN, datePattern);
	}

	@Override
	public String getDateSeparator() {
		return (String) getStateHelper().eval(DATE_SEPARATOR, null);
	}

	@Override
	public void setDateSeparator(String dateSeparator) {
		getStateHelper().put(DATE_SEPARATOR, dateSeparator);
	}

	@Override
	public String getFor() {
		return (String) getStateHelper().eval(FOR, null);
	}

	@Override
	public void setFor(String for_) {
		getStateHelper().put(FOR, for_);
	}

	@Override
	public Object getLocale() {
		return (Object) getStateHelper().eval(LOCALE, null);
	}

	@Override
	public void setLocale(Object locale) {
		getStateHelper().put(LOCALE, locale);
	}

	@Override
	public String getMask() {
		return (String) getStateHelper().eval(MASK, null);
	}

	@Override
	public void setMask(String mask) {
		getStateHelper().put(MASK, mask);
	}

	@Override
	public Object getMaximumDate() {
		return (Object) getStateHelper().eval(MAXIMUM_DATE, null);
	}

	@Override
	public void setMaximumDate(Object maximumDate) {
		getStateHelper().put(MAXIMUM_DATE, maximumDate);
	}

	@Override
	public Object getMinimumDate() {
		return (Object) getStateHelper().eval(MINIMUM_DATE, null);
	}

	@Override
	public void setMinimumDate(Object minimumDate) {
		getStateHelper().put(MINIMUM_DATE, minimumDate);
	}

	@Override
	public Object getPanes() {
		return (Object) getStateHelper().eval(PANES, null);
	}

	@Override
	public void setPanes(Object panes) {
		getStateHelper().put(PANES, panes);
	}

	@Override
	public Object getPopover() {
		return (Object) getStateHelper().eval(POPOVER, null);
	}

	@Override
	public void setPopover(Object popover) {
		getStateHelper().put(POPOVER, popover);
	}

	@Override
	public String getPopoverCssClass() {
		return (String) getStateHelper().eval(POPOVER_CSS_CLASS, null);
	}

	@Override
	public void setPopoverCssClass(String popoverCssClass) {
		getStateHelper().put(POPOVER_CSS_CLASS, popoverCssClass);
	}

	@Override
	public String getSelectionMode() {
		return (String) getStateHelper().eval(SELECTION_MODE, null);
	}

	@Override
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

	@Override
	public String getTrigger() {
		return (String) getStateHelper().eval(TRIGGER, null);
	}

	@Override
	public void setTrigger(String trigger) {
		getStateHelper().put(TRIGGER, trigger);
	}

	@Override
	public String getValueExtractor() {
		return (String) getStateHelper().eval(VALUE_EXTRACTOR, null);
	}

	@Override
	public void setValueExtractor(String valueExtractor) {
		getStateHelper().put(VALUE_EXTRACTOR, valueExtractor);
	}

	@Override
	public String getValueFormatter() {
		return (String) getStateHelper().eval(VALUE_FORMATTER, null);
	}

	@Override
	public void setValueFormatter(String valueFormatter) {
		getStateHelper().put(VALUE_FORMATTER, valueFormatter);
	}

	@Override
	public Object getzIndex() {
		return (Object) getStateHelper().eval(Z_INDEX, null);
	}

	@Override
	public void setzIndex(Object zIndex) {
		getStateHelper().put(Z_INDEX, zIndex);
	}
}
//J+
