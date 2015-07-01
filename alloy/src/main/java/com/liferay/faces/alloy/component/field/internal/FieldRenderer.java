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
package com.liferay.faces.alloy.component.field.internal;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.field.Field;
import com.liferay.faces.alloy.component.selectbooleancheckbox.SelectBooleanCheckbox;


/**
 * @author  Kyle Stiemann
 */
@FacesRenderer(componentFamily = Field.COMPONENT_FAMILY, rendererType = Field.RENDERER_TYPE)
public class FieldRenderer extends FieldRendererBase {

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Field field = (Field) uiComponent;
		String label = field.getLabel();

		if (label != null) {

			ResponseWriter responseWriter = facesContext.getResponseWriter();
			UIComponent checkboxChild = getSelectBooleanCheckboxChild(uiComponent.getChildren());
			boolean labelFirst = field.isLabelFirst();

			if (checkboxChild != null) {

				responseWriter.startElement(LABEL, field);

				String checkboxChildClientId = checkboxChild.getClientId(facesContext);

				if (labelFirst) {

					encodeCheckboxLabelInnerHTML(responseWriter, label, checkboxChildClientId);
					super.encodeChildren(facesContext, field);
				}
				else {

					super.encodeChildren(facesContext, field);
					encodeCheckboxLabelInnerHTML(responseWriter, label, checkboxChildClientId);
				}

				responseWriter.endElement(LABEL);
			}
			else {

				if (labelFirst) {

					encodeLabel(responseWriter, field, label);
					super.encodeChildren(facesContext, field);
				}
				else {

					super.encodeChildren(facesContext, field);
					encodeLabel(responseWriter, field, label);
				}
			}
		}
		else {
			super.encodeChildren(facesContext, field);
		}
	}

	protected void encodeCheckboxLabelInnerHTML(ResponseWriter responseWriter, String label, String checkboxClientId)
		throws IOException {

		responseWriter.writeAttribute("class", "checkbox", null);
		responseWriter.writeAttribute("for", checkboxClientId, null);
		responseWriter.writeText(label, LABEL);
	}

	protected void encodeLabel(ResponseWriter responseWriter, Field field, String label) throws IOException {

		responseWriter.startElement(LABEL, field);
		responseWriter.writeAttribute("class", "control-label", null);
		responseWriter.writeText(label, LABEL);
		responseWriter.endElement(LABEL);
	}

	private UIComponent getSelectBooleanCheckboxChild(List<UIComponent> children) {

		UIComponent selectBooleanCheckboxChild = null;

		for (UIComponent child : children) {

			if (child instanceof SelectBooleanCheckbox) {

				selectBooleanCheckboxChild = child;

				break;
			}
		}

		return selectBooleanCheckboxChild;
	}
}
