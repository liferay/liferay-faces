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
package com.liferay.faces.alloy.component.inputsourcecode;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.renderkit.DelegatingAlloyRendererBase;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputSourceCodeRendererBase extends DelegatingAlloyRendererBase {

	// Protected Constants
	protected static final String CLIENT_KEY = "clientKey";
	protected static final String HEIGHT = "height";
	protected static final String HIGHLIGHT_ACTIVE_LINE = "highlightActiveLine";
	protected static final String LOCALE = "locale";
	protected static final String MODE = "mode";
	protected static final String READ_ONLY = "readOnly";
	protected static final String SHOW_PRINT_MARGIN = "showPrintMargin";
	protected static final String TAB_SIZE = "tabSize";
	protected static final String USE_SOFT_TABS = "useSoftTabs";
	protected static final String USE_WRAP_MODE = "useWrapMode";
	protected static final String VALUE = "value";
	protected static final String WIDTH = "width";

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "AceEditor";
	private static final String ALLOY_MODULE_NAME = "aui-ace-editor";

	// Modules
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		InputSourceCode inputSourceCode = (InputSourceCode) uiComponent;
		boolean first = true;

		String height = inputSourceCode.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, inputSourceCode, height, first);
			first = false;
		}

		Boolean highlightActiveLine = inputSourceCode.isHighlightActiveLine();

		if (highlightActiveLine != null) {

			encodeHighlightActiveLine(responseWriter, inputSourceCode, highlightActiveLine, first);
			first = false;
		}

		String locale = inputSourceCode.getLocale();

		if (locale != null) {

			encodeLocale(responseWriter, inputSourceCode, locale, first);
			first = false;
		}

		String mode = inputSourceCode.getMode();

		if (mode != null) {

			encodeMode(responseWriter, inputSourceCode, mode, first);
			first = false;
		}

		Boolean readOnly = inputSourceCode.isReadOnly();

		if (readOnly != null) {

			encodeReadOnly(responseWriter, inputSourceCode, readOnly, first);
			first = false;
		}

		Boolean showPrintMargin = inputSourceCode.isShowPrintMargin();

		if (showPrintMargin != null) {

			encodeShowPrintMargin(responseWriter, inputSourceCode, showPrintMargin, first);
			first = false;
		}

		String tabSize = inputSourceCode.getTabSize();

		if (tabSize != null) {

			encodeTabSize(responseWriter, inputSourceCode, tabSize, first);
			first = false;
		}

		Boolean useSoftTabs = inputSourceCode.isUseSoftTabs();

		if (useSoftTabs != null) {

			encodeUseSoftTabs(responseWriter, inputSourceCode, useSoftTabs, first);
			first = false;
		}

		Boolean useWrapMode = inputSourceCode.isUseWrapMode();

		if (useWrapMode != null) {

			encodeUseWrapMode(responseWriter, inputSourceCode, useWrapMode, first);
			first = false;
		}

		Object value = inputSourceCode.getValue();

		if (value != null) {

			encodeValue(responseWriter, inputSourceCode, value, first);
			first = false;
		}

		String width = inputSourceCode.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, inputSourceCode, width, first);
			first = false;
		}

		encodeHiddenAttributes(facesContext, responseWriter, inputSourceCode, first);
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules(UIComponent uiComponent) {
		return MODULES;
	}

	protected void encodeHeight(ResponseWriter responseWriter, InputSourceCode inputSourceCode, String height, boolean first) throws IOException {
		encodeString(responseWriter, HEIGHT, height, first);
	}

	protected void encodeHighlightActiveLine(ResponseWriter responseWriter, InputSourceCode inputSourceCode, Boolean highlightActiveLine, boolean first) throws IOException {
		encodeBoolean(responseWriter, HIGHLIGHT_ACTIVE_LINE, highlightActiveLine, first);
	}

	protected void encodeLocale(ResponseWriter responseWriter, InputSourceCode inputSourceCode, String locale, boolean first) throws IOException {
		encodeString(responseWriter, LOCALE, locale, first);
	}

	protected void encodeMode(ResponseWriter responseWriter, InputSourceCode inputSourceCode, String mode, boolean first) throws IOException {
		encodeString(responseWriter, MODE, mode, first);
	}

	protected void encodeReadOnly(ResponseWriter responseWriter, InputSourceCode inputSourceCode, Boolean readOnly, boolean first) throws IOException {
		encodeBoolean(responseWriter, READ_ONLY, readOnly, first);
	}

	protected void encodeShowPrintMargin(ResponseWriter responseWriter, InputSourceCode inputSourceCode, Boolean showPrintMargin, boolean first) throws IOException {
		encodeBoolean(responseWriter, SHOW_PRINT_MARGIN, showPrintMargin, first);
	}

	protected void encodeTabSize(ResponseWriter responseWriter, InputSourceCode inputSourceCode, String tabSize, boolean first) throws IOException {
		encodeString(responseWriter, TAB_SIZE, tabSize, first);
	}

	protected void encodeUseSoftTabs(ResponseWriter responseWriter, InputSourceCode inputSourceCode, Boolean useSoftTabs, boolean first) throws IOException {
		encodeBoolean(responseWriter, USE_SOFT_TABS, useSoftTabs, first);
	}

	protected void encodeUseWrapMode(ResponseWriter responseWriter, InputSourceCode inputSourceCode, Boolean useWrapMode, boolean first) throws IOException {
		encodeBoolean(responseWriter, USE_WRAP_MODE, useWrapMode, first);
	}

	protected void encodeValue(ResponseWriter responseWriter, InputSourceCode inputSourceCode, Object value, boolean first) throws IOException {
		encodeString(responseWriter, VALUE, value, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, InputSourceCode inputSourceCode, String width, boolean first) throws IOException {
		encodeString(responseWriter, WIDTH, width, first);
	}

	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, InputSourceCode inputSourceCode, boolean first) throws IOException {
		// no-op
	}
}
//J+
