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
package com.liferay.faces.bridge.renderkit.bridge;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.lifecycle.ClientWindow;
import javax.faces.render.ResponseStateManager;


/**
 * @author  Neil Griffin
 */
public abstract class ResponseWriterBridgeCompat_2_2_Impl extends ResponseWriterBridgeCompat_2_0_Impl {

	// Protected Constants
	protected static final String CLIENT_WINDOW_PARAM = ResponseStateManager.CLIENT_WINDOW_PARAM;

	public ResponseWriterBridgeCompat_2_2_Impl(ResponseWriter wrappedResponseWriter) {
		super(wrappedResponseWriter);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {

		// Note that this is no longer necessary for JSF 2.2, so simply delegate to the wrapped ResponseWriter rather
		// than executing the JSF 2.0 method in the parent class.
		getWrapped().write(cbuf, off, len);
	}

	@Override
	public void writePreamble(String preamble) throws IOException {
		// No-op for portlets: http://java.net/jira/browse/JAVASERVERFACES_SPEC_PUBLIC-1069
	}

	protected void writeClientWindowHiddenField() throws IOException {

		startElement(ELEMENT_INPUT, null);
		writeAttribute(ATTRIBUTE_TYPE, TYPE_HIDDEN, null);

		String clientWindowName = CLIENT_WINDOW_PARAM;

		if (namespacedParameters) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			String namingContainerId = facesContext.getViewRoot().getContainerClientId(facesContext);
			clientWindowName = namingContainerId + clientWindowName;
		}

		writeAttribute(ATTRIBUTE_NAME, clientWindowName, null);

		// TODO: The following line is a workaround and needs to be fixed in FACES-1798.
		writeAttribute(ATTRIBUTE_ID, clientWindowName, null);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		ClientWindow clientWindow = externalContext.getClientWindow();

		if (clientWindow != null) {
			String clientWindowId = clientWindow.getId();
			writeAttribute(ATTRIBUTE_VALUE, clientWindowId, null);
		}

		writeAttribute(ATTRIBUTE_AUTOCOMPLETE, VALUE_OFF, null);
		endElement(ELEMENT_INPUT);
	}
}
