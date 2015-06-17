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
package com.liferay.faces.alloy.component.tab;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIColumn;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class TabBase extends UIColumn implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.tab.Tab";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.tab.TabRenderer";

	// Protected Enumerations
	protected enum TabPropertyKeys {
		contentClass,
		disabled,
		headerClass,
		headerText,
		style,
		styleClass
	}

	public TabBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getContentClass() {
		return (String) getStateHelper().eval(TabPropertyKeys.contentClass, null);
	}

	public void setContentClass(String contentClass) {
		getStateHelper().put(TabPropertyKeys.contentClass, contentClass);
	}

	public boolean isDisabled() {
		return (Boolean) getStateHelper().eval(TabPropertyKeys.disabled, false);
	}

	public void setDisabled(boolean disabled) {
		getStateHelper().put(TabPropertyKeys.disabled, disabled);
	}

	public String getHeaderClass() {
		return (String) getStateHelper().eval(TabPropertyKeys.headerClass, null);
	}

	public void setHeaderClass(String headerClass) {
		getStateHelper().put(TabPropertyKeys.headerClass, headerClass);
	}

	public String getHeaderText() {
		return (String) getStateHelper().eval(TabPropertyKeys.headerText, null);
	}

	public void setHeaderText(String headerText) {
		getStateHelper().put(TabPropertyKeys.headerText, headerText);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(TabPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(TabPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(TabPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(TabPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-tab");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(TabPropertyKeys.styleClass, styleClass);
	}
}
//J+
