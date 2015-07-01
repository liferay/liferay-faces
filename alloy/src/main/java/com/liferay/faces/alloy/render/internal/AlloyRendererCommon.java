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
package com.liferay.faces.alloy.render.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Kyle Stiemann
 */
/* package-private */ class AlloyRendererCommon {

	// Private Constants
	private static final String A_DOT = "A.";
	private static final String DESTROY = "destroy";
	private static final String IF = "if";
	private static final String JAVA_SCRIPT_HEX_PREFIX = "\\x";
	private static final String NEW = "new";
	private static final String BACKSLASH_COLON = "\\\\:";
	private static final String REGEX_COLON = "[:]";
	private static final char[] _HEX_DIGITS = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		};

	/* package-private */ static void encodeBoolean(ResponseWriter responseWriter, String attributeName, Boolean attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(",");
		}

		responseWriter.write(attributeName);
		responseWriter.write(":");
		responseWriter.write(attributeValue.toString());
	}

	/* package-private */ static void encodeClientId(ResponseWriter responseWriter, String attributeName, String clientId, boolean first)
		throws IOException {

		String escapedClientId = "#" + clientId.replaceAll(REGEX_COLON, BACKSLASH_COLON);
		encodeString(responseWriter, attributeName, escapedClientId, first);
	}

	/* package-private */ static void encodeClientId(ResponseWriter responseWriter, String attributeName, String clientId,
		UIComponent uiComponent, boolean first) throws IOException {

		UIComponent forComponent = uiComponent.findComponent(clientId);
		String escapedClientId = clientId;

		if (forComponent != null) {
			escapedClientId = forComponent.getClientId();
		}

		encodeClientId(responseWriter, attributeName, escapedClientId, first);
	}

	/* package-private */ static void encodeEventCallback(ResponseWriter responseWriter, String varName, String methodName,
		String eventName, String callback) throws IOException {
		responseWriter.write(varName);
		responseWriter.write(".");
		responseWriter.write(methodName);
		responseWriter.write("('");
		responseWriter.write(eventName);
		responseWriter.write("',function(event){");
		responseWriter.write(callback);
		responseWriter.write("});");
	}

	/* package-private */ static void encodeFunctionCall(ResponseWriter responseWriter, String functionName, Object... parameters)
		throws IOException {

		responseWriter.write(functionName);
		responseWriter.write("(");

		boolean first = true;

		for (Object parameter : parameters) {

			if (first) {
				first = false;
			}
			else {
				responseWriter.write(",");
			}

			encodeFunctionParameter(responseWriter, parameter);
		}

		responseWriter.write(");");
	}

	/* package-private */ static void encodeInteger(ResponseWriter responseWriter, String attributeName, Integer attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(",");
		}

		responseWriter.write(attributeName);
		responseWriter.write(":");
		responseWriter.write(attributeValue.toString());
	}

	/* package-private */ static void encodeJavaScriptBegin(FacesContext facesContext, UIComponent uiComponent, AlloyRenderer alloyRenderer,
		String[] modules, boolean ajax, boolean sandboxed) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (sandboxed) {

			String yuiConfig = alloyRenderer.getYUIConfig(facesContext, responseWriter, uiComponent);
			BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
					BrowserSnifferFactory.class);
			BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());
			String alloyBeginScript = AlloyRendererUtil.getAlloyBeginScript(modules, yuiConfig, browserSniffer);
			responseWriter.write(alloyBeginScript);
		}

		if (ajax && (uiComponent instanceof ClientComponent)) {

			ClientComponent clientComponent = (ClientComponent) uiComponent;
			String clientVarName = getClientVarName(facesContext, clientComponent);
			String clientKey = clientComponent.getClientKey();

			if (clientKey == null) {
				clientKey = clientVarName;
			}

			encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);
			responseWriter.write(IF);
			responseWriter.write("(");
			responseWriter.write(clientVarName);
			responseWriter.write("){");
			responseWriter.write(clientVarName);
			responseWriter.write(".");
			responseWriter.write(DESTROY);
			responseWriter.write("();}");
		}
	}

	/* package-private */ static void encodeJavaScriptEnd(FacesContext facesContext, UIComponent uiComponent, boolean ajax,
		boolean sandboxed) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (sandboxed) {

			responseWriter.write("});");
		}
	}

	/**
	 * This method renders JavaScript which creates the Alloy component and puts it into the Liferay.component map.
	 * Example output of this function is shown below:
	 *
	 * <pre>
	    {@code
	        Liferay.component('clientKey',
	                new A.AlloyComponent({
	                    attribute1:value1,
	                    attribute2:value2,
	                    ...
	                    attributeN:valueN
	                })
	        );
	    }
	 * </pre>
	 *
	 * @throws  IOException
	 */
	/* package-private */ static void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent, String alloyClassName,
		AlloyRenderer alloyRenderer) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		ClientComponent clientComponent = (ClientComponent) uiComponent;
		String clientKey = clientComponent.getClientKey();

		if (clientKey == null) {
			clientKey = getClientVarName(facesContext, clientComponent);
		}

		// Begin encoding JavaScript to create the Alloy JavaScript component and put it in the Liferay.component map.
		responseWriter.write(AlloyRenderer.LIFERAY_COMPONENT);
		responseWriter.write("('");

		String escapedClientKey = escapeJavaScript(clientKey);
		responseWriter.write(escapedClientKey);
		responseWriter.write("',");

		// Write Alloy JavaScript component.
		responseWriter.write(NEW);
		responseWriter.write(" ");
		responseWriter.write(A_DOT);
		responseWriter.write(alloyClassName);
		responseWriter.write("({");
		alloyRenderer.encodeAlloyAttributes(facesContext, responseWriter, uiComponent);
		responseWriter.write("})");

		// Close Liferay.component parenthesis.
		responseWriter.write(");");
	}

	/* package-private */ static void encodeLiferayComponent(ResponseWriter responseWriter, String clientKey) throws IOException {

		responseWriter.write(AlloyRenderer.LIFERAY_COMPONENT);
		responseWriter.write("('");

		String escapedClientKey = escapeJavaScript(clientKey);
		responseWriter.write(escapedClientKey);
		responseWriter.write("')");
	}

	/* package-private */ static void encodeLiferayComponentVar(ResponseWriter responseWriter, String clientVarName, String clientKey)
		throws IOException {

		responseWriter.write("var ");
		responseWriter.write(clientVarName);
		responseWriter.write("=");
		encodeLiferayComponent(responseWriter, clientKey);
		responseWriter.write(";");
	}

	/* package-private */ static void encodeNonEscapedObject(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(",");
		}

		responseWriter.write(attributeName);
		responseWriter.write(":");
		responseWriter.write(attributeValue.toString());
	}

	/* package-private */ static void encodeString(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		String escapedAttributeValue = escapeJavaScript(attributeValue.toString());

		if (!first) {
			responseWriter.write(",");
		}

		responseWriter.write(attributeName);
		responseWriter.write(":'");
		responseWriter.write(escapedAttributeValue);
		responseWriter.write("'");
	}

	/* package-private */ static void encodeWidgetRender(ResponseWriter responseWriter, boolean first) throws IOException {
		encodeBoolean(responseWriter, "render", true, first);
	}

	// TODO el method
	/* package-private */ static String escapeClientId(String clientId) {

		String escapedClientId = clientId;

		if (escapedClientId != null) {

			escapedClientId = escapedClientId.replaceAll(REGEX_COLON, BACKSLASH_COLON);
			escapedClientId = escapeJavaScript(escapedClientId);
		}

		return escapedClientId;
	}

	/* package-private */ static String escapeJavaScript(String javaScript) {

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
					stringBuilder.append("0");
				}

				stringBuilder.append(hexString);
			}
		}

		if (stringBuilder.length() != javaScript.length()) {
			javaScript = stringBuilder.toString();
		}

		return javaScript;
	}

	private static void encodeFunctionParameter(ResponseWriter responseWriter, Object parameter) throws IOException {

		if (parameter == null) {
			responseWriter.write("null");
		}
		else {

			if (parameter instanceof Object[]) {
				Object[] parameterItems = (Object[]) parameter;

				if (parameterItems.length == 0) {
					responseWriter.write("[]");
				}
				else {
					responseWriter.write("[");

					boolean firstIndex = true;

					for (Object parameterItem : parameterItems) {

						if (firstIndex) {
							firstIndex = false;
						}
						else {
							responseWriter.write(",");
						}

						encodeFunctionParameter(responseWriter, parameterItem);
					}

					responseWriter.write("]");
				}
			}
			else if (parameter instanceof String) {
				responseWriter.write("'" + parameter.toString() + "'");
			}
			else {
				responseWriter.write(parameter.toString());
			}
		}
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

	/* package-private */ static String getClientVarName(FacesContext facesContext, ClientComponent clientComponent) {

		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String clientId = clientComponent.getClientId();
		String regex = "[" + separatorChar + "]";
		String clientVarName = clientId.replaceAll(regex, "_");

		return clientVarName;
	}
}
