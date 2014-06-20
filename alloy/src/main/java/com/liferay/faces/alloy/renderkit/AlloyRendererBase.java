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

import com.liferay.faces.util.render.ClientComponentRendererBase;


/**
 * This is an abstract class that provides base rendering functionality for AlloyUI JavaScript components.
 *
 * @author  Kyle Stiemann
 */
public abstract class AlloyRendererBase extends ClientComponentRendererBase implements AlloyRenderer {

	@Override
	public abstract void encodeAlloyAttributes(ResponseWriter respoonseWriter, UIComponent uiComponent)
		throws IOException;

	@Override
	public abstract void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public abstract void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public void encodeBoolean(ResponseWriter responseWriter, String attributeName, Boolean attributeValue,
		boolean first) throws IOException {
		AlloyRendererUtil.encodeBoolean(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeClientId(ResponseWriter responseWriter, String attributeName, String clientId, boolean first)
		throws IOException {
		AlloyRendererUtil.encodeClientId(responseWriter, attributeName, clientId, first);
	}

	@Override
	public void encodeClientId(ResponseWriter responseWriter, String attributeName, String clientId,
		UIComponent uiComponent, boolean first) throws IOException {
		AlloyRendererUtil.encodeClientId(responseWriter, attributeName, clientId, uiComponent, first);
	}

	@Override
	public void encodeInteger(ResponseWriter responseWriter, String attributeName, Integer attributeValue,
		boolean first) throws IOException {
		AlloyRendererUtil.encodeInteger(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeJavaScriptBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		AlloyRendererUtil.encodeJavaScriptBegin(facesContext, uiComponent, this, getModules(), isAjax(facesContext),
			isForceInline(facesContext, uiComponent));
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// Default method provided as a no-op for convenience to subclasses.
	}

	@Override
	public void encodeJavaScriptEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		AlloyRendererUtil.encodeJavaScriptEnd(facesContext, uiComponent, isAjax(facesContext),
			isForceInline(facesContext, uiComponent));
	}

	@Override
	public void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		AlloyRendererUtil.encodeJavaScriptMain(facesContext, uiComponent, getAlloyClassName(), this);
	}

	@Override
	public void encodeLang(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {
		// no-op
	}

	@Override
	public void encodeLiferayComponent(ResponseWriter responseWriter, String clientKey) throws IOException {
		AlloyRendererUtil.encodeLiferayComponent(responseWriter, clientKey);
	}

	@Override
	public void encodeLiferayComponentVar(ResponseWriter responseWriter, String clientVarName, String clientKey)
		throws IOException {
		AlloyRendererUtil.encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);
	}

	@Override
	public void encodeNonEscapedObject(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {
		AlloyRendererUtil.encodeNonEscapedObject(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeString(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException {
		AlloyRendererUtil.encodeString(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeWidgetRender(ResponseWriter responseWriter, boolean first) throws IOException {
		AlloyRendererUtil.encodeWidgetRender(responseWriter, first);
	}

	@Override
	public abstract String getAlloyClassName();
}
