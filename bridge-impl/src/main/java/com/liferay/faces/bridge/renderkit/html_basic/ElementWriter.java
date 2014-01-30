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
package com.liferay.faces.bridge.renderkit.html_basic;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.w3c.dom.Element;


/**
 * @author  Neil Griffin
 */
public class ElementWriter extends Writer {

	private Element element;

	public ElementWriter(Element element) {
		this.element = element;
	}

	public void append(String str) {

		if (str != null) {
			String textContent = element.getTextContent();

			if (textContent == null) {
				element.setTextContent(str);
			}
			else {
				element.setTextContent(textContent + str);
			}
		}
	}

	@Override
	public Writer append(CharSequence csq) throws IOException {
		StringWriter stringWriter = new StringWriter();
		Writer writer = stringWriter.append(csq);
		append(stringWriter.getBuffer().toString());

		return writer;
	}

	@Override
	public Writer append(char c) throws IOException {
		StringWriter stringWriter = new StringWriter();
		Writer writer = stringWriter.append(c);
		append(stringWriter.getBuffer().toString());

		return writer;
	}

	@Override
	public Writer append(CharSequence csq, int start, int end) throws IOException {
		StringWriter stringWriter = new StringWriter();
		Writer writer = stringWriter.append(csq, start, end);
		append(stringWriter.getBuffer().toString());

		return writer;
	}

	@Override
	public void close() throws IOException {
		// no-op
	}

	@Override
	public void flush() throws IOException {
		// no-op
	}

	@Override
	public void write(int c) throws IOException {
		StringWriter stringWriter = new StringWriter();
		stringWriter.write(c);
		append(stringWriter.getBuffer().toString());
	}

	@Override
	public void write(char[] cbuf) throws IOException {
		StringWriter stringWriter = new StringWriter();
		stringWriter.write(cbuf);
		append(stringWriter.getBuffer().toString());
	}

	@Override
	public void write(String str) throws IOException {
		append(str);
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		StringWriter stringWriter = new StringWriter();
		stringWriter.write(str, off, len);
		append(stringWriter.getBuffer().toString());
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		StringWriter stringWriter = new StringWriter();
		stringWriter.write(cbuf, off, len);
		append(stringWriter.getBuffer().toString());
	}

	public Element getElement() {
		return element;
	}

}
