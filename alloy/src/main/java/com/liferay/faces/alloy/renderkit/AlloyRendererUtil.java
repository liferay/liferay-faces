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
package com.liferay.faces.alloy.renderkit;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Kyle Stiemann
 */
public class AlloyRendererUtil {

	// Public Constants
	public static final String A_DOT_ONE = "A.one";
	public static final String BOUNDING_BOX = "boundingBox";
	public static final String CONTENT_BOX = "contentBox";
	public static final String CSS_CLASS = "cssClass";
	public static final String DISPLAY_NONE = "display:none;";
	public static final String DOT_HIDE = ".hide()";
	public static final String DOT_SHOW = ".show()";
	public static final String FUNCTION_EVENT = "function(event)";
	public static final String HEADER_CONTENT = "headerContent";
	public static final String LIFERAY_COMPONENT = "Liferay.component";
	public static final String LIFERAY_Z_INDEX_OVERLAY = "Liferay.zIndex.OVERLAY";
	public static final String LIFERAY_Z_INDEX_TOOLTIP = "Liferay.zIndex.TOOLTIP";
	public static final String RENDER = "render";
	public static final String VISIBLE = "visible";

	// Private Constants
	private static final String A_DOT = "A.";
	private static final String DESTROY = "destroy";
	private static final String IF = "if";
	private static final String NEW = "new";

	public static void encodeBoolean(ResponseWriter responseWriter, String attributeName, Boolean attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(attributeValue.toString());
	}

	public static void encodeClientId(ResponseWriter responseWriter, String attributeName, String clientId,
		boolean first) throws IOException {

		String escapedClientId = StringPool.POUND +
			clientId.replaceAll(RendererUtil.REGEX_COLON, RendererUtil.BACKSLASH_COLON);
		encodeString(responseWriter, attributeName, escapedClientId, first);
	}

	public static void encodeClientId(ResponseWriter responseWriter, String attributeName, String clientId,
		UIComponent uiComponent, boolean first) throws IOException {

		UIComponent forComponent = uiComponent.findComponent(clientId);
		String escapedClientId = clientId;

		if (forComponent != null) {
			escapedClientId = forComponent.getClientId();
		}

		encodeClientId(responseWriter, attributeName, escapedClientId, first);
	}

	public static void encodeEventCallback(ResponseWriter responseWriter, String varName, String methodName,
		String eventName, String callback) throws IOException {
		responseWriter.write(varName);
		responseWriter.write(StringPool.PERIOD);
		responseWriter.write(methodName);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(eventName);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.COMMA);
		responseWriter.write(AlloyRendererUtil.FUNCTION_EVENT);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(callback);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
	}

	public static void encodeInteger(ResponseWriter responseWriter, String attributeName, Integer attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(attributeValue.toString());
	}

	public static void encodeJavaScriptBegin(FacesContext facesContext, UIComponent uiComponent,
		AlloyRenderer alloyRenderer, String[] modules, boolean ajax, boolean forceInline) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (!ajax && forceInline) {

			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.CDATA_OPEN);
			responseWriter.write(StringPool.NEW_LINE);
		}

		if (ajax || forceInline) {

			ClientComponent clientComponent = (ClientComponent) uiComponent;
			String clientVarName = ComponentUtil.getClientVarName(facesContext, clientComponent);
			String clientKey = clientComponent.getClientKey();

			if (clientKey == null) {
				clientKey = clientVarName;
			}

			encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);
			responseWriter.write(IF);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(clientVarName);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.OPEN_CURLY_BRACE);
			responseWriter.write(clientVarName);
			responseWriter.write(StringPool.PERIOD);
			responseWriter.write(DESTROY);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.SEMICOLON);
			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);

			String yuiConfig = alloyRenderer.getYUIConfig(facesContext, responseWriter, uiComponent);
			String alloyBeginScript = RendererUtil.getAlloyBeginScript(facesContext, modules, yuiConfig);
			responseWriter.write(alloyBeginScript);
		}
	}

	public static void encodeJavaScriptEnd(FacesContext facesContext, UIComponent uiComponent, boolean ajax,
		boolean forceInline) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (ajax || forceInline) {

			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.SEMICOLON);
		}

		if (!ajax && forceInline) {

			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.CDATA_CLOSE);
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
	public static void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent, String alloyClassName,
		AlloyRenderer alloyRenderer) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		ClientComponent clientComponent = (ClientComponent) uiComponent;
		String clientKey = clientComponent.getClientKey();

		if (clientKey == null) {
			clientKey = ComponentUtil.getClientVarName(facesContext, clientComponent);
		}

		// Begin encoding JavaScript to create the Alloy JavaScript component and put it in the Liferay.component map.
		responseWriter.write(LIFERAY_COMPONENT);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);

		String escapedClientKey = RendererUtil.escapeJavaScript(clientKey);
		responseWriter.write(escapedClientKey);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.COMMA);

		// Write Alloy JavaScript component.
		responseWriter.write(NEW);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(A_DOT);
		responseWriter.write(alloyClassName);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		alloyRenderer.encodeAlloyAttributes(facesContext, responseWriter, uiComponent);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);

		// Close Liferay.component parenthesis.
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
	}

	public static void encodeLiferayComponent(ResponseWriter responseWriter, String clientKey) throws IOException {

		responseWriter.write(LIFERAY_COMPONENT);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);

		String escapedClientKey = RendererUtil.escapeJavaScript(clientKey);
		responseWriter.write(escapedClientKey);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
	}

	public static void encodeLiferayComponentVar(ResponseWriter responseWriter, String clientVarName, String clientKey)
		throws IOException {

		responseWriter.write(StringPool.VAR);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(clientVarName);
		responseWriter.write(StringPool.EQUAL);
		encodeLiferayComponent(responseWriter, clientKey);
		responseWriter.write(StringPool.SEMICOLON);
	}

	public static void encodeNonEscapedObject(ResponseWriter responseWriter, String attributeName,
		Object attributeValue, boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(attributeValue.toString());
	}

	public static void encodeString(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		String escapedAttributeValue = RendererUtil.escapeJavaScript(attributeValue.toString());

		if (!first) {
			responseWriter.write(StringPool.COMMA);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(escapedAttributeValue);
		responseWriter.write(StringPool.APOSTROPHE);
	}

	public static void encodeWidgetRender(ResponseWriter responseWriter, boolean first) throws IOException {
		encodeBoolean(responseWriter, RENDER, true, first);
	}
}
