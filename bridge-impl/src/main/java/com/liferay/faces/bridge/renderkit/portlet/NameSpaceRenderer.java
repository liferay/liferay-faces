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
package com.liferay.faces.bridge.renderkit.portlet;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;


/**
 * This is the name space generated for a portlet for the {@link PortletNameSpace} component. It conforms as closely as
 * possible to the requirements set forth in section PLT.26.5 of the JSR 286 Portlet Specification, Version 2.0.
 *
 * @author  Neil Griffin
 */
public class NameSpaceRenderer extends Renderer {

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		try {
			Map<String, Object> attributes = uiComponent.getAttributes();
			String namespace = facesContext.getExternalContext().encodeNamespace("");

			// If the user didn't specify a value for the "var" attribute, then write the URL to the response.
			String varName = (String) attributes.get("var");

			if (varName == null) {
				ResponseWriter responseWriter = facesContext.getResponseWriter();
				responseWriter.write(namespace);
			}

			// Otherwise, place the url into the request scope so that it can be resolved via EL with the name
			// specified in the "var" attribute.
			else {
				ExternalContext externalContext = facesContext.getExternalContext();
				externalContext.getRequestMap().put(varName, namespace);
			}
		}
		catch (Exception e) {
			throw new IOException();
		}

	}

}
