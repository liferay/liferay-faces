/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.util.lang.StringPool;


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

		// aui_deprecated.css: fieldset
		classNames.append("fieldset");

		Boolean columnFlag = Boolean.FALSE;
		String column = (String) attributes.get("column");

		if (column != null) {

			if (column.trim().equalsIgnoreCase("true")) {
				columnFlag = Boolean.TRUE;
			}
		}

		if (columnFlag) {

			// aui_deprecated.css: column
			// aui-form-column not found in 6.2 (all form-column are nested css classes)
			classNames.append(" column aui-form-column");
		}

		String cssClass = (String) attributes.get("cssClass");

		if ((cssClass != null) && (cssClass.length() > 0)) {
			classNames.append(StringPool.SPACE);
			classNames.append(cssClass);
		}

		String styleClass = (String) attributes.get("styleClass");

		if ((styleClass != null) && (styleClass.length() > 0)) {
			classNames.append(StringPool.SPACE);
			classNames.append(styleClass);
		}

		responseWriter.writeAttribute("class", classNames.toString(), null);

		// <legend>
		String label = (String) attributes.get("label");

		if ((label != null) && (label.length() > 0)) {
			responseWriter.startElement("legend", uiComponent);

			// aui_deprecated.css: fieldset-legend
			responseWriter.writeAttribute("class", "fieldset-legend", null);

			// <span> inside the <legend>
			responseWriter.startElement("span", uiComponent);

			// Used to be aui-legend, but has been replaced by non-deprecated legend
			responseWriter.writeAttribute("class", "legend", null);
			responseWriter.writeText(label, uiComponent, "label");
			responseWriter.endElement("span");
			responseWriter.endElement("legend");
		}

		// Inner <div>
		responseWriter.startElement("div", null);
		classNames = new StringBuilder();

		// aui-fieldset-content not found in 6.2 (all fieldset-content are nested css classes)
		classNames.append("aui-fieldset-content");

		if (columnFlag) {

			// column-content not found in 6.2 (all column-content are nested css classes)
			classNames.append(" column-content");
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
