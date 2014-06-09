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
package com.liferay.faces.alloy.component.tabview;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIData;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class TabViewBase extends UIData implements Styleable, ClientComponent {

	// Public Constants
	public static final String CLIENT_KEY = "clientKey";
	public static final String HEIGHT = "height";
	public static final String STACKED = "stacked";
	public static final String STYLE = "style";
	public static final String STYLE_CLASS = "styleClass";
	public static final String WIDTH = "width";

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(CLIENT_KEY, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(CLIENT_KEY, clientKey);
	}

	public String getHeight() {
		return (String) getStateHelper().eval(HEIGHT, null);
	}

	public void setHeight(String height) {
		getStateHelper().put(HEIGHT, height);
	}

	public Boolean isStacked() {
		return (Boolean) getStateHelper().eval(STACKED, null);
	}

	public void setStacked(Boolean stacked) {
		getStateHelper().put(STACKED, stacked);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(STYLE, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(STYLE, style);
	}

	@Override
	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}

	public String getWidth() {
		return (String) getStateHelper().eval(WIDTH, null);
	}

	public void setWidth(String width) {
		getStateHelper().put(WIDTH, width);
	}
}
//J+
