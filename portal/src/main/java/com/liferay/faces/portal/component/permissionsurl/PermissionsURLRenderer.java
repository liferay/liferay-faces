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
package com.liferay.faces.portal.component.permissionsurl;

import java.io.IOException;

import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.util.jsp.PageContextAdapter;
import com.liferay.faces.util.jsp.StringJspWriter;

import com.liferay.portal.util.PortalUtil;

import com.liferay.taglib.security.PermissionsURLTag;


/**
 * @author  Vernon Singleton
 */

//J-
@FacesRenderer(componentFamily = PermissionsURL.COMPONENT_FAMILY, rendererType = PermissionsURL.RENDERER_TYPE)
//J+
public class PermissionsURLRenderer extends PermissionsURLRendererBase {

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Setup the Facelet -> JSP tag adapter
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
		HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
		ELContext elContext = facesContext.getELContext();
		StringJspWriter stringJspWriter = new StringJspWriter();
		PageContextAdapter pageContextAdapter = new PageContextAdapter(httpServletRequest, httpServletResponse,
				elContext, stringJspWriter);

		// Copy the attributes from the Facelet tag to the JSP tag.
		PermissionsURL permissionsURL = (PermissionsURL) uiComponent;
		PermissionsURLTag permissionsURLTag = new PermissionsURLTag();
		permissionsURLTag.setPageContext(pageContextAdapter);
		permissionsURLTag.setModelResource(permissionsURL.getModelResource());
		permissionsURLTag.setModelResourceDescription(permissionsURL.getModelResourceDescription());
		permissionsURLTag.setRedirect(permissionsURL.getRedirect());
		permissionsURLTag.setResourceGroupId(permissionsURL.getResourceGroupId());
		permissionsURLTag.setResourcePrimKey(permissionsURL.getResourcePrimKey());
		permissionsURLTag.setRoleTypes(permissionsURL.getRoleTypes());
		permissionsURLTag.setWindowState(permissionsURL.getWindowState());

		try {

			// Invoke the JSP tag lifecycle directly (rather than using the tag from a JSP).
			permissionsURLTag.doStartTag();
			permissionsURLTag.doEndTag();

			// Get the URL from the tag output.
			String url = stringJspWriter.toString();

			// If the user didn't specify a value for the "var" attribute, then write the URL to the response.
			String varName = permissionsURL.getVar();

			if (varName == null) {
				ResponseWriter responseWriter = facesContext.getResponseWriter();
				responseWriter.write(url);
			}

			// Otherwise, place the URL into the request scope so that it can be resolved via EL with the name
			// specified in the "var" attribute.
			else {
				externalContext.getRequestMap().put(varName, url);
			}
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}
}
