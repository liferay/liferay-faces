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
import javax.faces.component.html.HtmlPanelGroup;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PopoverBase extends HtmlPanelGroup implements Styleable, ClientComponent {

	// Protected Enumerations
	protected enum PopoverPropertyKeys {
		autoShow,
		clientKey,
		for_,
		headerText,
		position,
		zIndex
	}

	public Boolean isAutoShow() {
		return (Boolean) getStateHelper().eval(PopoverPropertyKeys.autoShow, null);
	}

	public void setAutoShow(Boolean autoShow) {
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

	public String getPosition() {
		return (String) getStateHelper().eval(PopoverPropertyKeys.position, null);
	}

	public void setPosition(String position) {
		getStateHelper().put(PopoverPropertyKeys.position, position);
	}

	public Integer getzIndex() {
		return (Integer) getStateHelper().eval(PopoverPropertyKeys.zIndex, null);
	}

	public void setzIndex(Integer zIndex) {
		getStateHelper().put(PopoverPropertyKeys.zIndex, zIndex);
	}
}
//J+
