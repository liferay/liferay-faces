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
package com.liferay.faces.alloy.taglib;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;

import com.liferay.faces.alloy.component.AUICol;


/**
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class AUIColTag extends AUIComponentELTag {

	// Private Data Members
	private ValueExpression first;
	private ValueExpression last;
	private ValueExpression offset;
	private ValueExpression offsetWidth;
	private ValueExpression span;
	private ValueExpression width;

	@Override
	public String getComponentType() {
		return "com.liferay.faces.alloy.AUICol";
	}

	public void setFirst(ValueExpression first) {
		this.first = first;
	}

	public void setLast(ValueExpression last) {
		this.last = last;
	}

	public void setOffset(ValueExpression offset) {
		this.offset = offset;
	}

	public void setOffsetWidth(ValueExpression offsetWidth) {
		this.offsetWidth = offsetWidth;
	}

	@Override
	protected void setProperties(UIComponent uiComponent) {

		super.setProperties(uiComponent);

		if (first != null) {
			uiComponent.setValueExpression(AUICol.FIRST, first);
		}

		if (last != null) {
			uiComponent.setValueExpression(AUICol.LAST, last);
		}

		if (offset != null) {
			uiComponent.setValueExpression(AUICol.OFFSET, offset);
		}

		if (offsetWidth != null) {
			uiComponent.setValueExpression(AUICol.OFFSET_WIDTH, offsetWidth);
		}

		if (span != null) {
			uiComponent.setValueExpression(AUICol.SPAN, span);
		}

		if (width != null) {
			uiComponent.setValueExpression(AUICol.WIDTH, width);
		}
	}

	@Override
	public String getRendererType() {
		return AUICol.RENDERER_TYPE;
	}

	public void setSpan(ValueExpression span) {
		this.span = span;
	}

	public void setWidth(ValueExpression width) {
		this.width = width;
	}
}
