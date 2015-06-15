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
package com.liferay.faces.util.render;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

import javax.faces.application.Application;
import javax.faces.component.behavior.AjaxBehavior;


/**
 * @author  Neil Griffin
 */
public class RendererUtil {

	// Public Constants
	public static final String[] MOUSE_DOM_EVENTS = {
			"onclick", "ondblclick", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup"
		};
	public static final String[] KEYBOARD_DOM_EVENTS = { "onkeydown", "onkeypress", "onkeyup" };

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(com.liferay.faces.util.render.RendererUtil.class);

	// Private Constants
	private static final String JAVA_SCRIPT_HEX_PREFIX = "\\x";
	private static final char[] _HEX_DIGITS = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		};

	public static void addDefaultAjaxBehavior(ClientBehaviorHolder clientBehaviorHolder, String execute, String process,
		String defaultExecute, String render, String update, String defaultRender) {

		Map<String, List<ClientBehavior>> clientBehaviorMap = clientBehaviorHolder.getClientBehaviors();
		String defaultEventName = clientBehaviorHolder.getDefaultEventName();
		List<ClientBehavior> clientBehaviors = clientBehaviorMap.get(defaultEventName);

		boolean doAdd = true;

		if (clientBehaviors != null) {

			for (ClientBehavior clientBehavior : clientBehaviors) {

				if (clientBehavior instanceof AjaxBehavior) {
					doAdd = false;

					break;
				}
			}
		}

		if (doAdd) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			Application application = facesContext.getApplication();
			AjaxBehavior ajaxBehavior = (AjaxBehavior) application.createBehavior(AjaxBehavior.BEHAVIOR_ID);
			Collection<String> executeIds = getExecuteIds(execute, process, defaultExecute);
			ajaxBehavior.setExecute(executeIds);

			Collection<String> renderIds = getRenderIds(render, update, defaultRender);
			ajaxBehavior.setRender(renderIds);
			clientBehaviorHolder.addClientBehavior(defaultEventName, ajaxBehavior);
		}
	}

	public static void decodeClientBehaviors(FacesContext facesContext, UIComponent uiComponent) {

		if (uiComponent instanceof ClientBehaviorHolder) {

			ClientBehaviorHolder clientBehaviorHolder = (ClientBehaviorHolder) uiComponent;
			Map<String, List<ClientBehavior>> clientBehaviorMap = clientBehaviorHolder.getClientBehaviors();

			Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
			String behaviorEvent = requestParameterMap.get("javax.faces.behavior.event");

			if (behaviorEvent != null) {

				List<ClientBehavior> clientBehaviors = clientBehaviorMap.get(behaviorEvent);

				if (clientBehaviors != null) {
					String source = requestParameterMap.get("javax.faces.source");

					if (source != null) {
						String clientId = uiComponent.getClientId(facesContext);

						if (clientId.startsWith(source)) {

							for (ClientBehavior behavior : clientBehaviors) {
								behavior.decode(facesContext, uiComponent);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * This method exists as a convenience for Component developers to encode attributes that pass through to the DOM in
	 * JSF 2.1.
	 */
	public static void encodePassThroughAttributes(ResponseWriter responseWriter, UIComponent uiComponent,
		final String[] PASS_THROUGH_ATTRIBUTES) throws IOException {

		Map<String, Object> attributes = uiComponent.getAttributes();

		for (final String PASS_THROUGH_ATTRIBUTE : PASS_THROUGH_ATTRIBUTES) {

			Object passThroughAttributeValue = attributes.get(PASS_THROUGH_ATTRIBUTE);

			if (passThroughAttributeValue != null) {
				responseWriter.writeAttribute(PASS_THROUGH_ATTRIBUTE, passThroughAttributeValue,
					PASS_THROUGH_ATTRIBUTE);
			}
		}
	}

	public static void encodeStyleable(ResponseWriter responseWriter, Styleable styleable, String... classNames)
		throws IOException {

		StringBuilder allCssClasses = new StringBuilder();
		String cssClasses = ComponentUtil.concatCssClasses(classNames);

		if (cssClasses != null) {
			allCssClasses.append(cssClasses);
			allCssClasses.append(StringPool.SPACE);
		}

		String styleClass = styleable.getStyleClass();

		if (styleClass != null) {
			allCssClasses.append(styleClass);
		}

		if (allCssClasses.length() > 0) {
			responseWriter.writeAttribute(StringPool.CLASS, allCssClasses.toString(), Styleable.STYLE_CLASS);
		}

		String style = styleable.getStyle();

		if (style != null) {
			responseWriter.writeAttribute(Styleable.STYLE, style, Styleable.STYLE);
		}
	}

	public static String escapeJavaScript(String javaScript) {

		StringBuilder stringBuilder = new StringBuilder();
		char[] javaScriptCharArray = javaScript.toCharArray();

		for (char character : javaScriptCharArray) {

			if ((character > 255) || Character.isLetterOrDigit(character)) {

				stringBuilder.append(character);
			}
			else {
				stringBuilder.append(JAVA_SCRIPT_HEX_PREFIX);

				String hexString = toHexString(character);

				if (hexString.length() == 1) {
					stringBuilder.append(StringPool.ASCII_TABLE[48]);
				}

				stringBuilder.append(hexString);
			}
		}

		if (stringBuilder.length() != javaScript.length()) {
			javaScript = stringBuilder.toString();
		}

		return javaScript;
	}

	private static String toHexString(int i) {
		char[] buffer = new char[8];

		int index = 8;

		do {
			buffer[--index] = _HEX_DIGITS[i & 15];

			i >>>= 4;
		}
		while (i != 0);

		return new String(buffer, index, 8 - index);
	}

	private static Collection<String> getExecuteIds(String execute, String process, String defaultValue) {

		// If the values of the execute and process attributes differ, then
		if (!execute.equals(process)) {

			// If the process attribute was specified and the execute attribute was omitted, then use the value of the
			// process attribute.
			if (execute.equals(defaultValue)) {
				execute = process;
			}

			// Otherwise, if both the execute and process attributes were specified with different values, then log a
			// warning indicating that the value of the execute attribute takes precedence.
			else if (!process.equals(defaultValue)) {
				logger.warn(
					"Different values were specified for the execute=[{0}] and process=[{0}]. The value for execute takes precedence.");
			}
		}

		return Arrays.asList(execute.split(" "));
	}

	private static Collection<String> getRenderIds(String render, String update, String defaultValue) {

		// If the values of the render and update attributes differ, then
		if (!render.equals(update)) {

			// If the update attribute was specified and the render attribute was omitted, then use the value of the
			// update attribute.
			if (render.equals(defaultValue)) {
				render = update;
			}

			// Otherwise, if both the render and update attributes were specified with different values, then log a
			// warning indicating that the value of the render attribute takes precedence.
			else if (!update.equals(defaultValue)) {
				logger.warn(
					"Different values were specified for the render=[{0}] and update=[{0}]. The value for render takes precedence.");
			}
		}

		return Arrays.asList(render.split(" "));
	}
}
