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
package com.liferay.faces.alloy.component.inputsecret;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = InputSecret.COMPONENT_TYPE)
public class InputSecret extends HtmlInputSecret {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.inputsecret.InputSecret";
	public static final String DELEGATE_COMPONENT_FAMILY = COMPONENT_FAMILY;
	public static final String DELEGATE_RENDERER_TYPE = "javax.faces.Secret";
	public static final String RENDERER_TYPE =
		"com.liferay.faces.alloy.component.inputsecret.internal.InputSecretRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-input-secret";

	public InputSecret() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getLabel() {

		String label = super.getLabel();

		if (label == null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();

			if (facesContext.getCurrentPhaseId() == PhaseId.PROCESS_VALIDATIONS) {
				label = ComponentUtil.getComponentLabel(this);
			}
		}

		return label;
	}

	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}
}
