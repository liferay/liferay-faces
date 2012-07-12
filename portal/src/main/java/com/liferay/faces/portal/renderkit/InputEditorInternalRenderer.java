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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.util.jsp.JspIncludeResponse;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;


/**
 * This is a renderer for the liferay-ui-internal:input-editor component.
 *
 * @author  Neil Griffin
 */
public class InputEditorInternalRenderer extends Renderer {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputEditorInternalRenderer.class);

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeBegin(facesContext, uiComponent);

		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);

		// Build up a URL that can be used to invoke the liferay-ui:input-editor JSP tag.
		String url = "/resources/liferay-ui/jsp/input-editor.jsp";
		Map<String, Object> attributes = uiComponent.getAttributes();
		StringBuilder queryString = new StringBuilder();
		queryString.append(StringPool.QUESTION);
		queryString.append("editorImpl");
		queryString.append(StringPool.EQUAL);
		queryString.append(attributes.get("editorImpl"));
		queryString.append(StringPool.AMPERSAND);
		queryString.append("height");
		queryString.append(StringPool.EQUAL);
		queryString.append(attributes.get("height"));
		queryString.append(StringPool.AMPERSAND);
		queryString.append("initMethod");
		queryString.append(StringPool.EQUAL);
		queryString.append(attributes.get("initMethod"));
		queryString.append(StringPool.AMPERSAND);
		queryString.append("name");
		queryString.append(StringPool.EQUAL);
		queryString.append(attributes.get("name"));
		queryString.append(StringPool.AMPERSAND);
		queryString.append("onChangeMethod");
		queryString.append(StringPool.EQUAL);
		queryString.append(attributes.get("onChangeMethod"));
		queryString.append(StringPool.AMPERSAND);
		queryString.append("toolbarSet");
		queryString.append(StringPool.EQUAL);
		queryString.append(attributes.get("toolbarSet"));
		queryString.append(StringPool.AMPERSAND);
		queryString.append("width");
		queryString.append(StringPool.EQUAL);
		queryString.append(attributes.get("width"));
		url = url + queryString.toString();

		// Invoke the tag and capture it's output in a String, rather than having the output go directly to the
		// response.
		RequestDispatcher requestDispatcher = httpServletRequest.getRequestDispatcher(url);
		JspIncludeResponse jspIncludeResponse = new JspIncludeResponse(httpServletResponse);

		try {
			requestDispatcher.include(httpServletRequest, jspIncludeResponse);
		}
		catch (ServletException e) {
			logger.error(e.getMessage());
			throw new IOException(e.getMessage());
		}

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String bufferedResponse = jspIncludeResponse.getBufferedResponse();

		// Write the captured output from the JSP tag to the Faces responseWriter.
		if (bufferedResponse != null) {

			// Note: Trim the buffered response since there is typically over 100 newlines at the beginning.
			responseWriter.write(bufferedResponse.trim());
		}
	}

}
