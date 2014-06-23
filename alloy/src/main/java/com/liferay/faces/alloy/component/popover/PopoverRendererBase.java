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
package com.liferay.faces.alloy.component.popover;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.overlay.OverlayRendererBase;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PopoverRendererBase extends OverlayRendererBase {

	// Protected Constants
	protected static final String CLIENT_KEY = "clientKey";
	protected static final String DISMISSABLE = "dismissable";
	protected static final String FOR = "for";
	protected static final String HEADER_CONTENT = "headerContent";
	protected static final String POSITION = "position";
	protected static final String SHOW_CLOSE_ICON = "showCloseIcon";
	protected static final String VISIBLE = "visible";
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

		Boolean autoShow = popover.isAutoShow();

		if (autoShow != null) {

			encodeVisible(responseWriter, popover, autoShow, first);
			first = false;
		}

		String headerText = popover.getHeaderText();

		if (headerText != null) {

			encodeHeaderContent(responseWriter, popover, headerText, first);
			first = false;
		}

		String position = popover.getPosition();

		if (position != null) {

			encodePosition(responseWriter, popover, position, first);
			first = false;
		}

		String zIndex = popover.getzIndex();

		if (zIndex != null) {

			encodeZIndex(responseWriter, popover, zIndex, first);
			first = false;
		}

		encodeHiddenAttributes(facesContext, responseWriter, popover, first);
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeVisible(ResponseWriter responseWriter, Popover popover, Boolean autoShow, boolean first) throws IOException {
		encodeBoolean(responseWriter, VISIBLE, autoShow, first);
	}

	protected void encodeHeaderContent(ResponseWriter responseWriter, Popover popover, String headerText, boolean first) throws IOException {
		encodeString(responseWriter, HEADER_CONTENT, headerText, first);
	}

	protected void encodePosition(ResponseWriter responseWriter, Popover popover, String position, boolean first) throws IOException {
		encodeString(responseWriter, POSITION, position, first);
	}

	protected void encodeZIndex(ResponseWriter responseWriter, Popover popover, String zIndex, boolean first) throws IOException {
		encodeString(responseWriter, Z_INDEX, zIndex, first);
	}

	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, Popover popover, boolean first) throws IOException {
		// no-op
	}
}
//J+
