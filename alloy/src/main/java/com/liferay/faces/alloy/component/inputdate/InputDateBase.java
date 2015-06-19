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
package com.liferay.faces.alloy.component.inputdate;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.inputdatetime.InputDateTime;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputDateBase extends InputDateTime implements Styleable, ClientComponent {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.inputdate.InputDate";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.inputdate.InputDateRenderer";

	// Protected Enumerations
	protected enum InputDatePropertyKeys {
		dateSelectListener,
		maxDate,
		minDate,
		nativeWhenMobile,
		panes,
		pattern,
		styleClass
	}

	public InputDateBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public javax.el.MethodExpression getDateSelectListener() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputDatePropertyKeys.dateSelectListener, null);
	}

	public void setDateSelectListener(javax.el.MethodExpression dateSelectListener) {
		getStateHelper().put(InputDatePropertyKeys.dateSelectListener, dateSelectListener);
	}

	public Object getMaxDate() {
		return (Object) getStateHelper().eval(InputDatePropertyKeys.maxDate, null);
	}

	public void setMaxDate(Object maxDate) {
		getStateHelper().put(InputDatePropertyKeys.maxDate, maxDate);
	}

	public Object getMinDate() {
		return (Object) getStateHelper().eval(InputDatePropertyKeys.minDate, null);
	}

	public void setMinDate(Object minDate) {
		getStateHelper().put(InputDatePropertyKeys.minDate, minDate);
	}

	public boolean isNativeWhenMobile() {
		return (Boolean) getStateHelper().eval(InputDatePropertyKeys.nativeWhenMobile, true);
	}

	public void setNativeWhenMobile(boolean nativeWhenMobile) {
		getStateHelper().put(InputDatePropertyKeys.nativeWhenMobile, nativeWhenMobile);
	}

	public Integer getPanes() {
		return (Integer) getStateHelper().eval(InputDatePropertyKeys.panes, null);
	}

	public void setPanes(Integer panes) {
		getStateHelper().put(InputDatePropertyKeys.panes, panes);
	}

	public String getPattern() {
		return (String) getStateHelper().eval(InputDatePropertyKeys.pattern, null);
	}

	public void setPattern(String pattern) {
		getStateHelper().put(InputDatePropertyKeys.pattern, pattern);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(InputDatePropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(InputDatePropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-input-date");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(InputDatePropertyKeys.styleClass, styleClass);
	}
}
//J+
