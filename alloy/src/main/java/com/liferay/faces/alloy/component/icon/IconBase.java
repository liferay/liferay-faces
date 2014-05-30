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
package com.liferay.faces.alloy.component.icon;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIComponentBase;

import com.liferay.faces.util.component.Styleable;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class IconBase extends UIComponentBase implements Styleable {

	// Public Constants
	public static final String NAME = "name";
	public static final String STYLE = "style";
	public static final String STYLE_CLASS = "styleClass";

	public String getName() {
		return (String) getStateHelper().eval(NAME, null);
	}

	public void setName(String name) {
		getStateHelper().put(NAME, name);
	}

	public String getStyle() {
		return (String) getStateHelper().eval(STYLE, null);
	}

	public void setStyle(String style) {
		getStateHelper().put(STYLE, style);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}
}
//J+
