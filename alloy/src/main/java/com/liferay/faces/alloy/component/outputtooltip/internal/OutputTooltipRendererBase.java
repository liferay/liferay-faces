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
package com.liferay.faces.alloy.component.outputtooltip.internal;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.outputtooltip.OutputTooltip;

import com.liferay.faces.alloy.component.overlay.internal.OverlayRendererBase;


/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class OutputTooltipRendererBase extends OverlayRendererBase {

	// Protected Constants
	protected static final String AUTO_SHOW = "autoShow";
	protected static final String CLIENT_KEY = "clientKey";
	protected static final String HEADER_TEXT = "headerText";
	protected static final String OPACITY = "opacity";
	protected static final String POSITION = "position";
	protected static final String STYLE_CLASS = "styleClass";
	protected static final String TRIGGER = "trigger";
	protected static final String Z_INDEX = "zIndex";

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "Tooltip";
	private static final String ALLOY_MODULE_NAME = "aui-tooltip";

	// Modules
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		OutputTooltip outputTooltip = (OutputTooltip) uiComponent;
		boolean first = true;

		String for_ = outputTooltip.getFor();

		if (for_ != null) {

			encodeTrigger(responseWriter, outputTooltip, for_, first);
			first = false;
		}

		String opacity = outputTooltip.getOpacity();

		if (opacity != null) {

			encodeOpacity(responseWriter, outputTooltip, opacity, first);
			first = false;
		}

		String position = outputTooltip.getPosition();

		if (position != null) {

			encodePosition(responseWriter, outputTooltip, position, first);
			first = false;
		}

		Integer zIndex = outputTooltip.getzIndex();

		if (zIndex != null) {

			encodeZIndex(responseWriter, outputTooltip, zIndex, first);
			first = false;
		}

		encodeHiddenAttributes(facesContext, responseWriter, outputTooltip, first);
	}

	@Override
	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent) {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return MODULES;
	}

	protected void encodeTrigger(ResponseWriter responseWriter, OutputTooltip outputTooltip, String for_, boolean first) throws IOException {
		encodeClientId(responseWriter, TRIGGER, for_, outputTooltip, first);
	}

	protected void encodeOpacity(ResponseWriter responseWriter, OutputTooltip outputTooltip, String opacity, boolean first) throws IOException {
		encodeString(responseWriter, OPACITY, opacity, first);
	}

	protected void encodePosition(ResponseWriter responseWriter, OutputTooltip outputTooltip, String position, boolean first) throws IOException {
		encodeString(responseWriter, POSITION, position, first);
	}

	protected void encodeZIndex(ResponseWriter responseWriter, OutputTooltip outputTooltip, Integer zIndex, boolean first) throws IOException {
		encodeInteger(responseWriter, Z_INDEX, zIndex, first);
	}

	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, OutputTooltip outputTooltip, boolean first) throws IOException {
		// no-op
	}

	@Override
	public String getDelegateComponentFamily() {
		return OutputTooltip.COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return "javax.faces.Text";
	}
}
//J+
