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
package com.liferay.faces.portal.component.permissionsurl.internal;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.servlet.jsp.JspException;

import com.liferay.faces.portal.component.permissionsurl.PermissionsURL;
import com.liferay.faces.portal.render.internal.PortalTagOutput;

import com.liferay.taglib.security.PermissionsURLTag;


/**
 * @author  Vernon Singleton
 */

//J-
@FacesRenderer(componentFamily = PermissionsURL.COMPONENT_FAMILY, rendererType = PermissionsURL.RENDERER_TYPE)
//J+
public class PermissionsURLRenderer extends PermissionsURLRendererBase {

	@Override
	public PermissionsURL cast(UIComponent uiComponent) {
		return (PermissionsURL) uiComponent;
	}

	@Override
	public void copyFrameworkAttributes(FacesContext facesContext, PermissionsURL permissionsURL,
		PermissionsURLTag permissionsURLTag) {
		permissionsURLTag.setId(permissionsURL.getClientId());
	}

	@Override
	public void copyNonFrameworkAttributes(FacesContext facesContext, PermissionsURL permissionsURL,
		PermissionsURLTag permissionsURLTag) {
		permissionsURLTag.setModelResource(permissionsURL.getModelResource());
		permissionsURLTag.setModelResourceDescription(permissionsURL.getModelResourceDescription());
		permissionsURLTag.setRedirect(permissionsURL.getRedirect());
		permissionsURLTag.setResourceGroupId(permissionsURL.getResourceGroupId());
		permissionsURLTag.setResourcePrimKey(permissionsURL.getResourcePrimKey());
		permissionsURLTag.setRoleTypes(permissionsURL.getRoleTypes());
		permissionsURLTag.setWindowState(permissionsURL.getWindowState());
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		try {

			// Get the URL from the tag output.
			PermissionsURLTag permissionsURLTag = newTag();
			copyAttributes(facesContext, cast(uiComponent), permissionsURLTag);

			PortalTagOutput portalTagOutput = getPortalTagOutput(facesContext, uiComponent, permissionsURLTag);
			String url = portalTagOutput.getMarkup();

			// If the user didn't specify a value for the "var" attribute, then write the URL to the response.
			PermissionsURL permissionsURL = (PermissionsURL) uiComponent;
			String varName = permissionsURL.getVar();

			if (varName == null) {
				ResponseWriter responseWriter = facesContext.getResponseWriter();
				responseWriter.write(url);
			}

			// Otherwise, place the URL into the request scope so that it can be resolved via EL with the name
			// specified in the "var" attribute.
			else {
				ExternalContext externalContext = facesContext.getExternalContext();
				Map<String, Object> requestMap = externalContext.getRequestMap();
				requestMap.put(varName, url);
			}
		}
		catch (JspException e) {
			throw new IOException(e);
		}
	}

	@Override
	public PermissionsURLTag newTag() {
		return new PermissionsURLTag();
	}
}
