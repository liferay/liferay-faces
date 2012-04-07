/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
/**
 * Copyright (c) 2011 American Automobile Association. All rights reserved.
 */
package com.liferay.faces.alloy.renderkit;

import java.io.IOException;

import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;


/**
 * @author  Neil Griffin
 */
public class SanitizingResponseWriter extends ResponseWriterWrapper {

	private ResponseWriter wrappedResponseWriter;

	public SanitizingResponseWriter(ResponseWriter wrappableResponseWriter) {
		this.wrappedResponseWriter = wrappableResponseWriter;
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {

		if (cbuf != null) {
			String valueAsString = new String(cbuf, off, len);
			valueAsString = sanitize(valueAsString);
			super.write(valueAsString.toCharArray(), 0, valueAsString.length());
		}
		else {
			super.write(cbuf, off, len);
		}
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (value != null) {
			super.writeAttribute(name, sanitize(value.toString()), property);
		}
		else {
			super.writeAttribute(name, value, property);
		}

	}

	protected String sanitize(String value) {

		String sanitizedValue = value;

//      System.err.println("!@#$ sanitize before=[" + sanitizedValue + "]");
		// Remove newline characters.
		if (sanitizedValue.indexOf("\n") > 0) {
			sanitizedValue = sanitizedValue.replaceAll("\n", "");
		}

		// Escape single quotes.
		if (sanitizedValue.indexOf("'") >= 0) {
			sanitizedValue = sanitizedValue.replaceAll("'", "\\\\'");
		}

//      System.err.println("!@#$ sanitize after=[" + sanitizedValue + "]");
		return sanitizedValue;
	}

	@Override
	public ResponseWriter getWrapped() {
		return wrappedResponseWriter;
	}

}
