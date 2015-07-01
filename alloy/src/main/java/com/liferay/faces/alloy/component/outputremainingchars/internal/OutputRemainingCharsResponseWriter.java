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
package com.liferay.faces.alloy.component.outputremainingchars.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


/**
 * @author  Neil Griffin
 * @author  Vernon Singleton
 */
public class OutputRemainingCharsResponseWriter extends DelegationResponseWriterBase {

	// Private Data Members
	private String counterInnerSpanId;
	private UIComponent uiComponent;
	private String remainingChars;

	public OutputRemainingCharsResponseWriter(ResponseWriter responseWriter, UIComponent uiComponent,
		String counterInnerSpanId, String remainingChars) {

		super(responseWriter);
		this.uiComponent = uiComponent;
		this.counterInnerSpanId = counterInnerSpanId;
		this.remainingChars = remainingChars;
	}

	// Mojarra will call this method to write the value when escape="false"
	@Override
	public void write(String text) throws IOException {
		writeValue(text, uiComponent, false);
	}

	// Mojarra will call this method to write the value when escape="true"
	@Override
	public void writeText(Object text, UIComponent uiComponent, String property) throws IOException {

		if ("value".equals(property) && (text != null)) {
			writeValue(text, uiComponent, true);
		}
		else {
			super.writeText(text, uiComponent, property);
		}
	}

	// This method will perform {0} token substitution for a component value like "You have {0} characters left".
	protected void writeValue(Object value, UIComponent uiComponent, boolean escape) throws IOException {

		String valueAsString = value.toString();

		// Figure out the characters before the remaining span, if any.
		int firstCurly = valueAsString.indexOf("{");

		String pre = "";

		if (firstCurly > 0) {
			pre = valueAsString.substring(0, firstCurly);
		}

		// Figure out the characters after the remaining span, if any.
		int lastCurly = valueAsString.indexOf("}");

		String post = "";

		if (lastCurly > 0) {
			post = valueAsString.substring(lastCurly + 1, valueAsString.length());
		}

		// Write out the characters before the remaining span.
		if (escape) {
			super.writeText(pre.toCharArray(), 0, pre.length());
		}
		else {
			super.write(pre.toCharArray());
		}

		// Write out the remaining span.
		super.startElement("span", uiComponent);
		super.writeAttribute("id", counterInnerSpanId, null);

		if (escape) {
			super.writeText(remainingChars.toCharArray(), 0, remainingChars.length());
		}
		else {
			super.write(remainingChars.toCharArray());
		}

		super.endElement("span");

		// Write out the characters after the remaining span.
		if (escape) {
			super.writeText(post.toCharArray(), 0, post.length());
		}
		else {
			super.write(post.toCharArray());
		}
	}

}
