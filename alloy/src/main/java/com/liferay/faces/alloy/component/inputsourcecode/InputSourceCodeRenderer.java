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
package com.liferay.faces.alloy.component.inputsourcecode;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.DelegationResponseWriter;
import com.liferay.faces.util.render.HiddenTextResponseWriter;


/**
 * @author  Neil Griffin
 */
@FacesRenderer(componentFamily = InputSourceCode.COMPONENT_FAMILY, rendererType = InputSourceCode.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
	}
)
public class InputSourceCodeRenderer extends InputSourceCodeRendererBase {

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
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		InputSourceCode inputSourceCode = (InputSourceCode) uiComponent;
		Boolean readOnly = inputSourceCode.isReadOnly();

		if ((readOnly == null) || (!readOnly)) {

			ClientComponent clientComponent = (ClientComponent) uiComponent;
			String clientVarName = ComponentUtil.getClientVarName(facesContext, clientComponent);
			String clientKey = clientComponent.getClientKey();

			if (clientKey == null) {
				clientKey = clientVarName;
			}

			encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);

			String hiddenInputClientId = getHiddenInputClientId(facesContext, uiComponent);
			String escapedHiddenInputClientId = ComponentUtil.escapeClientId(hiddenInputClientId);
			String hiddenInputCssSelector = StringPool.POUND + escapedHiddenInputClientId;

			responseWriter.write(clientVarName);
			responseWriter.write(".getSession().on('change', function() {A.one('");
			responseWriter.write(hiddenInputCssSelector);
			responseWriter.write("').set('value',");
			responseWriter.write(clientVarName);
			responseWriter.write(".getSession().getValue())});");
		}
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		// Start the encoding of the outermost <div> element.
		String clientId = uiComponent.getClientId(facesContext);
		responseWriter.startElement(StringPool.DIV, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, clientId, StringPool.ID);

		// Encode the entire boundingbox <div>...<div> element.
		String defaultBoundingBoxClientId = getDefaultBoundingBoxClientId(facesContext, uiComponent);
		responseWriter.startElement(StringPool.DIV, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, defaultBoundingBoxClientId, StringPool.ID);
		responseWriter.endElement(StringPool.DIV);

		// Start the encoding of the hidden text input by delegating to the renderer from the JSF runtime.
		DelegationResponseWriter delegationResponseWriter = getDelegationResponseWriter(facesContext, uiComponent);
		super.encodeMarkupBegin(facesContext, uiComponent, delegationResponseWriter);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Finish the encoding of the hidden text input by delegating to the renderer from the JSF runtime.
		DelegationResponseWriter delegationResponseWriter = getDelegationResponseWriter(facesContext, uiComponent);
		super.encodeMarkupEnd(facesContext, uiComponent, delegationResponseWriter);

		// Finish the encoding of the outermost </div> element.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringPool.DIV);
	}

	@Override
	protected void encodeHiddenAttributes(ResponseWriter responseWriter, InputSourceCode inputSourceCode, boolean first)
		throws IOException {

		encodeWidgetRender(responseWriter, first);

		first = false;

		FacesContext facesContext = FacesContext.getCurrentInstance();
		String defaultBoundingBoxClientId = getDefaultBoundingBoxClientId(facesContext, inputSourceCode);
		String boundingBox = StringPool.POUND + ComponentUtil.escapeClientId(defaultBoundingBoxClientId);

		encodeString(responseWriter, AlloyRendererUtil.BOUNDING_BOX, boundingBox, first);
	}

	@Override
	protected void encodeValue(ResponseWriter responseWriter, InputSourceCode inputSourceCode, Object value,
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

		super.encodeValue(responseWriter, inputSourceCode, value, first);
	}

	protected String getDefaultBoundingBoxClientId(FacesContext facesContext, UIComponent uiComponent) {
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);

		return uiComponent.getClientId(facesContext) + separatorChar + AlloyRendererUtil.BOUNDING_BOX;
	}

	@Override
	public String getDelegateComponentFamily() {
		return InputSourceCode.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return InputSourceCode.DELEGATE_RENDERER_TYPE;
	}

	public DelegationResponseWriter getDelegationResponseWriter(FacesContext facesContext, UIComponent uiComponent) {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String hiddenInputClientId = getHiddenInputClientId(facesContext, uiComponent);

		return new HiddenTextResponseWriter(responseWriter, hiddenInputClientId);
	}

	protected String getHiddenInputClientId(FacesContext facesContext, UIComponent uiComponent) {
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);

		return uiComponent.getClientId(facesContext) + separatorChar + StringPool.HIDDEN;
	}
}
