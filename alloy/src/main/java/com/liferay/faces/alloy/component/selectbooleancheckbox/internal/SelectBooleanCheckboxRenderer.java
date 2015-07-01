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
package com.liferay.faces.alloy.component.selectbooleancheckbox.internal;

import java.io.IOException;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.field.Field;
import com.liferay.faces.alloy.component.selectbooleancheckbox.SelectBooleanCheckbox;
import com.liferay.faces.util.render.internal.IdDelegationResponseWriter;


/**
 * This is the renderer for the {@link SelectBooleanCheckbox} component. All of the methods in this renderer delegate to
 * the renderer provided by the JSF runtime for for {@link HtmlSelectBooleanCheckbox}, along with one added feature.
 * Specifically, this renderer overcomes the limitation outlined <a
 * href="http://balusc.blogspot.com/2008/09/validate-required-checkbox.html">this blog post</a> in which the "required"
 * attribute of {@link HtmlSelectBooleanCheckbox} is effectively disabled.
 *
 * @author  Vernon Singleton
 */
@FacesRenderer(
	componentFamily = SelectBooleanCheckbox.COMPONENT_FAMILY, rendererType = SelectBooleanCheckbox.RENDERER_TYPE
)
@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.css")
public class SelectBooleanCheckboxRenderer extends SelectBooleanCheckboxRendererBase {

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		super.decode(facesContext, uiComponent);

		SelectBooleanCheckbox selectBooleanCheckbox = (SelectBooleanCheckbox) uiComponent;

		Object submittedValue = selectBooleanCheckbox.getSubmittedValue();

		if (selectBooleanCheckbox.isRequired() && submittedValue.getClass().equals(Boolean.class) &&
				Boolean.FALSE.equals(submittedValue)) {
			selectBooleanCheckbox.setSubmittedValue("");
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		UIComponent parent = uiComponent.getParent();

		if (parent instanceof Field) {

			// Force the JSF runtime to output the "id" attribute so that the FieldRenderer can encode a label
			// element with a "for" attribute that associates the label with this checkbox.
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			IdDelegationResponseWriter idDelegationResponseWriter = new IdDelegationResponseWriter(responseWriter,
					"input", uiComponent.getClientId(facesContext));
			super.encodeEnd(facesContext, uiComponent, idDelegationResponseWriter);
		}
		else {
			super.encodeEnd(facesContext, uiComponent);
		}
	}

	@Override
	public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue)
		throws ConverterException {

		Object convertedValue = super.getConvertedValue(facesContext, uiComponent, submittedValue);

		SelectBooleanCheckbox selectBooleanCheckbox = (SelectBooleanCheckbox) uiComponent;

		if (selectBooleanCheckbox.isRequired() && "".equals(submittedValue)) {
			convertedValue = submittedValue;
		}

		return convertedValue;
	}
}
