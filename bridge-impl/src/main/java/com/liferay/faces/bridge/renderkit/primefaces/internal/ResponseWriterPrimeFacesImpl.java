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
package com.liferay.faces.bridge.renderkit.primefaces.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;


/**
 * @author  Neil Griffin
 */
public class ResponseWriterPrimeFacesImpl extends ResponseWriterWrapper {

	// Private Data Members
	private String nonAjaxPartialActionURL;
	private ResponseWriter wrappedResponseWriter;
	private boolean writingForm;

	public ResponseWriterPrimeFacesImpl(ResponseWriter responseWriter, String nonAjaxPartialActionURL) {
		this.wrappedResponseWriter = responseWriter;
		this.nonAjaxPartialActionURL = nonAjaxPartialActionURL;
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {
		super.startElement(name, component);

		if ("form".equals(name)) {
			writingForm = true;
		}
		else {
			writingForm = false;
		}
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (writingForm && "action".equals(name)) {
			value = nonAjaxPartialActionURL;
		}

		super.writeAttribute(name, value, property);
	}

	@Override
	public ResponseWriter getWrapped() {
		return wrappedResponseWriter;
	}
}
