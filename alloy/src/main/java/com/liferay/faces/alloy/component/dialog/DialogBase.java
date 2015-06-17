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
package com.liferay.faces.alloy.component.dialog;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.panelgroup.PanelGroupBlockLayout;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class DialogBase extends PanelGroupBlockLayout implements Styleable, ClientComponent {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.dialog.Dialog";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.dialog.DialogRenderer";

	// Protected Enumerations
	protected enum DialogPropertyKeys {
		autoShow,
		clientKey,
		dismissible,
		headerText,
		height,
		hideIconRendered,
		modal,
		styleClass,
		width,
		zIndex
	}

	public DialogBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public boolean isAutoShow() {
		return (Boolean) getStateHelper().eval(DialogPropertyKeys.autoShow, false);
	}

	public void setAutoShow(boolean autoShow) {
		getStateHelper().put(DialogPropertyKeys.autoShow, autoShow);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(DialogPropertyKeys.clientKey, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(DialogPropertyKeys.clientKey, clientKey);
	}

	public boolean isDismissible() {
		return (Boolean) getStateHelper().eval(DialogPropertyKeys.dismissible, false);
	}

	public void setDismissible(boolean dismissible) {
		getStateHelper().put(DialogPropertyKeys.dismissible, dismissible);
	}

	public String getHeaderText() {
		return (String) getStateHelper().eval(DialogPropertyKeys.headerText, null);
	}

	public void setHeaderText(String headerText) {
		getStateHelper().put(DialogPropertyKeys.headerText, headerText);
	}

	public String getHeight() {
		return (String) getStateHelper().eval(DialogPropertyKeys.height, null);
	}

	public void setHeight(String height) {
		getStateHelper().put(DialogPropertyKeys.height, height);
	}

	public boolean isHideIconRendered() {
		return (Boolean) getStateHelper().eval(DialogPropertyKeys.hideIconRendered, true);
	}

	public void setHideIconRendered(boolean hideIconRendered) {
		getStateHelper().put(DialogPropertyKeys.hideIconRendered, hideIconRendered);
	}

	public boolean isModal() {
		return (Boolean) getStateHelper().eval(DialogPropertyKeys.modal, false);
	}

	public void setModal(boolean modal) {
		getStateHelper().put(DialogPropertyKeys.modal, modal);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(DialogPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(DialogPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-dialog");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(DialogPropertyKeys.styleClass, styleClass);
	}

	public String getWidth() {
		return (String) getStateHelper().eval(DialogPropertyKeys.width, null);
	}

	public void setWidth(String width) {
		getStateHelper().put(DialogPropertyKeys.width, width);
	}

	public Integer getzIndex() {
		return (Integer) getStateHelper().eval(DialogPropertyKeys.zIndex, Integer.MIN_VALUE);
	}

	public void setzIndex(Integer zIndex) {
		getStateHelper().put(DialogPropertyKeys.zIndex, zIndex);
	}
}
//J+
