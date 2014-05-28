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
public abstract class InputSourceCodeBase extends HtmlInputText implements Styleable, ClientComponent, InputSourceCodeAlloy {

	@Override
	public String getBoundingBox() {
		return (String) getStateHelper().eval(BOUNDING_BOX, null);
	}

	@Override
	public void setBoundingBox(String boundingBox) {
		getStateHelper().put(BOUNDING_BOX, boundingBox);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(CLIENT_KEY, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(CLIENT_KEY, clientKey);
	}

	@Override
	public String getContentBox() {
		return (String) getStateHelper().eval(CONTENT_BOX, null);
	}

	@Override
	public void setContentBox(String contentBox) {
		getStateHelper().put(CONTENT_BOX, contentBox);
	}

	@Override
	public Object getHeight() {
		return (Object) getStateHelper().eval(HEIGHT, null);
	}

	@Override
	public void setHeight(Object height) {
		getStateHelper().put(HEIGHT, height);
	}

	@Override
	public Boolean isHighlightActiveLine() {
		return (Boolean) getStateHelper().eval(HIGHLIGHT_ACTIVE_LINE, null);
	}

	@Override
	public void setHighlightActiveLine(Boolean highlightActiveLine) {
		getStateHelper().put(HIGHLIGHT_ACTIVE_LINE, highlightActiveLine);
	}

	@Override
	public String getLocale() {
		return (String) getStateHelper().eval(LOCALE, null);
	}

	@Override
	public void setLocale(String locale) {
		getStateHelper().put(LOCALE, locale);
	}

	@Override
	public String getMode() {
		return (String) getStateHelper().eval(MODE, null);
	}

	@Override
	public void setMode(String mode) {
		getStateHelper().put(MODE, mode);
	}

	@Override
	public Boolean isReadOnly() {
		return (Boolean) getStateHelper().eval(READ_ONLY, null);
	}

	@Override
	public void setReadOnly(Boolean readOnly) {
		getStateHelper().put(READ_ONLY, readOnly);
	}

	@Override
	public Boolean isShowPrintMargin() {
		return (Boolean) getStateHelper().eval(SHOW_PRINT_MARGIN, null);
	}

	@Override
	public void setShowPrintMargin(Boolean showPrintMargin) {
		getStateHelper().put(SHOW_PRINT_MARGIN, showPrintMargin);
	}

	@Override
	public String getSrcNode() {
		return (String) getStateHelper().eval(SRC_NODE, null);
	}

	@Override
	public void setSrcNode(String srcNode) {
		getStateHelper().put(SRC_NODE, srcNode);
	}

	@Override
	public Object getStrings() {
		return (Object) getStateHelper().eval(STRINGS, null);
	}

	@Override
	public void setStrings(Object strings) {
		getStateHelper().put(STRINGS, strings);
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

	@Override
	public Object getTabIndex() {
		return (Object) getStateHelper().eval(TAB_INDEX, null);
	}

	@Override
	public void setTabIndex(Object tabIndex) {
		getStateHelper().put(TAB_INDEX, tabIndex);
	}

	@Override
	public Object getTabSize() {
		return (Object) getStateHelper().eval(TAB_SIZE, null);
	}

	@Override
	public void setTabSize(Object tabSize) {
		getStateHelper().put(TAB_SIZE, tabSize);
	}

	@Override
	public Boolean isUseSoftTabs() {
		return (Boolean) getStateHelper().eval(USE_SOFT_TABS, null);
	}

	@Override
	public void setUseSoftTabs(Boolean useSoftTabs) {
		getStateHelper().put(USE_SOFT_TABS, useSoftTabs);
	}

	@Override
	public Boolean isUseWrapMode() {
		return (Boolean) getStateHelper().eval(USE_WRAP_MODE, null);
	}

	@Override
	public void setUseWrapMode(Boolean useWrapMode) {
		getStateHelper().put(USE_WRAP_MODE, useWrapMode);
	}

	@Override
	public Boolean isVisible() {
		return (Boolean) getStateHelper().eval(VISIBLE, null);
	}

	@Override
	public void setVisible(Boolean visible) {
		getStateHelper().put(VISIBLE, visible);
	}

	@Override
	public String getWidgetId() {
		return (String) getStateHelper().eval(WIDGET_ID, null);
	}

	@Override
	public void setWidgetId(String widgetId) {
		getStateHelper().put(WIDGET_ID, widgetId);
	}

	@Override
	public Boolean isWidgetRender() {
		return (Boolean) getStateHelper().eval(WIDGET_RENDER, null);
	}

	@Override
	public void setWidgetRender(Boolean widgetRender) {
		getStateHelper().put(WIDGET_RENDER, widgetRender);
	}

	@Override
	public Object getWidth() {
		return (Object) getStateHelper().eval(WIDTH, null);
	}

	@Override
	public void setWidth(Object width) {
		getStateHelper().put(WIDTH, width);
	}
}
//J+
