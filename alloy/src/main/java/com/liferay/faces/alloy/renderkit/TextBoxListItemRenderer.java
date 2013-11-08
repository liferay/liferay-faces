/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.alloy.renderkit;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.alloy.util.StringConstants;


/**
 * @author  Neil Griffin
 */
public class TextBoxListItemRenderer extends Renderer {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeBegin(facesContext, uiComponent);

		Map<String, Object> attributes = uiComponent.getAttributes();

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		responseWriter.startElement(StringConstants.ELEMENT_LI, uiComponent);

		String id = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute(StringConstants.ATTRIBUTE_ID, id, StringConstants.ATTRIBUTE_ID);

		StringBuilder classNames = new StringBuilder();

		// aui_deprecated.css: textboxlistentry textboxlistentry-focused
		// aui-widget not found in 6.2
		// aui-component found in 6.2 only in javascript TODO
		classNames.append("aui-widget aui-component textboxlistentry textboxlistentry-focused");

		String cssClass = (String) attributes.get(StringConstants.ATTRIBUTE_CSS_CLASS);

		if ((cssClass != null) && (cssClass.length() > 0)) {
			classNames.append(StringConstants.CHAR_SPACE);
			classNames.append(cssClass);
		}

		String styleClass = (String) attributes.get(StringConstants.ATTRIBUTE_STYLE_CLASS);

		if ((styleClass != null) && (styleClass.length() > 0)) {
			classNames.append(StringConstants.CHAR_SPACE);
			classNames.append(styleClass);
		}

		responseWriter.writeAttribute(StringConstants.ATTRIBUTE_CLASS, classNames.toString(), null);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringConstants.ELEMENT_LI);
	}

}
