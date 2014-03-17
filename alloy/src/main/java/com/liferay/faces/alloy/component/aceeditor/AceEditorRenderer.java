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

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
@FacesRenderer(componentFamily = AceEditor.COMPONENT_FAMILY, rendererType = AceEditor.RENDERER_TYPE)
@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
public class AceEditorRenderer extends AceEditorRendererBase {

	@Override
	protected void encodeHTMLBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		responseWriter.startElement(StringPool.DIV, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, uiComponent.getClientId(), StringPool.ID);
		responseWriter.endElement(StringPool.DIV);
	}
}
