/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.renderkit;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.portlet.MimeResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.portal.component.PermissionsURL;
import com.liferay.faces.portal.context.LiferayFacesContext;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * This class is the HTML renderer for the pf:permissionsLink tag.
 *
 * @author  Ed Shin
 */
public class PermissionsURLRenderer extends Renderer {

	// Private Constants
	private static final String PORTLET_CONFIGURATION = "86";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PermissionsURLRenderer.class);

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {

		if ((context == null) || (component == null)) {
			throw new NullPointerException("One or more parameters are null");
		}

		String href = getPermissionsURL(context, component);

		if (!component.isRendered()) {
			return;
		}

		ResponseWriter writer = context.getResponseWriter();

		if (writer != null) {
			writer.startElement("a", component);

			if ((null == href) || (0 == href.length())) {
				href = "";
			}

			writer.writeURIAttribute("href", href, "href");

			UIOutput uiOutput = (UIOutput) component;
			Object value = uiOutput.getValue();

			if (value != null) {
				writer.write(value.toString());
			}

			writer.flush();
		}
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {

		if ((context == null) || (component == null)) {
			throw new NullPointerException("One or more parameters are null");
		}

		if (!component.isRendered()) {
			return;
		}

		ResponseWriter writer = context.getResponseWriter();

		if (writer != null) {
			writer.endElement("a");
		}
	}

	public String getPermissionsURL(FacesContext context, UIComponent component) {

		PermissionsURL permissionsURLComponent = (PermissionsURL) component;

		LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

		String permissionsURL = StringPool.BLANK;

		try {
			ExternalContext externalContext = liferayFacesContext.getExternalContext();
			PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();

			if (portletResponse instanceof MimeResponse) {
				MimeResponse mimeResponse = (MimeResponse) portletResponse;
				PortletURL portletURL = mimeResponse.createRenderURL();
				portletURL.setPortletMode(PortletMode.VIEW);

				Class<?> portletURLClass = portletURL.getClass();

				Method method = portletURLClass.getMethod("setPortletId", new Class[] { String.class });

				if (method != null) {
					method.invoke(portletURL, new Object[] { PORTLET_CONFIGURATION });
				}

				method = portletURLClass.getMethod("setPlid", new Class[] { long.class });

				if (method != null) {
					long plid = liferayFacesContext.getPlid();
					method.invoke(portletURL, new Object[] { new Long(plid) });
				}

				method = portletURLClass.getMethod("setLifecycle", new Class[] { String.class });

				if (method != null) {
					method.invoke(portletURL, new Object[] { PortletRequest.RENDER_PHASE });
				}

				portletURL.setWindowState(WindowState.MAXIMIZED);

				portletURL.setParameter("struts_action", "/portlet_configuration/edit_permissions");

				ThemeDisplay themeDisplay = liferayFacesContext.getThemeDisplay();
				String redirect = permissionsURLComponent.getRedirect();

				if (redirect != null) {
					portletURL.setParameter("redirect", redirect);

					if (!themeDisplay.isStateMaximized()) {
						portletURL.setParameter("returnToFullPageURL", redirect);
					}
				}

				PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
				portletURL.setParameter("portletResource", portletDisplay.getId());

				String modelResource = permissionsURLComponent.getModelResource();

				if (modelResource != null) {
					portletURL.setParameter("modelResource", modelResource);
				}
				else {
					logger.error("modelResource cannot be null");
				}

				String modelResourceDescription = permissionsURLComponent.getModelResourceDescription();

				if (modelResourceDescription != null) {
					portletURL.setParameter("modelResourceDescription", modelResourceDescription);
				}

				String resourcePrimKey = permissionsURLComponent.getResourcePrimKey();

				if (resourcePrimKey != null) {
					portletURL.setParameter("resourcePrimKey", resourcePrimKey);
				}

				permissionsURL = portletURL.toString();
			}
			else {
				logger.error(
					"Unable to create a portlet render URL because PortletResponse=[{0}] is not a MimeResponse",
					portletResponse);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			liferayFacesContext.addGlobalUnexpectedErrorMessage();
		}

		return permissionsURL;
	}

}
