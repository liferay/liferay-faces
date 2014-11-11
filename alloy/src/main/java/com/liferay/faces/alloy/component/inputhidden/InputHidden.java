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
package com.liferay.faces.alloy.component.inputhidden;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = InputHidden.COMPONENT_TYPE)
public class InputHidden extends InputHiddenBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.inputhidden.InputHidden";
	public static final String DELEGATE_COMPONENT_FAMILY = COMPONENT_FAMILY;
	public static final String DELEGATE_RENDERER_TYPE = "javax.faces.Hidden";
	public static final String RENDERER_TYPE =
		"com.liferay.faces.alloy.component.inputhidden.internal.InputHiddenRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-input-hidden";

	public InputHidden() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getLabel() {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (facesContext.getCurrentPhaseId() == PhaseId.PROCESS_VALIDATIONS) {
			return ComponentUtil.getComponentLabel(this);
		}
		else {
			return null;
		}
	}
}
