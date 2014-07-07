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
package com.liferay.faces.alloy.component.inputdate;

import java.io.IOException;

import javax.faces.application.Application;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.pickdate.PickDate;
import com.liferay.faces.alloy.component.pickdate.PickDateUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = InputDate.COMPONENT_FAMILY, rendererType = InputDate.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "pickdate.js")
	}
)
//J+
public class InputDateRenderer extends InputDateRendererBase {

	// Private Constants
	// This is a javascript function that sets the value of the input to the new date and fires a change event
	// on the input. It is used when showOn="button".
	private static final String BUTTON_ON_DATE_CLICK_TEMPLATE = "function(event){if(this._canBeSelected(event.date)){" +
		"var input=A.one('{0}');input.set('value',A.Date.format(event.date,{format:'{1}'}));" +
		"input.simulate('change');}}";
	private static final String TOKEN_0 = "{0}";
	private static final String TOKEN_1 = "{1}";

	@Override
	protected void encodePicker(FacesContext facesContext, UIComponent uiComponent, Application application,
		String trigger, String escapedClientId) throws IOException {

		InputDate inputDate = (InputDate) uiComponent;

		// Create a pickDate and pass attributes through to it.
		PickDate pickDate = (PickDate) application.createComponent(PickDate.COMPONENT_TYPE);
		String datePattern = inputDate.getDatePattern();
		pickDate.setAutoHide(inputDate.isAutoHide());
		pickDate.setDatePattern(datePattern);
		pickDate.setFor(trigger);
		pickDate.setLocale(inputDate.getLocale(facesContext));
		pickDate.setMaximumDate(inputDate.getMaximumDate());
		pickDate.setMinimumDate(inputDate.getMinimumDate());
		pickDate.setPanes(inputDate.getPanes());
		pickDate.setStyleClass(inputDate.getStyleClass());
		pickDate.setTimeZone(inputDate.getTimeZone());
		pickDate.setzIndex(inputDate.getzIndex());

		String showOn = inputDate.getShowOn();

		if (showOn.equals(BUTTON)) {

			// Each time a date is clicked, the input must be updated via javascript because the pickDate is not
			// attached to the input.
			String node = StringPool.POUND + escapedClientId;
			String buttonOnDateClick = BUTTON_ON_DATE_CLICK_TEMPLATE.replace(TOKEN_0, node);
			String mask = PickDateUtil.getMaskFromDatePattern(datePattern);
			buttonOnDateClick = buttonOnDateClick.replace(TOKEN_1, mask);
			pickDate.setOnDateClick(buttonOnDateClick);
		}

		// Invoke the date picker's renderer so that it renders itself.
		pickDate.encodeAll(facesContext);
	}
}
