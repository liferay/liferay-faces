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
import java.io.InputStream;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.filter.PortletRequestWrapper;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.liferay.faces.portal.component.InputEditorInternal;
import com.liferay.faces.portal.resource.LiferayFacesResourceHandler;
import com.liferay.faces.portal.servlet.NonNamespacedHttpServletRequest;
import com.liferay.faces.util.jsp.JspIncludeResponse;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.editor.EditorUtil;
import com.liferay.portal.util.PortalUtil;


/**
 * This is a renderer for the liferay-ui-internal:input-editor component.
 *
 * @author  Neil Griffin
 */
public class InputEditorInternalRenderer extends Renderer {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputEditorInternalRenderer.class);

	// Private Constants
	private static final String CKEDITOR = "ckeditor";

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeBegin(facesContext, uiComponent);

		InputEditorInternal inputEditor =
			(InputEditorInternal) uiComponent;

		if (inputEditor.isVisible()) {

			if (logger.isDebugEnabled()) {
				logger.debug("showEditor");
			}

			showEditor(facesContext, inputEditor);
		}
		else {

			if (logger.isDebugEnabled()) {
				logger.debug("hideEditor");
			}

			hideEditor(facesContext, inputEditor);
		}

	}

	protected void showEditor(
		FacesContext facesContext, InputEditorInternal inputEditor)
			throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		httpServletRequest = new NonNamespacedHttpServletRequest(httpServletRequest);

		HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);

		// Build up a URL that can be used to invoke the liferay-ui:input-editor JSP tag.
		String url = "/resources/liferay-ui/jsp/input-editor.jsp";
		Map<String, Object> attributes = inputEditor.getAttributes();
		StringBuilder queryString = new StringBuilder();
		queryString.append(StringPool.QUESTION);
		queryString.append("editorImpl");
		queryString.append(StringPool.EQUAL);

		String editorImpl = (String) attributes.get("editorImpl");

		if (editorImpl == null) {
			editorImpl = CKEDITOR;
		}

		queryString.append(editorImpl);
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

			String editorType = EditorUtil.getEditorValue(httpServletRequest, editorImpl);

			if ((editorType.indexOf(CKEDITOR) >= 0) &&
					(getLiferayPortletRequest(portletRequest) instanceof ResourceRequest) &&
					(!inputEditor.isPreviouslyVisible())) {
				bufferedResponse = removeAllElements(bufferedResponse, "style");
				bufferedResponse = removeAllElements(bufferedResponse, "script");
			}

			// Note: Trim the buffered response since there is typically over 100 newlines at the beginning.
			responseWriter.write(bufferedResponse.trim());
		}
	}

	protected void hideEditor(
		FacesContext facesContext, InputEditorInternal inputEditor)
			throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		responseWriter.startElement(StringPool.SCRIPT, inputEditor);
		responseWriter.write(_getCleanupScript(facesContext));
		responseWriter.endElement(StringPool.SCRIPT);
	}

	protected String removeAllElements(String content, String elementName) {

		String beginElementToken = StringPool.LESS_THAN + elementName;
		String endElementToken = StringPool.FORWARD_SLASH + elementName + StringPool.GREATER_THAN;
		int endElementLength = endElementToken.length();

		boolean done = false;

		while (!done) {
			int beginPos = content.indexOf(beginElementToken);
			int endPos = content.indexOf(endElementToken, beginPos);

			if ((beginPos >= 0) && (endPos > beginPos)) {
				content = content.substring(0, beginPos) + content.substring(endPos + endElementLength);
			}
			else {
				done = true;
			}
		}

		return content;
	}

	protected PortletRequest getLiferayPortletRequest(PortletRequest portletRequest) {

		PortletRequest liferayPortletRequest = portletRequest;

		if (liferayPortletRequest instanceof PortletRequestWrapper) {
			PortletRequestWrapper portletRequestWrapper = (PortletRequestWrapper) portletRequest;
			liferayPortletRequest = getLiferayPortletRequest(portletRequestWrapper.getRequest());
		}

		return liferayPortletRequest;
	}

	private String _getCleanupScript(FacesContext facesContext) throws IOException {
		InputStream _inputStream = getClass().getResourceAsStream(
			"/META-INF/resources/liferay-ui/input-editor-cleanup.js");
		return IOUtils.toString(_inputStream);
	}

}
