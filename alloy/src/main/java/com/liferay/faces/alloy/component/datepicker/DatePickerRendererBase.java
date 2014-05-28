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

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.renderkit.AlloyRendererBase;
import com.liferay.faces.alloy.util.AlloyConstants;
import com.liferay.faces.util.lang.StringPool;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class DatePickerRendererBase extends AlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "DatePicker";
	private static final String ALLOY_MODULE_NAME = "aui-datepicker";
	private static final String ACTIVE_INPUT_CHANGE = "activeInputChange";
	private static final String AUTO_HIDE_CHANGE = "autoHideChange";
	private static final String CALENDAR_CHANGE = "calendarChange";
	private static final String CONTAINER_CHANGE = "containerChange";
	private static final String CONTENT_CHANGE = "contentChange";
	private static final String DATE_SEPARATOR_CHANGE = "dateSeparatorChange";
	private static final String DESTROYED_CHANGE = "destroyedChange";
	private static final String INITIALIZED_CHANGE = "initializedChange";
	private static final String MASK_CHANGE = "maskChange";
	private static final String PANES_CHANGE = "panesChange";
	private static final String POPOVER_CHANGE = "popoverChange";
	private static final String POPOVER_CSS_CLASS_CHANGE = "popoverCssClassChange";
	private static final String VALUE_EXTRACTOR_CHANGE = "valueExtractorChange";
	private static final String VALUE_FORMATTER_CHANGE = "valueFormatterChange";

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		DatePickerAlloy datePickerAlloy = (DatePickerAlloy) uiComponent;
		boolean first = true;

		Object activeInput = datePickerAlloy.getActiveInput();

		if (activeInput != null) {

			encodeActiveInput(responseWriter, datePickerAlloy, activeInput, first);
			first = false;
		}

		Boolean autoHide = datePickerAlloy.isAutoHide();

		if (autoHide != null) {

			encodeAutoHide(responseWriter, datePickerAlloy, autoHide, first);
			first = false;
		}

		Object calendar = datePickerAlloy.getCalendar();

		if (calendar != null) {

			encodeCalendar(responseWriter, datePickerAlloy, calendar, first);
			first = false;
		}

		Object container = datePickerAlloy.getContainer();

		if (container != null) {

			encodeContainer(responseWriter, datePickerAlloy, container, first);
			first = false;
		}

		String content = datePickerAlloy.getContent();

		if (content != null) {

			encodeContent(responseWriter, datePickerAlloy, content, first);
			first = false;
		}

		String datePattern = datePickerAlloy.getDatePattern();

		if (datePattern != null) {

			encodeDatePattern(responseWriter, datePickerAlloy, datePattern, first);
			first = false;
		}

		String dateSeparator = datePickerAlloy.getDateSeparator();

		if (dateSeparator != null) {

			encodeDateSeparator(responseWriter, datePickerAlloy, dateSeparator, first);
			first = false;
		}

		String for_ = datePickerAlloy.getFor();

		if (for_ != null) {

			encodeFor(responseWriter, datePickerAlloy, for_, first);
			first = false;
		}

		Object locale = datePickerAlloy.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, datePickerAlloy, locale, first);
			first = false;
		}

		String mask = datePickerAlloy.getMask();

		if (mask != null) {

			encodeMask(responseWriter, datePickerAlloy, mask, first);
			first = false;
		}

		Object maximumDate = datePickerAlloy.getMaximumDate();

		if (maximumDate != null) {

			encodeMaximumDate(responseWriter, datePickerAlloy, maximumDate, first);
			first = false;
		}

		Object minimumDate = datePickerAlloy.getMinimumDate();

		if (minimumDate != null) {

			encodeMinimumDate(responseWriter, datePickerAlloy, minimumDate, first);
			first = false;
		}

		Object panes = datePickerAlloy.getPanes();

		if (panes != null) {

			encodePanes(responseWriter, datePickerAlloy, panes, first);
			first = false;
		}

		Object popover = datePickerAlloy.getPopover();

		if (popover != null) {

			encodePopover(responseWriter, datePickerAlloy, popover, first);
			first = false;
		}

		String popoverCssClass = datePickerAlloy.getPopoverCssClass();

		if (popoverCssClass != null) {

			encodePopoverCssClass(responseWriter, datePickerAlloy, popoverCssClass, first);
			first = false;
		}

		String selectionMode = datePickerAlloy.getSelectionMode();

		if (selectionMode != null) {

			encodeSelectionMode(responseWriter, datePickerAlloy, selectionMode, first);
			first = false;
		}

		String trigger = datePickerAlloy.getTrigger();

		if (trigger != null) {

			encodeTrigger(responseWriter, datePickerAlloy, trigger, first);
			first = false;
		}

		String valueExtractor = datePickerAlloy.getValueExtractor();

		if (valueExtractor != null) {

			encodeValueExtractor(responseWriter, datePickerAlloy, valueExtractor, first);
			first = false;
		}

		String valueFormatter = datePickerAlloy.getValueFormatter();

		if (valueFormatter != null) {

			encodeValueFormatter(responseWriter, datePickerAlloy, valueFormatter, first);
			first = false;
		}

		Object zIndex = datePickerAlloy.getzIndex();

		if (zIndex != null) {

			encodeZIndex(responseWriter, datePickerAlloy, zIndex, first);
			first = false;
		}
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeActiveInput(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Object activeInput, boolean first) throws IOException {
		encodeObject(responseWriter, DatePickerAlloy.ACTIVE_INPUT, activeInput, first);
	}

	protected void encodeAutoHide(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Boolean autoHide, boolean first) throws IOException {
		encodeBoolean(responseWriter, DatePickerAlloy.AUTO_HIDE, autoHide, first);
	}

	protected void encodeCalendar(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Object calendar, boolean first) throws IOException {
		encodeObject(responseWriter, DatePickerAlloy.CALENDAR, calendar, first);
	}

	protected void encodeContainer(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Object container, boolean first) throws IOException {
		encodeObject(responseWriter, DatePickerAlloy.CONTAINER, container, first);
	}

	protected void encodeContent(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String content, boolean first) throws IOException {
		encodeString(responseWriter, DatePickerAlloy.CONTENT, content, first);
	}

	protected void encodeDatePattern(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String datePattern, boolean first) throws IOException {
		encodeString(responseWriter, DatePickerAlloy.DATE_PATTERN, datePattern, first);
	}

	protected void encodeDateSeparator(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String dateSeparator, boolean first) throws IOException {
		encodeString(responseWriter, DatePickerAlloy.DATE_SEPARATOR, dateSeparator, first);
	}

	protected void encodeFor(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String for_, boolean first) throws IOException {
		encodeString(responseWriter, DatePickerAlloy.FOR, for_, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Object locale, boolean first) throws IOException {
		encodeObject(responseWriter, DatePickerAlloy.LOCALE, locale, first);
	}

	protected void encodeMask(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String mask, boolean first) throws IOException {
		encodeString(responseWriter, DatePickerAlloy.MASK, mask, first);
	}

	protected void encodeMaximumDate(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Object maximumDate, boolean first) throws IOException {
		encodeObject(responseWriter, DatePickerAlloy.MAXIMUM_DATE, maximumDate, first);
	}

	protected void encodeMinimumDate(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Object minimumDate, boolean first) throws IOException {
		encodeObject(responseWriter, DatePickerAlloy.MINIMUM_DATE, minimumDate, first);
	}

	protected void encodePanes(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Object panes, boolean first) throws IOException {
		encodeNumber(responseWriter, DatePickerAlloy.PANES, panes, first);
	}

	protected void encodePopover(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Object popover, boolean first) throws IOException {
		encodeObject(responseWriter, DatePickerAlloy.POPOVER, popover, first);
	}

	protected void encodePopoverCssClass(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String popoverCssClass, boolean first) throws IOException {
		encodeString(responseWriter, DatePickerAlloy.POPOVER_CSS_CLASS, popoverCssClass, first);
	}

	protected void encodeSelectionMode(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String selectionMode, boolean first) throws IOException {
		encodeString(responseWriter, DatePickerAlloy.SELECTION_MODE, selectionMode, first);
	}

	protected void encodeTrigger(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String trigger, boolean first) throws IOException {
		encodeString(responseWriter, DatePickerAlloy.TRIGGER, trigger, first);
	}

	protected void encodeValueExtractor(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String valueExtractor, boolean first) throws IOException {
		encodeString(responseWriter, DatePickerAlloy.VALUE_EXTRACTOR, valueExtractor, first);
	}

	protected void encodeValueFormatter(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String valueFormatter, boolean first) throws IOException {
		encodeString(responseWriter, DatePickerAlloy.VALUE_FORMATTER, valueFormatter, first);
	}

	protected void encodeZIndex(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Object zIndex, boolean first) throws IOException {
		encodeObject(responseWriter, DatePickerAlloy.Z_INDEX, zIndex, first);
	}
}
//J+
