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
public class FieldRenderer extends Renderer {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeBegin(facesContext, uiComponent);

		Map<String, Object> attributes = uiComponent.getAttributes();

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		// Outer <span>
		responseWriter.startElement("span", uiComponent);

		String id = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute("id", id, "id");

		StringBuilder classNames = new StringBuilder();

		// aui_deprecated.css: field
		classNames.append("field");

		String type = (String) attributes.get("type");

		if (type != null) {
			String lowerCaseType = type.trim().toLowerCase();

			if (lowerCaseType.equals("checkbox") || lowerCaseType.equals("boolean")) {

				// aui-field-choice not found in 6.2 (all field-choice are nested css classes)
				classNames.append(" aui-field-choice");
			}
			else if (lowerCaseType.equals("menu") || lowerCaseType.equals("select")) {

				// aui-field-menu not found in 6.2
				classNames.append(" aui-field-menu");
			}
		}
		else {

			// aui-field-text not found in 6.2 (all field-text are nested css classes)
			classNames.append(" aui-field-text");
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

		// Inner <span>
		responseWriter.startElement("span", null);

		// aui_deprecated.css: field-content
		responseWriter.writeAttribute("class", "field-content", null);

		String lowerCaseInlineLabel = "left";
		String inlineLabel = (String) attributes.get("inlineLabel");

		if (inlineLabel != null) {
			lowerCaseInlineLabel = inlineLabel.trim().toLowerCase();
		}

		// Left-aligned <label>
		if (lowerCaseInlineLabel.equals("left")) {
			responseWriter.startElement("label", null);

			// aui_deprecated.css: field-label
			responseWriter.writeAttribute("class", "field-label", null);

			String label = (String) attributes.get("label");

			if (label != null) {
				responseWriter.writeText(label, uiComponent, "label");
			}

			responseWriter.endElement("label");
		}

		// Inner-inner <span>
		responseWriter.startElement("span", null);
		classNames = new StringBuilder();

		// aui-field-element not found in 6.2 (all field-element are nested css classes)
		classNames.append("aui-field-element");

		if (lowerCaseInlineLabel.equalsIgnoreCase("right")) {
			classNames.append(" aui-field-label-right");
		}

		responseWriter.writeAttribute("class", classNames.toString(), null);

	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		// Inner-inner </span>
		responseWriter.endElement("span");

		String lowerCaseInlineLabel = "";
		Map<String, Object> attributes = uiComponent.getAttributes();
		String inlineLabel = (String) attributes.get("inlineLabel");

		if (inlineLabel != null) {
			lowerCaseInlineLabel = inlineLabel.trim().toLowerCase();
		}

		// Right-aligned <label>
		if (lowerCaseInlineLabel.equals("right")) {
			responseWriter.startElement("label", null);

			// aui_deprecated.css: field-label
			responseWriter.writeAttribute("class", "field-label", null);

			String label = (String) attributes.get("label");

			if (label != null) {
				responseWriter.writeText(label, uiComponent, "label");
			}

			responseWriter.endElement("label");
		}

		// Inner </span>
		responseWriter.endElement("span");

		// Outer </span>
		responseWriter.endElement("span");
	}

}
