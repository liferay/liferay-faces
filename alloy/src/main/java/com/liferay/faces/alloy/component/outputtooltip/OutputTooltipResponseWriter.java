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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.DelegationResponseWriterBase;


/**
 * @author  Vernon Singleton
 */
public class OutputTooltipResponseWriter extends DelegationResponseWriterBase {

	public OutputTooltipResponseWriter(ResponseWriter responseWriter, UIComponent uiComponent) {
		super(responseWriter);
	}
	
	@Override
	public void endElement(String name) throws IOException {
		if (name != null) {
			if (StringPool.SPAN.equals(name)) {
				name = StringPool.DIV;
			}
		}
//		super.endElement(name);
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {
		System.err.println("startElement: name = " + name);
		if (name != null) {
			if (StringPool.SPAN.equals(name)) {
				System.err.println("startElement: rendered a div earlier ...");
				super.startElement(StringPool.DIV, component);
				super.writeAttribute(StringPool.ID, component.getClientId(), StringPool.ID);
			} else {
				super.startElement(name, component);
			}
		}
	}
	
	@Override
	public void writeAttribute(String name, Object value, String property)
			throws IOException {
		if (StringPool.ID.equals(name)) {
			// no-op
		} else {
			System.err.println("writeAttribute: " + name + " = " + value);
			super.writeAttribute(name, value, property);
		}
	}

	// Mojarra will call this method to write the value when escape="false"
	@Override
	public void write(String text) throws IOException {
		if (text == null) {
			System.err.println("writeText: text == null");
		} else {
			System.err.println("writeText: text.toString() = " + text.toString());
		}
		super.write(text);
	}

	// Mojarra will call this method to write the value when escape="true"
	@Override
	public void writeText(Object text, UIComponent uiComponent, String property) throws IOException {

		if (text == null) {
			System.err.println("writeText: text == null");
		} else {
			System.err.println("writeText: text.toString() = " + text.toString());
		}
		super.writeText(text, uiComponent, property);
	}

}
