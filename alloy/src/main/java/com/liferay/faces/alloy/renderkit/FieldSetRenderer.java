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
package com.liferay.faces.alloy.renderkit;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;


/**
 * @author  Neil Griffin
 */
public class FieldSetRenderer extends Renderer {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeBegin(facesContext, uiComponent);

		Map<String, Object> attributes = uiComponent.getAttributes();

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		// Outer <fieldset>
		responseWriter.startElement("fieldset", uiComponent);

		String id = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute("id", id, "id");

		StringBuilder classNames = new StringBuilder();
		classNames.append("aui-fieldset");

		Boolean columnFlag = Boolean.FALSE;
		String column = (String) attributes.get("column");

		if (column != null) {

			if (column.trim().equalsIgnoreCase("true")) {
				columnFlag = Boolean.TRUE;
			}
		}

		if (columnFlag) {
			classNames.append(" aui-column aui-form-column");
		}

		String cssClass = (String) attributes.get("cssClass");

		if ((cssClass != null) && (cssClass.length() > 0)) {
			classNames.append(" ");
			classNames.append(cssClass);
		}

		String styleClass = (String) attributes.get("styleClass");

		if ((styleClass != null) && (styleClass.length() > 0)) {
			classNames.append(" ");
			classNames.append(styleClass);
		}

		responseWriter.writeAttribute("class", classNames.toString(), null);

		// <legend>
		String label = (String) attributes.get("label");

		if ((label != null) && (label.length() > 0)) {
			responseWriter.startElement("legend", uiComponent);
			responseWriter.writeAttribute("class", "aui-fieldset-legend", null);

			// <span> inside the <legend>
			responseWriter.startElement("span", uiComponent);
			responseWriter.writeAttribute("class", "aui-legend", null);
			responseWriter.writeText(label, uiComponent, "label");
			responseWriter.endElement("span");
			responseWriter.endElement("legend");
		}

		// Inner <div>
		responseWriter.startElement("div", null);
		classNames = new StringBuilder();
		classNames.append("aui-fieldset-content");

		if (columnFlag) {
			classNames.append(" aui-column-content");
		}

		responseWriter.writeAttribute("class", classNames.toString(), null);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		// Inner </div>
		responseWriter.endElement("div");

		// Outer </fieldset>
		responseWriter.endElement("fieldset");
	}

}
