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
package com.liferay.faces.portal.component.header.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.portal.component.header.Header;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.render.RendererUtil;
import com.liferay.taglib.ui.HeaderTag;


/**
 * @author  Vernon Singleton
 */
//J-
@FacesRenderer(componentFamily = Header.COMPONENT_FAMILY, rendererType = Header.RENDERER_TYPE)
//J+
public class HeaderRenderer extends HeaderRendererBase {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the rich text editor.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);

		String clientId = uiComponent.getClientId();
		responseWriter.writeAttribute("id", clientId, null);
		RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent);

		// Begin the JSP tag lifecycle and write the output to the response.
		super.encodeBegin(facesContext, uiComponent);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// End writing the output of the JSP tag lifecycle.
		super.encodeEnd(facesContext, uiComponent);

		// Encode the ending <div> element that represents the rich text editor.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");
	}

	@Override
	public HeaderTag newTag() {
		return new HeaderTag();
	}

	@Override
	protected Header cast(UIComponent uiComponent) {
		return (Header) uiComponent;
	}

	@Override
	protected void copyFrameworkAttributes(FacesContext facesContext, Header header, HeaderTag headerTag) {
		headerTag.setCssClass(header.getStyleClass());
	}

	@Override
	protected void copyNonFrameworkAttributes(FacesContext facesContext, Header header, HeaderTag headerTag) {
		headerTag.setBackLabel(header.getBackLabel());
		headerTag.setBackURL(header.getBackURL());
		headerTag.setEscapeXml(header.isEscapeXml());
		headerTag.setShowBackURL(header.isShowBackURL());
		headerTag.setTitle(header.getTitle());
	}

	@Override
	public String getChildInsertionMarker() {
		return "</h3>";
	}
}
