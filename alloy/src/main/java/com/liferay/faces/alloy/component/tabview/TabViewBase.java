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
package com.liferay.faces.alloy.component.tabview;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIData;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class TabViewBase extends UIData implements Styleable, ClientComponent, TabViewAlloy {

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
	public String getDefaultChildType() {
		return (String) getStateHelper().eval(DEFAULT_CHILD_TYPE, null);
	}

	@Override
	public void setDefaultChildType(String defaultChildType) {
		getStateHelper().put(DEFAULT_CHILD_TYPE, defaultChildType);
	}

	@Override
	public Boolean isDisabled() {
		return (Boolean) getStateHelper().eval(DISABLED, null);
	}

	@Override
	public void setDisabled(Boolean disabled) {
		getStateHelper().put(DISABLED, disabled);
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
	public String getLocale() {
		return (String) getStateHelper().eval(LOCALE, null);
	}

	@Override
	public void setLocale(String locale) {
		getStateHelper().put(LOCALE, locale);
	}

	@Override
	public Boolean isMultiple() {
		return (Boolean) getStateHelper().eval(MULTIPLE, null);
	}

	@Override
	public void setMultiple(Boolean multiple) {
		getStateHelper().put(MULTIPLE, multiple);
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
	public Boolean isStacked() {
		return (Boolean) getStateHelper().eval(STACKED, null);
	}

	@Override
	public void setStacked(Boolean stacked) {
		getStateHelper().put(STACKED, stacked);
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
	public String getType() {
		return (String) getStateHelper().eval(TYPE, null);
	}

	@Override
	public void setType(String type) {
		getStateHelper().put(TYPE, type);
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
