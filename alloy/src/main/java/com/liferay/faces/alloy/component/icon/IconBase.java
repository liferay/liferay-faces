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
package com.liferay.faces.alloy.component.icon;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIComponentBase;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class IconBase extends UIComponentBase implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.icon.Icon";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.icon.IconRenderer";

	// Protected Enumerations
	protected enum IconPropertyKeys {
		color,
		name,
		style,
		styleClass
	}

	public IconBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getColor() {
		return (String) getStateHelper().eval(IconPropertyKeys.color, null);
	}

	public void setColor(String color) {
		getStateHelper().put(IconPropertyKeys.color, color);
	}

	public String getName() {
		return (String) getStateHelper().eval(IconPropertyKeys.name, null);
	}

	public void setName(String name) {
		getStateHelper().put(IconPropertyKeys.name, name);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(IconPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(IconPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(IconPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(IconPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-icon");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(IconPropertyKeys.styleClass, styleClass);
	}
}
//J+
