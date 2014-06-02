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
package com.liferay.faces.alloy.component.inputsourcecode;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = InputSourceCode.COMPONENT_TYPE)
public class InputSourceCode extends InputSourceCodeBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.inputsourcecode.InputSourceCode";
	public static final String DELEGATE_COMPONENT_FAMILY = COMPONENT_FAMILY;
	public static final String DELEGATE_RENDERER_TYPE = "javax.faces.Text";
	public static final String RENDERER_TYPE =
		"com.liferay.faces.alloy.component.inputsourcecode.InputSourceCodeRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-input-source-code";

	public InputSourceCode() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getBoundingBox() {

		String boundingBox = super.getBoundingBox();

		if (boundingBox == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			String defaultBoundingBoxClientId = InputSourceCodeUtil.getDefaultBoundingBoxClientId(facesContext, this);
			boundingBox = StringPool.POUND + ComponentUtil.escapeClientId(defaultBoundingBoxClientId);
		}

		return boundingBox;
	}

	@Override
	public Boolean isWidgetRender() {
		return (Boolean) getStateHelper().eval(WIDGET_RENDER, true);
	}

	@Override
	public String getStyleClass() {

		String styleClass = (String) getStateHelper().eval(STYLE_CLASS, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}
}
