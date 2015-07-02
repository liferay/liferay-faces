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
package com.liferay.faces.portal.component.runtime.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.portal.component.runtime.Runtime;

import com.liferay.taglib.portletext.RuntimeTag;


/**
 * @author  Juan Gonzalez
 */
//J-
@FacesRenderer(componentFamily = Runtime.COMPONENT_FAMILY, rendererType = Runtime.RENDERER_TYPE)
//J+
public class RuntimeRenderer extends RuntimeRendererBase {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the portlet.
		// This is needed as it is possible to not render the portlet boundaries

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);
		responseWriter.writeAttribute("id", uiComponent.getClientId(), "id");

		// Delegate to PortalTagRenderer so that the JSP tag output will get encoded.
		super.encodeBegin(facesContext, uiComponent);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Delegate to PortalTagRenderer so that the JSP tag output will get encoded.
		super.encodeEnd(facesContext, uiComponent);

		// Encode the closing </div> element for the navBar.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");
	}

	@Override
	public RuntimeTag newTag() {
		return new RuntimeTag();
	}

	@Override
	protected Runtime cast(UIComponent uiComponent) {
		return (Runtime) uiComponent;
	}

	@Override
	protected void copyFrameworkAttributes(FacesContext facesContext, Runtime runtime, RuntimeTag runtimeTag) {
		runtimeTag.setDefaultPreferences(runtime.getDefaultPreferences());
		runtimeTag.setPortletName(runtime.getPortletName());
		runtimeTag.setQueryString(runtime.getQueryString());
	}

	@Override
	protected void copyNonFrameworkAttributes(FacesContext facesContext, Runtime u, RuntimeTag t) {

	}

	@Override
	public String getChildInsertionMarker() {
		return "</div>";
	}
}
