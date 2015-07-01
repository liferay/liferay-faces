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
package com.liferay.faces.alloy.renderkit.internal;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;


/**
 * @author  Neil Griffin
 */
public class TextBoxListItemRenderer extends Renderer {

	// Private Constants
	private static final String CSS_CLASS = "cssClass";
	private static final String STYLE_CLASS = "styleClass";

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeBegin(facesContext, uiComponent);

		Map<String, Object> attributes = uiComponent.getAttributes();

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		responseWriter.startElement("li", uiComponent);

		String id = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute("id", id, "id");

		StringBuilder classNames = new StringBuilder();

		// aui_deprecated.css: textboxlistentry textboxlistentry-focused
		// aui-widget not found in 6.2
		// aui-component found in 6.2 only in javascript TODO
		classNames.append("aui-widget aui-component textboxlistentry textboxlistentry-focused");

		String cssClass = (String) attributes.get(CSS_CLASS);

		if ((cssClass != null) && (cssClass.length() > 0)) {
			classNames.append(" ");
			classNames.append(cssClass);
		}

		String styleClass = (String) attributes.get(STYLE_CLASS);

		if ((styleClass != null) && (styleClass.length() > 0)) {
			classNames.append(" ");
			classNames.append(styleClass);
		}

		responseWriter.writeAttribute("class", classNames.toString(), null);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("li");
	}

}
