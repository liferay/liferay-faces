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

		Boolean destroyed = datePickerAlloy.isDestroyed();

		if (destroyed != null) {

			encodeDestroyed(responseWriter, datePickerAlloy, destroyed, first);
			first = false;
		}

		String for_ = datePickerAlloy.getFor();

		if (for_ != null) {

			encodeFor(responseWriter, datePickerAlloy, for_, first);
			first = false;
		}

		Boolean initialized = datePickerAlloy.isInitialized();

		if (initialized != null) {

			encodeInitialized(responseWriter, datePickerAlloy, initialized, first);
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

		// Begin encoding "after" object
		encodeObject(responseWriter, AlloyConstants.AFTER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String afterActiveInputChange = datePickerAlloy.getAfterActiveInputChange();

		if (afterActiveInputChange != null) {

			encodeAfterActiveInputChange(responseWriter, datePickerAlloy, afterActiveInputChange, first);
			first = false;
		}

		String afterAutoHideChange = datePickerAlloy.getAfterAutoHideChange();

		if (afterAutoHideChange != null) {

			encodeAfterAutoHideChange(responseWriter, datePickerAlloy, afterAutoHideChange, first);
			first = false;
		}

		String afterCalendarChange = datePickerAlloy.getAfterCalendarChange();

		if (afterCalendarChange != null) {

			encodeAfterCalendarChange(responseWriter, datePickerAlloy, afterCalendarChange, first);
			first = false;
		}

		String afterContainerChange = datePickerAlloy.getAfterContainerChange();

		if (afterContainerChange != null) {

			encodeAfterContainerChange(responseWriter, datePickerAlloy, afterContainerChange, first);
			first = false;
		}

		String afterContentChange = datePickerAlloy.getAfterContentChange();

		if (afterContentChange != null) {

			encodeAfterContentChange(responseWriter, datePickerAlloy, afterContentChange, first);
			first = false;
		}

		String afterDateSeparatorChange = datePickerAlloy.getAfterDateSeparatorChange();

		if (afterDateSeparatorChange != null) {

			encodeAfterDateSeparatorChange(responseWriter, datePickerAlloy, afterDateSeparatorChange, first);
			first = false;
		}

		String afterDestroyedChange = datePickerAlloy.getAfterDestroyedChange();

		if (afterDestroyedChange != null) {

			encodeAfterDestroyedChange(responseWriter, datePickerAlloy, afterDestroyedChange, first);
			first = false;
		}

		String afterInitializedChange = datePickerAlloy.getAfterInitializedChange();

		if (afterInitializedChange != null) {

			encodeAfterInitializedChange(responseWriter, datePickerAlloy, afterInitializedChange, first);
			first = false;
		}

		String afterMaskChange = datePickerAlloy.getAfterMaskChange();

		if (afterMaskChange != null) {

			encodeAfterMaskChange(responseWriter, datePickerAlloy, afterMaskChange, first);
			first = false;
		}

		String afterPanesChange = datePickerAlloy.getAfterPanesChange();

		if (afterPanesChange != null) {

			encodeAfterPanesChange(responseWriter, datePickerAlloy, afterPanesChange, first);
			first = false;
		}

		String afterPopoverChange = datePickerAlloy.getAfterPopoverChange();

		if (afterPopoverChange != null) {

			encodeAfterPopoverChange(responseWriter, datePickerAlloy, afterPopoverChange, first);
			first = false;
		}

		String afterPopoverCssClassChange = datePickerAlloy.getAfterPopoverCssClassChange();

		if (afterPopoverCssClassChange != null) {

			encodeAfterPopoverCssClassChange(responseWriter, datePickerAlloy, afterPopoverCssClassChange, first);
			first = false;
		}

		String afterValueExtractorChange = datePickerAlloy.getAfterValueExtractorChange();

		if (afterValueExtractorChange != null) {

			encodeAfterValueExtractorChange(responseWriter, datePickerAlloy, afterValueExtractorChange, first);
			first = false;
		}

		String afterValueFormatterChange = datePickerAlloy.getAfterValueFormatterChange();

		if (afterValueFormatterChange != null) {

			encodeAfterValueFormatterChange(responseWriter, datePickerAlloy, afterValueFormatterChange, first);
			first = false;
		}

		// End encoding "after" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

		// Begin encoding "on" object
		first = false;
		encodeObject(responseWriter, AlloyConstants.ON, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		first = true;

		String onActiveInputChange = datePickerAlloy.getOnActiveInputChange();

		if (onActiveInputChange != null) {

			encodeOnActiveInputChange(responseWriter, datePickerAlloy, onActiveInputChange, first);
			first = false;
		}

		String onAutoHideChange = datePickerAlloy.getOnAutoHideChange();

		if (onAutoHideChange != null) {

			encodeOnAutoHideChange(responseWriter, datePickerAlloy, onAutoHideChange, first);
			first = false;
		}

		String onCalendarChange = datePickerAlloy.getOnCalendarChange();

		if (onCalendarChange != null) {

			encodeOnCalendarChange(responseWriter, datePickerAlloy, onCalendarChange, first);
			first = false;
		}

		String onContainerChange = datePickerAlloy.getOnContainerChange();

		if (onContainerChange != null) {

			encodeOnContainerChange(responseWriter, datePickerAlloy, onContainerChange, first);
			first = false;
		}

		String onContentChange = datePickerAlloy.getOnContentChange();

		if (onContentChange != null) {

			encodeOnContentChange(responseWriter, datePickerAlloy, onContentChange, first);
			first = false;
		}

		String onDateSeparatorChange = datePickerAlloy.getOnDateSeparatorChange();

		if (onDateSeparatorChange != null) {

			encodeOnDateSeparatorChange(responseWriter, datePickerAlloy, onDateSeparatorChange, first);
			first = false;
		}

		String onDestroyedChange = datePickerAlloy.getOnDestroyedChange();

		if (onDestroyedChange != null) {

			encodeOnDestroyedChange(responseWriter, datePickerAlloy, onDestroyedChange, first);
			first = false;
		}

		String onInitializedChange = datePickerAlloy.getOnInitializedChange();

		if (onInitializedChange != null) {

			encodeOnInitializedChange(responseWriter, datePickerAlloy, onInitializedChange, first);
			first = false;
		}

		String onMaskChange = datePickerAlloy.getOnMaskChange();

		if (onMaskChange != null) {

			encodeOnMaskChange(responseWriter, datePickerAlloy, onMaskChange, first);
			first = false;
		}

		String onPanesChange = datePickerAlloy.getOnPanesChange();

		if (onPanesChange != null) {

			encodeOnPanesChange(responseWriter, datePickerAlloy, onPanesChange, first);
			first = false;
		}

		String onPopoverChange = datePickerAlloy.getOnPopoverChange();

		if (onPopoverChange != null) {

			encodeOnPopoverChange(responseWriter, datePickerAlloy, onPopoverChange, first);
			first = false;
		}

		String onPopoverCssClassChange = datePickerAlloy.getOnPopoverCssClassChange();

		if (onPopoverCssClassChange != null) {

			encodeOnPopoverCssClassChange(responseWriter, datePickerAlloy, onPopoverCssClassChange, first);
			first = false;
		}

		String onValueExtractorChange = datePickerAlloy.getOnValueExtractorChange();

		if (onValueExtractorChange != null) {

			encodeOnValueExtractorChange(responseWriter, datePickerAlloy, onValueExtractorChange, first);
			first = false;
		}

		String onValueFormatterChange = datePickerAlloy.getOnValueFormatterChange();

		if (onValueFormatterChange != null) {

			encodeOnValueFormatterChange(responseWriter, datePickerAlloy, onValueFormatterChange, first);
			first = false;
		}

		// End encoding "on" object
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
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

	protected void encodeAfterActiveInputChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterActiveInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ACTIVE_INPUT_CHANGE, afterActiveInputChange, first);
	}

	protected void encodeAfterAutoHideChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterAutoHideChange, boolean first) throws IOException {
		encodeEvent(responseWriter, AUTO_HIDE_CHANGE, afterAutoHideChange, first);
	}

	protected void encodeAfterCalendarChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterCalendarChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CALENDAR_CHANGE, afterCalendarChange, first);
	}

	protected void encodeAfterContainerChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterContainerChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTAINER_CHANGE, afterContainerChange, first);
	}

	protected void encodeAfterContentChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterContentChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_CHANGE, afterContentChange, first);
	}

	protected void encodeAfterDateSeparatorChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterDateSeparatorChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DATE_SEPARATOR_CHANGE, afterDateSeparatorChange, first);
	}

	protected void encodeAfterDestroyedChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, afterDestroyedChange, first);
	}

	protected void encodeAfterInitializedChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, afterInitializedChange, first);
	}

	protected void encodeAfterMaskChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterMaskChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MASK_CHANGE, afterMaskChange, first);
	}

	protected void encodeAfterPanesChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterPanesChange, boolean first) throws IOException {
		encodeEvent(responseWriter, PANES_CHANGE, afterPanesChange, first);
	}

	protected void encodeAfterPopoverChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterPopoverChange, boolean first) throws IOException {
		encodeEvent(responseWriter, POPOVER_CHANGE, afterPopoverChange, first);
	}

	protected void encodeAfterPopoverCssClassChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterPopoverCssClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, POPOVER_CSS_CLASS_CHANGE, afterPopoverCssClassChange, first);
	}

	protected void encodeAfterValueExtractorChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterValueExtractorChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_EXTRACTOR_CHANGE, afterValueExtractorChange, first);
	}

	protected void encodeAfterValueFormatterChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String afterValueFormatterChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_FORMATTER_CHANGE, afterValueFormatterChange, first);
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

	protected void encodeDestroyed(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Boolean destroyed, boolean first) throws IOException {
		encodeBoolean(responseWriter, DatePickerAlloy.DESTROYED, destroyed, first);
	}

	protected void encodeFor(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String for_, boolean first) throws IOException {
		encodeString(responseWriter, DatePickerAlloy.FOR, for_, first);
	}

	protected void encodeInitialized(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, Boolean initialized, boolean first) throws IOException {
		encodeBoolean(responseWriter, DatePickerAlloy.INITIALIZED, initialized, first);
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

	protected void encodeOnActiveInputChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onActiveInputChange, boolean first) throws IOException {
		encodeEvent(responseWriter, ACTIVE_INPUT_CHANGE, onActiveInputChange, first);
	}

	protected void encodeOnAutoHideChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onAutoHideChange, boolean first) throws IOException {
		encodeEvent(responseWriter, AUTO_HIDE_CHANGE, onAutoHideChange, first);
	}

	protected void encodeOnCalendarChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onCalendarChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CALENDAR_CHANGE, onCalendarChange, first);
	}

	protected void encodeOnContainerChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onContainerChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTAINER_CHANGE, onContainerChange, first);
	}

	protected void encodeOnContentChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onContentChange, boolean first) throws IOException {
		encodeEvent(responseWriter, CONTENT_CHANGE, onContentChange, first);
	}

	protected void encodeOnDateSeparatorChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onDateSeparatorChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DATE_SEPARATOR_CHANGE, onDateSeparatorChange, first);
	}

	protected void encodeOnDestroyedChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onDestroyedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, DESTROYED_CHANGE, onDestroyedChange, first);
	}

	protected void encodeOnInitializedChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onInitializedChange, boolean first) throws IOException {
		encodeEvent(responseWriter, INITIALIZED_CHANGE, onInitializedChange, first);
	}

	protected void encodeOnMaskChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onMaskChange, boolean first) throws IOException {
		encodeEvent(responseWriter, MASK_CHANGE, onMaskChange, first);
	}

	protected void encodeOnPanesChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onPanesChange, boolean first) throws IOException {
		encodeEvent(responseWriter, PANES_CHANGE, onPanesChange, first);
	}

	protected void encodeOnPopoverChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onPopoverChange, boolean first) throws IOException {
		encodeEvent(responseWriter, POPOVER_CHANGE, onPopoverChange, first);
	}

	protected void encodeOnPopoverCssClassChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onPopoverCssClassChange, boolean first) throws IOException {
		encodeEvent(responseWriter, POPOVER_CSS_CLASS_CHANGE, onPopoverCssClassChange, first);
	}

	protected void encodeOnValueExtractorChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onValueExtractorChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_EXTRACTOR_CHANGE, onValueExtractorChange, first);
	}

	protected void encodeOnValueFormatterChange(ResponseWriter responseWriter, DatePickerAlloy datePickerAlloy, String onValueFormatterChange, boolean first) throws IOException {
		encodeEvent(responseWriter, VALUE_FORMATTER_CHANGE, onValueFormatterChange, first);
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
