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
package com.liferay.faces.alloy.component;

import javax.faces.component.NamingContainer;


/**
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class AUICol extends AUIPanel implements NamingContainer {

	// Public Constants
	public static final int COLUMNS = 12;
	public static final String FIRST = "first";
	public static final String LAST = "last";
	public static final String OFFSET = "offset";
	public static final String OFFSET_WIDTH = "offsetWidth";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.renderkit.ColRenderer";
	public static final String SPAN = "span";
	public static final String WIDTH = "width";

	// Private Constants
	private static final String CSS_CLASS = "cssClass";
	private static final String STYLE_CLASS = "styleClass";

	public String getCssClass() {
		return (String) getStateHelper().eval(CSS_CLASS, null);
	}

	public void setCssClass(String cssClass) {
		getStateHelper().put(CSS_CLASS, cssClass);
	}

	public void setFirst(Boolean first) {
		getStateHelper().put(FIRST, first);
	}

	public void setLast(Boolean last) {
		getStateHelper().put(LAST, last);
	}

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

	@Override
	public String getRendererType() {
		return RENDERER_TYPE;
	}

	public Integer getSpan() {
		return (Integer) getStateHelper().eval(SPAN, COLUMNS);
	}

	public void setSpan(Integer span) {
		getStateHelper().put(SPAN, span);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}

	public Boolean isFirst() {
		return (Boolean) getStateHelper().eval(FIRST, null);
	}

	public Boolean isLast() {
		return (Boolean) getStateHelper().eval(LAST, null);
	}

	public Integer getWidth() {
		return (Integer) getStateHelper().eval(WIDTH, null);
	}

	public void setWidth(Integer width) {
		getStateHelper().put(WIDTH, width);
	}
}
