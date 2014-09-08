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
import java.util.Map;

import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.util.jsp.PageContextAdapter;
import com.liferay.faces.util.jsp.StringJspWriter;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.util.PortalUtil;

import com.liferay.taglib.security.PermissionsURLTag;


/**
 * @author  Vernon Singleton
 */

//J-
@FacesRenderer(componentFamily = PermissionsURL.COMPONENT_FAMILY, rendererType = PermissionsURL.RENDERER_TYPE)
//J+
public class PermissionsURLRenderer extends PermissionsURLRendererBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PermissionsURLRenderer.class);

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();

		if (portletResponse instanceof MimeResponse) {

			ResponseWriter responseWriter = facesContext.getResponseWriter();
			PermissionsURL permissionsURL = (PermissionsURL) uiComponent;

			// Get the underlying HttpServletRequest and HttpServletResponse
			PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
			HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
			HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
			ELContext elContext = facesContext.getELContext();
			StringJspWriter stringJspWriter = new StringJspWriter();
			PageContextAdapter pageContextAdapter = new PageContextAdapter(httpServletRequest, httpServletResponse,
					elContext, stringJspWriter);

			// Invoke the Liferay Tag class directly (rather than using the tag from a JSP).
			PermissionsURLTag permissionsURLTag = new PermissionsURLTag();
			
			permissionsURLTag.setPageContext(pageContextAdapter);
			permissionsURLTag.setModelResource(permissionsURL.getModelResource());
			permissionsURLTag.setModelResourceDescription(permissionsURL.getModelResourceDescription());
			permissionsURLTag.setRedirect(String.valueOf(permissionsURL.isRedirect()));
			permissionsURLTag.setResourceGroupId(permissionsURL.getResourceGroupId());
			permissionsURLTag.setResourcePrimKey(permissionsURL.getResourcePrimKey());
			permissionsURLTag.setRoleTypes(permissionsURL.getRoleTypes());
			// set var to null if you want the tag to write out the url
			permissionsURLTag.setVar(null);
			permissionsURLTag.setWindowState(permissionsURL.getWindowState());

			try {
				permissionsURLTag.doStartTag();
				permissionsURLTag.doEndTag();
			}
			catch (Exception e) {
				logger.error(e);
			}

			String url = stringJspWriter.toString();

			// If the user didn't specify a value for the "var" attribute, then write the URL to the response.
			Map<String, Object> attributes = uiComponent.getAttributes();
			String varName = (String) attributes.get(StringPool.VAR);

			if (varName == null) {
				responseWriter.write(url);
			}

			// Otherwise, place the url into the request scope so that it can be resolved via EL with the name
			// specified in the "var" attribute.
			else {
				externalContext.getRequestMap().put(varName, url);
			}

		}
		else {
			logger.error(
				"encodeEnd: Unable to create a portlet URL because PortletResponse=[{0}] is not a MimeResponse",
				portletResponse);
		}
	}
}
