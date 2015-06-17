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
package com.liferay.faces.alloy.component.datalist;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIData;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class DataListBase extends UIData implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.datalist.DataList";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.datalist.DataListRenderer";

	// Protected Enumerations
	protected enum DataListPropertyKeys {
		style,
		styleClass,
		type
	}

	public DataListBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(DataListPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(DataListPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(DataListPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(DataListPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-data-list");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(DataListPropertyKeys.styleClass, styleClass);
	}

	public String getType() {
		return (String) getStateHelper().eval(DataListPropertyKeys.type, "unordered");
	}

	public void setType(String type) {
		getStateHelper().put(DataListPropertyKeys.type, type);
	}
}
//J+
