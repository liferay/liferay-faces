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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.util.AlloyConstants;
import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererBase;


/**
 * This is an abstract class that provides base rendering functionality for AlloyUI JavaScript components.
 *
 * @author  Neil Griffin
 */
public abstract class AlloyRendererBase extends RendererBase {

	// Private Constants
	private static final String A = "A";
	private static final String DESTROY = "destroy";
	private static final String FUNCTION = "function";
	private static final String FUNCTION_A = "function(A)";
	private static final String IF = "if";
	private static final String LIFERAY_COMPONENT = "Liferay.component";
	private static final String NEW = "new";
	private static final String NUMBER_REGEX = "([-])?[0-9]+([.])?[0-9]*";
	private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
	private static final String RETURN = "return";
	private static final String USE = "use";
	private static final String VAR = "var";
	private static final String YUI = "YUI";

	protected abstract void encodeAlloyAttributes(ResponseWriter respoonseWriter, UIComponent uiComponent)
		throws IOException;

	protected void encodeArray(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {
		encodeObject(responseWriter, attributeName, attributeValue, first);
	}

	protected void encodeBoolean(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {
		encodeObject(responseWriter, attributeName, attributeValue, first);
	}

	protected void encodeComplexBoolean(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		String value = attributeValue.toString();

		if (value.equalsIgnoreCase(StringPool.TRUE) || value.equalsIgnoreCase(StringPool.FALSE)) {
			encodeBoolean(responseWriter, attributeName, value, first);
		}
		else {
			encodeString(responseWriter, attributeName, value, first);
		}
	}

	protected void encodeComplexNumber(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		String value = attributeValue.toString();
		Matcher matcher = NUMBER_PATTERN.matcher(value);

		if (matcher.matches()) {
			encodeNumber(responseWriter, attributeName, value, first);
		}
		else {
			encodeString(responseWriter, attributeName, value, first);
		}
	}

	protected void encodeEvent(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(AlloyConstants.FUNCTION_EVENT);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(attributeValue.toString());
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	protected void encodeJavaScriptBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (!isAjax(facesContext) && isForceInline(facesContext, uiComponent)) {

			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.CDATA_OPEN);
			responseWriter.write(StringPool.NEW_LINE);
		}

		if (isAjax(facesContext) || isForceInline(facesContext, uiComponent)) {

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
			responseWriter.write(YUI);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			encodeLang(facesContext, responseWriter, uiComponent);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.PERIOD);
			responseWriter.write(USE);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);

			String[] modules = getModules();

			for (String module : modules) {

				responseWriter.write(StringPool.APOSTROPHE);
				responseWriter.write(module);
				responseWriter.write(StringPool.APOSTROPHE);
				responseWriter.write(StringPool.COMMA);
			}

			responseWriter.write(FUNCTION_A);
			responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		}
	}

	@Override
	protected void encodeJavaScriptEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (isAjax(facesContext) || isForceInline(facesContext, uiComponent)) {

			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.SEMICOLON);
		}

		if (!isAjax(facesContext) && isForceInline(facesContext, uiComponent)) {

			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.CDATA_CLOSE);
		}
	}

	@Override
	protected void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		ClientComponent clientComponent = (ClientComponent) uiComponent;
		String clientVarName = ComponentUtil.getClientVarName(facesContext, clientComponent);
		String clientKey = clientComponent.getClientKey();
		
		if (clientKey == null) {
			clientKey = clientVarName;
		}

		responseWriter.write(VAR);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(clientVarName);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(LIFERAY_COMPONENT);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(clientKey);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.COMMA);
		responseWriter.write(FUNCTION);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(IF);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.EXCLAMATION);
		responseWriter.write(clientVarName);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(clientVarName);
		responseWriter.write(StringPool.EQUAL);
		responseWriter.write(NEW);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(A);
		responseWriter.write(StringPool.PERIOD);
		responseWriter.write(getAlloyClassName());
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		encodeAlloyAttributes(responseWriter, uiComponent);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(RETURN);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(clientVarName);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
		responseWriter.write(LIFERAY_COMPONENT);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(clientVarName);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
	}

	protected void encodeLang(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {
		// no-op
	}

	protected void encodeLiferayComponent(ResponseWriter responseWriter, String clientKey) throws IOException {

		responseWriter.write(LIFERAY_COMPONENT);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(clientKey);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
	}

	protected void encodeLiferayComponentVar(ResponseWriter responseWriter, String clientVarName, String clientKey)
		throws IOException {

		responseWriter.write(VAR);
		responseWriter.write(StringPool.SPACE);
		responseWriter.write(clientVarName);
		responseWriter.write(StringPool.EQUAL);
		encodeLiferayComponent(responseWriter, clientKey);
		responseWriter.write(StringPool.SEMICOLON);
	}

	protected void encodeMap(ResponseWriter responseWriter, String attributeName, Map<String, String> attributeValues,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
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
		encodeObject(responseWriter, attributeName, attributeValue, first);
	}

	protected void encodeObject(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(attributeValue.toString());
	}

	protected void encodeString(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {

		if (!first) {
			responseWriter.write(StringPool.COMMA);
		}

		responseWriter.write(attributeName);
		responseWriter.write(StringPool.COLON);
		responseWriter.write(StringPool.QUOTE);
		responseWriter.write(attributeValue.toString());
		responseWriter.write(StringPool.QUOTE);
	}

	@Override
	protected boolean hasJavaScript() {
		return true;
	}

	protected abstract String getAlloyClassName();
}
