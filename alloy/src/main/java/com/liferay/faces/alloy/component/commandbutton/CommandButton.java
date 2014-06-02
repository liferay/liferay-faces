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
package com.liferay.faces.alloy.component.commandbutton;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlCommandButton;

import com.liferay.faces.alloy.component.button.FacesButton;
import com.liferay.faces.alloy.component.button.HTML5Button;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;


/**
 * @author  Kyle Stiemann
 */
@FacesComponent(value = CommandButton.COMPONENT_TYPE)
public class CommandButton extends HtmlCommandButton implements FacesButton, HTML5Button, Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.commandbutton.CommandButton";
	public static final String DELEGATE_COMPONENT_FAMILY = COMPONENT_FAMILY;
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.commandbutton.CommandButtonRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-command-button";

	public CommandButton() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public void setAutofocus(Boolean autofocus) {
		getStateHelper().put(AUTOFOCUS, autofocus);
	}

	@Override
	public Boolean isAutofocus() {
		return (Boolean) getStateHelper().eval(AUTOFOCUS, null);
	}

	@Override
	public String getStyleClass() {

		String styleClass = (String) getStateHelper().eval(STYLE_CLASS, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}
}
