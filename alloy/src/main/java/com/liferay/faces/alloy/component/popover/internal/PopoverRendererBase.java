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
package com.liferay.faces.alloy.component.popover.internal;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.popover.Popover;

import com.liferay.faces.alloy.component.overlay.internal.OverlayRendererBase;


/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PopoverRendererBase extends OverlayRendererBase {

	// Protected Constants
	protected static final String AUTO_SHOW = "autoShow";
	protected static final String CLIENT_KEY = "clientKey";
	protected static final String DISMISSIBLE = "dismissible";
	protected static final String FOR = "for";
	protected static final String HEADER_TEXT = "headerText";
	protected static final String HEIGHT = "height";
	protected static final String HIDE_ICON_RENDERED = "hideIconRendered";
	protected static final String POSITION = "position";
	protected static final String STYLE_CLASS = "styleClass";
	protected static final String WIDTH = "width";
	protected static final String Z_INDEX = "zIndex";

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "Popover";
	private static final String ALLOY_MODULE_NAME = "aui-popover";

	// Modules
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		Popover popover = (Popover) uiComponent;
		boolean first = true;

		String height = popover.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, popover, height, first);
			first = false;
		}

		String position = popover.getPosition();

		if (position != null) {

			encodePosition(responseWriter, popover, position, first);
			first = false;
		}

		String width = popover.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, popover, width, first);
			first = false;
		}

		Integer zIndex = popover.getzIndex();

		if (zIndex != null) {

			encodeZIndex(responseWriter, popover, zIndex, first);
			first = false;
		}

		encodeHiddenAttributes(facesContext, responseWriter, popover, first);
	}

	@Override
	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent) {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return MODULES;
	}

	protected void encodeHeight(ResponseWriter responseWriter, Popover popover, String height, boolean first) throws IOException {
		encodeString(responseWriter, HEIGHT, height, first);
	}

	protected void encodePosition(ResponseWriter responseWriter, Popover popover, String position, boolean first) throws IOException {
		encodeString(responseWriter, POSITION, position, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, Popover popover, String width, boolean first) throws IOException {
		encodeString(responseWriter, WIDTH, width, first);
	}

	protected void encodeZIndex(ResponseWriter responseWriter, Popover popover, Integer zIndex, boolean first) throws IOException {
		encodeInteger(responseWriter, Z_INDEX, zIndex, first);
	}

	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, Popover popover, boolean first) throws IOException {
		// no-op
	}

	@Override
	public String getDelegateComponentFamily() {
		return Popover.COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return "javax.faces.Group";
	}
}
//J+
