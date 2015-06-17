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
package com.liferay.faces.util.jsp.internal;

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
public class BodyContentStringImpl extends BodyContent {

	// Private Data Members
	private JspWriter stringJspWriter;

	public BodyContentStringImpl(JspWriter stringJspWriter) {
		super(stringJspWriter);
		this.stringJspWriter = stringJspWriter;
	}

	@Override
	public void clear() throws IOException {
		stringJspWriter.clear();
	}

	@Override
	public void clearBuffer() throws IOException {
		stringJspWriter.clearBuffer();
	}

	@Override
	public void close() throws IOException {
		stringJspWriter.close();
	}

	@Override
	public void newLine() throws IOException {
		stringJspWriter.newLine();
	}

	@Override
	public void print(boolean b) throws IOException {
		stringJspWriter.print(b);
	}

	@Override
	public void print(char c) throws IOException {
		stringJspWriter.print(c);
	}

	@Override
	public void print(int i) throws IOException {
		stringJspWriter.print(i);
	}

	@Override
	public void print(long l) throws IOException {
		stringJspWriter.print(l);
	}

	@Override
	public void print(float f) throws IOException {
		stringJspWriter.print(f);
	}

	@Override
	public void print(double d) throws IOException {
		stringJspWriter.print(d);
	}

	@Override
	public void print(char[] s) throws IOException {
		stringJspWriter.print(s);
	}

	@Override
	public void print(String s) throws IOException {
		stringJspWriter.print(s);
	}

	@Override
	public void print(Object o) throws IOException {
		stringJspWriter.print(o);
	}

	@Override
	public void println() throws IOException {
		stringJspWriter.println();
	}

	@Override
	public void println(boolean b) throws IOException {
		stringJspWriter.println(b);
	}

	@Override
	public void println(char c) throws IOException {
		stringJspWriter.println(c);
	}

	@Override
	public void println(int i) throws IOException {
		stringJspWriter.println(i);
	}

	@Override
	public void println(long l) throws IOException {
		stringJspWriter.println(l);
	}

	@Override
	public void println(float f) throws IOException {
		stringJspWriter.println(f);
	}

	@Override
	public void println(double d) throws IOException {
		stringJspWriter.println(d);
	}

	@Override
	public void println(char[] s) throws IOException {
		stringJspWriter.println(s);
	}

	@Override
	public void println(String s) throws IOException {
		stringJspWriter.println(s);
	}

	@Override
	public void println(Object o) throws IOException {
		stringJspWriter.println(o);
	}

	@Override
	public void write(char[] buf, int off, int len) throws IOException {
		stringJspWriter.write(buf, off, len);
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
		return stringJspWriter.getRemaining();
	}

	@Override
	public String getString() {
		return stringJspWriter.toString();
	}

}
