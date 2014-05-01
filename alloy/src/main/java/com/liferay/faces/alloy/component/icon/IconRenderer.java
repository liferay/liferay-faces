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
package com.liferay.faces.alloy.component.icon;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Kyle Stiemann
 */
@FacesRenderer(componentFamily = Icon.COMPONENT_FAMILY, rendererType = Icon.RENDERER_TYPE)
public class IconRenderer extends Renderer {

	// Private Constants
	private static final String ICON_CLASS_PREFIX = "icon-";

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringPool.SPAN, uiComponent);

		String clientId = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute(StringPool.ID, clientId, StringPool.ID);

		Icon icon = (Icon) uiComponent;

		String name = icon.getName();

		if (name != null) {
			name = ICON_CLASS_PREFIX + name;
		}

		RendererUtil.encodeStylable(responseWriter, icon, name);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringPool.SPAN);
	}
}
