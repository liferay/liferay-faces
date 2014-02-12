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

import com.liferay.faces.alloy.component.AUIRow;


/**
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class AUIRowTag extends AUIComponentELTag {

	// Private Data Members
	private ValueExpression fluid;

	@Override
	public String getComponentType() {
		return "com.liferay.faces.alloy.AUIRow";
	}

	public void setFluid(ValueExpression fluid) {
		this.fluid = fluid;
	}

	@Override
	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if (fluid != null) {
			uiComponent.setValueExpression(AUIRow.FLUID, fluid);
		}
	}

	@Override
	public String getRendererType() {
		return AUIRow.RENDERER_TYPE;
	}
}
