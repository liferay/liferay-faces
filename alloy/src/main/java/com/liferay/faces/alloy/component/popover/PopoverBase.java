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
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PopoverBase extends HtmlPanelGroup implements Styleable, ClientComponent {

	// Public Constants
	public static final String AUTO_SHOW = "autoShow";
	public static final String CLIENT_KEY = "clientKey";
	public static final String FOR = "for";
	public static final String HEADER_TEXT = "headerText";
	public static final String POSITION = "position";
	public static final String Z_INDEX = "zIndex";

	public Boolean isAutoShow() {
		return (Boolean) getStateHelper().eval(AUTO_SHOW, null);
	}

	public void setAutoShow(Boolean autoShow) {
		getStateHelper().put(AUTO_SHOW, autoShow);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(CLIENT_KEY, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(CLIENT_KEY, clientKey);
	}

	public String getFor() {
		return (String) getStateHelper().eval(FOR, null);
	}

	public void setFor(String for_) {
		getStateHelper().put(FOR, for_);
	}

	public String getHeaderText() {
		return (String) getStateHelper().eval(HEADER_TEXT, null);
	}

	public void setHeaderText(String headerText) {
		getStateHelper().put(HEADER_TEXT, headerText);
	}

	public String getPosition() {
		return (String) getStateHelper().eval(POSITION, null);
	}

	public void setPosition(String position) {
		getStateHelper().put(POSITION, position);
	}

	public Integer getzIndex() {
		return (Integer) getStateHelper().eval(Z_INDEX, null);
	}

	public void setzIndex(Integer zIndex) {
		getStateHelper().put(Z_INDEX, zIndex);
	}
}
//J+
