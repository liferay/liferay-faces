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

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public interface DatePickerAlloy {

	// Public Constants
	public static final String ACTIVE_INPUT = "activeInput";
	public static final String AFTER_ACTIVE_INPUT_CHANGE = "afterActiveInputChange";
	public static final String AFTER_AUTO_HIDE_CHANGE = "afterAutoHideChange";
	public static final String AFTER_CALENDAR_CHANGE = "afterCalendarChange";
	public static final String AFTER_CONTAINER_CHANGE = "afterContainerChange";
	public static final String AFTER_CONTENT_CHANGE = "afterContentChange";
	public static final String AFTER_DATE_CLICK = "afterDateClick";
	public static final String AFTER_DATE_SEPARATOR_CHANGE = "afterDateSeparatorChange";
	public static final String AFTER_DESTROYED_CHANGE = "afterDestroyedChange";
	public static final String AFTER_INITIALIZED_CHANGE = "afterInitializedChange";
	public static final String AFTER_MASK_CHANGE = "afterMaskChange";
	public static final String AFTER_PANES_CHANGE = "afterPanesChange";
	public static final String AFTER_POPOVER_CHANGE = "afterPopoverChange";
	public static final String AFTER_POPOVER_CSS_CLASS_CHANGE = "afterPopoverCssClassChange";
	public static final String AFTER_VALUE_EXTRACTOR_CHANGE = "afterValueExtractorChange";
	public static final String AFTER_VALUE_FORMATTER_CHANGE = "afterValueFormatterChange";
	public static final String AUTO_HIDE = "autoHide";
	public static final String CALENDAR = "calendar";
	public static final String CONTAINER = "container";
	public static final String CONTENT = "content";
	public static final String DATE_PATTERN = "datePattern";
	public static final String DATE_SEPARATOR = "dateSeparator";
	public static final String DESTROYED = "destroyed";
	public static final String FOR = "for";
	public static final String INITIALIZED = "initialized";
	public static final String LOCALE = "locale";
	public static final String MASK = "mask";
	public static final String MAXIMUM_DATE = "maximumDate";
	public static final String MINIMUM_DATE = "minimumDate";
	public static final String ON_ACTIVE_INPUT_CHANGE = "onActiveInputChange";
	public static final String ON_AUTO_HIDE_CHANGE = "onAutoHideChange";
	public static final String ON_CALENDAR_CHANGE = "onCalendarChange";
	public static final String ON_CONTAINER_CHANGE = "onContainerChange";
	public static final String ON_CONTENT_CHANGE = "onContentChange";
	public static final String ON_DATE_CLICK = "onDateClick";
	public static final String ON_DATE_SEPARATOR_CHANGE = "onDateSeparatorChange";
	public static final String ON_DESTROYED_CHANGE = "onDestroyedChange";
	public static final String ON_INITIALIZED_CHANGE = "onInitializedChange";
	public static final String ON_MASK_CHANGE = "onMaskChange";
	public static final String ON_PANES_CHANGE = "onPanesChange";
	public static final String ON_POPOVER_CHANGE = "onPopoverChange";
	public static final String ON_POPOVER_CSS_CLASS_CHANGE = "onPopoverCssClassChange";
	public static final String ON_VALUE_EXTRACTOR_CHANGE = "onValueExtractorChange";
	public static final String ON_VALUE_FORMATTER_CHANGE = "onValueFormatterChange";
	public static final String PANES = "panes";
	public static final String POPOVER = "popover";
	public static final String POPOVER_CSS_CLASS = "popoverCssClass";
	public static final String SELECTION_MODE = "selectionMode";
	public static final String TRIGGER = "trigger";
	public static final String VALUE_EXTRACTOR = "valueExtractor";
	public static final String VALUE_FORMATTER = "valueFormatter";
	public static final String Z_INDEX = "zIndex";

	public Object getActiveInput();

	public void setActiveInput(Object activeInput);

	public String getAfterActiveInputChange();

	public void setAfterActiveInputChange(String afterActiveInputChange);

	public String getAfterAutoHideChange();

	public void setAfterAutoHideChange(String afterAutoHideChange);

	public String getAfterCalendarChange();

	public void setAfterCalendarChange(String afterCalendarChange);

	public String getAfterContainerChange();

	public void setAfterContainerChange(String afterContainerChange);

	public String getAfterContentChange();

	public void setAfterContentChange(String afterContentChange);

	public String getAfterDateClick();

	public void setAfterDateClick(String afterDateClick);

	public String getAfterDateSeparatorChange();

	public void setAfterDateSeparatorChange(String afterDateSeparatorChange);

	public String getAfterDestroyedChange();

	public void setAfterDestroyedChange(String afterDestroyedChange);

	public String getAfterInitializedChange();

	public void setAfterInitializedChange(String afterInitializedChange);

	public String getAfterMaskChange();

	public void setAfterMaskChange(String afterMaskChange);

	public String getAfterPanesChange();

	public void setAfterPanesChange(String afterPanesChange);

	public String getAfterPopoverChange();

	public void setAfterPopoverChange(String afterPopoverChange);

	public String getAfterPopoverCssClassChange();

	public void setAfterPopoverCssClassChange(String afterPopoverCssClassChange);

	public String getAfterValueExtractorChange();

	public void setAfterValueExtractorChange(String afterValueExtractorChange);

	public String getAfterValueFormatterChange();

	public void setAfterValueFormatterChange(String afterValueFormatterChange);

	public Boolean isAutoHide();

	public void setAutoHide(Boolean autoHide);

	public Object getCalendar();

	public void setCalendar(Object calendar);

	public Object getContainer();

	public void setContainer(Object container);

	public String getContent();

	public void setContent(String content);

	public String getDatePattern();

	public void setDatePattern(String datePattern);

	public String getDateSeparator();

	public void setDateSeparator(String dateSeparator);

	public Boolean isDestroyed();

	public void setDestroyed(Boolean destroyed);

	public String getFor();

	public void setFor(String for_);

	public Boolean isInitialized();

	public void setInitialized(Boolean initialized);

	public Object getLocale();

	public void setLocale(Object locale);

	public String getMask();

	public void setMask(String mask);

	public Object getMaximumDate();

	public void setMaximumDate(Object maximumDate);

	public Object getMinimumDate();

	public void setMinimumDate(Object minimumDate);

	public String getOnActiveInputChange();

	public void setOnActiveInputChange(String onActiveInputChange);

	public String getOnAutoHideChange();

	public void setOnAutoHideChange(String onAutoHideChange);

	public String getOnCalendarChange();

	public void setOnCalendarChange(String onCalendarChange);

	public String getOnContainerChange();

	public void setOnContainerChange(String onContainerChange);

	public String getOnContentChange();

	public void setOnContentChange(String onContentChange);

	public String getOnDateClick();

	public void setOnDateClick(String onDateClick);

	public String getOnDateSeparatorChange();

	public void setOnDateSeparatorChange(String onDateSeparatorChange);

	public String getOnDestroyedChange();

	public void setOnDestroyedChange(String onDestroyedChange);

	public String getOnInitializedChange();

	public void setOnInitializedChange(String onInitializedChange);

	public String getOnMaskChange();

	public void setOnMaskChange(String onMaskChange);

	public String getOnPanesChange();

	public void setOnPanesChange(String onPanesChange);

	public String getOnPopoverChange();

	public void setOnPopoverChange(String onPopoverChange);

	public String getOnPopoverCssClassChange();

	public void setOnPopoverCssClassChange(String onPopoverCssClassChange);

	public String getOnValueExtractorChange();

	public void setOnValueExtractorChange(String onValueExtractorChange);

	public String getOnValueFormatterChange();

	public void setOnValueFormatterChange(String onValueFormatterChange);

	public Object getPanes();

	public void setPanes(Object panes);

	public Object getPopover();

	public void setPopover(Object popover);

	public String getPopoverCssClass();

	public void setPopoverCssClass(String popoverCssClass);

	public String getSelectionMode();

	public void setSelectionMode(String selectionMode);

	public String getTrigger();

	public void setTrigger(String trigger);

	public String getValueExtractor();

	public void setValueExtractor(String valueExtractor);

	public String getValueFormatter();

	public void setValueFormatter(String valueFormatter);

	public Object getzIndex();

	public void setzIndex(Object zIndex);
}
//J+
