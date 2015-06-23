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
package com.liferay.faces.alloy.component.progressbar.internal;
//J-

import java.io.IOException;

import javax.annotation.Generated;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.progressbar.ProgressBar;

import com.liferay.faces.alloy.render.internal.AlloyRendererBase;


/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ProgressBarRendererBase extends AlloyRendererBase {

	// Protected Constants
	protected static final String CLIENT_KEY = "clientKey";
	protected static final String HEIGHT = "height";
	protected static final String LABEL = "label";
	protected static final String MAX = "max";
	protected static final String MIN = "min";
	protected static final String ONCOMPLETE = "oncomplete";
	protected static final String ORIENTATION = "orientation";
	protected static final String POLLING_DELAY = "pollingDelay";
	protected static final String STYLE = "style";
	protected static final String STYLE_CLASS = "styleClass";
	protected static final String VALUE = "value";
	protected static final String WIDTH = "width";

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "ProgressBar";
	private static final String ALLOY_MODULE_NAME = "aui-progressbar";

	// Modules
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		ProgressBar progressBar = (ProgressBar) uiComponent;
		boolean first = true;

		String height = progressBar.getHeight();

		if (height != null) {

			encodeHeight(responseWriter, progressBar, height, first);
			first = false;
		}

		String label = progressBar.getLabel();

		if (label != null) {

			encodeLabel(responseWriter, progressBar, label, first);
			first = false;
		}

		String layout = progressBar.getLayout();

		if (layout != null) {

			encodeOrientation(responseWriter, progressBar, layout, first);
			first = false;
		}

		Integer maxProgress = progressBar.getMaxProgress();

		if (maxProgress != null) {

			encodeMax(responseWriter, progressBar, maxProgress, first);
			first = false;
		}

		Integer minProgress = progressBar.getMinProgress();

		if (minProgress != null) {

			encodeMin(responseWriter, progressBar, minProgress, first);
			first = false;
		}

		Integer value = progressBar.getValue();

		if (value != null) {

			encodeValue(responseWriter, progressBar, value, first);
			first = false;
		}

		String width = progressBar.getWidth();

		if (width != null) {

			encodeWidth(responseWriter, progressBar, width, first);
			first = false;
		}

		encodeHiddenAttributes(facesContext, responseWriter, progressBar, first);
	}

	@Override
	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent) {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return MODULES;
	}

	protected void encodeHeight(ResponseWriter responseWriter, ProgressBar progressBar, String height, boolean first) throws IOException {
		encodeString(responseWriter, HEIGHT, height, first);
	}

	protected void encodeLabel(ResponseWriter responseWriter, ProgressBar progressBar, String label, boolean first) throws IOException {
		encodeString(responseWriter, LABEL, label, first);
	}

	protected void encodeOrientation(ResponseWriter responseWriter, ProgressBar progressBar, String layout, boolean first) throws IOException {
		encodeString(responseWriter, ORIENTATION, layout, first);
	}

	protected void encodeMax(ResponseWriter responseWriter, ProgressBar progressBar, Integer maxProgress, boolean first) throws IOException {
		encodeInteger(responseWriter, MAX, maxProgress, first);
	}

	protected void encodeMin(ResponseWriter responseWriter, ProgressBar progressBar, Integer minProgress, boolean first) throws IOException {
		encodeInteger(responseWriter, MIN, minProgress, first);
	}

	protected void encodeValue(ResponseWriter responseWriter, ProgressBar progressBar, Integer value, boolean first) throws IOException {
		encodeInteger(responseWriter, VALUE, value, first);
	}

	protected void encodeWidth(ResponseWriter responseWriter, ProgressBar progressBar, String width, boolean first) throws IOException {
		encodeString(responseWriter, WIDTH, width, first);
	}

	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, ProgressBar progressBar, boolean first) throws IOException {
		// no-op
	}
}
//J+
