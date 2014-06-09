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

import javax.annotation.Generated;
import javax.faces.component.html.HtmlInputText;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputSourceCodeBase extends HtmlInputText implements Styleable, ClientComponent {

	// Public Constants
	public static final String CLIENT_KEY = "clientKey";
	public static final String HEIGHT = "height";
	public static final String HIGHLIGHT_ACTIVE_LINE = "highlightActiveLine";
	public static final String LOCALE = "locale";
	public static final String MODE = "mode";
	public static final String READ_ONLY = "readOnly";
	public static final String SHOW_PRINT_MARGIN = "showPrintMargin";
	public static final String STYLE = "style";
	public static final String STYLE_CLASS = "styleClass";
	public static final String TAB_SIZE = "tabSize";
	public static final String USE_SOFT_TABS = "useSoftTabs";
	public static final String USE_WRAP_MODE = "useWrapMode";
	public static final String VALUE = "value";
	public static final String WIDTH = "width";

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(CLIENT_KEY, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(CLIENT_KEY, clientKey);
	}

	public String getHeight() {
		return (String) getStateHelper().eval(HEIGHT, null);
	}

	public void setHeight(String height) {
		getStateHelper().put(HEIGHT, height);
	}

	public Boolean isHighlightActiveLine() {
		return (Boolean) getStateHelper().eval(HIGHLIGHT_ACTIVE_LINE, null);
	}

	public void setHighlightActiveLine(Boolean highlightActiveLine) {
		getStateHelper().put(HIGHLIGHT_ACTIVE_LINE, highlightActiveLine);
	}

	public String getLocale() {
		return (String) getStateHelper().eval(LOCALE, null);
	}

	public void setLocale(String locale) {
		getStateHelper().put(LOCALE, locale);
	}

	public String getMode() {
		return (String) getStateHelper().eval(MODE, null);
	}

	public void setMode(String mode) {
		getStateHelper().put(MODE, mode);
	}

	public Boolean isReadOnly() {
		return (Boolean) getStateHelper().eval(READ_ONLY, null);
	}

	public void setReadOnly(Boolean readOnly) {
		getStateHelper().put(READ_ONLY, readOnly);
	}

	public Boolean isShowPrintMargin() {
		return (Boolean) getStateHelper().eval(SHOW_PRINT_MARGIN, null);
	}

	public void setShowPrintMargin(Boolean showPrintMargin) {
		getStateHelper().put(SHOW_PRINT_MARGIN, showPrintMargin);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(STYLE, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(STYLE, style);
	}

	@Override
	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}

	public String getTabSize() {
		return (String) getStateHelper().eval(TAB_SIZE, null);
	}

	public void setTabSize(String tabSize) {
		getStateHelper().put(TAB_SIZE, tabSize);
	}

	public Boolean isUseSoftTabs() {
		return (Boolean) getStateHelper().eval(USE_SOFT_TABS, null);
	}

	public void setUseSoftTabs(Boolean useSoftTabs) {
		getStateHelper().put(USE_SOFT_TABS, useSoftTabs);
	}

	public Boolean isUseWrapMode() {
		return (Boolean) getStateHelper().eval(USE_WRAP_MODE, null);
	}

	public void setUseWrapMode(Boolean useWrapMode) {
		getStateHelper().put(USE_WRAP_MODE, useWrapMode);
	}

	public String getWidth() {
		return (String) getStateHelper().eval(WIDTH, null);
	}

	public void setWidth(String width) {
		getStateHelper().put(WIDTH, width);
	}
}
//J+
