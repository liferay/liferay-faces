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
package com.liferay.faces.alloy.component.button.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


/**
 * @author  Kyle Stiemann
 */
public class ButtonResponseWriter extends DelegationResponseWriterBase {

	// Private data members.
	private String src;
	private boolean writeOnclick;

	public ButtonResponseWriter(ResponseWriter responseWriter, boolean writeOnclick) {
		super(responseWriter);
		this.writeOnclick = writeOnclick;
	}

	@Override
	public void endElement(String name) throws IOException {

		if ("input".equalsIgnoreCase(name)) {
			// no-op Becuase the button needs to be a button element and endElement must be called in encodeEnd.
		}
		else {
			super.endElement(name);
		}
	}

	@Override
	public void startElement(String name, UIComponent uiComponent) throws IOException {

		if ("input".equalsIgnoreCase(name)) {
			// no-op Becuase the button needs to be a button element.
		}
		else {
			super.startElement(name, uiComponent);
		}
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if ("disabled".equalsIgnoreCase(name) || "type".equalsIgnoreCase(name) ||
				("onclick".equalsIgnoreCase(name) && !writeOnclick) || "class".equalsIgnoreCase(name) ||
				ButtonRenderer.ONBLUR.equals(name) || ButtonRenderer.ONFOCUS.equals(name) ||
				"value".equalsIgnoreCase(name) || Styleable.STYLE.equals(name)) {
			// no-op Because writing of these attributes need to be controlled directly by ButtonRender.
		}
		else {
			super.writeAttribute(name, value, property);
		}
	}

	@Override
	public void writeURIAttribute(String name, Object value, String property) throws IOException {

		if ("src".equalsIgnoreCase(name)) {

			// Save the value of the "src" attribute to write it to the response later.
			if (value != null) {
				src = value.toString();
			}
		}
		else {
			super.writeURIAttribute(name, value, property);
		}
	}

	public String getSrc() {
		return src;
	}
}
