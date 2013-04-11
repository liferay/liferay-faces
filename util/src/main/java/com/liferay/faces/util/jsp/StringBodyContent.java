/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.jsp;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;


/**
 * This class serves as an implementation of {@link BodyContent} that utilizes a {@link StringJspWriter} instead of a
 * {@link JspWriter} provided by the servlet container. This provides the ability to have JSP {@link Tag} classes write
 * their {@link BodyContent} to an underlying String rather than to a JSP, which is helpful when invoking {@link Tag}
 * classes directly for use with Facelets.
 *
 * @author  Neil Griffin
 */
public class StringBodyContent extends BodyContent {

	// Public Constants
	public static final boolean DEFAULT_AUTO_FLUSH = true;
	public static final int DEFAULT_BUFFER_SIZE = 1024;

	// Private Data Members
	private StringJspWriter facesStringWriter;

	public StringBodyContent() {
		this(DEFAULT_BUFFER_SIZE);
	}

	public StringBodyContent(int bufferSize) {
		this(new StringJspWriter(bufferSize, DEFAULT_AUTO_FLUSH));
	}

	public StringBodyContent(int bufferSize, boolean autoFlush) {
		this(new StringJspWriter(bufferSize, autoFlush));
	}

	protected StringBodyContent(StringJspWriter facesStringWriter) {
		super(facesStringWriter);
		this.facesStringWriter = facesStringWriter;
	}

	@Override
	public void clear() throws IOException {
		facesStringWriter.clear();
	}

	@Override
	public void clearBuffer() throws IOException {
		facesStringWriter.clearBuffer();
	}

	@Override
	public void close() throws IOException {
		facesStringWriter.close();
	}

	@Override
	public void newLine() throws IOException {
		facesStringWriter.newLine();
	}

	@Override
	public void print(boolean b) throws IOException {
		facesStringWriter.print(b);
	}

	@Override
	public void print(char c) throws IOException {
		facesStringWriter.print(c);
	}

	@Override
	public void print(int i) throws IOException {
		facesStringWriter.print(i);
	}

	@Override
	public void print(long l) throws IOException {
		facesStringWriter.print(l);
	}

	@Override
	public void print(float f) throws IOException {
		facesStringWriter.print(f);
	}

	@Override
	public void print(double d) throws IOException {
		facesStringWriter.print(d);
	}

	@Override
	public void print(char[] s) throws IOException {
		facesStringWriter.print(s);
	}

	@Override
	public void print(String s) throws IOException {
		facesStringWriter.print(s);
	}

	@Override
	public void print(Object o) throws IOException {
		facesStringWriter.print(o);
	}

	@Override
	public void println() throws IOException {
		facesStringWriter.println();
	}

	@Override
	public void println(boolean b) throws IOException {
		facesStringWriter.println(b);
	}

	@Override
	public void println(char c) throws IOException {
		facesStringWriter.println(c);
	}

	@Override
	public void println(int i) throws IOException {
		facesStringWriter.println(i);
	}

	@Override
	public void println(long l) throws IOException {
		facesStringWriter.println(l);
	}

	@Override
	public void println(float f) throws IOException {
		facesStringWriter.println(f);
	}

	@Override
	public void println(double d) throws IOException {
		facesStringWriter.println(d);
	}

	@Override
	public void println(char[] s) throws IOException {
		facesStringWriter.println(s);
	}

	@Override
	public void println(String s) throws IOException {
		facesStringWriter.println(s);
	}

	@Override
	public void println(Object o) throws IOException {
		facesStringWriter.println(o);
	}

	@Override
	public void write(char[] buf, int off, int len) throws IOException {
		facesStringWriter.write(buf, off, len);
	}

	@Override
	public void writeOut(Writer out) throws IOException {
		out.write(getString());
	}

	@Override
	public Reader getReader() {
		return new StringReader(getString());
	}

	@Override
	public int getRemaining() {
		return facesStringWriter.getRemaining();
	}

	@Override
	public String getString() {
		return facesStringWriter.toString();
	}

}
