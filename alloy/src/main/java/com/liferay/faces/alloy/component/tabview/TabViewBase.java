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
package com.liferay.faces.alloy.component.tabview;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIData;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class TabViewBase extends UIData implements Styleable, ClientComponent {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.tabview.TabView";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.tabview.TabViewRenderer";

	// Protected Enumerations
	protected enum TabViewPropertyKeys {
		clientKey,
		height,
		selectedIndex,
		stacked,
		style,
		styleClass,
		width
	}

	public TabViewBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(TabViewPropertyKeys.clientKey, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(TabViewPropertyKeys.clientKey, clientKey);
	}

	public String getHeight() {
		return (String) getStateHelper().eval(TabViewPropertyKeys.height, null);
	}

	public void setHeight(String height) {
		getStateHelper().put(TabViewPropertyKeys.height, height);
	}

	public Integer getSelectedIndex() {
		return (Integer) getStateHelper().eval(TabViewPropertyKeys.selectedIndex, null);
	}

	public void setSelectedIndex(Integer selectedIndex) {
		getStateHelper().put(TabViewPropertyKeys.selectedIndex, selectedIndex);
	}

	public Boolean getStacked() {
		return (Boolean) getStateHelper().eval(TabViewPropertyKeys.stacked, null);
	}

	public void setStacked(Boolean stacked) {
		getStateHelper().put(TabViewPropertyKeys.stacked, stacked);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(TabViewPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(TabViewPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(TabViewPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(TabViewPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-tab-view");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(TabViewPropertyKeys.styleClass, styleClass);
	}

	public String getWidth() {
		return (String) getStateHelper().eval(TabViewPropertyKeys.width, null);
	}

	public void setWidth(String width) {
		getStateHelper().put(TabViewPropertyKeys.width, width);
	}
}
//J+
