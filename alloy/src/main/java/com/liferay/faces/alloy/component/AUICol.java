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

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;


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

	// Private Data Members
	private String cssClass;
	private Boolean first;
	private Boolean last;
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

	public void setFirst(Boolean first) {
		this.first = first;
	}

	public void setLast(Boolean last) {
		this.last = last;
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
			ValueExpression valueExpression = getValueExpression(SPAN);

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

			if (valueExpression != null) {
				styleClass = (String) valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
			}
		}

		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public Boolean isFirst() {

		if (first == null) {
			ValueExpression valueExpression = getValueExpression(FIRST);

			if (valueExpression != null) {
				first = (Boolean) valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
			}
			else {
				first = Boolean.FALSE;
			}
		}

		return first;
	}

	public Boolean isLast() {

		if (last == null) {
			ValueExpression valueExpression = getValueExpression(LAST);

			if (valueExpression != null) {
				last = (Boolean) valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
			}
			else {
				last = Boolean.FALSE;
			}
		}

		return last;
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
