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
package com.liferay.faces.alloy.component.datatable.internal;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.datatable.DataTable;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.internal.DelegationResponseWriter;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = DataTable.COMPONENT_FAMILY, rendererType = DataTable.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
	}
)
//J+
public class DataTableRenderer extends DataTableRendererBase {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		boolean hasTableHeaderFacet = (uiComponent.getFacet(StringPool.HEADER) != null);
		DelegationResponseWriter dataTableResponseWriter = new DataTableResponseWriter(responseWriter,
				hasTableHeaderFacet);
		super.encodeBegin(facesContext, uiComponent, dataTableResponseWriter);
	}

	@Override
	public String getDelegateComponentFamily() {
		return DataTable.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return DataTable.DELEGATE_RENDERER_TYPE;
	}
}
