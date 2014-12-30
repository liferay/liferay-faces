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
package com.liferay.faces.alloy.component.panelgrid.internal;

import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.panelgrid.PanelGrid;
import com.liferay.faces.util.render.internal.DelegatingRendererBase;


/**
 * @author  Neil Griffin
 */
@FacesRenderer(componentFamily = PanelGrid.COMPONENT_FAMILY, rendererType = PanelGrid.RENDERER_TYPE)
public class PanelGridRenderer extends DelegatingRendererBase {

	@Override
	public String getDelegateComponentFamily() {
		return PanelGrid.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return PanelGrid.DELEGATE_RENDERER_TYPE;
	}
}
