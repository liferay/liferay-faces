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
package com.liferay.faces.alloy.component.inputdatetime;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.inputtext.InputText;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputDateTimeBase extends InputText implements Styleable {

	// Protected Enumerations
	protected enum InputDateTimePropertyKeys {
		autoHide,
		clientKey,
		locale,
		responsive,
		showOn,
		timeZone,
		zIndex
	}

	public Boolean getAutoHide() {
		return (Boolean) getStateHelper().eval(InputDateTimePropertyKeys.autoHide, null);
	}

	public void setAutoHide(Boolean autoHide) {
		getStateHelper().put(InputDateTimePropertyKeys.autoHide, autoHide);
	}

	public String getClientKey() {
		return (String) getStateHelper().eval(InputDateTimePropertyKeys.clientKey, null);
	}

	public void setClientKey(String clientKey) {
		getStateHelper().put(InputDateTimePropertyKeys.clientKey, clientKey);
	}

	public Object getLocale() {
		return (Object) getStateHelper().eval(InputDateTimePropertyKeys.locale, null);
	}

	public void setLocale(Object locale) {
		getStateHelper().put(InputDateTimePropertyKeys.locale, locale);
	}

	public boolean isResponsive() {
		return (Boolean) getStateHelper().eval(InputDateTimePropertyKeys.responsive, true);
	}

	public void setResponsive(boolean responsive) {
		getStateHelper().put(InputDateTimePropertyKeys.responsive, responsive);
	}

	public String getShowOn() {
		return (String) getStateHelper().eval(InputDateTimePropertyKeys.showOn, null);
	}

	public void setShowOn(String showOn) {
		getStateHelper().put(InputDateTimePropertyKeys.showOn, showOn);
	}

	public String getTimeZone() {
		return (String) getStateHelper().eval(InputDateTimePropertyKeys.timeZone, null);
	}

	public void setTimeZone(String timeZone) {
		getStateHelper().put(InputDateTimePropertyKeys.timeZone, timeZone);
	}

	public Integer getzIndex() {
		return (Integer) getStateHelper().eval(InputDateTimePropertyKeys.zIndex, null);
	}

	public void setzIndex(Integer zIndex) {
		getStateHelper().put(InputDateTimePropertyKeys.zIndex, zIndex);
	}
}
//J+
