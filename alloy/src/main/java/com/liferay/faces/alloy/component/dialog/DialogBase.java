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

	// Protected Enumerations
	protected enum DialogPropertyKeys {
		autoShow,
		clientKey,
		headerText,
		hideIconRendered,
		hideOnBlur,
		modal,
		zIndex
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

	public String getHeaderText() {
		return (String) getStateHelper().eval(DialogPropertyKeys.headerText, null);
	}

	public void setHeaderText(String headerText) {
		getStateHelper().put(DialogPropertyKeys.headerText, headerText);
	}

	public boolean isHideIconRendered() {
		return (Boolean) getStateHelper().eval(DialogPropertyKeys.hideIconRendered, true);
	}

	public void setHideIconRendered(boolean hideIconRendered) {
		getStateHelper().put(DialogPropertyKeys.hideIconRendered, hideIconRendered);
	}

	public boolean isHideOnBlur() {
		return (Boolean) getStateHelper().eval(DialogPropertyKeys.hideOnBlur, false);
	}

	public void setHideOnBlur(boolean hideOnBlur) {
		getStateHelper().put(DialogPropertyKeys.hideOnBlur, hideOnBlur);
	}

	public boolean isModal() {
		return (Boolean) getStateHelper().eval(DialogPropertyKeys.modal, false);
	}

	public void setModal(boolean modal) {
		getStateHelper().put(DialogPropertyKeys.modal, modal);
	}

	public Integer getzIndex() {
		return (Integer) getStateHelper().eval(DialogPropertyKeys.zIndex, Integer.MIN_VALUE);
	}

	public void setzIndex(Integer zIndex) {
		getStateHelper().put(DialogPropertyKeys.zIndex, zIndex);
	}
}
//J+
