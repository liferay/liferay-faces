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
package com.liferay.faces.portal.component.inputrichtext.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.ResourceDependency;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.portal.component.inputrichtext.InputRichText;
import com.liferay.faces.util.ContentTypes;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.render.RendererUtil;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

import com.liferay.taglib.ui.InputEditorTag;


/**
 * @author  Neil Griffin
 */
//J-
@FacesRenderer(componentFamily = InputRichText.COMPONENT_FAMILY, rendererType = InputRichText.RENDERER_TYPE)
@ResourceDependency(library = "liferay-faces-portal", name = "portal.js")
//J+
public class InputRichTextRenderer extends InputRichTextRendererBase {

	@Override
	public InputRichText cast(UIComponent uiComponent) {
		return (InputRichText) uiComponent;
	}

	@Override
	public void copyFrameworkAttributes(FacesContext facesContext, InputRichText inputRichText,
		InputEditorTag inputEditorTag) {

		inputEditorTag.setCssClass(inputRichText.getStyleClass());

		// When ckeditor.jsp renders a hidden textarea, the name is rendered as the id and name attributes of the
		// textarea element. Since this renderer creates its own textarea, it is necessary to set a name that will
		// not interfere when decoding.
		String escapedEditorName = getEditorId(facesContext, inputRichText);

		inputEditorTag.setName(escapedEditorName);
	}

	@Override
	public void copyNonFrameworkAttributes(FacesContext facesContext, InputRichText inputRichText,
		InputEditorTag inputEditorTag) {

		inputEditorTag.setConfigParams(inputRichText.getConfigParams());
		inputEditorTag.setContentsLanguageId(inputRichText.getContentsLanguageId());
		inputEditorTag.setEditorImpl(inputRichText.getEditorKey());
		inputEditorTag.setFileBrowserParams(inputRichText.getFileBrowserParams());

		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String clientId = inputRichText.getClientId();
		String functionNamespace = clientId.replace(separatorChar, '_');
		inputEditorTag.setInitMethod(functionNamespace + "Init");
		inputEditorTag.setOnBlurMethod(functionNamespace + "Blur");
		inputEditorTag.setOnChangeMethod(functionNamespace + "Change");
		inputEditorTag.setOnFocusMethod(functionNamespace + "Focus");
		inputEditorTag.setResizable(inputRichText.isResizable());
		inputEditorTag.setSkipEditorLoading(inputRichText.isSkipEditorLoading());

		String toolbarSet = inputRichText.getToolbarSet();

		if (toolbarSet != null) {
			inputEditorTag.setToolbarSet(toolbarSet);
		}
		else {
			String editorType = getEditorType(inputRichText);

			if ("ckeditor_bbcode".equals(editorType)) {
				inputEditorTag.setToolbarSet("bbcode");
			}
			else if ("ckeditor_creole".equals(editorType)) {
				inputEditorTag.setToolbarSet("creole");
			}
			else {
				inputEditorTag.setToolbarSet("liferay");
			}
		}
	}

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String escapedEditorName = getEditorId(facesContext, uiComponent);
		String submittedValue = requestParameterMap.get(escapedEditorName);
		InputRichText inputRichText = (InputRichText) uiComponent;

		inputRichText.setSubmittedValue(submittedValue);
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the rich text editor.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);

		String clientId = uiComponent.getClientId();
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String escapedEditorName = getEditorId(facesContext, uiComponent);
		responseWriter.writeAttribute("id", clientId, null);
		RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent);

		// Encode the starting <textarea> element.
		InputRichText inputRichText = (InputRichText) uiComponent;

		responseWriter.startElement("textarea", uiComponent);
		responseWriter.writeAttribute("id", clientId + "_input", null);
		responseWriter.writeAttribute("name", escapedEditorName, null);
		responseWriter.writeAttribute(Styleable.STYLE, "display:none;", null);

		// Encode the onblur/onchange/onfocus attributes and any associated client behavior scripts.
		String onblur = inputRichText.getOnblur();
		String onchange = inputRichText.getOnchange();
		String onfocus = inputRichText.getOnfocus();
		Map<String, List<ClientBehavior>> clientBehaviorMap = inputRichText.getClientBehaviors();

		for (String eventName : inputRichText.getEventNames()) {
			List<ClientBehavior> clientBehaviorsForEvent = clientBehaviorMap.get(eventName);

			if (clientBehaviorsForEvent != null) {

				for (ClientBehavior clientBehavior : clientBehaviorsForEvent) {

					List<ClientBehaviorContext.Parameter> parameters = null;
					UIViewRoot viewRoot = facesContext.getViewRoot();

					if (viewRoot instanceof NamingContainer) {

						String namingContainerId = viewRoot.getContainerClientId(facesContext);
						parameters = new ArrayList<ClientBehaviorContext.Parameter>();
						parameters.add(new ClientBehaviorContext.Parameter("'com.sun.faces.namingContainerId'",
								namingContainerId));
					}

					ClientBehaviorContext clientBehaviorContext = ClientBehaviorContext.createClientBehaviorContext(
							facesContext, inputRichText, eventName, clientId, parameters);
					String clientBehaviorScript = clientBehavior.getScript(clientBehaviorContext);

					if (clientBehaviorScript != null) {

						if ("valueChange".equals(eventName) || "change".equals(eventName)) {

							if (onchange != null) {
								clientBehaviorScript = onchange.concat(";").concat(clientBehaviorScript);
								onchange = null;
							}

							responseWriter.writeAttribute("onchange", clientBehaviorScript, null);
						}
						else if ("blur".equals(eventName)) {

							if (onblur != null) {
								clientBehaviorScript = onblur.concat(";").concat(clientBehaviorScript);
								onblur = null;
							}

							responseWriter.writeAttribute("onblur", clientBehaviorScript, null);
						}
						else if ("focus".equals(eventName)) {

							if (onfocus != null) {
								clientBehaviorScript = onfocus.concat(";").concat(clientBehaviorScript);
								onfocus = null;
							}

							responseWriter.writeAttribute("onfocus", clientBehaviorScript, null);
						}
					}
				}
			}
		}

		if (onblur != null) {
			responseWriter.writeAttribute("onblur", onblur, null);
		}

		if (onchange != null) {
			responseWriter.writeAttribute("onchange", onchange, null);
		}

		if (onfocus != null) {
			responseWriter.writeAttribute("onfocus", onfocus, null);
		}

		// Encode the value of the component as a child of the textarea element.
		Object value = inputRichText.getValue();

		if (value != null) {
			responseWriter.writeText(value, null);
		}

		// Encode the closing </textarea> element.
		responseWriter.endElement("textarea");

		// Encode the script that contains functions with names specific to this component, so that they can be
		// invoked directly by the JavaScript generated by the JSP tag.
		responseWriter.startElement("script", uiComponent);
		responseWriter.writeAttribute("type", ContentTypes.TEXT_JAVASCRIPT, null);
		encodeTagFunction(responseWriter, clientId, separatorChar, "Init");
		encodeTagFunction(responseWriter, clientId, separatorChar, "Blur");
		encodeTagFunction(responseWriter, clientId, separatorChar, "Change", "text");
		encodeTagFunction(responseWriter, clientId, separatorChar, "Focus");
		responseWriter.endElement("script");

		// Begin the JSP tag lifecycle and write the output to the response.
		super.encodeBegin(facesContext, uiComponent);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// End writing the output of the JSP tag lifecycle.
		super.encodeEnd(facesContext, uiComponent);

		// Encode the ending <div> element that represents the rich text editor.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");
	}

	@Override
	public InputEditorTag newTag() {
		return new InputEditorTag();
	}

	private void encodeTagFunction(ResponseWriter responseWriter, String clientId, char separatorChar,
		String functionName, String... parameters) throws IOException {

		responseWriter.write("function ");

		String functionNamespace = clientId.replace(separatorChar, '_');
		responseWriter.write(functionNamespace);
		responseWriter.write(functionName);
		responseWriter.write("(");

		boolean first = true;

		for (String parameter : parameters) {

			if (!first) {
				responseWriter.write(",");
			}

			responseWriter.write(parameter);
			first = false;
		}

		responseWriter.write("){");

		if ("Init".equals(functionName)) {
			responseWriter.write("return ");
		}

		responseWriter.write("LFPI.inputRichText");
		responseWriter.write(functionName);
		responseWriter.write("(\"");
		responseWriter.write(clientId);
		responseWriter.write("\"");

		for (String parameter : parameters) {

			responseWriter.write(",");
			responseWriter.write(parameter);
		}

		responseWriter.write(");};");
	}

	@Override
	public String getChildInsertionMarker() {
		return "</div>";
	}

	protected String getEditorId(FacesContext facesContext, UIComponent uiComponent) {

		String clientId = uiComponent.getClientId();
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String editorId = clientId.replace(separatorChar, '_').concat("_jsptag");
		String editorType = getEditorType(cast(uiComponent));

		if ("ckeditor_bbcode".equals(editorType)) {
			editorId = editorId + "_bbcodeInput";
		}
		else {
			editorId = editorId + "_nonInput";
		}

		return editorId;
	}

	protected String getEditorType(InputRichText inputRichText) {

		String editorType = PropsUtil.get(PropsKeys.EDITOR_WYSIWYG_DEFAULT);
		String editorKey = inputRichText.getEditorKey();

		if (editorKey != null) {
			editorType = PropsUtil.get(editorKey);
		}

		return editorType;
	}

	@Override
	protected String getScripts(UIComponent uiComponent, String scripts) throws Exception {

		if (scripts != null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			String escapedEditorName = getEditorId(facesContext, uiComponent);

			// There are two possible methods to be executed, depending on inline editor enabled or not:
			// CKEDITOR.replace or CKEDITOR.inline
			int configIndex = scripts.indexOf("CKEDITOR.replace");
			Boolean editorConfigReplace = null;

			if (configIndex != -1) {
				editorConfigReplace = Boolean.TRUE;
			}
			else {
				configIndex = scripts.indexOf("CKEDITOR.inline");

				if (configIndex != -1) {
					editorConfigReplace = Boolean.FALSE;
				}
			}

			if (editorConfigReplace != null) {

				// Due to Liferay's CKEditor javascript not ready for AJAX re-rendering, we have to dynamically add
				// some lines to the tag generated scripts
				StringBuilder replacement = new StringBuilder();
				replacement.append("createEditor(); ");
				replacement.append("if (CKEDITOR.instances['");
				replacement.append(escapedEditorName);
				replacement.append("']) {");
				replacement.append("CKEDITOR.instances['");
				replacement.append(escapedEditorName);
				replacement.append("'].fire('customDataProcessorLoaded');} ");
				scripts = StringUtil.replace(scripts, "createEditor();", replacement.toString());

				// Now, in order to refresh the config, we have to enter a different URL. We achieve this by adding a
				// "timestamp" param (without this, a default toolbar will be loaded).
				int customConfigIndex = scripts.indexOf("customConfig");

				String configURL = scripts.substring(customConfigIndex, scripts.indexOf(",", customConfigIndex));
				String[] configArray = configURL.split("'");
				configURL = configArray[1];
				scripts = StringUtil.replace(scripts, configURL, configURL.concat("&t=" + new Date().getTime()));
			}
		}

		return scripts;
	}
}
