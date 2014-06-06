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
import javax.faces.component.html.HtmlColumn;

import com.liferay.faces.util.component.Styleable;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ColumnBase extends HtmlColumn implements Styleable {

	// Public Constants
	public static final String FILTER_BY = "filterBy";
	public static final String HEADER_TEXT = "headerText";
	public static final String OFFSET = "offset";
	public static final String OFFSET_WIDTH = "offsetWidth";
	public static final String SORT_BY = "sortBy";
	public static final String SPAN = "span";
	public static final String STYLE = "style";
	public static final String STYLE_CLASS = "styleClass";
	public static final String WIDTH = "width";

	public Object getFilterBy() {
		return (Object) getStateHelper().eval(FILTER_BY, null);
	}

	public void setFilterBy(Object filterBy) {
		getStateHelper().put(FILTER_BY, filterBy);
	}

	public String getHeaderText() {
		return (String) getStateHelper().eval(HEADER_TEXT, null);
	}

	public void setHeaderText(String headerText) {
		getStateHelper().put(HEADER_TEXT, headerText);
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

	public Object getSortBy() {
		return (Object) getStateHelper().eval(SORT_BY, null);
	}

	public void setSortBy(Object sortBy) {
		getStateHelper().put(SORT_BY, sortBy);
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
