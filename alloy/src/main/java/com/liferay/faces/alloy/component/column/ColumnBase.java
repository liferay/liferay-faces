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

	// Protected Enumerations
	protected enum ColumnPropertyKeys {
		filterBy,
		headerText,
		offset,
		offsetWidth,
		sortBy,
		span,
		style,
		styleClass,
		width
	}

	public Object getFilterBy() {
		return (Object) getStateHelper().eval(ColumnPropertyKeys.filterBy, null);
	}

	public void setFilterBy(Object filterBy) {
		getStateHelper().put(ColumnPropertyKeys.filterBy, filterBy);
	}

	public String getHeaderText() {
		return (String) getStateHelper().eval(ColumnPropertyKeys.headerText, null);
	}

	public void setHeaderText(String headerText) {
		getStateHelper().put(ColumnPropertyKeys.headerText, headerText);
	}

	public Integer getOffset() {
		return (Integer) getStateHelper().eval(ColumnPropertyKeys.offset, null);
	}

	public void setOffset(Integer offset) {
		getStateHelper().put(ColumnPropertyKeys.offset, offset);
	}

	public Integer getOffsetWidth() {
		return (Integer) getStateHelper().eval(ColumnPropertyKeys.offsetWidth, null);
	}

	public void setOffsetWidth(Integer offsetWidth) {
		getStateHelper().put(ColumnPropertyKeys.offsetWidth, offsetWidth);
	}

	public Object getSortBy() {
		return (Object) getStateHelper().eval(ColumnPropertyKeys.sortBy, null);
	}

	public void setSortBy(Object sortBy) {
		getStateHelper().put(ColumnPropertyKeys.sortBy, sortBy);
	}

	public Integer getSpan() {
		return (Integer) getStateHelper().eval(ColumnPropertyKeys.span, null);
	}

	public void setSpan(Integer span) {
		getStateHelper().put(ColumnPropertyKeys.span, span);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(ColumnPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(ColumnPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		return (String) getStateHelper().eval(ColumnPropertyKeys.styleClass, null);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(ColumnPropertyKeys.styleClass, styleClass);
	}

	public Integer getWidth() {
		return (Integer) getStateHelper().eval(ColumnPropertyKeys.width, null);
	}

	public void setWidth(Integer width) {
		getStateHelper().put(ColumnPropertyKeys.width, width);
	}
}
//J+
