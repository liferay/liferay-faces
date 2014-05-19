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
package com.liferay.faces.bridge.renderkit.bridge;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

import com.liferay.faces.bridge.component.HtmlInputFile;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.map.ContextMapFactory;
import com.liferay.faces.bridge.event.FileUploadEvent;
import com.liferay.faces.bridge.model.UploadedFile;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class InputFileRenderer extends Renderer {

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		super.decode(facesContext, uiComponent);

		ContextMapFactory contextMapFactory = (ContextMapFactory) FactoryExtensionFinder.getFactory(
				ContextMapFactory.class);
		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		Map<String, Collection<UploadedFile>> uploadedFileMap = contextMapFactory.getUploadedFileMap(bridgeContext);

		if (uploadedFileMap != null) {

			String clientId = uiComponent.getClientId(facesContext);
			Collection<UploadedFile> uploadedFiles = uploadedFileMap.get(clientId);

			if ((uploadedFiles != null) && (uploadedFiles.size() > 0)) {

				HtmlInputFile htmlInputFile = (HtmlInputFile) uiComponent;

				htmlInputFile.setSubmittedValue(uploadedFiles);

				// Queue the FileUploadEvent so that each uploaded file can be handled individually with an
				// ActionListener.
				int i = 0;

				for (UploadedFile uploadedFile : uploadedFiles) {

					if (i == 0) {

						// Support legacy feature that is used in conjunction with the binding attribute.
						htmlInputFile.setUploadedFile(uploadedFile);
					}

					FileUploadEvent fileUploadEvent = new FileUploadEvent(uiComponent, uploadedFile);
					uiComponent.queueEvent(fileUploadEvent);
					i++;
				}
			}
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		String clientId = uiComponent.getClientId(facesContext);
		Map<String, Object> attributeMap = uiComponent.getAttributes();
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringPool.INPUT, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, clientId, null);
		responseWriter.writeAttribute(StringPool.NAME, clientId, null);

		// Write attributes related to <input type="file" />
		responseWriter.writeAttribute(StringPool.TYPE, "file", null);
		writePropertyAttribute(responseWriter, "accept", attributeMap);
		writePropertyAttribute(responseWriter, "size", attributeMap);

		// Write standard HTML input attributes
		writePropertyAttribute(responseWriter, "styleClass", StringPool.CLASS, attributeMap);
		writePropertyAttribute(responseWriter, "accesskey", attributeMap);
		writePropertyAttribute(responseWriter, "dir", attributeMap);
		writePropertyAttribute(responseWriter, "disabled", attributeMap);
		writePropertyAttribute(responseWriter, "lang", attributeMap);
		writePropertyAttribute(responseWriter, "style", attributeMap);
		writePropertyAttribute(responseWriter, "tabIndex", attributeMap);
		writePropertyAttribute(responseWriter, "title", attributeMap);
		writePropertyAttribute(responseWriter, "xml:lang", attributeMap);

		// Write attributes related to HTML5
		writePropertyAttribute(responseWriter, "multiple", attributeMap);

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

		responseWriter.endElement(StringPool.INPUT);
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

	@Override
	public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
		throws ConverterException {
		return submittedValue;
	}
}
