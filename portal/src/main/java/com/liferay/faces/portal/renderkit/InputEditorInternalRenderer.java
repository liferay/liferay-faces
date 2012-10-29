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
import javax.portlet.ResourceRequest;
import javax.portlet.filter.PortletRequestWrapper;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.portal.component.InputEditorInternal;
import com.liferay.faces.portal.servlet.NonNamespacedHttpServletRequest;
import com.liferay.faces.util.icefaces.ICEfacesJavaScriptRunner;
import com.liferay.faces.util.jsp.JspIncludeResponse;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;
import com.liferay.faces.util.render.CleanupRenderer;
import com.liferay.portal.kernel.editor.EditorUtil;
import com.liferay.portal.util.PortalUtil;


/**
 * This is a renderer for the liferay-ui-internal:input-editor component.
 *
 * @author  Neil Griffin
 */
public class InputEditorInternalRenderer extends Renderer implements CleanupRenderer {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputEditorInternalRenderer.class);

	// Private Constants
	private static final String EDITOR_NAME_TOKEN = "%EDITOR_NAME%";
	private static final String ONBLUR_JS;
	private static final String ONBLUR_METHOD_NAME_TOKEN = "%ONBLUR_METHOD_NAME%";
	private static final String COMMENT_CDATA_CLOSE = "// " + StringPool.CDATA_CLOSE;
	private static final String CKEDITOR = "ckeditor";
	private static final boolean ICEFACES_DETECTED = ProductMap.getInstance().get(ProductConstants.ICEFACES)
		.isDetected();

	static {
		StringBuilder onBlurJS = new StringBuilder();
		onBlurJS.append("(function() {");
		onBlurJS.append("var ckEditor = CKEDITOR.instances['");
		onBlurJS.append(EDITOR_NAME_TOKEN);
		onBlurJS.append("'];");
		onBlurJS.append("ckEditor.on('blur',");
		onBlurJS.append("function () {");
		onBlurJS.append(ONBLUR_METHOD_NAME_TOKEN);
		onBlurJS.append("();");
		onBlurJS.append("});");
		onBlurJS.append("})();");
		ONBLUR_JS = onBlurJS.toString();
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeBegin(facesContext, uiComponent);

		InputEditorInternal inputEditorInternal = (InputEditorInternal) uiComponent;
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		ExternalContext externalContext = facesContext.getExternalContext();
		PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
		PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		httpServletRequest = new NonNamespacedHttpServletRequest(httpServletRequest);

		HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
		PortletRequest liferayPortletRequest = getLiferayPortletRequest(portletRequest);
		boolean resourcePhase = (liferayPortletRequest instanceof ResourceRequest);
		Map<String, Object> attributes = inputEditorInternal.getAttributes();
		String onBlurMethod = (String) attributes.get("onBlurMethod");
		String editorImpl = (String) attributes.get("editorImpl");

		if (editorImpl == null) {
			editorImpl = CKEDITOR;
		}

		// Build up a URL that can be used to invoke the liferay-ui:input-editor JSP tag.
		String url = "/resources/liferay-ui/jsp/input-editor.jsp";
		StringBuilder queryString = new StringBuilder();
		queryString.append(StringPool.QUESTION);
		queryString.append("editorImpl");
		queryString.append(StringPool.EQUAL);
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

		String editorName = (String) attributes.get("name");
		queryString.append(editorName);
		queryString.append(StringPool.AMPERSAND);
		queryString.append("onChangeMethod");
		queryString.append(StringPool.EQUAL);
		queryString.append(attributes.get("onChangeMethod"));
		queryString.append(StringPool.AMPERSAND);
		queryString.append("skipEditorLoading");
		queryString.append(StringPool.EQUAL);

		if (resourcePhase) {

			// FACES-1439: Ensure that the <script src=".../ckeditor.js" /> element is not included in the response by
			// specifying skipEditorLoading="true" during Ajax requests.
			queryString.append(Boolean.TRUE.toString());
		}
		else {
			queryString.append(Boolean.FALSE.toString());
		}

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

		String bufferedResponse = jspIncludeResponse.getBufferedResponse();

		if (bufferedResponse != null) {

			// Note: Trim the buffered response since there is typically over 100 newlines at the beginning.
			bufferedResponse = bufferedResponse.trim();

			// If rendering an instance of the CKEditor, then
			String editorType = EditorUtil.getEditorValue(httpServletRequest, editorImpl);

			if (editorType.indexOf(CKEDITOR) >= 0) {

				String namespace = portletResponse.getNamespace();

				// FACES-1441: The liferay-ui:input-editor JSP tag (and associated ckeditor.jsp file) do not provide a
				// way to hook-in to the "onblur" callback feature of the CKEditor. In order to overcome this
				// limitation, it is necessary to append a <script>...</script> to the response that provides this
				// ability.
				String onBlurScript = getOnBlurScript(editorName, onBlurMethod, namespace);

				// Include the "onblur" callback script directly in the response.
				StringBuilder scriptMarkup = new StringBuilder();
				scriptMarkup.append(StringPool.LESS_THAN);
				scriptMarkup.append(StringPool.SCRIPT);
				scriptMarkup.append(StringPool.GREATER_THAN);
				scriptMarkup.append(StringPool.CDATA_OPEN);
				scriptMarkup.append(onBlurScript);
				scriptMarkup.append(COMMENT_CDATA_CLOSE);
				scriptMarkup.append(StringPool.LESS_THAN);
				scriptMarkup.append(StringPool.FORWARD_SLASH);
				scriptMarkup.append(StringPool.SCRIPT);
				scriptMarkup.append(StringPool.GREATER_THAN);
				bufferedResponse = bufferedResponse.concat(scriptMarkup.toString());

				// FACES-1439: If the component was rendered on the page on the previous JSF lifecycle, then prevent it
				// from being re-initialized by removing all <script>...</script> elements.
				boolean scriptsRemoved = false;

				if (resourcePhase && inputEditorInternal.isPreviouslyRendered()) {

					logger.debug("Preventing re-initialization of CKEditor for clientId=[{0}]",
						inputEditorInternal.getClientId());

					ParsedResponse parsedResponse = new ParsedResponse(bufferedResponse);
					bufferedResponse = parsedResponse.getNonScripts();
					scriptsRemoved = true;
				}

				// FACES-1422: If ICEfaces is detected, then preempt a DOM-diff by moving the <script>...</script>
				// elements to the ICEfaces JavaScriptRunner.
				if (ICEFACES_DETECTED && !scriptsRemoved) {

					logger.debug(
						"Moving CKEditor scripts to ICEfaces JavaScriptRunner in order to preempt DOM-diff for clientId=[{0}]",
						inputEditorInternal.getClientId());

					ParsedResponse parsedResponse = new ParsedResponse(bufferedResponse);
					bufferedResponse = parsedResponse.getNonScripts();

					String scripts = parsedResponse.getScripts();
					ICEfacesJavaScriptRunner.runScript(facesContext, scripts);
					logger.trace(scripts);
				}
			}

			// Write the captured output from the JSP tag to the Faces responseWriter.
			logger.trace(bufferedResponse);
			responseWriter.write(bufferedResponse);
		}
	}

	public void encodeCleanup(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		PortletResponse portletResponse = (PortletResponse) facesContext.getExternalContext().getResponse();
		String namespace = portletResponse.getNamespace();

		String editorName = uiComponent.getParent().getParent().getClientId();
		StringBuilder scriptBuilder = new StringBuilder();

		// Build up a JavaScript fragment that will cleanup the DOM.
		if (!ICEFACES_DETECTED) {
			scriptBuilder.append(StringPool.LESS_THAN);
			scriptBuilder.append(StringPool.SCRIPT);
			scriptBuilder.append(StringPool.GREATER_THAN);
			scriptBuilder.append(StringPool.CDATA_OPEN);
		}

		scriptBuilder.append("var oldEditor = CKEDITOR.instances['");
		scriptBuilder.append(namespace);
		scriptBuilder.append(editorName);
		scriptBuilder.append("']; if (oldEditor) {");
		scriptBuilder.append("oldEditor.destroy(true);");
		scriptBuilder.append("delete window['");
		scriptBuilder.append(namespace);
		scriptBuilder.append(editorName);
		scriptBuilder.append("'];");
		scriptBuilder.append("}");

		if (!ICEFACES_DETECTED) {
			scriptBuilder.append(StringPool.CDATA_CLOSE);
			scriptBuilder.append(StringPool.LESS_THAN);
			scriptBuilder.append(StringPool.FORWARD_SLASH);
			scriptBuilder.append(StringPool.SCRIPT);
			scriptBuilder.append(StringPool.GREATER_THAN);
		}

		String script = scriptBuilder.toString();

		// If ICEfaces is detected, then move the script to the ICEfaces JavaScriptRunner.
		if (ICEFACES_DETECTED) {

			logger.debug(
				"Moving CKEditor scripts to ICEfaces JavaScriptRunner in order to preempt DOM-diff for clientId=[{0}]",
				uiComponent.getClientId());

			ICEfacesJavaScriptRunner.runScript(facesContext, script);
		}

		// Otherwise, write the script directly to the response.
		else {
			facesContext.getResponseWriter().write(script);
		}

		logger.trace(script);
	}

	protected PortletRequest getLiferayPortletRequest(PortletRequest portletRequest) {

		PortletRequest liferayPortletRequest = portletRequest;

		if (liferayPortletRequest instanceof PortletRequestWrapper) {
			PortletRequestWrapper portletRequestWrapper = (PortletRequestWrapper) portletRequest;
			liferayPortletRequest = getLiferayPortletRequest(portletRequestWrapper.getRequest());
		}

		return liferayPortletRequest;
	}

	protected String getOnBlurScript(String editorName, String onBlurMethod, String namespace) {
		String onBlurScript = ONBLUR_JS;

		// Replace %EDITOR_NAME% token with specified editor name.
		int editorNameTokenPos = onBlurScript.indexOf(EDITOR_NAME_TOKEN);

		if (editorNameTokenPos > 0) {
			onBlurScript = onBlurScript.substring(0, editorNameTokenPos) + namespace + editorName +
				onBlurScript.substring(editorNameTokenPos + EDITOR_NAME_TOKEN.length());
		}

		// Replace %ONBLUR_METHOD_NAME% token with specified onblur method name.
		int onBlurTokenPos = onBlurScript.indexOf(ONBLUR_METHOD_NAME_TOKEN);

		if (onBlurTokenPos > 0) {
			onBlurScript = onBlurScript.substring(0, onBlurTokenPos) + namespace + onBlurMethod +
				onBlurScript.substring(onBlurTokenPos + ONBLUR_METHOD_NAME_TOKEN.length());
		}

		return onBlurScript;
	}

	protected class ParsedResponse {

		private String scripts;
		private String nonScripts;

		public ParsedResponse(String response) {

			StringBuilder scriptBuilder = new StringBuilder();
			String beginScriptToken = StringPool.LESS_THAN + StringPool.SCRIPT;
			String endScriptToken = StringPool.FORWARD_SLASH + StringPool.SCRIPT + StringPool.GREATER_THAN;
			int endElementLength = endScriptToken.length();

			boolean done1 = false;

			while (!done1) {
				int beginPos = response.indexOf(beginScriptToken);
				int endPos = response.indexOf(endScriptToken, beginPos);

				if ((beginPos >= 0) && (endPos > beginPos)) {
					String script = response.substring(beginPos, endPos + endElementLength);
					boolean done2 = false;

					while (!done2) {
						int cdataOpenPos = script.indexOf(StringPool.CDATA_OPEN);

						if (cdataOpenPos > 0) {
							script = script.substring(cdataOpenPos + StringPool.CDATA_OPEN.length());

							int cdataClosePos = script.indexOf(COMMENT_CDATA_CLOSE);

							if (cdataClosePos > 0) {
								script = script.substring(0, cdataClosePos);
							}
						}
						else {
							done2 = true;
						}
					}

					scriptBuilder.append(script);
					response = response.substring(0, beginPos) + response.substring(endPos + endElementLength);
				}
				else {
					done1 = true;
				}
			}

			this.scripts = scriptBuilder.toString().trim();
			this.nonScripts = response.trim();
		}

		public String getNonScripts() {
			return nonScripts;
		}

		public String getScripts() {
			return scripts;
		}

	}
}
