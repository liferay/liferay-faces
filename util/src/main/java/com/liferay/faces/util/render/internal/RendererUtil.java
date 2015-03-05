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
package com.liferay.faces.util.render.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.client.ClientScriptFactory;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class RendererUtil {

	// Public Constants
	public static final String BACKSLASH_COLON = "\\\\:";
	public static final String REGEX_COLON = "[:]";

	// Private Constants
	private static final String FUNCTION_A = "function(A)";
	private static final String JAVA_SCRIPT_HEX_PREFIX = "\\x";
	private static final boolean LIFERAY_FACES_BRIDGE_DETECTED = ProductMap.getInstance().get(
			ProductConstants.LIFERAY_FACES_BRIDGE).isDetected();
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();
	private static final String READY = "ready";
	private static final String USE = "use";
	private static final String AUI = "AUI";

	private static final char[] _HEX_DIGITS = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		};

	public static void encodeFunctionCall(ResponseWriter responseWriter, String functionName, Object... parameters)
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

	public static void encodeFunctionParameter(ResponseWriter responseWriter, Object parameter) throws IOException {

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

	public static String escapeClientId(String clientId) {

		String escapedClientId = clientId;

		if (escapedClientId != null) {

			escapedClientId = escapedClientId.replaceAll(REGEX_COLON, BACKSLASH_COLON);
			escapedClientId = escapeJavaScript(escapedClientId);
		}

		return escapedClientId;
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

	public static void renderScript(String script, String use) {

		ClientScriptFactory clientScriptFactory = (ClientScriptFactory) FactoryExtensionFinder.getFactory(
				ClientScriptFactory.class);
		ClientScript clientScript = clientScriptFactory.getClientScript();
		clientScript.append(script, use);
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

	public static String getAlloyBeginScript(FacesContext facesContext, String[] modules) {
		return getAlloyBeginScript(facesContext, modules, null);
	}

	public static String getAlloyBeginScript(FacesContext facesContext, String[] modules, String config) {

		boolean browserIE = false;
		float browserMajorVersion = 1;

		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());

		if (LIFERAY_PORTAL_DETECTED) {
			browserIE = browserSniffer.isIe();
			browserMajorVersion = browserSniffer.getMajorVersion();
		}
		else if (LIFERAY_FACES_BRIDGE_DETECTED) {
			// no-op because there is no way to obtain the underlying HttpServletRequest.
		}
		else {
			browserIE = browserSniffer.isIe();
			browserMajorVersion = browserSniffer.getMajorVersion();
		}

		return getAlloyBeginScript(modules, config, browserMajorVersion, browserIE);
	}

	public static String getAlloyBeginScript(String[] modules, float browserMajorVersion, boolean browserIE) {
		return getAlloyBeginScript(modules, null, browserMajorVersion, browserIE);
	}

	private static String getAlloyBeginScript(String[] modules, String config, float browserMajorVersion,
		boolean browserIE) {

		StringBuilder stringBuilder = new StringBuilder();
		String loadMethod = USE;

		if (browserIE && (browserMajorVersion < 8)) {
			loadMethod = READY;
		}

		// If there is config render a YUI sandbox to avoid using the preconfigured AUI sandbox in Liferay Portal.
		if ((config != null) && (config.length() > 0)) {

			stringBuilder.append("YUI");
			stringBuilder.append(StringPool.OPEN_PARENTHESIS);
			stringBuilder.append(config);
		}
		else {

			stringBuilder.append(AUI);
			stringBuilder.append(StringPool.OPEN_PARENTHESIS);
		}

		stringBuilder.append(StringPool.CLOSE_PARENTHESIS);
		stringBuilder.append(StringPool.PERIOD);
		stringBuilder.append(loadMethod);
		stringBuilder.append(StringPool.OPEN_PARENTHESIS);

		if (modules != null) {

			for (String module : modules) {
				stringBuilder.append(StringPool.APOSTROPHE);
				stringBuilder.append(module.trim());
				stringBuilder.append(StringPool.APOSTROPHE);
				stringBuilder.append(StringPool.COMMA_AND_SPACE);
			}
		}

		stringBuilder.append(FUNCTION_A);
		stringBuilder.append(StringPool.OPEN_CURLY_BRACE);

		return stringBuilder.toString();
	}
}
