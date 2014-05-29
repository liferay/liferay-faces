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

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public interface PickDateAlloy {

	// Public Constants
	public static final String ACTIVE_INPUT = "activeInput";
	public static final String AUTO_HIDE = "autoHide";
	public static final String CALENDAR = "calendar";
	public static final String CONTAINER = "container";
	public static final String CONTENT = "content";
	public static final String DATE_PATTERN = "datePattern";
	public static final String DATE_SEPARATOR = "dateSeparator";
	public static final String FOR = "for";
	public static final String LOCALE = "locale";
	public static final String MASK = "mask";
	public static final String MAXIMUM_DATE = "maximumDate";
	public static final String MINIMUM_DATE = "minimumDate";
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

	public String getFor();

	public void setFor(String for_);

	public Object getLocale();

	public void setLocale(Object locale);

	public String getMask();

	public void setMask(String mask);

	public Object getMaximumDate();

	public void setMaximumDate(Object maximumDate);

	public Object getMinimumDate();

	public void setMinimumDate(Object minimumDate);

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
