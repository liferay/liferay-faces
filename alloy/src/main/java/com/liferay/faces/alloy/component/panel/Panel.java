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
package com.liferay.faces.alloy.component.panel;

import javax.faces.component.FacesComponent;

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@FacesComponent(value = Panel.COMPONENT_TYPE)
public class Panel extends PanelBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.panel.Panel";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.panel.internal.PanelRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-panel alloy-panel-default";

	public Panel() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}
}
