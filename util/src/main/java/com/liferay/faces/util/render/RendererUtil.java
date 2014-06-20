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
package com.liferay.faces.util.render;

import java.io.IOException;

import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class RendererUtil {
	
	// Public Constants
	public static final String BACKSLASH_COLON = "\\\\:";
	public static final String REGEX_COLON = "[:]";

	// Private Constants
	private static final String JAVA_SCRIPT_HEX_PREFIX = "\\x";

	private static final char[] _HEX_DIGITS = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		};

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

	public static String escapeClientId(String clientId) {

		String escapedClientId = clientId;

		if (escapedClientId != null) {

			escapedClientId = escapedClientId.replaceAll(REGEX_COLON, BACKSLASH_COLON);
			escapedClientId = escapeJavaScript(escapedClientId);
		}

		return escapedClientId;
	}
}
