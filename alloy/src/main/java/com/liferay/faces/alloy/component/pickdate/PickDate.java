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

import java.util.TimeZone;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author  Kyle Stiemann
 */
@FacesComponent(value = PickDate.COMPONENT_TYPE)
public class PickDate extends PickDateBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.alloy.component.pickdate";
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.pickdate.PickDate";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.pickdate.PickDateRenderer";
	public static final String STYLE_CLASS_NAME = "datepicker-popover alloy-pick-date";

	// Private Constants
	private static final String ON_DATE_CLICK = "onDateClick";
	private static final String GREENWICH = "Greenwich";

	public PickDate() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getDatePattern() {

		String datePattern = super.getDatePattern();

		if (datePattern == null) {

			// Provide a default datePattern based on the locale.
			Object locale = getLocale();
			datePattern = PickDateUtil.getDefaultDatePattern(locale);
		}

		return datePattern;
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	@Override
	public Object getLocale() {
		return getLocale(FacesContext.getCurrentInstance());
	}

	public Object getLocale(FacesContext facesContext) {
		return PickDateUtil.determineLocale(facesContext, super.getLocale());
	}

	public String getOnDateClick() {
		return (String) getStateHelper().eval(ON_DATE_CLICK, null);
	}

	public void setOnDateClick(String onDateClick) {
		getStateHelper().put(ON_DATE_CLICK, onDateClick);
	}

	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PickDatePropertyKeys.styleClass, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}

	@Override
	public Object getTimeZone() {
		Object timeZone = super.getTimeZone();

		if (timeZone == null) {
			timeZone = TimeZone.getTimeZone(GREENWICH);
		}

		return timeZone;
	}
}
