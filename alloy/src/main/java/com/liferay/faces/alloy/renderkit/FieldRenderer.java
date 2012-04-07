/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
		classNames.append("aui-field");

		String type = (String) attributes.get("type");

		if (type != null) {
			String lowerCaseType = type.trim().toLowerCase();

			if (lowerCaseType.equals("checkbox") || lowerCaseType.equals("boolean")) {
				classNames.append(" aui-field-choice");
			}
			else if (lowerCaseType.equals("menu") || lowerCaseType.equals("select")) {
				classNames.append(" aui-field-menu");
			}
		}
		else {
			classNames.append(" aui-field-text");
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

		// Inner <span>
		responseWriter.startElement("span", null);
		responseWriter.writeAttribute("class", "aui-field-content", null);

		String lowerCaseInlineLabel = "left";
		String inlineLabel = (String) attributes.get("inlineLabel");

		if (inlineLabel != null) {
			lowerCaseInlineLabel = inlineLabel.trim().toLowerCase();
		}

		// Left-aligned <label>
		if (lowerCaseInlineLabel.equals("left")) {
			responseWriter.startElement("label", null);
			responseWriter.writeAttribute("class", "aui-field-label", null);

			String label = (String) attributes.get("label");

			if (label != null) {
				responseWriter.write(label);
			}

			responseWriter.endElement("label");
		}

		// Inner-inner <span>
		responseWriter.startElement("span", null);
		classNames = new StringBuilder();
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
			responseWriter.writeAttribute("class", "aui-field-label", null);

			String label = (String) attributes.get("label");

			if (label != null) {
				responseWriter.write(label);
			}

			responseWriter.endElement("label");
		}

		// Inner </span>
		responseWriter.endElement("span");

		// Outer </span>
		responseWriter.endElement("span");
	}

}
