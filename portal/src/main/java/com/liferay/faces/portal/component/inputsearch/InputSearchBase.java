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
package com.liferay.faces.portal.component.inputsearch;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIInput;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputSearchBase extends UIInput implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.inputsearch.InputSearch";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.inputsearch.InputSearchRenderer";

	// Protected Enumerations
	protected enum InputSearchPropertyKeys {
		action,
		actionListener,
		autoFocus,
		buttonLabel,
		placeholder,
		showButton,
		style,
		styleClass,
		title
	}

	public InputSearchBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public javax.el.MethodExpression getAction() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputSearchPropertyKeys.action, null);
	}

	public void setAction(javax.el.MethodExpression action) {
		getStateHelper().put(InputSearchPropertyKeys.action, action);
	}

	public javax.el.MethodExpression getActionListener() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputSearchPropertyKeys.actionListener, null);
	}

	public void setActionListener(javax.el.MethodExpression actionListener) {
		getStateHelper().put(InputSearchPropertyKeys.actionListener, actionListener);
	}

	public boolean isAutoFocus() {
		return (Boolean) getStateHelper().eval(InputSearchPropertyKeys.autoFocus, false);
	}

	public void setAutoFocus(boolean autoFocus) {
		getStateHelper().put(InputSearchPropertyKeys.autoFocus, autoFocus);
	}

	public String getButtonLabel() {
		return (String) getStateHelper().eval(InputSearchPropertyKeys.buttonLabel, null);
	}

	public void setButtonLabel(String buttonLabel) {
		getStateHelper().put(InputSearchPropertyKeys.buttonLabel, buttonLabel);
	}

	public String getPlaceholder() {
		return (String) getStateHelper().eval(InputSearchPropertyKeys.placeholder, null);
	}

	public void setPlaceholder(String placeholder) {
		getStateHelper().put(InputSearchPropertyKeys.placeholder, placeholder);
	}

	public boolean isShowButton() {
		return (Boolean) getStateHelper().eval(InputSearchPropertyKeys.showButton, true);
	}

	public void setShowButton(boolean showButton) {
		getStateHelper().put(InputSearchPropertyKeys.showButton, showButton);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(InputSearchPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(InputSearchPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(InputSearchPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(InputSearchPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-input-search");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(InputSearchPropertyKeys.styleClass, styleClass);
	}

	public String getTitle() {
		return (String) getStateHelper().eval(InputSearchPropertyKeys.title, null);
	}

	public void setTitle(String title) {
		getStateHelper().put(InputSearchPropertyKeys.title, title);
	}
}
//J+
