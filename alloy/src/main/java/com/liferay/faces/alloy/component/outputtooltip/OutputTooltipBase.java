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
package com.liferay.faces.alloy.component.outputtooltip;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.outputtext.OutputText;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class OutputTooltipBase extends OutputText implements Styleable, ClientComponent {

	// Public Constants
	public static final String AUTO_SHOW = "autoShow";
	public static final String CLIENT_KEY = "clientKey";
	public static final String CSS_CLASS = "cssClass";
	public static final String FOR = "for";
	public static final String HEADER_TEXT = "headerText";
	public static final String OPACITY = "opacity";
	public static final String POSITION = "position";
	public static final String STYLE = "style";
	public static final String STYLE_CLASS = "styleClass";
	public static final String VALUE = "value";
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

	public String getCssClass() {
		return (String) getStateHelper().eval(CSS_CLASS, null);
	}

	public void setCssClass(String cssClass) {
		getStateHelper().put(CSS_CLASS, cssClass);
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

	public String getOpacity() {
		return (String) getStateHelper().eval(OPACITY, null);
	}

	public void setOpacity(String opacity) {
		getStateHelper().put(OPACITY, opacity);
	}

	public String getPosition() {
		return (String) getStateHelper().eval(POSITION, null);
	}

	public void setPosition(String position) {
		getStateHelper().put(POSITION, position);
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

	public Object getzIndex() {
		return (Object) getStateHelper().eval(Z_INDEX, null);
	}

	public void setzIndex(Object zIndex) {
		getStateHelper().put(Z_INDEX, zIndex);
	}
}
//J+
