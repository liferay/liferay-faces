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
package com.liferay.faces.alloy.component.dialog.internal;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.dialog.Dialog;

import com.liferay.faces.alloy.component.overlay.internal.OverlayRendererBase;


/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class DialogRendererBase extends OverlayRendererBase {

	// Protected Constants
	protected static final String AUTO_SHOW = "autoShow";
	protected static final String CLIENT_KEY = "clientKey";
	protected static final String DISMISSIBLE = "dismissible";
	protected static final String HEADER_TEXT = "headerText";
	protected static final String HEIGHT = "height";
	protected static final String HIDE_ICON_RENDERED = "hideIconRendered";
	protected static final String MODAL = "modal";
	protected static final String STYLE_CLASS = "styleClass";
	protected static final String WIDTH = "width";
	protected static final String Z_INDEX = "zIndex";

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "Modal";
	private static final String ALLOY_MODULE_NAME = "aui-modal";

	// Modules
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		Dialog dialog = (Dialog) uiComponent;
		boolean first = true;

		String height = dialog.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, dialog, height, first);
			first = false;
		}

		Boolean modal = dialog.isModal();

		if (modal != null) {

			encodeModal(responseWriter, dialog, modal, first);
			first = false;
		}

		String width = dialog.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, dialog, width, first);
			first = false;
		}

		Integer zIndex = dialog.getzIndex();

		if (zIndex != null) {

			encodeZIndex(responseWriter, dialog, zIndex, first);
			first = false;
		}

		encodeHiddenAttributes(facesContext, responseWriter, dialog, first);
	}

	@Override
	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent) {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return MODULES;
	}

	protected void encodeHeight(ResponseWriter responseWriter, Dialog dialog, String height, boolean first) throws IOException {
		encodeString(responseWriter, HEIGHT, height, first);
	}

	protected void encodeModal(ResponseWriter responseWriter, Dialog dialog, Boolean modal, boolean first) throws IOException {
		encodeBoolean(responseWriter, MODAL, modal, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, Dialog dialog, String width, boolean first) throws IOException {
		encodeString(responseWriter, WIDTH, width, first);
	}

	protected void encodeZIndex(ResponseWriter responseWriter, Dialog dialog, Integer zIndex, boolean first) throws IOException {
		encodeInteger(responseWriter, Z_INDEX, zIndex, first);
	}

	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, Dialog dialog, boolean first) throws IOException {
		// no-op
	}

	@Override
	public String getDelegateComponentFamily() {
		return Dialog.COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return "javax.faces.Group";
	}
}
//J+
