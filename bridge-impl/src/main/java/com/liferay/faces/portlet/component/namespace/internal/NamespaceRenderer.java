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
package com.liferay.faces.portlet.component.namespace.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.portlet.component.namespace.Namespace;


/**
 * @author  Neil Griffin
 */
//J-
@FacesRenderer(componentFamily = Namespace.COMPONENT_FAMILY, rendererType = Namespace.RENDERER_TYPE)
//J+
public class NamespaceRenderer extends NamespaceRendererBase {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// no-op
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// no-op
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		String namespace = facesContext.getExternalContext().encodeNamespace("");
		Namespace namespaceComponent = (Namespace) uiComponent;
		String var = namespaceComponent.getVar();

		if (var == null) {

			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.write(namespace);
		}
		else {

			ExternalContext externalContext = facesContext.getExternalContext();
			externalContext.getRequestMap().put(var, namespace);
		}
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
