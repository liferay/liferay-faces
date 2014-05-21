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
package com.liferay.faces.alloy.component.fieldset;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;
import javax.faces.application.ResourceDependency;


/**
 * @author  Kyle Stiemann
 */
@FacesRenderer(componentFamily = Fieldset.COMPONENT_FAMILY, rendererType = Fieldset.RENDERER_TYPE)
@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css")
public class FieldsetRenderer extends Renderer {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(Fieldset.FIELDSET, uiComponent);

		String clientId = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute(StringPool.ID, clientId, StringPool.ID);
		responseWriter.writeAttribute(StringPool.NAME, clientId, StringPool.ID);

		Fieldset fieldset = (Fieldset) uiComponent;

		boolean disabled = fieldset.isDisabled();

		if (disabled) {
			responseWriter.writeAttribute(StringPool.DISABLED, Boolean.toString(disabled), StringPool.DISABLED);
		}

		RendererUtil.encodeStyleable(responseWriter, fieldset);

		// Encode passthrough attributes.			
		String onclick = fieldset.getOnclick();

		if (onclick != null) {
			responseWriter.writeAttribute(Fieldset.ONCLICK, onclick, Fieldset.ONCLICK);
		}
			
		String ondblclick = fieldset.getOndblclick();

		if (ondblclick != null) {
			responseWriter.writeAttribute(Fieldset.ONDBLCLICK, ondblclick, Fieldset.ONDBLCLICK);
		}
			
		String onkeydown = fieldset.getOnkeydown();

		if (onkeydown != null) {
			responseWriter.writeAttribute(Fieldset.ONKEYDOWN, onkeydown, Fieldset.ONKEYDOWN);
		}
			
		String onkeypress = fieldset.getOnkeypress();

		if (onkeypress != null) {
			responseWriter.writeAttribute(Fieldset.ONKEYPRESS, onkeypress, Fieldset.ONKEYPRESS);
		}
			
		String onkeyup = fieldset.getOnkeyup();

		if (onkeyup != null) {
			responseWriter.writeAttribute(Fieldset.ONKEYUP, onkeyup, Fieldset.ONKEYUP);
		}
			
		String onmousedown = fieldset.getOnmousedown();

		if (onmousedown != null) {
			responseWriter.writeAttribute(Fieldset.ONMOUSEDOWN, onmousedown, Fieldset.ONMOUSEDOWN);
		}
			
		String onmousemove = fieldset.getOnmousemove();

		if (onmousemove != null) {
			responseWriter.writeAttribute(Fieldset.ONMOUSEMOVE, onmousemove, Fieldset.ONMOUSEMOVE);
		}
			
		String onmouseout = fieldset.getOnmouseout();

		if (onmouseout != null) {
			responseWriter.writeAttribute(Fieldset.ONMOUSEOUT, onmouseout, Fieldset.ONMOUSEOUT);
		}
			
		String onmouseover = fieldset.getOnmouseover();

		if (onmouseover != null) {
			responseWriter.writeAttribute(Fieldset.ONMOUSEOVER, onmouseover, Fieldset.ONMOUSEOVER);
		}
			
		String onmouseup = fieldset.getOnmouseup();

		if (onmouseup != null) {
			responseWriter.writeAttribute(Fieldset.ONMOUSEUP, onmouseup, Fieldset.ONMOUSEUP);
		}
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		Fieldset fieldset = (Fieldset) uiComponent;
		String legend = fieldset.getLegend();

		if (legend != null) {

			responseWriter.startElement(Fieldset.LEGEND, uiComponent);
			responseWriter.writeAttribute(StringPool.CLASS, Fieldset.LEGEND, null);
			responseWriter.writeText(legend, Fieldset.LEGEND);
			responseWriter.endElement(Fieldset.LEGEND);
		}

		super.encodeChildren(facesContext, uiComponent);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(Fieldset.FIELDSET);
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
