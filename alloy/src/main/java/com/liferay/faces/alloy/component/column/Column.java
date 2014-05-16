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

import javax.faces.component.FacesComponent;

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Kyle Stiemann
 */
@FacesComponent(value = Column.COMPONENT_TYPE)
public class Column extends ColumnBase {

	// Public Constants
	public static final int COLUMNS = 12;
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.column.Column";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.column.ColumnRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-column";

	public Column() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public Integer getSpan() {
		return (Integer) getStateHelper().eval(StringPool.SPAN, COLUMNS);
	}

	@Override
	public String getStyleClass() {

		String styleClass = super.getStyleClass();

		if (styleClass == null) {
			styleClass = STYLE_CLASS_NAME;
		}
		else {
			styleClass = styleClass + StringPool.SPACE + STYLE_CLASS_NAME;
		}

		return styleClass;
	}
}
