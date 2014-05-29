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

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.renderkit.AlloyRendererBase;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PickDateRendererBase extends AlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "DatePicker";
	private static final String ALLOY_MODULE_NAME = "aui-datepicker";

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		PickDateAlloy pickDateAlloy = (PickDateAlloy) uiComponent;
		boolean first = true;

		Object activeInput = pickDateAlloy.getActiveInput();

		if (activeInput != null) {

			encodeActiveInput(responseWriter, pickDateAlloy, activeInput, first);
			first = false;
		}

		Boolean autoHide = pickDateAlloy.isAutoHide();

		if (autoHide != null) {

			encodeAutoHide(responseWriter, pickDateAlloy, autoHide, first);
			first = false;
		}

		Object calendar = pickDateAlloy.getCalendar();

		if (calendar != null) {

			encodeCalendar(responseWriter, pickDateAlloy, calendar, first);
			first = false;
		}

		Object container = pickDateAlloy.getContainer();

		if (container != null) {

			encodeContainer(responseWriter, pickDateAlloy, container, first);
			first = false;
		}

		String content = pickDateAlloy.getContent();

		if (content != null) {

			encodeContent(responseWriter, pickDateAlloy, content, first);
			first = false;
		}

		String datePattern = pickDateAlloy.getDatePattern();

		if (datePattern != null) {

			encodeDatePattern(responseWriter, pickDateAlloy, datePattern, first);
			first = false;
		}

		String dateSeparator = pickDateAlloy.getDateSeparator();

		if (dateSeparator != null) {

			encodeDateSeparator(responseWriter, pickDateAlloy, dateSeparator, first);
			first = false;
		}

		String for_ = pickDateAlloy.getFor();

		if (for_ != null) {

			encodeFor(responseWriter, pickDateAlloy, for_, first);
			first = false;
		}

		Object locale = pickDateAlloy.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, pickDateAlloy, locale, first);
			first = false;
		}

		String mask = pickDateAlloy.getMask();

		if (mask != null) {

			encodeMask(responseWriter, pickDateAlloy, mask, first);
			first = false;
		}

		Object maximumDate = pickDateAlloy.getMaximumDate();

		if (maximumDate != null) {

			encodeMaximumDate(responseWriter, pickDateAlloy, maximumDate, first);
			first = false;
		}

		Object minimumDate = pickDateAlloy.getMinimumDate();

		if (minimumDate != null) {

			encodeMinimumDate(responseWriter, pickDateAlloy, minimumDate, first);
			first = false;
		}

		Object panes = pickDateAlloy.getPanes();

		if (panes != null) {

			encodePanes(responseWriter, pickDateAlloy, panes, first);
			first = false;
		}

		Object popover = pickDateAlloy.getPopover();

		if (popover != null) {

			encodePopover(responseWriter, pickDateAlloy, popover, first);
			first = false;
		}

		String popoverCssClass = pickDateAlloy.getPopoverCssClass();

		if (popoverCssClass != null) {

			encodePopoverCssClass(responseWriter, pickDateAlloy, popoverCssClass, first);
			first = false;
		}

		String selectionMode = pickDateAlloy.getSelectionMode();

		if (selectionMode != null) {

			encodeSelectionMode(responseWriter, pickDateAlloy, selectionMode, first);
			first = false;
		}

		String trigger = pickDateAlloy.getTrigger();

		if (trigger != null) {

			encodeTrigger(responseWriter, pickDateAlloy, trigger, first);
			first = false;
		}

		String valueExtractor = pickDateAlloy.getValueExtractor();

		if (valueExtractor != null) {

			encodeValueExtractor(responseWriter, pickDateAlloy, valueExtractor, first);
			first = false;
		}

		String valueFormatter = pickDateAlloy.getValueFormatter();

		if (valueFormatter != null) {

			encodeValueFormatter(responseWriter, pickDateAlloy, valueFormatter, first);
			first = false;
		}

		Object zIndex = pickDateAlloy.getzIndex();

		if (zIndex != null) {

			encodeZIndex(responseWriter, pickDateAlloy, zIndex, first);
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

	protected void encodeActiveInput(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, Object activeInput, boolean first) throws IOException {
		encodeObject(responseWriter, PickDateAlloy.ACTIVE_INPUT, activeInput, first);
	}

	protected void encodeAutoHide(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, Boolean autoHide, boolean first) throws IOException {
		encodeBoolean(responseWriter, PickDateAlloy.AUTO_HIDE, autoHide, first);
	}

	protected void encodeCalendar(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, Object calendar, boolean first) throws IOException {
		encodeObject(responseWriter, PickDateAlloy.CALENDAR, calendar, first);
	}

	protected void encodeContainer(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, Object container, boolean first) throws IOException {
		encodeObject(responseWriter, PickDateAlloy.CONTAINER, container, first);
	}

	protected void encodeContent(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, String content, boolean first) throws IOException {
		encodeString(responseWriter, PickDateAlloy.CONTENT, content, first);
	}

	protected void encodeDatePattern(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, String datePattern, boolean first) throws IOException {
		encodeString(responseWriter, PickDateAlloy.DATE_PATTERN, datePattern, first);
	}

	protected void encodeDateSeparator(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, String dateSeparator, boolean first) throws IOException {
		encodeString(responseWriter, PickDateAlloy.DATE_SEPARATOR, dateSeparator, first);
	}

	protected void encodeFor(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, String for_, boolean first) throws IOException {
		encodeString(responseWriter, PickDateAlloy.FOR, for_, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, Object locale, boolean first) throws IOException {
		encodeObject(responseWriter, PickDateAlloy.LOCALE, locale, first);
	}

	protected void encodeMask(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, String mask, boolean first) throws IOException {
		encodeString(responseWriter, PickDateAlloy.MASK, mask, first);
	}

	protected void encodeMaximumDate(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, Object maximumDate, boolean first) throws IOException {
		encodeObject(responseWriter, PickDateAlloy.MAXIMUM_DATE, maximumDate, first);
	}

	protected void encodeMinimumDate(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, Object minimumDate, boolean first) throws IOException {
		encodeObject(responseWriter, PickDateAlloy.MINIMUM_DATE, minimumDate, first);
	}

	protected void encodePanes(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, Object panes, boolean first) throws IOException {
		encodeNumber(responseWriter, PickDateAlloy.PANES, panes, first);
	}

	protected void encodePopover(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, Object popover, boolean first) throws IOException {
		encodeObject(responseWriter, PickDateAlloy.POPOVER, popover, first);
	}

	protected void encodePopoverCssClass(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, String popoverCssClass, boolean first) throws IOException {
		encodeString(responseWriter, PickDateAlloy.POPOVER_CSS_CLASS, popoverCssClass, first);
	}

	protected void encodeSelectionMode(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, String selectionMode, boolean first) throws IOException {
		encodeString(responseWriter, PickDateAlloy.SELECTION_MODE, selectionMode, first);
	}

	protected void encodeTrigger(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, String trigger, boolean first) throws IOException {
		encodeString(responseWriter, PickDateAlloy.TRIGGER, trigger, first);
	}

	protected void encodeValueExtractor(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, String valueExtractor, boolean first) throws IOException {
		encodeString(responseWriter, PickDateAlloy.VALUE_EXTRACTOR, valueExtractor, first);
	}

	protected void encodeValueFormatter(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, String valueFormatter, boolean first) throws IOException {
		encodeString(responseWriter, PickDateAlloy.VALUE_FORMATTER, valueFormatter, first);
	}

	protected void encodeZIndex(ResponseWriter responseWriter, PickDateAlloy pickDateAlloy, Object zIndex, boolean first) throws IOException {
		encodeObject(responseWriter, PickDateAlloy.Z_INDEX, zIndex, first);
	}
}
//J+
