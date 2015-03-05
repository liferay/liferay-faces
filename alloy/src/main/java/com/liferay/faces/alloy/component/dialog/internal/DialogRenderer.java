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
package com.liferay.faces.alloy.component.dialog.internal;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.dialog.Dialog;
import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Vernon Singleton
 */
//J-
@FacesRenderer(componentFamily = Dialog.COMPONENT_FAMILY, rendererType = Dialog.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
	}
)
//J+
public class DialogRenderer extends DialogRendererBase {

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		Dialog dialog = (Dialog) uiComponent;
		ClientComponent clientComponent = (ClientComponent) uiComponent;
		String clientVarName = ComponentUtil.getClientVarName(facesContext, clientComponent);
		String clientKey = clientComponent.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}

		// Prevent scrolling when the show() JavaScript function is called.
		responseWriter.write("var " + clientKey + "_scrollx=window.scrollX;");
		responseWriter.write("var " + clientKey + "_scrolly=window.scrollY;");
		responseWriter.write("A.Do.before(function(stuff) { " + clientKey + "_scrollx=window.scrollX; " + clientKey +
			"_scrolly=window.scrollY;},Liferay.component('" + clientKey + "'),'show');");
		responseWriter.write("A.Do.after(function(stuff){window.scrollTo(" + clientKey + "_scrollx," + clientKey +
			"_scrolly);},Liferay.component('" + clientKey + "'),'show');");

		if (!dialog.isHideIconRendered()) {
			responseWriter.write(LIFERAY_COMPONENT);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(clientKey);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(".removeToolbar('header')");
			responseWriter.write(StringPool.SEMICOLON);
		}

		if (!dialog.isModal() && dialog.isDismissible()) {
			encodeOverlayDismissible(responseWriter, dialog, clientKey);
		}

		encodeOverlayJavaScriptCustom(responseWriter, facesContext, dialog);
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, Dialog dialog,
		boolean first) throws IOException {

		// Encode the "centered: true" Alloy attribute.
		encodeBoolean(responseWriter, StringPool.CENTERED, true, first);

		first = false;

		// contentBox, headerText, render : true, visible
		encodeOverlayHiddenAttributes(facesContext, responseWriter, dialog, first);
	}

	@Override
	protected void encodeZIndex(ResponseWriter responseWriter, Dialog dialog, Integer zIndex, boolean first)
		throws IOException {
		encodeOverlayZIndex(responseWriter, dialog, zIndex, LIFERAY_Z_INDEX_OVERLAY, first);
	}

	@Override
	public String getDelegateComponentFamily() {
		return Dialog.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return Dialog.DELEGATE_RENDERER_TYPE;
	}
}
