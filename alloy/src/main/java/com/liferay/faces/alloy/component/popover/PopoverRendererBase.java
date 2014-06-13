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
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.renderkit.DelegatingAlloyRendererBase;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PopoverRendererBase extends DelegatingAlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "Popover";
	private static final String ALLOY_MODULE_NAME = "aui-popover";
	protected static final String ALIGN = "align";
	protected static final String HEADER_CONTENT = "headerContent";

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		Popover popover = (Popover) uiComponent;
		boolean first = true;

		String for_ = popover.getFor();

		if (for_ != null) {

			encodeAlign(responseWriter, popover, for_, first);
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

		Integer zIndex = popover.getzIndex();

		if (zIndex != null) {

			encodeZIndex(responseWriter, popover, zIndex, first);
			first = false;
		}

		encodeHiddenAttributes(responseWriter, popover, first);
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeAlign(ResponseWriter responseWriter, Popover popover, String for_, boolean first) throws IOException {
		encodeString(responseWriter, ALIGN, for_, first);
	}

	protected void encodeHeaderContent(ResponseWriter responseWriter, Popover popover, String headerText, boolean first) throws IOException {
		encodeString(responseWriter, HEADER_CONTENT, headerText, first);
	}

	protected void encodePosition(ResponseWriter responseWriter, Popover popover, String position, boolean first) throws IOException {
		encodeString(responseWriter, Popover.POSITION, position, first);
	}

	protected void encodeZIndex(ResponseWriter responseWriter, Popover popover, Integer zIndex, boolean first) throws IOException {
		encodeInteger(responseWriter, Popover.Z_INDEX, zIndex, first);
	}

	protected void encodeHiddenAttributes(ResponseWriter responseWriter, Popover popover, boolean first) throws IOException {
		// no-op
	}
}
//J+
