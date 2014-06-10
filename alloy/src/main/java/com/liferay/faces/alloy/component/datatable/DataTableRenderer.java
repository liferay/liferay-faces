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
package com.liferay.faces.alloy.component.datatable;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.renderkit.DelegatingAlloyRendererBase;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.DelegationResponseWriter;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@FacesRenderer(componentFamily = DataTable.COMPONENT_FAMILY, rendererType = DataTable.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
	}
)
public class DataTableRenderer extends DelegatingAlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "DataTable";
	private static final String ALLOY_MODULE_NAME = "aui-datatable";

	// Protected Constants
	protected static final String[] MODULES = { ALLOY_MODULE_NAME };

	@Override
	public void encodeAlloyAttributes(ResponseWriter respoonseWriter, UIComponent uiComponent) throws IOException {
		// No-op since AlloyUI attributes are not exposed to JSF
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		boolean hasTableHeaderFacet = (uiComponent.getFacet(StringPool.HEADER) != null);
		DelegationResponseWriter dataTableResponseWriter = new DataTableResponseWriter(responseWriter, hasTableHeaderFacet);
		super.encodeBegin(facesContext, uiComponent, dataTableResponseWriter);
	}

	@Override
	public void encodeJavaScriptBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeJavaScriptBegin(facesContext, uiComponent);
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	public String getDelegateComponentFamily() {
		return DataTable.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return DataTable.DELEGATE_RENDERER_TYPE;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}
}
