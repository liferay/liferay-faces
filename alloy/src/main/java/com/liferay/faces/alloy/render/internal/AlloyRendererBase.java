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
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.render.RendererUtil;


/**
 * This is an abstract class that provides base rendering functionality for AlloyUI JavaScript components.
 *
 * @author  Kyle Stiemann
 */
public abstract class AlloyRendererBase extends ClientComponentRendererBase implements AlloyRenderer {

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {
		super.decode(facesContext, uiComponent);
		decodeClientBehaviors(facesContext, uiComponent);
	}

	@Override
	public void decodeClientBehaviors(FacesContext facesContext, UIComponent uiComponent) {
		RendererUtil.decodeClientBehaviors(facesContext, uiComponent);
	}

	@Override
	public void encodeBoolean(ResponseWriter responseWriter, String attributeName, Boolean attributeValue,
		boolean first) throws IOException {
		AlloyRendererCommon.encodeBoolean(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeClientId(ResponseWriter responseWriter, String attributeName, String clientId, boolean first)
		throws IOException {
		AlloyRendererCommon.encodeClientId(responseWriter, attributeName, clientId, first);
	}

	@Override
	public void encodeClientId(ResponseWriter responseWriter, String attributeName, String clientId,
		UIComponent uiComponent, boolean first) throws IOException {
		AlloyRendererCommon.encodeClientId(responseWriter, attributeName, clientId, uiComponent, first);
	}

	@Override
	public void encodeEventCallback(ResponseWriter responseWriter, String varName, String methodName, String eventName,
		String callback) throws IOException {
		AlloyRendererCommon.encodeEventCallback(responseWriter, varName, methodName, eventName, callback);
	}

	@Override
	public void encodeFunctionCall(ResponseWriter responseWriter, String functionName, Object... parameters)
		throws IOException {
		AlloyRendererCommon.encodeFunctionCall(responseWriter, functionName, parameters);
	}

	@Override
	public void encodeInteger(ResponseWriter responseWriter, String attributeName, Integer attributeValue,
		boolean first) throws IOException {
		AlloyRendererCommon.encodeInteger(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeJavaScriptBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		AlloyRendererCommon.encodeJavaScriptBegin(facesContext, uiComponent, this,
			getModules(facesContext, uiComponent), isAjax(facesContext), isSandboxed(facesContext, uiComponent));
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// Default method provided as a no-op for convenience to subclasses.
	}

	@Override
	public void encodeJavaScriptEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		AlloyRendererCommon.encodeJavaScriptEnd(facesContext, uiComponent, isAjax(facesContext),
			isSandboxed(facesContext, uiComponent));
	}

	@Override
	public void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		AlloyRendererCommon.encodeJavaScriptMain(facesContext, uiComponent,
			getAlloyClassName(facesContext, uiComponent), this);
	}

	@Override
	public void encodeLiferayComponentVar(ResponseWriter responseWriter, String clientVarName, String clientKey)
		throws IOException {
		AlloyRendererCommon.encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);
	}

	@Override
	public void encodeNonEscapedObject(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {
		AlloyRendererCommon.encodeNonEscapedObject(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeString(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException {
		AlloyRendererCommon.encodeString(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeWidgetRender(ResponseWriter responseWriter, boolean first) throws IOException {
		AlloyRendererCommon.encodeWidgetRender(responseWriter, first);
	}

	@Override
	public String escapeClientId(String clientId) {
		return AlloyRendererCommon.escapeClientId(clientId);
	}

	@Override
	public String escapeJavaScript(String javaScript) {
		return AlloyRendererCommon.escapeJavaScript(javaScript);
	}

	@Override
	public String getClientVarName(FacesContext facesContext, ClientComponent clientComponent) {
		return AlloyRendererCommon.getClientVarName(facesContext, clientComponent);
	}

	@Override
	public String getYUIConfig(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {
		return null;
	}
}
