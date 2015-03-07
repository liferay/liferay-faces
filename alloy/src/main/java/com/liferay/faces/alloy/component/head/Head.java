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
package com.liferay.faces.alloy.component.head;

import javax.faces.component.FacesComponent;

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = Head.COMPONENT_TYPE)
public class Head extends HeadBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.head.Head";
	public static final String DELEGATE_COMPONENT_FAMILY = COMPONENT_FAMILY;
	public static final String DELEGATE_RENDERER_TYPE = "javax.faces.Head";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.head.internal.HeadRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-head";

	public Head() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getStyleClass() {

		String styleClass = super.getStyleClass();

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}
}
