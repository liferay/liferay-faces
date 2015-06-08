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
package com.liferay.faces.alloy.component.outputremainingchars.internal;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.outputremainingchars.OutputRemainingChars;

import com.liferay.faces.alloy.render.internal.DelegatingAlloyRendererBase;


/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class OutputRemainingCharsRendererBase extends DelegatingAlloyRendererBase {

	// Protected Constants
	protected static final String CLIENT_KEY = "clientKey";
	protected static final String INPUT = "input";
	protected static final String MAX_LENGTH = "maxLength";
	protected static final String ONCE_MAXLENGTH_REACHED = "onceMaxlengthReached";
	protected static final String ON_MAXLENGTH_REACHED = "onMaxlengthReached";
	protected static final String STYLE_CLASS = "styleClass";

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "CharCounter";
	private static final String ALLOY_MODULE_NAME = "aui-char-counter";

	// Modules
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		OutputRemainingChars outputRemainingChars = (OutputRemainingChars) uiComponent;
		boolean first = true;

		String for_ = outputRemainingChars.getFor();

		if (for_ != null) {

			encodeInput(responseWriter, outputRemainingChars, for_, first);
			first = false;
		}

		Integer maxLength = outputRemainingChars.getMaxLength();

		if (maxLength != null) {

			encodeMaxLength(responseWriter, outputRemainingChars, maxLength, first);
			first = false;
		}

		encodeHiddenAttributes(facesContext, responseWriter, outputRemainingChars, first);
	}

	@Override
	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent) {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return MODULES;
	}

	protected void encodeInput(ResponseWriter responseWriter, OutputRemainingChars outputRemainingChars, String for_, boolean first) throws IOException {
		encodeClientId(responseWriter, INPUT, for_, outputRemainingChars, first);
	}

	protected void encodeMaxLength(ResponseWriter responseWriter, OutputRemainingChars outputRemainingChars, Integer maxLength, boolean first) throws IOException {
		encodeInteger(responseWriter, MAX_LENGTH, maxLength, first);
	}

	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, OutputRemainingChars outputRemainingChars, boolean first) throws IOException {
		// no-op
	}

	@Override
	public String getDelegateComponentFamily() {
		return OutputRemainingChars.COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return "javax.faces.Text";
	}
}
//J+
