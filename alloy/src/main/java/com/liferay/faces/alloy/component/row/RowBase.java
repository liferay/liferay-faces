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
package com.liferay.faces.alloy.component.row;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIPanel;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class RowBase extends UIPanel implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.row.Row";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.row.RowRenderer";

	// Protected Enumerations
	protected enum RowPropertyKeys {
		fluid,
		style,
		styleClass
	}

	public RowBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public boolean isFluid() {
		return (Boolean) getStateHelper().eval(RowPropertyKeys.fluid, true);
	}

	public void setFluid(boolean fluid) {
		getStateHelper().put(RowPropertyKeys.fluid, fluid);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(RowPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(RowPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(RowPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(RowPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-row");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(RowPropertyKeys.styleClass, styleClass);
	}
}
//J+
