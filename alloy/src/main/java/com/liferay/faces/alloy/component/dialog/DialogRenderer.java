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
package com.liferay.faces.alloy.component.dialog;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.overlay.Overlay;
import com.liferay.faces.alloy.component.overlay.OverlayRendererUtil;
import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Vernon Singleton
 */
//J-
@FacesRenderer(componentFamily = Dialog.COMPONENT_FAMILY, rendererType = Dialog.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
	}
)
//J+
public class DialogRenderer extends DialogRendererBase {

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		OverlayRendererUtil.encodeJavaScriptCustom(facesContext, (Overlay) uiComponent, false);
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		OverlayRendererUtil.encodeMarkupBegin(facesContext, uiComponent, this);
	}

	@Override
	protected void encodeHiddenAttributes(ResponseWriter responseWriter, Dialog dialog, boolean first)
		throws IOException {

		// Encode the "centered: true" Alloy attribute.
		encodeBoolean(responseWriter, StringPool.CENTERED, true, first);

		first = false;

		OverlayRendererUtil.encodeHiddenAttributes(responseWriter, dialog, first, this);
	}

	@Override
	protected void encodeZIndex(ResponseWriter responseWriter, Dialog dialog, Integer zIndex, boolean first)
		throws IOException {

		if (zIndex == Integer.MIN_VALUE) {
			encodeNonEscapedObject(responseWriter, Dialog.Z_INDEX, AlloyRendererUtil.LIFERAY_Z_INDEX_OVERLAY, first);
		}
		else {
			super.encodeZIndex(responseWriter, dialog, zIndex, first);
		}
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
