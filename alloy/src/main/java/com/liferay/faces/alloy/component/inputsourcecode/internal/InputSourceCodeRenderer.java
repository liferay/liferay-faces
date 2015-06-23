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
package com.liferay.faces.alloy.component.inputsourcecode.internal;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.inputsourcecode.InputSourceCode;
import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.internal.DelegationResponseWriter;
import com.liferay.faces.util.render.internal.HiddenTextResponseWriter;


/**
 * @author  Neil Griffin
 */
//J-
@FacesRenderer(componentFamily = InputSourceCode.COMPONENT_FAMILY, rendererType = InputSourceCode.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
	}
)
//J+
public class InputSourceCodeRenderer extends InputSourceCodeRendererBase {

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		InputSourceCode inputSourceCode = (InputSourceCode) uiComponent;
		Boolean readOnly = inputSourceCode.getReadOnly();

		ClientComponent clientComponent = (ClientComponent) uiComponent;
		String clientVarName = getClientVarName(facesContext, clientComponent);
		String clientKey = clientComponent.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}

		encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);

		if ((readOnly == null) || (!readOnly)) {

			String hiddenInputClientId = getHiddenInputClientId(facesContext, uiComponent);
			String escapedHiddenInputClientId = StringPool.POUND + escapeClientId(hiddenInputClientId);

			responseWriter.write(clientVarName);
			responseWriter.write(".getSession().on('change', function() {A.one('");
			responseWriter.write(escapedHiddenInputClientId);
			responseWriter.write("').set('value',");
			responseWriter.write(clientVarName);
			responseWriter.write(".getSession().getValue())});");
		}

		responseWriter.write(clientVarName);
		responseWriter.write(".editor.setOptions({ minLines: ");
		responseWriter.write(inputSourceCode.getMinLines().toString());
		responseWriter.write("});");
		responseWriter.write(clientVarName);
		responseWriter.write(".editor.setOptions({ maxLines: ");
		responseWriter.write(inputSourceCode.getMaxLines().toString());
		responseWriter.write("});");
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
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter,
		InputSourceCode inputSourceCode, boolean first) throws IOException {

		encodeWidgetRender(responseWriter, first);

		first = false;

		String boundingBox = getDefaultBoundingBoxClientId(facesContext, inputSourceCode);

		encodeClientId(responseWriter, BOUNDING_BOX, boundingBox, first);
	}

	protected String getDefaultBoundingBoxClientId(FacesContext facesContext, UIComponent uiComponent) {
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);

		return uiComponent.getClientId(facesContext) + separatorChar + BOUNDING_BOX;
	}

	public DelegationResponseWriter getDelegationResponseWriter(FacesContext facesContext, UIComponent uiComponent) {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String hiddenInputClientId = getHiddenInputClientId(facesContext, uiComponent);

		return new HiddenTextResponseWriter(responseWriter, hiddenInputClientId);
	}

	protected String getHiddenInputClientId(FacesContext facesContext, UIComponent uiComponent) {
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);

		return uiComponent.getClientId(facesContext) + separatorChar + "hidden";
	}
}
