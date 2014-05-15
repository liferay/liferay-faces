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

import com.liferay.faces.alloy.component.datepicker.DatePicker;
import com.liferay.faces.alloy.component.datepicker.DatePickerUtil;
import com.liferay.faces.alloy.component.inputdatetime.InputDateTimeRendererBase;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Kyle Stiemann
 */
@FacesRenderer(componentFamily = InputDate.COMPONENT_FAMILY, rendererType = InputDate.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "datepicker/datePicker.js")
	}
)
public class InputDateRenderer extends InputDateTimeRendererBase {

	// Private Constants
	private static final String BUTTON_ON_DATE_CLICK_TEMPLATE = "if(this._canBeSelected(event.date)){" +
		"inputDateButtonOnDateClick(A.one('{0}'), A.Date.format(event.date,{format:'{1}'}));}";
	private static final String TOKEN_0 = "{0}";
	private static final String TOKEN_1 = "{1}";

	@Override
	protected void encodePicker(FacesContext facesContext, UIComponent uiComponent, Application application,
		String trigger, String escapedClientId) throws IOException {

		InputDate inputDate = (InputDate) uiComponent;

		// Create a datePicker and pass attributes through to it.
		DatePicker datePicker = (DatePicker) application.createComponent(DatePicker.COMPONENT_TYPE);

		// Because the datePicker is not in the component tree, utilizing DatePicker.setFor() with the component id
		// will not work since it uses UIComponent.findComopnent() which only works for compnents that appear in the
		// component tree. To workaround this, the datePicker's trigger must be set directly.
		String datePattern = inputDate.getDatePattern();
		datePicker.setDatePattern(datePattern);
		datePicker.setLocale(inputDate.getLocale());
		datePicker.setMaximumDate(inputDate.getMaximumDate());
		datePicker.setMinimumDate(inputDate.getMinimumDate());
		datePicker.setPanes(inputDate.getPanes());
		datePicker.setStyleClass(inputDate.getStyleClass());
		datePicker.setTrigger(trigger);
		datePicker.setzIndex(inputDate.getzIndex());

		String showOn = inputDate.getShowOn();

		if (showOn.equals(BUTTON)) {

			// Each time a date is clicked, the input must be updated via javascript because the datePicker is not
			// attached to the input.
			String node = StringPool.POUND + escapedClientId;
			String buttonOnDateClick = BUTTON_ON_DATE_CLICK_TEMPLATE.replace(TOKEN_0, node);
			String mask = DatePickerUtil.getMaskFromDatePattern(datePattern);
			buttonOnDateClick = buttonOnDateClick.replace(TOKEN_1, mask);
			datePicker.setOnDateClick(buttonOnDateClick);
		}

		// Invoke the date picker's renderer so that it renders itself.
		datePicker.encodeAll(facesContext);
	}
}
