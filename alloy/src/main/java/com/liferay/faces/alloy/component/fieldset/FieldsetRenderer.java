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

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Kyle Stiemann
 */
@FacesRenderer(componentFamily = Fieldset.COMPONENT_FAMILY, rendererType = Fieldset.RENDERER_TYPE)
@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css")
public class FieldsetRenderer extends FieldsetRendererBase {

	// Protected Constants
	protected static final String FIELDSET = "fieldset";

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(FIELDSET, uiComponent);

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
			responseWriter.writeAttribute(ONCLICK, onclick, ONCLICK);
		}

		String ondblclick = fieldset.getOndblclick();

		if (ondblclick != null) {
			responseWriter.writeAttribute(ONDBLCLICK, ondblclick, ONDBLCLICK);
		}

		String onkeydown = fieldset.getOnkeydown();

		if (onkeydown != null) {
			responseWriter.writeAttribute(ONKEYDOWN, onkeydown, ONKEYDOWN);
		}

		String onkeypress = fieldset.getOnkeypress();

		if (onkeypress != null) {
			responseWriter.writeAttribute(ONKEYPRESS, onkeypress, ONKEYPRESS);
		}

		String onkeyup = fieldset.getOnkeyup();

		if (onkeyup != null) {
			responseWriter.writeAttribute(ONKEYUP, onkeyup, ONKEYUP);
		}

		String onmousedown = fieldset.getOnmousedown();

		if (onmousedown != null) {
			responseWriter.writeAttribute(ONMOUSEDOWN, onmousedown, ONMOUSEDOWN);
		}

		String onmousemove = fieldset.getOnmousemove();

		if (onmousemove != null) {
			responseWriter.writeAttribute(ONMOUSEMOVE, onmousemove, ONMOUSEMOVE);
		}

		String onmouseout = fieldset.getOnmouseout();

		if (onmouseout != null) {
			responseWriter.writeAttribute(ONMOUSEOUT, onmouseout, ONMOUSEOUT);
		}

		String onmouseover = fieldset.getOnmouseover();

		if (onmouseover != null) {
			responseWriter.writeAttribute(ONMOUSEOVER, onmouseover, ONMOUSEOVER);
		}

		String onmouseup = fieldset.getOnmouseup();

		if (onmouseup != null) {
			responseWriter.writeAttribute(ONMOUSEUP, onmouseup, ONMOUSEUP);
		}
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		Fieldset fieldset = (Fieldset) uiComponent;
		String legend = fieldset.getLegend();

		if (legend != null) {

			responseWriter.startElement(LEGEND, uiComponent);
			responseWriter.writeAttribute(StringPool.CLASS, LEGEND, null);
			responseWriter.writeText(legend, LEGEND);
			responseWriter.endElement(LEGEND);
		}

		super.encodeChildren(facesContext, uiComponent);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(FIELDSET);
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
