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
package com.liferay.faces.alloy.component.field;

import javax.faces.component.FacesComponent;

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author  Kyle Stiemann
 */
@FacesComponent(value = Field.COMPONENT_TYPE)
public class Field extends FieldBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.field.Field";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.field.FieldRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-field";

	// Private Constants
	private static final String BLOCK = "block";
	private static final String CONTROL_GROUP = "control-group";
	private static final String LAYOUT = "layout";

	public Field() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getLayout() {
		return (String) getStateHelper().eval(LAYOUT, BLOCK);
	}

	@Override
	public void setLayout(String layout) {
		getStateHelper().put(LAYOUT, layout);
	}

	@Override
	public String getStyleClass() {

		String styleClass = (String) getStateHelper().eval(STYLE_CLASS, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME, CONTROL_GROUP);
	}
}
