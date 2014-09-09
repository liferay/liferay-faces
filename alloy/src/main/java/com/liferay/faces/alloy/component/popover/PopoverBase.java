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
package com.liferay.faces.alloy.component.popover;
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
public abstract class PopoverBase extends PanelGroupBlockLayout implements Styleable, ClientComponent {

	// Protected Enumerations
	protected enum PopoverPropertyKeys {
		autoShow,
		clientKey,
		dismissible,
		for_,
		headerText,
		height,
		hideIconRendered,
		position,
		width,
		zIndex
	}

	public boolean isAutoShow() {
		return (Boolean) getStateHelper().eval(PopoverPropertyKeys.autoShow, false);
	}

	public void setAutoShow(boolean autoShow) {
		getStateHelper().put(PopoverPropertyKeys.autoShow, autoShow);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(PopoverPropertyKeys.clientKey, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(PopoverPropertyKeys.clientKey, clientKey);
	}

	public boolean isDismissible() {
		return (Boolean) getStateHelper().eval(PopoverPropertyKeys.dismissible, true);
	}

	public void setDismissible(boolean dismissible) {
		getStateHelper().put(PopoverPropertyKeys.dismissible, dismissible);
	}

	public String getFor() {
		return (String) getStateHelper().eval(PopoverPropertyKeys.for_, null);
	}

	public void setFor(String for_) {
		getStateHelper().put(PopoverPropertyKeys.for_, for_);
	}

	public String getHeaderText() {
		return (String) getStateHelper().eval(PopoverPropertyKeys.headerText, null);
	}

	public void setHeaderText(String headerText) {
		getStateHelper().put(PopoverPropertyKeys.headerText, headerText);
	}

	public String getHeight() {
		return (String) getStateHelper().eval(PopoverPropertyKeys.height, null);
	}

	public void setHeight(String height) {
		getStateHelper().put(PopoverPropertyKeys.height, height);
	}

	public boolean isHideIconRendered() {
		return (Boolean) getStateHelper().eval(PopoverPropertyKeys.hideIconRendered, true);
	}

	public void setHideIconRendered(boolean hideIconRendered) {
		getStateHelper().put(PopoverPropertyKeys.hideIconRendered, hideIconRendered);
	}

	public String getPosition() {
		return (String) getStateHelper().eval(PopoverPropertyKeys.position, com.liferay.faces.util.lang.StringPool.RIGHT);
	}

	public void setPosition(String position) {
		getStateHelper().put(PopoverPropertyKeys.position, position);
	}

	public String getWidth() {
		return (String) getStateHelper().eval(PopoverPropertyKeys.width, null);
	}

	public void setWidth(String width) {
		getStateHelper().put(PopoverPropertyKeys.width, width);
	}

	public Integer getzIndex() {
		return (Integer) getStateHelper().eval(PopoverPropertyKeys.zIndex, Integer.MIN_VALUE);
	}

	public void setzIndex(Integer zIndex) {
		getStateHelper().put(PopoverPropertyKeys.zIndex, zIndex);
	}
}
//J+
