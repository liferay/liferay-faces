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
package com.liferay.faces.alloy.component.field;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.panelgroup.PanelGroup;

import com.liferay.faces.util.component.Styleable;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class FieldBase extends PanelGroup implements Styleable {

	// Public Constants
	public static final String LABEL = "label";
	public static final String STYLE = "style";
	public static final String STYLE_CLASS = "styleClass";

	public String getLabel() {
		return (String) getStateHelper().eval(LABEL, null);
	}

	public void setLabel(String label) {
		getStateHelper().put(LABEL, label);
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
