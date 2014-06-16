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

	// Protected Constants
	protected static final String AUTO_HIDE = "autoHide";
	protected static final String CLIENT_KEY = "clientKey";
	protected static final String DATE_SEPARATOR = "dateSeparator";
	protected static final String LOCALE = "locale";
	protected static final String MASK = "mask";
	protected static final String MAXIMUM_DATE = "maximumDate";
	protected static final String MINIMUM_DATE = "minimumDate";
	protected static final String PANES = "panes";
	protected static final String SELECTION_MODE = "selectionMode";
	protected static final String STYLE = "style";
	protected static final String STYLE_CLASS = "styleClass";
	protected static final String TRIGGER = "trigger";
	protected static final String Z_INDEX = "zIndex";

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "DatePicker";
	private static final String ALLOY_MODULE_NAME = "aui-datepicker";

	// Modules
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		PickDate pickDate = (PickDate) uiComponent;
		boolean first = true;

		Boolean autoHide = pickDate.isAutoHide();

		if (autoHide != null) {

			encodeAutoHide(responseWriter, pickDate, autoHide, first);
			first = false;
		}

		String datePattern = pickDate.getDatePattern();

		if (datePattern != null) {

			encodeMask(responseWriter, pickDate, datePattern, first);
			first = false;
		}

		String dateSeparator = pickDate.getDateSeparator();

		if (dateSeparator != null) {

			encodeDateSeparator(responseWriter, pickDate, dateSeparator, first);
			first = false;
		}

		String for_ = pickDate.getFor();

		if (for_ != null) {

			encodeTrigger(responseWriter, pickDate, for_, first);
			first = false;
		}

		Integer panes = pickDate.getPanes();

		if (panes != null) {

			encodePanes(responseWriter, pickDate, panes, first);
			first = false;
		}

		encodeHiddenAttributes(responseWriter, pickDate, first);
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeAutoHide(ResponseWriter responseWriter, PickDate pickDate, Boolean autoHide, boolean first) throws IOException {
		encodeBoolean(responseWriter, AUTO_HIDE, autoHide, first);
	}

	protected void encodeMask(ResponseWriter responseWriter, PickDate pickDate, String datePattern, boolean first) throws IOException {
		encodeString(responseWriter, MASK, datePattern, first);
	}

	protected void encodeDateSeparator(ResponseWriter responseWriter, PickDate pickDate, String dateSeparator, boolean first) throws IOException {
		encodeString(responseWriter, DATE_SEPARATOR, dateSeparator, first);
	}

	protected void encodeTrigger(ResponseWriter responseWriter, PickDate pickDate, String for_, boolean first) throws IOException {
		encodeString(responseWriter, TRIGGER, for_, first);
	}

	protected void encodePanes(ResponseWriter responseWriter, PickDate pickDate, Integer panes, boolean first) throws IOException {
		encodeInteger(responseWriter, PANES, panes, first);
	}

	protected void encodeHiddenAttributes(ResponseWriter responseWriter, PickDate pickDate, boolean first) throws IOException {
		// no-op
	}
}
//J+
