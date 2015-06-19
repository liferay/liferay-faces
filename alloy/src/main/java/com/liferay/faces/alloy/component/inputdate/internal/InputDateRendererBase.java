/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.alloy.component.inputdate.internal;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.inputdate.InputDate;

import com.liferay.faces.alloy.component.inputdatetime.internal.InputDateTimeRenderer;


/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputDateRendererBase extends InputDateTimeRenderer {

	// Protected Constants
	protected static final String DATE_SELECT_LISTENER = "dateSelectListener";
	protected static final String MASK = "mask";
	protected static final String MAX_DATE = "maxDate";
	protected static final String MIN_DATE = "minDate";
	protected static final String NATIVE_WHEN_MOBILE = "nativeWhenMobile";
	protected static final String PANES = "panes";
	protected static final String STYLE_CLASS = "styleClass";

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "DatePicker";
	private static final String ALLOY_MODULE_NAME = "aui-datepicker";

	// Modules
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		InputDate inputDate = (InputDate) uiComponent;
		boolean first = true;

		Integer panes = inputDate.getPanes();

		if (panes != null) {

			encodePanes(responseWriter, inputDate, panes, first);
			first = false;
		}

		String pattern = inputDate.getPattern();

		if (pattern != null) {

			encodeMask(responseWriter, inputDate, pattern, first);
			first = false;
		}

		encodeHiddenAttributes(facesContext, responseWriter, inputDate, first);
	}

	@Override
	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent) {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return MODULES;
	}

	protected void encodePanes(ResponseWriter responseWriter, InputDate inputDate, Integer panes, boolean first) throws IOException {
		encodeInteger(responseWriter, PANES, panes, first);
	}

	protected void encodeMask(ResponseWriter responseWriter, InputDate inputDate, String pattern, boolean first) throws IOException {
		encodeString(responseWriter, MASK, pattern, first);
	}

	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, InputDate inputDate, boolean first) throws IOException {
		// no-op
	}

	@Override
	public String getDelegateComponentFamily() {
		return InputDate.COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return "javax.faces.Text";
	}
}
//J+
