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

	// Protected Enumerations
	protected enum InputDatePropertyKeys {
		datePattern,
		dateSelectListener,
		maxDate,
		minDate,
		panes,
		responsive
	}

	public String getDatePattern() {
		return (String) getStateHelper().eval(InputDatePropertyKeys.datePattern, null);
	}

	public void setDatePattern(String datePattern) {
		getStateHelper().put(InputDatePropertyKeys.datePattern, datePattern);
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

	public Integer getPanes() {
		return (Integer) getStateHelper().eval(InputDatePropertyKeys.panes, null);
	}

	public void setPanes(Integer panes) {
		getStateHelper().put(InputDatePropertyKeys.panes, panes);
	}

	public boolean isResponsive() {
		return (Boolean) getStateHelper().eval(InputDatePropertyKeys.responsive, true);
	}

	public void setResponsive(boolean responsive) {
		getStateHelper().put(InputDatePropertyKeys.responsive, responsive);
	}
}
//J+
