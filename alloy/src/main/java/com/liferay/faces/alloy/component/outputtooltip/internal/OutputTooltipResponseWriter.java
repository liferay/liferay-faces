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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


/**
 * The purpose of this class is ignore the "span" element written by the JSF runtime's renderer.
 *
 * @author  Vernon Singleton
 */
public class OutputTooltipResponseWriter extends DelegationResponseWriterBase {

	public OutputTooltipResponseWriter(ResponseWriter responseWriter, String idValue) {
		super(responseWriter);
	}

	@Override
	public void endElement(String name) throws IOException {

		// Prevent the JSF runtime from closing the </span> tag since the
		// OutputToolTipRenderer.encodeMarkupEnd(FacesContext, UIComponent) method will write a closing </div> tag.
		if (!"span".equals(name)) {
			super.endElement(name);
		}
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		// Prevent the JSF runtime from opening the <span> tag since the
		// OutputToolTipRenderer.encodeMarkupBegin(FacesContext, UIComponent) method has already written an opening
		// <div> tag.
		if (!"span".equals(name)) {
			super.startElement(name, component);
		}
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		// Prevent the JSF runtime writing the "id", "style", and "class" attributes since the
		// OutputToolTipRenderer.encodeMarkupBegin(FacesContext, UIComponent) method has already written them.
		if (!"id".equals(name) && !Styleable.STYLE.equals(name) && !Styleable.STYLE_CLASS.equals(name)) {
			super.writeAttribute(name, value, property);
		}
	}
}
