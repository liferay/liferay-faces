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
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.client.BrowserSnifferUtil;
import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.client.ClientScriptFactory;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.lang.FacesConstants;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.portal.LiferayPortletUtil;
import com.liferay.faces.util.portal.PortalUtil;
import com.liferay.faces.util.portal.WebKeys;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class RendererUtil {

	// Public Constants
	public static final String ALLOY_END_SCRIPT = "});";
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

	public static void decodeClientBehaviors(FacesContext facesContext, UIComponent uiComponent) {

		if (uiComponent instanceof ClientBehaviorHolder) {

			ClientBehaviorHolder clientBehaviorHolder = (ClientBehaviorHolder) uiComponent;
			Map<String, List<ClientBehavior>> clientBehaviorMap = clientBehaviorHolder.getClientBehaviors();

			Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
			String behaviorEvent = requestParameterMap.get(FacesConstants.JAVAX_FACES_BEHAVIOR_EVENT);

			if (behaviorEvent != null) {

				List<ClientBehavior> clientBehaviors = clientBehaviorMap.get(behaviorEvent);

				if (clientBehaviors != null) {
					String source = requestParameterMap.get(FacesConstants.JAVAX_FACES_SOURCE);

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

	public static void renderScript(FacesContext facesContext, UIComponent uiComponent, String script, String use) {

		// Render the script at the bottom of the page by setting the WebKeys.AUI_SCRIPT_DATA request attribute.
		ExternalContext externalContext = facesContext.getExternalContext();
		ClientScriptFactory clientScriptFactory = (ClientScriptFactory) FactoryExtensionFinder.getFactory(
				ClientScriptFactory.class);
		ClientScript clientScript = clientScriptFactory.getClientScript(externalContext);

		String portletId = StringPool.BLANK;
		Object portlet = externalContext.getRequestMap().get(WebKeys.RENDER_PORTLET);

		if (portlet != null) {
			portletId = LiferayPortletUtil.getPortletId(portlet);
		}

		clientScript.append(portletId, script, use);
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

	public static String getAlloyBeginScript(String[] modules, float browserMajorVersion, boolean browserIE) {
		return getAlloyBeginScript(modules, null, browserMajorVersion, browserIE);
	}

	public static String getAlloyBeginScript(FacesContext facesContext, String[] modules, String config) {

		boolean browserIE = false;
		float browserMajorVersion = 1;

		if (LIFERAY_PORTAL_DETECTED) {
			HttpServletRequest httpServletRequest = PortalUtil.getHttpServeletRequest(
					facesContext.getExternalContext());
			browserIE = BrowserSnifferUtil.isIe(httpServletRequest);
			browserMajorVersion = BrowserSnifferUtil.getMajorVersion(httpServletRequest);
		}
		else if (LIFERAY_FACES_BRIDGE_DETECTED) {
			// no-op because there is no way to obtain the underlying HttpServletRequest.
		}
		else {
			HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
			browserIE = BrowserSnifferUtil.isIe(httpServletRequest);
			browserMajorVersion = BrowserSnifferUtil.getMajorVersion(httpServletRequest);
		}

		return getAlloyBeginScript(modules, config, browserMajorVersion, browserIE);
	}

	public static String getAlloyBeginScript(String[] modules, String config, float browserMajorVersion,
		boolean browserIE) {

		StringBuilder stringBuilder = new StringBuilder();
		String loadMethod = USE;

		if (browserIE && (browserMajorVersion < 8)) {
			loadMethod = READY;
		}

		stringBuilder.append(AUI);
		stringBuilder.append(StringPool.OPEN_PARENTHESIS);

		if ((config != null) && (config.length() > 0)) {
			stringBuilder.append(config);
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
