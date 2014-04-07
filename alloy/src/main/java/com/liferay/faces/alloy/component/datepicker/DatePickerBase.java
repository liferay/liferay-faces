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
import javax.faces.component.UIPanel;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class DatePickerBase extends UIPanel implements Styleable, ClientComponent, DatePickerAlloy {

	@Override
	public Object getActiveInput() {
		return (Object) getStateHelper().eval(ACTIVE_INPUT, null);
	}

	@Override
	public void setActiveInput(Object activeInput) {
		getStateHelper().put(ACTIVE_INPUT, activeInput);
	}

	@Override
	public String getAfterActiveInputChange() {
		return (String) getStateHelper().eval(AFTER_ACTIVE_INPUT_CHANGE, null);
	}

	@Override
	public void setAfterActiveInputChange(String afterActiveInputChange) {
		getStateHelper().put(AFTER_ACTIVE_INPUT_CHANGE, afterActiveInputChange);
	}

	@Override
	public String getAfterAutoHideChange() {
		return (String) getStateHelper().eval(AFTER_AUTO_HIDE_CHANGE, null);
	}

	@Override
	public void setAfterAutoHideChange(String afterAutoHideChange) {
		getStateHelper().put(AFTER_AUTO_HIDE_CHANGE, afterAutoHideChange);
	}

	@Override
	public String getAfterCalendarChange() {
		return (String) getStateHelper().eval(AFTER_CALENDAR_CHANGE, null);
	}

	@Override
	public void setAfterCalendarChange(String afterCalendarChange) {
		getStateHelper().put(AFTER_CALENDAR_CHANGE, afterCalendarChange);
	}

	@Override
	public String getAfterContainerChange() {
		return (String) getStateHelper().eval(AFTER_CONTAINER_CHANGE, null);
	}

	@Override
	public void setAfterContainerChange(String afterContainerChange) {
		getStateHelper().put(AFTER_CONTAINER_CHANGE, afterContainerChange);
	}

	@Override
	public String getAfterContentChange() {
		return (String) getStateHelper().eval(AFTER_CONTENT_CHANGE, null);
	}

	@Override
	public void setAfterContentChange(String afterContentChange) {
		getStateHelper().put(AFTER_CONTENT_CHANGE, afterContentChange);
	}

	@Override
	public String getAfterDateClick() {
		return (String) getStateHelper().eval(AFTER_DATE_CLICK, null);
	}

	@Override
	public void setAfterDateClick(String afterDateClick) {
		getStateHelper().put(AFTER_DATE_CLICK, afterDateClick);
	}

	@Override
	public String getAfterDateSeparatorChange() {
		return (String) getStateHelper().eval(AFTER_DATE_SEPARATOR_CHANGE, null);
	}

	@Override
	public void setAfterDateSeparatorChange(String afterDateSeparatorChange) {
		getStateHelper().put(AFTER_DATE_SEPARATOR_CHANGE, afterDateSeparatorChange);
	}

	@Override
	public String getAfterDestroyedChange() {
		return (String) getStateHelper().eval(AFTER_DESTROYED_CHANGE, null);
	}

	@Override
	public void setAfterDestroyedChange(String afterDestroyedChange) {
		getStateHelper().put(AFTER_DESTROYED_CHANGE, afterDestroyedChange);
	}

	@Override
	public String getAfterInitializedChange() {
		return (String) getStateHelper().eval(AFTER_INITIALIZED_CHANGE, null);
	}

	@Override
	public void setAfterInitializedChange(String afterInitializedChange) {
		getStateHelper().put(AFTER_INITIALIZED_CHANGE, afterInitializedChange);
	}

	@Override
	public String getAfterMaskChange() {
		return (String) getStateHelper().eval(AFTER_MASK_CHANGE, null);
	}

	@Override
	public void setAfterMaskChange(String afterMaskChange) {
		getStateHelper().put(AFTER_MASK_CHANGE, afterMaskChange);
	}

	@Override
	public String getAfterPanesChange() {
		return (String) getStateHelper().eval(AFTER_PANES_CHANGE, null);
	}

	@Override
	public void setAfterPanesChange(String afterPanesChange) {
		getStateHelper().put(AFTER_PANES_CHANGE, afterPanesChange);
	}

	@Override
	public String getAfterPopoverChange() {
		return (String) getStateHelper().eval(AFTER_POPOVER_CHANGE, null);
	}

	@Override
	public void setAfterPopoverChange(String afterPopoverChange) {
		getStateHelper().put(AFTER_POPOVER_CHANGE, afterPopoverChange);
	}

	@Override
	public String getAfterPopoverCssClassChange() {
		return (String) getStateHelper().eval(AFTER_POPOVER_CSS_CLASS_CHANGE, null);
	}

	@Override
	public void setAfterPopoverCssClassChange(String afterPopoverCssClassChange) {
		getStateHelper().put(AFTER_POPOVER_CSS_CLASS_CHANGE, afterPopoverCssClassChange);
	}

	@Override
	public String getAfterValueExtractorChange() {
		return (String) getStateHelper().eval(AFTER_VALUE_EXTRACTOR_CHANGE, null);
	}

	@Override
	public void setAfterValueExtractorChange(String afterValueExtractorChange) {
		getStateHelper().put(AFTER_VALUE_EXTRACTOR_CHANGE, afterValueExtractorChange);
	}

	@Override
	public String getAfterValueFormatterChange() {
		return (String) getStateHelper().eval(AFTER_VALUE_FORMATTER_CHANGE, null);
	}

	@Override
	public void setAfterValueFormatterChange(String afterValueFormatterChange) {
		getStateHelper().put(AFTER_VALUE_FORMATTER_CHANGE, afterValueFormatterChange);
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
	public Boolean isDestroyed() {
		return (Boolean) getStateHelper().eval(DESTROYED, null);
	}

	@Override
	public void setDestroyed(Boolean destroyed) {
		getStateHelper().put(DESTROYED, destroyed);
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
	public Boolean isInitialized() {
		return (Boolean) getStateHelper().eval(INITIALIZED, null);
	}

	@Override
	public void setInitialized(Boolean initialized) {
		getStateHelper().put(INITIALIZED, initialized);
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
	public String getOnActiveInputChange() {
		return (String) getStateHelper().eval(ON_ACTIVE_INPUT_CHANGE, null);
	}

	@Override
	public void setOnActiveInputChange(String onActiveInputChange) {
		getStateHelper().put(ON_ACTIVE_INPUT_CHANGE, onActiveInputChange);
	}

	@Override
	public String getOnAutoHideChange() {
		return (String) getStateHelper().eval(ON_AUTO_HIDE_CHANGE, null);
	}

	@Override
	public void setOnAutoHideChange(String onAutoHideChange) {
		getStateHelper().put(ON_AUTO_HIDE_CHANGE, onAutoHideChange);
	}

	@Override
	public String getOnCalendarChange() {
		return (String) getStateHelper().eval(ON_CALENDAR_CHANGE, null);
	}

	@Override
	public void setOnCalendarChange(String onCalendarChange) {
		getStateHelper().put(ON_CALENDAR_CHANGE, onCalendarChange);
	}

	@Override
	public String getOnContainerChange() {
		return (String) getStateHelper().eval(ON_CONTAINER_CHANGE, null);
	}

	@Override
	public void setOnContainerChange(String onContainerChange) {
		getStateHelper().put(ON_CONTAINER_CHANGE, onContainerChange);
	}

	@Override
	public String getOnContentChange() {
		return (String) getStateHelper().eval(ON_CONTENT_CHANGE, null);
	}

	@Override
	public void setOnContentChange(String onContentChange) {
		getStateHelper().put(ON_CONTENT_CHANGE, onContentChange);
	}

	@Override
	public String getOnDateClick() {
		return (String) getStateHelper().eval(ON_DATE_CLICK, null);
	}

	@Override
	public void setOnDateClick(String onDateClick) {
		getStateHelper().put(ON_DATE_CLICK, onDateClick);
	}

	@Override
	public String getOnDateSeparatorChange() {
		return (String) getStateHelper().eval(ON_DATE_SEPARATOR_CHANGE, null);
	}

	@Override
	public void setOnDateSeparatorChange(String onDateSeparatorChange) {
		getStateHelper().put(ON_DATE_SEPARATOR_CHANGE, onDateSeparatorChange);
	}

	@Override
	public String getOnDestroyedChange() {
		return (String) getStateHelper().eval(ON_DESTROYED_CHANGE, null);
	}

	@Override
	public void setOnDestroyedChange(String onDestroyedChange) {
		getStateHelper().put(ON_DESTROYED_CHANGE, onDestroyedChange);
	}

	@Override
	public String getOnInitializedChange() {
		return (String) getStateHelper().eval(ON_INITIALIZED_CHANGE, null);
	}

	@Override
	public void setOnInitializedChange(String onInitializedChange) {
		getStateHelper().put(ON_INITIALIZED_CHANGE, onInitializedChange);
	}

	@Override
	public String getOnMaskChange() {
		return (String) getStateHelper().eval(ON_MASK_CHANGE, null);
	}

	@Override
	public void setOnMaskChange(String onMaskChange) {
		getStateHelper().put(ON_MASK_CHANGE, onMaskChange);
	}

	@Override
	public String getOnPanesChange() {
		return (String) getStateHelper().eval(ON_PANES_CHANGE, null);
	}

	@Override
	public void setOnPanesChange(String onPanesChange) {
		getStateHelper().put(ON_PANES_CHANGE, onPanesChange);
	}

	@Override
	public String getOnPopoverChange() {
		return (String) getStateHelper().eval(ON_POPOVER_CHANGE, null);
	}

	@Override
	public void setOnPopoverChange(String onPopoverChange) {
		getStateHelper().put(ON_POPOVER_CHANGE, onPopoverChange);
	}

	@Override
	public String getOnPopoverCssClassChange() {
		return (String) getStateHelper().eval(ON_POPOVER_CSS_CLASS_CHANGE, null);
	}

	@Override
	public void setOnPopoverCssClassChange(String onPopoverCssClassChange) {
		getStateHelper().put(ON_POPOVER_CSS_CLASS_CHANGE, onPopoverCssClassChange);
	}

	@Override
	public String getOnValueExtractorChange() {
		return (String) getStateHelper().eval(ON_VALUE_EXTRACTOR_CHANGE, null);
	}

	@Override
	public void setOnValueExtractorChange(String onValueExtractorChange) {
		getStateHelper().put(ON_VALUE_EXTRACTOR_CHANGE, onValueExtractorChange);
	}

	@Override
	public String getOnValueFormatterChange() {
		return (String) getStateHelper().eval(ON_VALUE_FORMATTER_CHANGE, null);
	}

	@Override
	public void setOnValueFormatterChange(String onValueFormatterChange) {
		getStateHelper().put(ON_VALUE_FORMATTER_CHANGE, onValueFormatterChange);
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
