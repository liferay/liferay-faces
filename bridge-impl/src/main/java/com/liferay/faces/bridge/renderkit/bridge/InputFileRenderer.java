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
package com.liferay.faces.bridge.renderkit.bridge;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.bridge.component.HtmlInputFile;
import com.liferay.faces.bridge.component.UploadedFile;
import com.liferay.faces.bridge.context.map.RequestParameterMap;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class InputFileRenderer extends Renderer {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputFileRenderer.class);

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {
		super.decode(facesContext, uiComponent);

		String clientId = uiComponent.getClientId(facesContext);
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String submittedValue = requestParameterMap.get(clientId);

		if (submittedValue != null) {
			HtmlInputFile htmlInputFile = (HtmlInputFile) uiComponent;
			htmlInputFile.setSubmittedValue(submittedValue);

			Map<String, Object> requestAttributeMap = externalContext.getRequestMap();
			@SuppressWarnings("unchecked")
			Map<String, UploadedFile> facesFileMap = (Map<String, UploadedFile>) requestAttributeMap.get(
					RequestParameterMap.PARAM_UPLOADED_FILES);
			UploadedFile uploadedFile = facesFileMap.get(clientId);
			htmlInputFile.setUploadedFile(uploadedFile);

			logger.debug("Setting submittedValue=[{0}]", submittedValue);
		}

	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		String clientId = uiComponent.getClientId(facesContext);
		Map<String, Object> attributeMap = uiComponent.getAttributes();
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("input", uiComponent);
		responseWriter.writeAttribute("id", clientId, null);
		responseWriter.writeAttribute("name", clientId, null);

		// Write attributes related to <input type="file" />
		responseWriter.writeAttribute("type", "file", null);
		writePropertyAttribute(responseWriter, "accept", attributeMap);
		writePropertyAttribute(responseWriter, "size", attributeMap);

		// Write standard HTML input attributes
		writePropertyAttribute(responseWriter, "styleClass", "class", attributeMap);
		writePropertyAttribute(responseWriter, "accesskey", attributeMap);
		writePropertyAttribute(responseWriter, "dir", attributeMap);
		writePropertyAttribute(responseWriter, "disabled", attributeMap);
		writePropertyAttribute(responseWriter, "lang", attributeMap);
		writePropertyAttribute(responseWriter, "style", attributeMap);
		writePropertyAttribute(responseWriter, "tabIndex", attributeMap);
		writePropertyAttribute(responseWriter, "title", attributeMap);
		writePropertyAttribute(responseWriter, "xml:lang", attributeMap);

		// Write event attributes
		writePropertyAttribute(responseWriter, "onblur", attributeMap);
		writePropertyAttribute(responseWriter, "onchange", attributeMap);
		writePropertyAttribute(responseWriter, "onclick", attributeMap);
		writePropertyAttribute(responseWriter, "ondblclick", attributeMap);
		writePropertyAttribute(responseWriter, "onfocus", attributeMap);
		writePropertyAttribute(responseWriter, "onmousedown", attributeMap);
		writePropertyAttribute(responseWriter, "onmousemove", attributeMap);
		writePropertyAttribute(responseWriter, "onmouseout", attributeMap);
		writePropertyAttribute(responseWriter, "onmouseover", attributeMap);
		writePropertyAttribute(responseWriter, "onmouseup", attributeMap);
		writePropertyAttribute(responseWriter, "onkeydown", attributeMap);
		writePropertyAttribute(responseWriter, "onkeypress", attributeMap);
		writePropertyAttribute(responseWriter, "onkeyup", attributeMap);
		writePropertyAttribute(responseWriter, "onselect", attributeMap);

		responseWriter.endElement("input");
	}

	protected void writePropertyAttribute(ResponseWriter responseWriter, String name, Map<String, Object> attributeMap)
		throws IOException {

		String value = (String) attributeMap.get(name);

		if (value != null) {
			responseWriter.writeAttribute(name, value, name);
		}
	}

	protected void writePropertyAttribute(ResponseWriter responseWriter, String name, String property,
		Map<String, Object> attributeMap) throws IOException {

		String value = (String) attributeMap.get(name);

		if (value != null) {
			responseWriter.writeAttribute(name, value, property);
		}
	}
}
