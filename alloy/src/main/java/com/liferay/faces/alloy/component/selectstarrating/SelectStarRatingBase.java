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
package com.liferay.faces.alloy.component.selectstarrating;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.selectoneradio.SelectOneRadio;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class SelectStarRatingBase extends SelectOneRadio implements Styleable, ClientComponent {

	// Public Constants
	public static final String CLIENT_KEY = "clientKey";
	public static final String DEFAULT_SELECTED = "defaultSelected";
	public static final String DISABLED = "disabled";
	public static final String LABEL = "label";
	public static final String STYLE = "style";
	public static final String STYLE_CLASS = "styleClass";
	public static final String VALUE = "value";

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(CLIENT_KEY, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(CLIENT_KEY, clientKey);
	}

	public String getDefaultSelected() {
		return (String) getStateHelper().eval(DEFAULT_SELECTED, null);
	}

	public void setDefaultSelected(String defaultSelected) {
		getStateHelper().put(DEFAULT_SELECTED, defaultSelected);
	}

	public boolean isDisabled() {
		return (Boolean) getStateHelper().eval(DISABLED, false);
	}

	public void setDisabled(boolean disabled) {
		getStateHelper().put(DISABLED, disabled);
	}

	public String getLabel() {
		return (String) getStateHelper().eval(LABEL, null);
	}

	public void setLabel(String label) {
		getStateHelper().put(LABEL, label);
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
}
//J+
