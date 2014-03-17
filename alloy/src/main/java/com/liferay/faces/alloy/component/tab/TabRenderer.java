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
package com.liferay.faces.alloy.component.tab;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererBase;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@FacesRenderer(componentFamily = Tab.COMPONENT_FAMILY, rendererType = Tab.RENDERER_TYPE)
public class TabRenderer extends RendererBase {

	// Private Constants
	private static final String TAB_PANE = "tab-pane";

	@Override
	protected void encodeHTMLBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the component.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringPool.DIV, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, uiComponent.getClientId(facesContext), StringPool.ID);
		encodeClassAttribute(responseWriter, (Styleable) uiComponent, TAB_PANE);
	}

	@Override
	protected void encodeHTMLEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the closing </div> element.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringPool.DIV);
	}
}
