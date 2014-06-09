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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.render.DelegatingClientComponentRendererBase;


/**
 * @author  Neil Griffin
 */
public abstract class DelegatingAlloyRendererBase extends DelegatingClientComponentRendererBase
	implements AlloyRenderer {

	@Override
	public abstract void encodeAlloyAttributes(ResponseWriter respoonseWriter, UIComponent uiComponent)
		throws IOException;

	@Override
	public void encodeArray(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException {
		AlloyRendererUtil.encodeArray(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeBoolean(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException {
		AlloyRendererUtil.encodeBoolean(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeComplexBoolean(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {
		AlloyRendererUtil.encodeComplexBoolean(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeComplexNumber(ResponseWriter responseWriter, String attributeName, Object attributeValue,
		boolean first) throws IOException {
		AlloyRendererUtil.encodeComplexNumber(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeEvent(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException {
		AlloyRendererUtil.encodeEvent(responseWriter, attributeName, attributeValue, first);
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
	public void encodeMap(ResponseWriter responseWriter, String attributeName, Map<String, String> attributeValues,
		boolean first) throws IOException {
		AlloyRendererUtil.encodeMap(responseWriter, attributeName, attributeValues, first);
	}

	@Override
	public void encodeNumber(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException {
		AlloyRendererUtil.encodeNumber(responseWriter, attributeName, attributeValue, first);
	}

	@Override
	public void encodeObject(ResponseWriter responseWriter, String attributeName, Object attributeValue, boolean first)
		throws IOException {
		AlloyRendererUtil.encodeObject(responseWriter, attributeName, attributeValue, first);
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
