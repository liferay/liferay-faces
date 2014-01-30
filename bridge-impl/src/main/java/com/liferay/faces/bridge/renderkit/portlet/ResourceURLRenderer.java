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
import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;

import com.liferay.faces.bridge.component.PortletResourceURL;


/**
 * This is the default renderer for the {@link PortletResourceURL} component. It conforms as closely as possible to the
 * requirements set forth in section PLT.26.4 of the JSR 286 Portlet Specification, Version 2.0.
 *
 * @author  Neil Griffin
 */

public class ResourceURLRenderer extends BaseURLRenderer {

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		MimeResponse mimeResponse = (MimeResponse) externalContext.getResponse();
		Map<String, Object> attributes = uiComponent.getAttributes();
		PortletURL portletURL = (PortletURL) mimeResponse.createResourceURL();
		ResourceURL resourceURL = (ResourceURL) portletURL;
		String id = (String) attributes.get("id");
		String cacheability = (String) attributes.get("cacheability");

		try {

			if (id != null) {
				resourceURL.setResourceID(id);
			}

			if (cacheability != null) {
				resourceURL.setCacheability(cacheability);
			}
			else {
				resourceURL.setCacheability(ResourceURL.PAGE);
			}

			super.encodeEnd(facesContext, uiComponent, resourceURL);
		}
		catch (Exception e) {
			throw new IOException();
		}

	}

}
