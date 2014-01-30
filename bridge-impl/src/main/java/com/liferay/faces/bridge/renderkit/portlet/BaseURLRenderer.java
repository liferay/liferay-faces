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
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.portlet.BaseURL;

import com.liferay.faces.bridge.component.PortletActionURL;
import com.liferay.faces.bridge.component.PortletParam;
import com.liferay.faces.bridge.component.PortletProperty;
import com.liferay.faces.bridge.component.PortletRenderURL;
import com.liferay.faces.bridge.component.PortletResourceURL;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This is the abstract base renderer for the rendering that is common to the {@link PortletActionURL}, {@link
 * PortletRenderURL}, and {@link PortletResourceURL} components.
 *
 * @author  Neil Griffin
 */
public abstract class BaseURLRenderer extends Renderer {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BaseURLRenderer.class);

	protected void encodeEnd(FacesContext facesContext, UIComponent uiComponent, BaseURL baseURL) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> attributes = uiComponent.getAttributes();
		Boolean escapeXML = (Boolean) attributes.get("escapeXML");
		Boolean secure = (Boolean) attributes.get("secure");
		String varName = (String) attributes.get("var");

		try {

			// Note: The escapeXML attribute can't be supported in a JSF environment since the bridge does not
			// implement the {@link PortletURL#write(java.io.Writer, boolean)} method.
			if (escapeXML != null) {
				logger.warn("escapeXML not supported for JSF");
			}

			if (secure != null) {
				baseURL.setSecure(secure);
			}

			List<UIComponent> children = uiComponent.getChildren();

			if (children != null) {

				for (UIComponent child : children) {

					if (child instanceof PortletParam) {
						String name = ((PortletParam) child).getName();
						String value = (String) ((PortletParam) child).getValue();
						baseURL.setParameter(name, value);
					}
					else if (child instanceof PortletProperty) {
						String name = ((PortletProperty) child).getName();
						String value = (String) ((PortletProperty) child).getValue();
						baseURL.addProperty(name, value);
					}
				}
			}

			// If the user didn't specify a value for the "var" attribute, then write the URL to the response.
			String url = baseURL.toString();

			if (varName == null) {
				ResponseWriter responseWriter = facesContext.getResponseWriter();
				responseWriter.write(url);
			}

			// Otherwise, place the url into the request scope so that it can be resolved via EL with the name
			// specified in the "var" attribute.
			else {
				externalContext.getRequestMap().put(varName, url);
			}
		}
		catch (Exception e) {
			logger.error(e);
			throw new IOException(e.getMessage());
		}

	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}

}
