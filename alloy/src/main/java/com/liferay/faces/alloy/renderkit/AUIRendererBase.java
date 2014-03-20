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
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Widget;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererBase;


/**
 * This is an abstract class that provides base rendering functionality for AlloyUI JavaScript components.
 *
 * @author  Neil Griffin
 */
public abstract class AUIRendererBase extends RendererBase {

	// Private Constants
	private static final String DESTROY = "destroy";
	private static final String FUNCTION_EVENT = "function(event)";
	private static final String FUNCTION_A = "function(A)";
	private static final String USE = "use";
	private static final String YUI = "YUI";

	// Protected Constants
	protected static final String AFTER = "after";
	protected static final String FUNCTION = "function";
	protected static final String IF = "if";
	protected static final String LIFERAY_COMPONENT = "Liferay.component";
	protected static final String NEW = "new";
	protected static final String ON = "on";
	protected static final String RETURN = "return";
	protected static final String VAR = "var";

	protected void encodeArray(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
			responseWriter.write(StringPool.NEW_LINE);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(String.valueOf(attributeValue));
	}

	protected void encodeBoolean(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
			responseWriter.write(StringPool.NEW_LINE);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(String.valueOf(attributeValue));
	}

	protected void encodeEvent(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
			responseWriter.write(StringPool.NEW_LINE);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(FUNCTION_EVENT);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(String.valueOf(attributeValue));
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	protected void encodeJavaScriptBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (!isAjax(facesContext) && isForceInline(uiComponent)) {

			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.SPACE);
			responseWriter.write(StringPool.CDATA_OPEN);
		}

		if (isAjax(facesContext) || isForceInline(uiComponent)) {

			String widgetVar = ComponentUtil.resolveWidgetVar(facesContext, (Widget) uiComponent);

			responseWriter.write(StringPool.NEW_LINE);
			responseWriter.write(VAR);
			responseWriter.write(StringPool.SPACE);
			responseWriter.write(widgetVar);
			responseWriter.write(StringPool.SPACE);
			responseWriter.write(StringPool.EQUAL);
			responseWriter.write(StringPool.SPACE);
			responseWriter.write(LIFERAY_COMPONENT);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(widgetVar);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.SEMICOLON);
			responseWriter.write(StringPool.NEW_LINE);
			responseWriter.write(IF);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(widgetVar);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.SPACE);
			responseWriter.write(StringPool.OPEN_CURLY_BRACE);
			responseWriter.write(widgetVar);
			responseWriter.write(StringPool.PERIOD);
			responseWriter.write(DESTROY);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.SEMICOLON);
			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
			responseWriter.write(StringPool.NEW_LINE);
			responseWriter.write(YUI);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			encodeLang(responseWriter, uiComponent);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.PERIOD);
			responseWriter.write(USE);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(StringPool.NEW_LINE);
			encodeModules(responseWriter);
			responseWriter.write(StringPool.NEW_LINE);
			responseWriter.write(FUNCTION_A);
			responseWriter.write(StringPool.SPACE);
			responseWriter.write(StringPool.OPEN_CURLY_BRACE);
			responseWriter.write(StringPool.NEW_LINE);
		}
	}

	@Override
	protected void encodeJavaScriptEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (isAjax(facesContext) || isForceInline(uiComponent)) {

			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
			responseWriter.write(StringPool.NEW_LINE);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.SEMICOLON);
		}

		if (!isAjax(facesContext) && isForceInline(uiComponent)) {

			responseWriter.write(StringPool.NEW_LINE);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.SPACE);
			responseWriter.write(StringPool.CDATA_CLOSE);
			responseWriter.write(StringPool.NEW_LINE);
		}
	}

	protected void encodeLang(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {
		// no-op
	}

	protected void encodeMap(ResponseWriter responseWriter, String attributeName, Map<String, String> attributeValues,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
			responseWriter.write(StringPool.NEW_LINE);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		boolean firstMapAttribute = true;

		for (Map.Entry<String, String> attributeValue : attributeValues.entrySet()) {
			encodeString(responseWriter, attributeValue.getKey(), attributeValue.getValue(), firstMapAttribute);
			firstMapAttribute = false;
		}

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	protected void encodeNumber(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
			responseWriter.write(StringPool.NEW_LINE);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(String.valueOf(attributeValue));
	}

	protected void encodeObject(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
			responseWriter.write(StringPool.NEW_LINE);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(String.valueOf(attributeValue));
	}

	protected void encodeString(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
			responseWriter.write(StringPool.NEW_LINE);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.QUOTE);
		responseWriter.write(String.valueOf(attributeValue));
		responseWriter.write(StringPool.QUOTE);
	}

	@Override
	protected boolean hasJavaScript() {
		return true;
	}

	protected void encodeModules(ResponseWriter responseWriter) throws IOException {

		List<String> modules = getModules();

		for (String module : modules) {

			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(module);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(StringPool.COMMA);
		}
	}
}
