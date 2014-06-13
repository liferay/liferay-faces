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
package com.liferay.faces.alloy.component.outputtooltip;
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
public abstract class OutputTooltipRendererBase extends DelegatingAlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "Tooltip";
	private static final String ALLOY_MODULE_NAME = "aui-tooltip";
	protected static final String TRIGGER = "trigger";

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		OutputTooltip outputTooltip = (OutputTooltip) uiComponent;
		boolean first = true;

		String cssClass = outputTooltip.getCssClass();

		if (cssClass != null) {

			encodeCssClass(responseWriter, outputTooltip, cssClass, first);
			first = false;
		}

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

		Object value = outputTooltip.getValue();

		if (value != null) {

			encodeValue(responseWriter, outputTooltip, value, first);
			first = false;
		}

		Integer zIndex = outputTooltip.getzIndex();

		if (zIndex != null) {

			encodeZIndex(responseWriter, outputTooltip, zIndex, first);
			first = false;
		}

		encodeHiddenAttributes(responseWriter, outputTooltip, first);
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeCssClass(ResponseWriter responseWriter, OutputTooltip outputTooltip, String cssClass, boolean first) throws IOException {
		encodeString(responseWriter, OutputTooltip.CSS_CLASS, cssClass, first);
	}

	protected void encodeTrigger(ResponseWriter responseWriter, OutputTooltip outputTooltip, String for_, boolean first) throws IOException {
		encodeString(responseWriter, TRIGGER, for_, first);
	}

	protected void encodeOpacity(ResponseWriter responseWriter, OutputTooltip outputTooltip, String opacity, boolean first) throws IOException {
		encodeString(responseWriter, OutputTooltip.OPACITY, opacity, first);
	}

	protected void encodePosition(ResponseWriter responseWriter, OutputTooltip outputTooltip, String position, boolean first) throws IOException {
		encodeString(responseWriter, OutputTooltip.POSITION, position, first);
	}

	protected void encodeValue(ResponseWriter responseWriter, OutputTooltip outputTooltip, Object value, boolean first) throws IOException {
		encodeString(responseWriter, OutputTooltip.VALUE, value, first);
	}

	protected void encodeZIndex(ResponseWriter responseWriter, OutputTooltip outputTooltip, Integer zIndex, boolean first) throws IOException {
		encodeInteger(responseWriter, OutputTooltip.Z_INDEX, zIndex, first);
	}

	protected void encodeHiddenAttributes(ResponseWriter responseWriter, OutputTooltip outputTooltip, boolean first) throws IOException {
		// no-op
	}
}
//J+
