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
import com.liferay.faces.alloy.component.panelgroup.PanelGroup;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class DialogBase extends PanelGroup implements Styleable, ClientComponent {

	// Protected Enumerations
	protected enum DialogPropertyKeys {
		autoShow,
		clientKey,
		dismissable,
		headerText,
		modal,
		showCloseIcon,
		zIndex
	}

	public Boolean isAutoShow() {
		return (Boolean) getStateHelper().eval(DialogPropertyKeys.autoShow, null);
	}

	public void setAutoShow(Boolean autoShow) {
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

	public Boolean isDismissable() {
		return (Boolean) getStateHelper().eval(DialogPropertyKeys.dismissable, null);
	}

	public void setDismissable(Boolean dismissable) {
		getStateHelper().put(DialogPropertyKeys.dismissable, dismissable);
	}

	public String getHeaderText() {
		return (String) getStateHelper().eval(DialogPropertyKeys.headerText, null);
	}

	public void setHeaderText(String headerText) {
		getStateHelper().put(DialogPropertyKeys.headerText, headerText);
	}

	public Boolean isModal() {
		return (Boolean) getStateHelper().eval(DialogPropertyKeys.modal, null);
	}

	public void setModal(Boolean modal) {
		getStateHelper().put(DialogPropertyKeys.modal, modal);
	}

	public Boolean isShowCloseIcon() {
		return (Boolean) getStateHelper().eval(DialogPropertyKeys.showCloseIcon, null);
	}

	public void setShowCloseIcon(Boolean showCloseIcon) {
		getStateHelper().put(DialogPropertyKeys.showCloseIcon, showCloseIcon);
	}

	public String getzIndex() {
		return (String) getStateHelper().eval(DialogPropertyKeys.zIndex, null);
	}

	public void setzIndex(String zIndex) {
		getStateHelper().put(DialogPropertyKeys.zIndex, zIndex);
	}
}
//J+
