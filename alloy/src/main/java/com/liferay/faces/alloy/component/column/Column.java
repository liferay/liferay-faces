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
package com.liferay.faces.alloy.component.column;

import javax.el.ValueExpression;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Juan Gonzalez
 */
public class Column extends UIPanel {

	// Public Constants
	public static final int COLUMNS = 12;
	public static final String FIRST = "first";
	public static final String LAST = "last";
	public static final String OFFSET = "offset";
	public static final String OFFSET_WIDTH = "offsetWidth";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.column.internal.ColumnRenderer";
	public static final String WIDTH = "width";
	public static final String STYLE_CLASS_NAME = "alloy-column";

	// Private Constants
	private static final String CSS_CLASS = "cssClass";
	private static final String STYLE_CLASS = "styleClass";

	// Private Data Members
	private String cssClass;
	private Integer offset;
	private Integer offsetWidth;
	private Integer span;
	private String styleClass;
	private Integer width;

	public String getCssClass() {

		if (cssClass == null) {
			ValueExpression valueExpression = getValueExpression(CSS_CLASS);

			if (valueExpression != null) {
				cssClass = (String) valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
			}
		}

		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public Integer getOffset() {

		if (offset == null) {
			ValueExpression valueExpression = getValueExpression(OFFSET);

			if (valueExpression != null) {
				offset = (Integer) valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
			}
		}

		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getOffsetWidth() {

		if (offsetWidth == null) {
			ValueExpression valueExpression = getValueExpression(OFFSET_WIDTH);

			if (valueExpression != null) {
				offsetWidth = (Integer) valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
			}
		}

		return offsetWidth;
	}

	public void setOffsetWidth(Integer offsetWidth) {
		this.offsetWidth = offsetWidth;
	}

	@Override
	public String getRendererType() {
		return RENDERER_TYPE;
	}

	public Integer getSpan() {

		if (span == null) {
			ValueExpression valueExpression = getValueExpression("span");

			if (valueExpression != null) {
				span = (Integer) valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
			}
			else {
				span = COLUMNS;
			}
		}

		return span;
	}

	public void setSpan(Integer span) {
		this.span = span;
	}

	public String getStyleClass() {

		if (styleClass == null) {
			ValueExpression valueExpression = getValueExpression(STYLE_CLASS);

			styleClass = StringPool.BLANK;

			if (valueExpression != null) {
				styleClass = (String) valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
			}

			styleClass = styleClass.concat(STYLE_CLASS_NAME);
		}

		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public Integer getWidth() {

		if (width == null) {
			ValueExpression valueExpression = getValueExpression(WIDTH);

			if (valueExpression != null) {
				width = (Integer) valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
			}
		}

		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}
}
