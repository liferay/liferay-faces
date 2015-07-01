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
import java.io.StringWriter;

import javax.servlet.jsp.JspWriter;


/**
 * This class is an implementation of {@link JspWriter} that writes to an underlying String instead of JSP.
 *
 * @author  Neil Griffin
 */
public class JspWriterStringImpl extends JspWriter {

	// Public Constants
	public static final boolean DEFAULT_AUTO_FLUSH = true;
	public static final int DEFAULT_BUFFER_SIZE = 1024;

	// Private Data Members
	private StringWriter stringWriter;

	public JspWriterStringImpl() {
		super(DEFAULT_BUFFER_SIZE, DEFAULT_AUTO_FLUSH);
		this.stringWriter = new StringWriter(DEFAULT_BUFFER_SIZE);
	}

	@Override
	public void clear() throws IOException {
		stringWriter = new StringWriter(getBufferSize());
	}

	@Override
	public void clearBuffer() throws IOException {
		stringWriter = new StringWriter(getBufferSize());
	}

	@Override
	public void close() throws IOException {
		stringWriter.close();
	}

	@Override
	public void flush() throws IOException {
		stringWriter.flush();
	}

	@Override
	public void newLine() throws IOException {
		stringWriter.write("\n");
	}

	@Override
	public void print(boolean b) throws IOException {
		stringWriter.write(Boolean.toString(b));
	}

	@Override
	public void print(char c) throws IOException {
		stringWriter.write(Character.toString(c));
	}

	@Override
	public void print(int i) throws IOException {
		stringWriter.write(Integer.toString(i));
	}

	@Override
	public void print(long l) throws IOException {
		stringWriter.write(Long.toString(l));
	}

	@Override
	public void print(float f) throws IOException {
		stringWriter.write(Float.toString(f));
	}

	@Override
	public void print(double d) throws IOException {
		stringWriter.write(Double.toString(d));
	}

	@Override
	public void print(char[] s) throws IOException {
		stringWriter.write(s);
	}

	@Override
	public void print(String s) throws IOException {

		if (s != null) {
			stringWriter.write(s);
		}
	}

	@Override
	public void print(Object o) throws IOException {

		if (o != null) {
			stringWriter.write(o.toString());
		}
	}

	@Override
	public void println() throws IOException {
		stringWriter.write("\n");
	}

	@Override
	public void println(boolean b) throws IOException {
		print(b);
		println();
	}

	@Override
	public void println(char c) throws IOException {
		print(c);
		println();
	}

	@Override
	public void println(int i) throws IOException {
		print(i);
		println();
	}

	@Override
	public void println(long l) throws IOException {
		print(l);
		println();
	}

	@Override
	public void println(float f) throws IOException {
		print(f);
		println();
	}

	@Override
	public void println(double d) throws IOException {
		print(d);
		println();
	}

	@Override
	public void println(char[] s) throws IOException {
		print(s);
		println();
	}

	@Override
	public void println(String s) throws IOException {
		print(s);
		println();
	}

	@Override
	public void println(Object o) throws IOException {
		print(o);
		println();
	}

	@Override
	public String toString() {
		return stringWriter.toString();
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		stringWriter.write(cbuf, off, len);
	}

	@Override
	public int getRemaining() {
		return getBufferSize() - stringWriter.getBuffer().length();
	}

}
