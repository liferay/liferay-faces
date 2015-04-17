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
package com.liferay.faces.alloy.component.inputsourcecode;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlInputText;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputSourceCodeBase extends HtmlInputText implements Styleable, ClientComponent {

	// Protected Enumerations
	protected enum InputSourceCodePropertyKeys {
		clientKey,
		height,
		highlightActiveLine,
		locale,
		maxLines,
		minLines,
		mode,
		readOnly,
		showPrintMargin,
		tabSize,
		useSoftTabs,
		useWrapMode,
		width
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(InputSourceCodePropertyKeys.clientKey, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(InputSourceCodePropertyKeys.clientKey, clientKey);
	}

	public String getHeight() {
		return (String) getStateHelper().eval(InputSourceCodePropertyKeys.height, null);
	}

	public void setHeight(String height) {
		getStateHelper().put(InputSourceCodePropertyKeys.height, height);
	}

	public Boolean getHighlightActiveLine() {
		return (Boolean) getStateHelper().eval(InputSourceCodePropertyKeys.highlightActiveLine, null);
	}

	public void setHighlightActiveLine(Boolean highlightActiveLine) {
		getStateHelper().put(InputSourceCodePropertyKeys.highlightActiveLine, highlightActiveLine);
	}

	public String getLocale() {
		return (String) getStateHelper().eval(InputSourceCodePropertyKeys.locale, null);
	}

	public void setLocale(String locale) {
		getStateHelper().put(InputSourceCodePropertyKeys.locale, locale);
	}

	public Integer getMaxLines() {
		return (Integer) getStateHelper().eval(InputSourceCodePropertyKeys.maxLines, 1024);
	}

	public void setMaxLines(Integer maxLines) {
		getStateHelper().put(InputSourceCodePropertyKeys.maxLines, maxLines);
	}

	public Integer getMinLines() {
		return (Integer) getStateHelper().eval(InputSourceCodePropertyKeys.minLines, 6);
	}

	public void setMinLines(Integer minLines) {
		getStateHelper().put(InputSourceCodePropertyKeys.minLines, minLines);
	}

	public String getMode() {
		return (String) getStateHelper().eval(InputSourceCodePropertyKeys.mode, null);
	}

	public void setMode(String mode) {
		getStateHelper().put(InputSourceCodePropertyKeys.mode, mode);
	}

	public Boolean getReadOnly() {
		return (Boolean) getStateHelper().eval(InputSourceCodePropertyKeys.readOnly, null);
	}

	public void setReadOnly(Boolean readOnly) {
		getStateHelper().put(InputSourceCodePropertyKeys.readOnly, readOnly);
	}

	public Boolean getShowPrintMargin() {
		return (Boolean) getStateHelper().eval(InputSourceCodePropertyKeys.showPrintMargin, null);
	}

	public void setShowPrintMargin(Boolean showPrintMargin) {
		getStateHelper().put(InputSourceCodePropertyKeys.showPrintMargin, showPrintMargin);
	}

	public String getTabSize() {
		return (String) getStateHelper().eval(InputSourceCodePropertyKeys.tabSize, null);
	}

	public void setTabSize(String tabSize) {
		getStateHelper().put(InputSourceCodePropertyKeys.tabSize, tabSize);
	}

	public Boolean getUseSoftTabs() {
		return (Boolean) getStateHelper().eval(InputSourceCodePropertyKeys.useSoftTabs, null);
	}

	public void setUseSoftTabs(Boolean useSoftTabs) {
		getStateHelper().put(InputSourceCodePropertyKeys.useSoftTabs, useSoftTabs);
	}

	public Boolean getUseWrapMode() {
		return (Boolean) getStateHelper().eval(InputSourceCodePropertyKeys.useWrapMode, null);
	}

	public void setUseWrapMode(Boolean useWrapMode) {
		getStateHelper().put(InputSourceCodePropertyKeys.useWrapMode, useWrapMode);
	}

	public String getWidth() {
		return (String) getStateHelper().eval(InputSourceCodePropertyKeys.width, null);
	}

	public void setWidth(String width) {
		getStateHelper().put(InputSourceCodePropertyKeys.width, width);
	}
}
//J+
