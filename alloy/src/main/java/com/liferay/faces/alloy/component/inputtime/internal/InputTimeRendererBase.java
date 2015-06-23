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
package com.liferay.faces.alloy.component.inputtime.internal;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.inputtime.InputTime;

import com.liferay.faces.alloy.component.inputdatetime.internal.InputDateTimeRenderer;


/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputTimeRendererBase extends InputDateTimeRenderer {

	// Protected Constants
	protected static final String ACTIVATE_FIRST_ITEM = "activateFirstItem";
	protected static final String CIRCULAR = "circular";
	protected static final String FILTER_TYPE = "filterType";
	protected static final String HEIGHT = "height";
	protected static final String HIGHLIGHTER_TYPE = "highlighterType";
	protected static final String MASK = "mask";
	protected static final String MAX_RESULTS = "maxResults";
	protected static final String MAX_TIME = "maxTime";
	protected static final String MIN_TIME = "minTime";
	protected static final String NATIVE_WHEN_MOBILE = "nativeWhenMobile";
	protected static final String QUERY_DELAY = "queryDelay";
	protected static final String SCROLL_INTO_VIEW = "scrollIntoView";
	protected static final String STEP = "step";
	protected static final String STYLE_CLASS = "styleClass";

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "TimePicker";
	private static final String ALLOY_MODULE_NAME = "aui-timepicker";

	// Modules
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		InputTime inputTime = (InputTime) uiComponent;
		boolean first = true;

		String pattern = inputTime.getPattern();

		if (pattern != null) {

			encodeMask(responseWriter, inputTime, pattern, first);
			first = false;
		}

		encodeHiddenAttributes(facesContext, responseWriter, inputTime, first);
	}

	@Override
	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent) {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return MODULES;
	}

	protected void encodeMask(ResponseWriter responseWriter, InputTime inputTime, String pattern, boolean first) throws IOException {
		encodeString(responseWriter, MASK, pattern, first);
	}

	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, InputTime inputTime, boolean first) throws IOException {
		// no-op
	}

	@Override
	public String getDelegateComponentFamily() {
		return InputTime.COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return "javax.faces.Text";
	}
}
//J+
