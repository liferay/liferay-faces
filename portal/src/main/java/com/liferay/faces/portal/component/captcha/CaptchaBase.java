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

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.captcha.Captcha";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.captcha.CaptchaRenderer";

	// Protected Enumerations
	protected enum CaptchaPropertyKeys {
		label,
		style,
		styleClass,
		url
	}

	public CaptchaBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getLabel() {

		String label = (String) getStateHelper().eval(CaptchaPropertyKeys.label, null);

		if (label == null) {

			javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();

			if (facesContext.getCurrentPhaseId() == javax.faces.event.PhaseId.PROCESS_VALIDATIONS) {
				label = com.liferay.faces.util.component.ComponentUtil.getComponentLabel(this);
			}
		}

		return label;
	}

	public void setLabel(String label) {
		getStateHelper().put(CaptchaPropertyKeys.label, label);
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
		// getStateHelper().eval(CaptchaPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(CaptchaPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-captcha");
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
