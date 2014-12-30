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
package com.liferay.faces.alloy.component.selectmanycheckbox.internal;

import javax.faces.application.ResourceDependency;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.selectmanycheckbox.SelectManyCheckbox;
import com.liferay.faces.util.render.internal.DelegatingRendererBase;


/**
 * @author  Vernon Singleton
 */
@FacesRenderer(componentFamily = SelectManyCheckbox.COMPONENT_FAMILY, rendererType = SelectManyCheckbox.RENDERER_TYPE)
@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.css")
public class SelectManyCheckboxRenderer extends DelegatingRendererBase {

	@Override
	public String getDelegateComponentFamily() {
		return SelectManyCheckbox.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return SelectManyCheckbox.DELEGATE_RENDERER_TYPE;
	}
}
