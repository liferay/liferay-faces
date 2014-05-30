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
package com.liferay.faces.alloy.component.column;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIColumn;

import com.liferay.faces.util.component.Styleable;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ColumnBase extends UIColumn implements Styleable {

	// Public Constants
	private static final String OFFSET = "offset";
	private static final String OFFSET_WIDTH = "offsetWidth";
	private static final String SPAN = "span";
	private static final String STYLE = "style";
	private static final String STYLE_CLASS = "styleClass";
	private static final String WIDTH = "width";

	public Integer getOffset() {
		return (Integer) getStateHelper().eval(OFFSET, null);
	}

	public void setOffset(Integer offset) {
		getStateHelper().put(OFFSET, offset);
	}

	public Integer getOffsetWidth() {
		return (Integer) getStateHelper().eval(OFFSET_WIDTH, null);
	}

	public void setOffsetWidth(Integer offsetWidth) {
		getStateHelper().put(OFFSET_WIDTH, offsetWidth);
	}

	public Integer getSpan() {
		return (Integer) getStateHelper().eval(SPAN, null);
	}

	public void setSpan(Integer span) {
		getStateHelper().put(SPAN, span);
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

	public Integer getWidth() {
		return (Integer) getStateHelper().eval(WIDTH, null);
	}

	public void setWidth(Integer width) {
		getStateHelper().put(WIDTH, width);
	}
}
//J+
