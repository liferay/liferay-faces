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
package com.liferay.faces.alloy.component.selectstarrating;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.selectoneradio.SelectOneRadio;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class SelectStarRatingBase extends SelectOneRadio implements Styleable, ClientComponent, SelectStarRatingAlloy {

	@Override
	public String getBoundingBox() {
		return (String) getStateHelper().eval(BOUNDING_BOX, null);
	}

	@Override
	public void setBoundingBox(String boundingBox) {
		getStateHelper().put(BOUNDING_BOX, boundingBox);
	}

	@Override
	public Boolean isCanReset() {
		return (Boolean) getStateHelper().eval(CAN_RESET, null);
	}

	@Override
	public void setCanReset(Boolean canReset) {
		getStateHelper().put(CAN_RESET, canReset);
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
	public String getCssClass() {
		return (String) getStateHelper().eval(CSS_CLASS, null);
	}

	@Override
	public void setCssClass(String cssClass) {
		getStateHelper().put(CSS_CLASS, cssClass);
	}

	@Override
	public Object getCssClasses() {
		return (Object) getStateHelper().eval(CSS_CLASSES, null);
	}

	@Override
	public void setCssClasses(Object cssClasses) {
		getStateHelper().put(CSS_CLASSES, cssClasses);
	}

	@Override
	public Object getDefaultSelected() {
		return (Object) getStateHelper().eval(DEFAULT_SELECTED, null);
	}

	@Override
	public void setDefaultSelected(Object defaultSelected) {
		getStateHelper().put(DEFAULT_SELECTED, defaultSelected);
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
	public String getHiddenInput() {
		return (String) getStateHelper().eval(HIDDEN_INPUT, null);
	}

	@Override
	public void setHiddenInput(String hiddenInput) {
		getStateHelper().put(HIDDEN_INPUT, hiddenInput);
	}

	@Override
	public String getHideClass() {
		return (String) getStateHelper().eval(HIDE_CLASS, null);
	}

	@Override
	public void setHideClass(String hideClass) {
		getStateHelper().put(HIDE_CLASS, hideClass);
	}

	@Override
	public String getInputName() {
		return (String) getStateHelper().eval(INPUT_NAME, null);
	}

	@Override
	public void setInputName(String inputName) {
		getStateHelper().put(INPUT_NAME, inputName);
	}

	@Override
	public String getLabelNode() {
		return (String) getStateHelper().eval(LABEL_NODE, null);
	}

	@Override
	public void setLabelNode(String labelNode) {
		getStateHelper().put(LABEL_NODE, labelNode);
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
	public Object getSelectedIndex() {
		return (Object) getStateHelper().eval(SELECTED_INDEX, null);
	}

	@Override
	public void setSelectedIndex(Object selectedIndex) {
		getStateHelper().put(SELECTED_INDEX, selectedIndex);
	}

	@Override
	public Boolean isShowTitle() {
		return (Boolean) getStateHelper().eval(SHOW_TITLE, null);
	}

	@Override
	public void setShowTitle(Boolean showTitle) {
		getStateHelper().put(SHOW_TITLE, showTitle);
	}

	@Override
	public Object getSize() {
		return (Object) getStateHelper().eval(SIZE, null);
	}

	@Override
	public void setSize(Object size) {
		getStateHelper().put(SIZE, size);
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
	public Object getTabIndex() {
		return (Object) getStateHelper().eval(TAB_INDEX, null);
	}

	@Override
	public void setTabIndex(Object tabIndex) {
		getStateHelper().put(TAB_INDEX, tabIndex);
	}

	@Override
	public Boolean isUseARIA() {
		return (Boolean) getStateHelper().eval(USE_ARIA, null);
	}

	@Override
	public void setUseARIA(Boolean useARIA) {
		getStateHelper().put(USE_ARIA, useARIA);
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
