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
public abstract class DialogRendererBase extends DelegatingAlloyRendererBase {

	// Private Constants
	private static final String ALLOY_CLASS_NAME = "Modal";
	private static final String ALLOY_MODULE_NAME = "aui-modal";
	protected static final String HEADER_CONTENT = "headerContent";
	protected static final String VISIBLE = "visible";

	// Protected Constants
	protected static final String[] MODULES = {ALLOY_MODULE_NAME};

	@Override
	public void encodeAlloyAttributes(ResponseWriter responseWriter, UIComponent uiComponent) throws IOException {

		Dialog dialog = (Dialog) uiComponent;
		boolean first = true;

		Boolean autoShow = dialog.isAutoShow();

		if (autoShow != null) {

			encodeVisible(responseWriter, dialog, autoShow, first);
			first = false;
		}

		String headerText = dialog.getHeaderText();

		if (headerText != null) {

			encodeHeaderContent(responseWriter, dialog, headerText, first);
			first = false;
		}

		Boolean modal = dialog.isModal();

		if (modal != null) {

			encodeModal(responseWriter, dialog, modal, first);
			first = false;
		}

		Integer zIndex = dialog.getzIndex();

		if (zIndex != null) {

			encodeZIndex(responseWriter, dialog, zIndex, first);
			first = false;
		}

		encodeHiddenAttributes(responseWriter, dialog, first);
	}

	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}

	@Override
	protected String[] getModules() {
		return MODULES;
	}

	protected void encodeVisible(ResponseWriter responseWriter, Dialog dialog, Boolean autoShow, boolean first) throws IOException {
		encodeBoolean(responseWriter, VISIBLE, autoShow, first);
	}

	protected void encodeHeaderContent(ResponseWriter responseWriter, Dialog dialog, String headerText, boolean first) throws IOException {
		encodeString(responseWriter, HEADER_CONTENT, headerText, first);
	}

	protected void encodeModal(ResponseWriter responseWriter, Dialog dialog, Boolean modal, boolean first) throws IOException {
		encodeBoolean(responseWriter, Dialog.MODAL, modal, first);
	}

	protected void encodeZIndex(ResponseWriter responseWriter, Dialog dialog, Integer zIndex, boolean first) throws IOException {
		encodeInteger(responseWriter, Dialog.Z_INDEX, zIndex, first);
	}

	protected void encodeHiddenAttributes(ResponseWriter responseWriter, Dialog dialog, boolean first) throws IOException {
		// no-op
	}
}
//J+
