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
package com.liferay.faces.portal.component.captcha;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIInput;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class CaptchaBase extends UIInput implements Styleable {

	// Protected Enumerations
	protected enum CaptchaPropertyKeys {
		style,
		styleClass,
		url
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(CaptchaPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(CaptchaPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		return (String) getStateHelper().eval(CaptchaPropertyKeys.styleClass, null);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(CaptchaPropertyKeys.styleClass, styleClass);
	}

	public String getUrl() {
		return (String) getStateHelper().eval(CaptchaPropertyKeys.url, null);
	}

	public void setUrl(String url) {
		getStateHelper().put(CaptchaPropertyKeys.url, url);
	}
}
//J+
