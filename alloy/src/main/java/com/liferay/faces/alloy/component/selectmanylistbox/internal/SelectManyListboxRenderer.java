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
package com.liferay.faces.alloy.component.selectmanylistbox.internal;

import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.selectmanylistbox.SelectManyListbox;
import com.liferay.faces.util.render.internal.DelegatingRendererBase;


/**
 * @author  Vernon Singleton
 */
@FacesRenderer(componentFamily = SelectManyListbox.COMPONENT_FAMILY, rendererType = SelectManyListbox.RENDERER_TYPE)
public class SelectManyListboxRenderer extends DelegatingRendererBase {

	@Override
	public String getDelegateComponentFamily() {
		return SelectManyListbox.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return SelectManyListbox.DELEGATE_RENDERER_TYPE;
	}
}
