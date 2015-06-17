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
package com.liferay.faces.alloy.component.button;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlOutcomeTargetButton;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ButtonBase extends HtmlOutcomeTargetButton implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.button.Button";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.button.ButtonRenderer";

	// Protected Enumerations
	protected enum ButtonPropertyKeys {
		autofocus,
		disabled,
		styleClass,
		type
	}

	public ButtonBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public Boolean getAutofocus() {
		return (Boolean) getStateHelper().eval(ButtonPropertyKeys.autofocus, null);
	}

	public void setAutofocus(Boolean autofocus) {
		getStateHelper().put(ButtonPropertyKeys.autofocus, autofocus);
	}

	public boolean isDisabled() {
		return (Boolean) getStateHelper().eval(ButtonPropertyKeys.disabled, false);
	}

	public void setDisabled(boolean disabled) {
		getStateHelper().put(ButtonPropertyKeys.disabled, disabled);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(ButtonPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(ButtonPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-button");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(ButtonPropertyKeys.styleClass, styleClass);
	}

	public String getType() {
		return (String) getStateHelper().eval(ButtonPropertyKeys.type, null);
	}

	public void setType(String type) {
		getStateHelper().put(ButtonPropertyKeys.type, type);
	}
}
//J+
