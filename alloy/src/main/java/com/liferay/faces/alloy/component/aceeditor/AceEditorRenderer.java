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
package com.liferay.faces.alloy.component.aceeditor;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.component.ValueHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.LiferayComponent;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
@FacesRenderer(componentFamily = AceEditor.COMPONENT_FAMILY, rendererType = AceEditor.RENDERER_TYPE)
@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
public class AceEditorRenderer extends AceEditorRendererBase {

	// Private Constants
	private static final String ESCAPED_BACKSLASH = "\\\\";
	private static final String ESCAPED_DOUBLE_QUOTE = "\\\\\"";
	private static final String ESCAPED_BACKSLASH_DOUBLE_QUOTE = ESCAPED_BACKSLASH + ESCAPED_DOUBLE_QUOTE;
	private static final String ESCAPED_NEWLINE = "\\\\n";
	private static final String ESCAPED_BACKSLASH_NEWLINE = ESCAPED_BACKSLASH + ESCAPED_NEWLINE;
	private static final String ESCAPED_TAB = "\\\\t";
	private static final String ESCAPED_BACKSLASH_TAB = ESCAPED_BACKSLASH + ESCAPED_TAB;
	private static final String REGEX_DOUBLE_QUOTE = "[\"]";
	private static final String REGEX_ESCAPED_DOUBLE_QUOTE = "[\\\\][\"]";
	private static final String REGEX_ESCAPED_NEWLINE = "[\\\\]n";
	private static final String REGEX_ESCAPED_TAB = "[\\\\]t";
	private static final String REGEX_NEWLINE = "\n";

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String clientId = uiComponent.getClientId();
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String hiddenInputClientId = clientId + separatorChar + StringPool.HIDDEN;
		String submittedValue = requestParameterMap.get(hiddenInputClientId);

		if (submittedValue != null) {
			UIInput uiInput = (UIInput) uiComponent;
			uiInput.setSubmittedValue(submittedValue);
		}
	}

	@Override
	protected void encodeHTMLBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringPool.DIV, uiComponent);

		String clientId = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute(StringPool.ID, clientId, StringPool.ID);
		responseWriter.endElement(StringPool.DIV);

		AceEditorComponent aceEditorComponent = (AceEditorComponent) uiComponent;
		Boolean readOnly = aceEditorComponent.isReadOnly();

		if ((readOnly == null) || (!readOnly)) {

			char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
			String hiddenInputClientId = clientId + separatorChar + StringPool.HIDDEN;

			responseWriter.startElement(StringPool.INPUT, uiComponent);
			responseWriter.writeAttribute(StringPool.ID, hiddenInputClientId, null);
			responseWriter.writeAttribute(StringPool.NAME, hiddenInputClientId, null);
			responseWriter.writeAttribute(StringPool.TYPE, StringPool.HIDDEN, null);

			ValueHolder valueHolder = (ValueHolder) uiComponent;
			responseWriter.writeAttribute(StringPool.VALUE, valueHolder.getValue(), StringPool.VALUE);
			responseWriter.endElement(StringPool.INPUT);
		}
	}

	@Override
	protected void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		AceEditorComponent aceEditorComponent = (AceEditorComponent) uiComponent;
		Boolean readOnly = aceEditorComponent.isReadOnly();

		if ((readOnly == null) || (!readOnly)) {

			String varName = ComponentUtil.getVarName(facesContext, (LiferayComponent) uiComponent);
			encodeLiferayComponent(responseWriter, varName);

			char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
			String hiddenInputClientId = uiComponent.getClientId() + separatorChar + StringPool.HIDDEN;
			String encodedHiddenInputClientId = StringPool.POUND + ComponentUtil.escapeClientId(hiddenInputClientId);

			responseWriter.write(varName);
			responseWriter.write(".getSession().on('change', function() {A.one('");
			responseWriter.write(encodedHiddenInputClientId);
			responseWriter.write("').set('value',");
			responseWriter.write(varName);
			responseWriter.write(".getSession().getValue())});");
		}
	}

	@Override
	protected void encodeValue(ResponseWriter responseWriter, AceEditorComponent aceEditorComponent, Object value,
		boolean first) throws IOException {

		if (value instanceof String) {
			String valueAsString = (String) value;
			valueAsString = valueAsString.replaceAll(REGEX_ESCAPED_TAB, ESCAPED_BACKSLASH_TAB);
			valueAsString = valueAsString.replaceAll(REGEX_ESCAPED_NEWLINE, ESCAPED_BACKSLASH_NEWLINE);
			valueAsString = valueAsString.replaceAll(REGEX_NEWLINE, ESCAPED_NEWLINE);
			valueAsString = valueAsString.replaceAll(REGEX_ESCAPED_DOUBLE_QUOTE, ESCAPED_BACKSLASH_DOUBLE_QUOTE);
			valueAsString = valueAsString.replaceAll(REGEX_DOUBLE_QUOTE, ESCAPED_DOUBLE_QUOTE);
			value = valueAsString;
		}

		super.encodeValue(responseWriter, aceEditorComponent, value, first);
	}

	@Override
	public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue)
		throws ConverterException {

		return ComponentUtil.convertSubmittedValue(facesContext, (ValueHolder) uiComponent, submittedValue);
	}
}
