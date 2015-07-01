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
package com.liferay.faces.alloy.component.panel.internal;

import java.io.IOException;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.panel.Panel;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = Panel.COMPONENT_FAMILY, rendererType = Panel.RENDERER_TYPE)
@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
//J+
public class PanelRenderer extends PanelRendererBase {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Delegate to the JSF runtime renderer in order to start encoding the outermost <div> of the panel.
		super.encodeBegin(facesContext, uiComponent);

		// If necessary, encode the <div>...</div> for the panel header.
		Panel panel = (Panel) uiComponent;
		String headerText = panel.getHeaderText();
		UIComponent headerFacet = uiComponent.getFacet("header");
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if ((headerFacet != null) || (headerText != null)) {
			responseWriter.startElement("div", uiComponent);
			responseWriter.writeAttribute("class", "alloy-panel-heading", null);

			if (headerFacet != null) {
				headerFacet.encodeAll(facesContext);
			}
			else {
				responseWriter.startElement("span", null);
				responseWriter.writeAttribute("class", "alloy-panel-title", null);
				responseWriter.writeText(headerText, null);
				responseWriter.endElement("span");
			}

			responseWriter.endElement("div");
		}

		// Encode a starting <div> for the panel body.
		responseWriter.startElement("div", uiComponent);
		responseWriter.writeAttribute("class", "alloy-panel-body", null);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the ending </div> for the panel body.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");

		// If necessary, encode the <div>...</div> for the panel header.
		Panel panel = (Panel) uiComponent;
		String footerText = panel.getFooterText();
		UIComponent footerFacet = uiComponent.getFacet("footer");

		if ((footerFacet != null) || (footerText != null)) {
			responseWriter.startElement("div", uiComponent);
			responseWriter.writeAttribute("class", "alloy-panel-footer", null);

			if (footerFacet != null) {
				footerFacet.encodeAll(facesContext);
			}
			else {
				responseWriter.startElement("span", null);
				responseWriter.writeText(footerText, null);
				responseWriter.endElement("span");
			}

			responseWriter.endElement("div");
		}

		// Delegate to the JSF runtime renderer in order to finish encoding the outermost </div> of the panel.
		super.encodeEnd(facesContext, uiComponent);
	}
}
