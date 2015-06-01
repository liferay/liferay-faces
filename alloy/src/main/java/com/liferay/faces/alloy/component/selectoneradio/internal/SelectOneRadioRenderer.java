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
package com.liferay.faces.alloy.component.selectoneradio.internal;

import java.io.IOException;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.select.internal.LabelSurroundingResponseWriter;
import com.liferay.faces.alloy.component.select.internal.TableSuppressingResponseWriter;
import com.liferay.faces.alloy.component.selectoneradio.SelectOneRadio;


/**
 * @author  Vernon Singleton
 */
@FacesRenderer(componentFamily = SelectOneRadio.COMPONENT_FAMILY, rendererType = SelectOneRadio.RENDERER_TYPE)
@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.css")
public class SelectOneRadioRenderer extends SelectOneRadioRendererBase {

	@Override
	public void encodeAll(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		ResponseWriter delegationResponseWriter = getSelectOneRadioResponseWriter(responseWriter);
		facesContext.setResponseWriter(delegationResponseWriter);
		super.encodeAll(facesContext, uiComponent);
		facesContext.setResponseWriter(responseWriter);
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		ResponseWriter delegationResponseWriter = getSelectOneRadioResponseWriter(responseWriter);
		facesContext.setResponseWriter(delegationResponseWriter);
		super.encodeBegin(facesContext, uiComponent);
		facesContext.setResponseWriter(responseWriter);
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		ResponseWriter delegationResponseWriter = getSelectOneRadioResponseWriter(responseWriter);
		facesContext.setResponseWriter(delegationResponseWriter);
		super.encodeChildren(facesContext, uiComponent);
		facesContext.setResponseWriter(responseWriter);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		ResponseWriter delegationResponseWriter = getSelectOneRadioResponseWriter(responseWriter);
		facesContext.setResponseWriter(delegationResponseWriter);
		super.encodeEnd(facesContext, uiComponent);
		facesContext.setResponseWriter(responseWriter);
	}

	private ResponseWriter getSelectOneRadioResponseWriter(ResponseWriter responseWriter) {

		responseWriter = new TableSuppressingResponseWriter(responseWriter);
		responseWriter = new LabelSurroundingResponseWriter(responseWriter, "radio");

		return responseWriter;
	}
}
