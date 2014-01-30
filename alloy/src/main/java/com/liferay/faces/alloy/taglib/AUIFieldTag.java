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

import javax.faces.component.UIComponent;


/**
 * @author  Neil Griffin
 */
public class AUIFieldTag extends AUIComponentELTag {

	private javax.el.ValueExpression label;

	@Override
	public void release() {
		super.release();
		this.label = null;
	}

	@Override
	public String getComponentType() {
		return "com.liferay.faces.alloy.AUIField";
	}

	public javax.el.ValueExpression getLabel() {
		return label;
	}

	public void setLabel(javax.el.ValueExpression label) {
		this.label = label;
	}

	@Override
	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);
		uiComponent.setValueExpression("label", label);
	}

	@Override
	public String getRendererType() {
		return "com.liferay.faces.alloy.renderkit.FieldRenderer";
	}

}
