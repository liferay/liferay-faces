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
import java.util.List;
import java.util.Map;

import javax.faces.application.ProjectStage;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.portal.component.inputrichtext.InputRichText;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.ContentTypes;
import com.liferay.faces.util.render.RendererUtil;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import com.liferay.taglib.ui.InputEditorTag;


/**
 * @author  Neil Griffin
 */
//J-
@FacesRenderer(componentFamily = InputRichText.COMPONENT_FAMILY, rendererType = InputRichText.RENDERER_TYPE)
//J+
public class InputRichTextRenderer extends InputRichTextRendererBase implements SystemEventListener {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputRichTextRenderer.class);

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
		String editorType = getEditorType(inputRichText);
		String escapedEditorName = inputRichText.getClientId();

		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		escapedEditorName = escapedEditorName.replace(separatorChar, '_').concat("_jsptag");

		if ("bbcode".equals(editorType)) {
			inputEditorTag.setName(escapedEditorName + "_bbcodeInput");
		}
		else {
			inputEditorTag.setName(escapedEditorName + "_nonInput");
		}
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
		inputEditorTag.setInitMethod(functionNamespace + "init");
		inputEditorTag.setOnBlurMethod(functionNamespace + "blur");
		inputEditorTag.setOnChangeMethod(functionNamespace + "change");
		inputEditorTag.setOnFocusMethod(functionNamespace + "focus");
		inputEditorTag.setResizable(inputRichText.isResizable());
		inputEditorTag.setSkipEditorLoading(inputRichText.isSkipEditorLoading());
		inputEditorTag.setToolbarSet(inputRichText.getToolbarSet());
	}

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();
		String clientId = uiComponent.getClientId();
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String escapedEditorName = clientId.replace(separatorChar, '_').concat("_jsptag");

		String submittedValue = requestParameterMap.get(escapedEditorName + "_bbcodeInput");

		if (submittedValue == null) {
			submittedValue = requestParameterMap.get(escapedEditorName);
		}

		InputRichText inputRichText = (InputRichText) uiComponent;
		inputRichText.setSubmittedValue(submittedValue);
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the rich text editor.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringPool.DIV, uiComponent);

		String clientId = uiComponent.getClientId();
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String escapedEditorName = clientId.replace(separatorChar, '_').concat("_jsptag");
		responseWriter.writeAttribute(StringPool.ID, clientId, null);
		RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent);

		// Encode the starting <textarea> element.
		InputRichText inputRichText = (InputRichText) uiComponent;

		responseWriter.startElement("textarea", uiComponent);
		responseWriter.writeAttribute(StringPool.ID, clientId + "_input", null);
		responseWriter.writeAttribute(StringPool.NAME, escapedEditorName, null);
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
		WYSIWYGTemplate wysiwygTemplate = getWYSIWYGTemplate(facesContext);
		String formattedTemplate = wysiwygTemplate.format(facesContext, inputRichText);
		responseWriter.startElement(StringPool.SCRIPT, uiComponent);
		responseWriter.writeAttribute(StringPool.TYPE, ContentTypes.TEXT_JAVASCRIPT, null);
		responseWriter.write(formattedTemplate);
		responseWriter.endElement(StringPool.SCRIPT);

		// Begin the JSP tag lifecycle and write the output to the response.
		super.encodeBegin(facesContext, uiComponent);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// End writing the output of the JSP tag lifecycle.
		super.encodeEnd(facesContext, uiComponent);

		// Encode the ending <div> element that represents the rich text editor.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringPool.DIV);
	}

	@Override
	public InputEditorTag newTag() {
		return new InputEditorTag();
	}

	@Override
	public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {

		// Due to ClassLoader problems during static initialization, it is necessary to delay creation of singleton
		// instances of template classes until the PostConstructApplicationConfigEvent is sent. Although template text
		// is the same for each webapp context, the template must be stored as a ServletContext (application scoped)
		// attribute in order to avoid a race condition when contexts are started up in parallel. For example:
		// http://wiki.apache.org/tomcat/HowTo/FasterStartUp#Starting_several_web_applications_in_parallel
		try {
			FacesContext startupFacesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = startupFacesContext.getExternalContext();
			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			boolean minified = startupFacesContext.isProjectStage(ProjectStage.Production);
			applicationMap.put(WYSIWYGTemplate.class.getName(), new WYSIWYGTemplate(minified));
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public boolean isListenerForSource(Object source) {
		return ((source != null) && (source instanceof ApplicationConfig));
	}

	protected String getEditorType(InputRichText inputRichText) {

		String editorType = PropsUtil.get(PropsKeys.EDITOR_WYSIWYG_DEFAULT);
		String editorKey = inputRichText.getEditorKey();

		if (editorKey != null) {
			editorType = PropsUtil.get(editorKey);
		}

		return editorType;
	}

	protected WYSIWYGTemplate getWYSIWYGTemplate(FacesContext facesContext) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> applicationMap = externalContext.getApplicationMap();

		return (WYSIWYGTemplate) applicationMap.get(WYSIWYGTemplate.class.getName());
	}
}
