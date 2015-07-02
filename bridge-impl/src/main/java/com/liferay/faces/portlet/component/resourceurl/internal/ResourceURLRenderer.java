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
package com.liferay.faces.portlet.component.resourceurl.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.portlet.BaseURL;
import javax.portlet.MimeResponse;

import com.liferay.faces.portlet.component.resourceurl.ResourceURL;


/**
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = ResourceURL.COMPONENT_FAMILY, rendererType = ResourceURL.RENDERER_TYPE)
//J+
public class ResourceURLRenderer extends ResourceURLRendererBase {

	@Override
	protected BaseURL getBaseURL(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		MimeResponse mimeResponse = (MimeResponse) externalContext.getResponse();
		javax.portlet.ResourceURL resourceURL = mimeResponse.createResourceURL();
		ResourceURL resourceURLComponent = (ResourceURL) uiComponent;
		String cacheability = resourceURLComponent.getCacheability();
		resourceURL.setCacheability(cacheability);

		String id = resourceURLComponent.getId();

		if (id != null) {
			resourceURL.setResourceID(id);
		}

		return resourceURL;
	}
}
