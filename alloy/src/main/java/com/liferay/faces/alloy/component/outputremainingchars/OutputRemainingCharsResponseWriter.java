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
package com.liferay.faces.alloy.component.outputremainingchars;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.DelegationResponseWriterBase;


/**
 * @author  Neil Griffin
 * @author  Vernon Singleton
 */
public class OutputRemainingCharsResponseWriter extends DelegationResponseWriterBase {

	// Private Data Members
	private String counterInnerSpanId;
	private UIComponent uiComponent;
	private String remainingChars;

	public OutputRemainingCharsResponseWriter(ResponseWriter responseWriter, UIComponent uiComponent, String counterInnerSpanId,
		String remainingChars) {

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
		if (StringPool.VALUE.equals(property) && (text != null)) {
			writeValue(text, uiComponent, true);
		}
		else {
			super.writeText(text, uiComponent, property);
		}
	}

	protected void writeValue(Object value, UIComponent uiComponent, boolean escape) throws IOException {

		// This is where all the code goes that takes the specified text value of something like "You have {0}
		// characters left" and does the substitution of the token.

		String valueAsString = value.toString();

		// Figure out the characters before the remaining span, if any.
		int firstCurly = valueAsString.indexOf(StringPool.OPEN_CURLY_BRACE);

		String pre = StringPool.BLANK;

		if (firstCurly > 0) {
			pre = valueAsString.substring(0, firstCurly);
		}

		// Figure out the characters after the remaining span, if any.
		int lastCurly = valueAsString.indexOf(StringPool.CLOSE_CURLY_BRACE);

		String post = StringPool.BLANK;

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
		super.startElement(StringPool.SPAN, uiComponent);
		super.writeAttribute(StringPool.ID, counterInnerSpanId, null);

		if (escape) {
			super.writeText(remainingChars.toCharArray(), 0, remainingChars.length());
		}
		else {
			super.write(remainingChars.toCharArray());
		}

		super.endElement(StringPool.SPAN);

		// Write out the characters after the remaining span.
		if (escape) {
			super.writeText(post.toCharArray(), 0, post.length());
		}
		else {
			super.write(post.toCharArray());
		}
	}

}
