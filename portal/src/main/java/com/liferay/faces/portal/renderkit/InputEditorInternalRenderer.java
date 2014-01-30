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
import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.portal.servlet.ScriptCapturingHttpServletRequest;
import com.liferay.faces.util.jsp.JspIncludeResponse;
import com.liferay.faces.util.jsp.PageContextAdapter;
import com.liferay.faces.util.jsp.StringJspWriter;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.portal.EditorUtil;
import com.liferay.faces.util.portal.ScriptDataUtil;
import com.liferay.faces.util.portal.ScriptTagUtil;
import com.liferay.faces.util.portal.WebKeys;
import com.liferay.faces.util.render.CleanupRenderer;

import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
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
	private static final String CKEDITOR_REPLACE = "CKEDITOR.replace(";
	private static final String CDPL_INITIALIZE_FALSE = "var customDataProcessorLoaded = false;";
	private static final String CDPL_INITIALIZE_TRUE = "var customDataProcessorLoaded = true;";

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
		PortletRequest liferayPortletRequest = getLiferayPortletRequest(portletRequest);
		boolean resourcePhase = (liferayPortletRequest instanceof ResourceRequest);
		HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		HttpServletRequest scriptCapturingHttpServletRequest = new ScriptCapturingHttpServletRequest(
				httpServletRequest);

		HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
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
		RequestDispatcher requestDispatcher = scriptCapturingHttpServletRequest.getRequestDispatcher(url);
		JspIncludeResponse jspIncludeResponse = new JspIncludeResponse(httpServletResponse);

		// FACES-1713: ThemeDisplay.isLifecycleResource() must return false during execution of the request dispatcher.
		ThemeDisplay themeDisplay = (ThemeDisplay) scriptCapturingHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);
		boolean lifecycleResourceBackup = themeDisplay.isLifecycleResource();
		themeDisplay.setLifecycleResource(false);

		try {
			requestDispatcher.include(scriptCapturingHttpServletRequest, jspIncludeResponse);
		}
		catch (ServletException e) {
			logger.error(e.getMessage());
			throw new IOException(e.getMessage());
		}

		// FACES-1713: Restore the value of ThemeDisplay.isLifecycleResource().
		themeDisplay.setLifecycleResource(lifecycleResourceBackup);

		String bufferedResponse = jspIncludeResponse.getBufferedResponse();

		if (bufferedResponse != null) {

			// Note: Trim the buffered response since there is typically over 100 newlines at the beginning.
			bufferedResponse = bufferedResponse.trim();

			// If rendering an instance of the CKEditor, then
			String clientId = inputEditorInternal.getClientId();
			String editorType = EditorUtil.getEditorValue(scriptCapturingHttpServletRequest, editorImpl);

			if (editorType.indexOf(CKEDITOR) >= 0) {

				// FACES-1441: The liferay-ui:input-editor JSP tag (and associated ckeditor.jsp file) do not provide a
				// way to hook-in to the "onblur" callback feature of the CKEditor. In order to overcome this
				// limitation, it is necessary to append a <script>...</script> to the response that provides this
				// ability.
				String onBlurScript = getOnBlurScript(editorName, onBlurMethod);

				// If running within an Ajax request, then the "onblur" callback script must be included directly to the
				// parital-response.
				if (resourcePhase) {
					StringBuilder scriptMarkup = new StringBuilder();
					scriptMarkup.append(StringPool.LESS_THAN);
					scriptMarkup.append(StringPool.SCRIPT);
					scriptMarkup.append(StringPool.GREATER_THAN);
					scriptMarkup.append(onBlurScript);
					scriptMarkup.append(StringPool.LESS_THAN);
					scriptMarkup.append(StringPool.FORWARD_SLASH);
					scriptMarkup.append(StringPool.SCRIPT);
					scriptMarkup.append(StringPool.GREATER_THAN);
					bufferedResponse = bufferedResponse.concat(scriptMarkup.toString());
				}

				// Otherwise, append the script to the WebKeys.AUI_SCRIPT_DATA request attribute, which will cause the
				// script to be rendered at the bottom of the portal page.
				else {

					Object scriptData = externalContext.getRequestMap().get(WebKeys.AUI_SCRIPT_DATA);
					ScriptDataUtil.append(scriptData, getPortletId(portletRequest), onBlurScript, "aui-base");
				}

				// FACES-1439: If the component was rendered on the page on the previous JSF lifecycle, then prevent it
				// from being re-initialized by removing all <script>...</script> elements.
				boolean scriptsRemoved = false;

				if (resourcePhase && inputEditorInternal.isPreviouslyRendered()) {

					logger.debug("Preventing re-initialization of CKEditor for clientId=[{0}]", clientId);

					ParsedResponse parsedResponse = new ParsedResponse(bufferedResponse);
					bufferedResponse = parsedResponse.getNonScripts();
					scriptsRemoved = true;
				}

				// FACES-1422: Move the scripts to the <eval>...</eval> section of the partial-response so that they
				// will execute properly. This has the added benefit of preempt a DOM-diff with ICEfaces.
				if (resourcePhase && !scriptsRemoved) {

					logger.debug(
						"Moving CKEditor scripts to <eval>...</eval> section of the partial-response for clientId=[{0}]",
						clientId);

					ParsedResponse parsedResponse = new ParsedResponse(bufferedResponse);
					bufferedResponse = parsedResponse.getNonScripts();

					String scripts = parsedResponse.getScripts();

					LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
					liferayFacesContext.getJavaScriptMap().put(clientId, scripts);
					logger.trace(scripts);
				}
			}

			// Write the captured HTML markup from the JSP tag to the Faces responseWriter.
			logger.trace(bufferedResponse);
			responseWriter.write(bufferedResponse);

			// The JSP (executed by the RequestDispatcher) encounters <aui:script> JSP tags that ultimately save
			// scripts in the WebKeys.AUI_SCRIPT_DATA request attribute. The intent (in a JSP environment) is to let
			// the ScriptDataPortletFilter render the script content at the bottom of the portal page on the initial
			// RenderRequest. However, in a JSF environment we need to render the scripts directly to the response
			// as part of the component markup, because the JSF environment might be utilizing the DOM-diff feature
			// of ICEfaces. If the scripts were rendered at the bottom of the page during the RenderRequest, and
			// then rendered inline during a subsequent ResourceRequest, then ICEfaces would detect a DOM-diff and
			// unnecessarily replace the DOM with a new editor.
			try {

				// Capture the scripts into a String.
				StringJspWriter stringJspWriter = new StringJspWriter();
				PageContextAdapter pageContextAdapter = new PageContextAdapter(scriptCapturingHttpServletRequest,
						httpServletResponse, facesContext.getELContext(), stringJspWriter);

				// Note that flushing the ScriptData will only flush and write the scripts that were added by the
				// request dispatcher. This is because the ScriptCapturingHttpServletRequest protects the
				// WebKeys.AUI_SCRIPT_DATA in the underlying HttpServletRequest.
				ScriptTagUtil.flushScriptData(pageContextAdapter);

				String javaScriptFromRequestDispatcher = stringJspWriter.toString();

				// Remove all the "<![CDATA[" and "]]>" tokens since they will interfere with the JSF
				// partial-response.
				String[] tokensToRemove = new String[] { StringPool.CDATA_OPEN, StringPool.CDATA_CLOSE };

				for (String token : tokensToRemove) {
					int pos = javaScriptFromRequestDispatcher.indexOf(token);

					while (pos >= 0) {
						javaScriptFromRequestDispatcher = javaScriptFromRequestDispatcher.substring(0, pos) +
							javaScriptFromRequestDispatcher.substring(pos + token.length());
						pos = javaScriptFromRequestDispatcher.indexOf(token);
					}
				}

				// Create a JavaScript fragment that will cleanup the DOM. This is necessary when a
				// partial-update takes place and replaces an existing CKEditor in the DOM with either a new
				// CKEditor or different elements.
				StringBuilder javaScriptFragment = new StringBuilder();
				javaScriptFragment.append("var oldEditor = CKEDITOR.instances['");
				javaScriptFragment.append(editorName);
				javaScriptFragment.append("']; if (oldEditor) {");
				javaScriptFragment.append("oldEditor.destroy(true);");
				javaScriptFragment.append("delete window['");
				javaScriptFragment.append(editorName);
				javaScriptFragment.append("'];");
				javaScriptFragment.append("}");
				javaScriptFragment.append(StringPool.NEW_LINE);
				javaScriptFragment.append(StringPool.TAB);
				javaScriptFragment.append(StringPool.TAB);

				// Insert the JavaScript fragment into the JavaScript code at the appropriate location.
				int pos = javaScriptFromRequestDispatcher.indexOf(CKEDITOR_REPLACE);

				if (pos > 0) {
					javaScriptFromRequestDispatcher = javaScriptFromRequestDispatcher.substring(0, pos) +
						javaScriptFragment.toString() + javaScriptFromRequestDispatcher.substring(pos);
				}
				else {
					javaScriptFromRequestDispatcher = javaScriptFromRequestDispatcher + StringPool.NEW_LINE +
						javaScriptFragment.toString();
				}

				// Create a JavaScript fragment that will change the way that the customDataProcessorLoaded
				// variable is initialized. Normally it is initialized to false, but if there is an old CKEditor
				// that was destroyed, then it should be initialized to true. That will guarantee that the
				// new CKEditor in the DOM will have its setData() method called with the value from the
				// hidden field.
				javaScriptFragment = new StringBuilder();
				javaScriptFragment.append("if (oldEditor)");
				javaScriptFragment.append(StringPool.OPEN_CURLY_BRACE);
				javaScriptFragment.append(CDPL_INITIALIZE_TRUE);
				javaScriptFragment.append(StringPool.CLOSE_CURLY_BRACE);
				javaScriptFragment.append("else");
				javaScriptFragment.append(StringPool.OPEN_CURLY_BRACE);
				javaScriptFragment.append(CDPL_INITIALIZE_FALSE);
				javaScriptFragment.append(StringPool.CLOSE_CURLY_BRACE);

				// Insert the JavaScript fragment into the JavaScript code at the appropriate location.
				pos = javaScriptFromRequestDispatcher.indexOf(CDPL_INITIALIZE_FALSE);

				if (pos > 0) {
					javaScriptFromRequestDispatcher = javaScriptFromRequestDispatcher.substring(0, pos) +
						javaScriptFragment.toString() +
						javaScriptFromRequestDispatcher.substring(pos + CDPL_INITIALIZE_FALSE.length());
				}
				else {
					javaScriptFromRequestDispatcher = javaScriptFromRequestDispatcher + StringPool.NEW_LINE +
						javaScriptFragment.toString();
				}

				responseWriter.write(javaScriptFromRequestDispatcher);
			}
			catch (Exception e) {
				logger.error(e);
				throw new IOException(e.getMessage());
			}
		}
	}

	public void encodeCleanup(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		String editorName = uiComponent.getParent().getParent().getClientId();
		StringBuilder scriptBuilder = new StringBuilder();

		// Build up a JavaScript fragment that will cleanup the DOM.
		scriptBuilder.append("var oldEditor = CKEDITOR.instances['");
		scriptBuilder.append(editorName);
		scriptBuilder.append("']; if (oldEditor) {");
		scriptBuilder.append("oldEditor.destroy(true);");
		scriptBuilder.append("delete window['");
		scriptBuilder.append(editorName);
		scriptBuilder.append("'];");
		scriptBuilder.append("}");

		String script = scriptBuilder.toString();

		LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
		liferayFacesContext.getJavaScriptMap().put(editorName, script);

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

	protected String getOnBlurScript(String editorName, String onBlurMethod) {
		String onBlurScript = ONBLUR_JS;

		// Replace %EDITOR_NAME% token with specified editor name.
		int editorNameTokenPos = onBlurScript.indexOf(EDITOR_NAME_TOKEN);

		if (editorNameTokenPos > 0) {
			onBlurScript = onBlurScript.substring(0, editorNameTokenPos) + editorName +
				onBlurScript.substring(editorNameTokenPos + EDITOR_NAME_TOKEN.length());
		}

		// Replace %ONBLUR_METHOD_NAME% token with specified onblur method name.
		int onBlurTokenPos = onBlurScript.indexOf(ONBLUR_METHOD_NAME_TOKEN);

		if (onBlurTokenPos > 0) {
			onBlurScript = onBlurScript.substring(0, onBlurTokenPos) + onBlurMethod +
				onBlurScript.substring(onBlurTokenPos + ONBLUR_METHOD_NAME_TOKEN.length());
		}

		return onBlurScript;
	}

	protected String getPortletId(PortletRequest portletRequest) {
		String portletId = StringPool.BLANK;
		Portlet portlet = (Portlet) portletRequest.getAttribute(WebKeys.RENDER_PORTLET);

		if (portlet != null) {
			portletId = portlet.getPortletId();
		}

		return portletId;
	}

	protected class ParsedResponse {

		private String scripts;
		private String nonScripts;

		public ParsedResponse(String response) {

			StringBuilder scriptBuilder = new StringBuilder();

			boolean done1 = false;

			while (!done1) {
				int beginPos = response.indexOf(StringPool.SCRIPT_TAG_BEGIN);
				int endPos = response.indexOf(StringPool.SCRIPT_TAG_END, beginPos);

				if ((beginPos >= 0) && (endPos > beginPos)) {
					String script = response.substring(beginPos, endPos + StringPool.SCRIPT_TAG_END.length());

					boolean done2 = false;

					while (!done2) {
						int cdataOpenPos = script.indexOf(StringPool.CDATA_OPEN);

						if (cdataOpenPos > 0) {
							script = script.substring(cdataOpenPos + StringPool.CDATA_OPEN.length());

							int cdataClosePos = script.indexOf(COMMENT_CDATA_CLOSE);

							if (cdataClosePos > 0) {
								script = script.substring(0, cdataClosePos);
							}
							else {
								cdataClosePos = script.indexOf(StringPool.CDATA_CLOSE);

								if (cdataClosePos > 0) {
									script = script.substring(0, cdataClosePos);
								}
							}
						}
						else {
							done2 = true;
						}
					}

					scriptBuilder.append(script);
					response = response.substring(0, beginPos) +
						response.substring(endPos + StringPool.SCRIPT_TAG_END.length());
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
