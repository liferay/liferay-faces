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
package com.liferay.faces.util.jsp;

import java.io.IOException;
import java.io.Writer;

import javax.faces.FacesWrapper;


/**
 * @author  Kyle Stiemann
 */
public class StringJspWriterWrapper extends StringJspWriter implements FacesWrapper<StringJspWriter> {

	StringJspWriter wrappedStringJspWriter;

	public StringJspWriterWrapper(StringJspWriter wrappedStringJspWriter) {
		super(0, true);
		this.wrappedStringJspWriter = wrappedStringJspWriter;
	}

	@Override
	public Writer append(char c) throws IOException {
		return wrappedStringJspWriter.append(c);
	}

	@Override
	public Writer append(CharSequence csq) throws IOException {
		return wrappedStringJspWriter.append(csq);
	}

	@Override
	public Writer append(CharSequence csq, int start, int end) throws IOException {
		return wrappedStringJspWriter.append(csq, start, end);
	}

	@Override
	public void clear() throws IOException {
		wrappedStringJspWriter.clear();
	}

	@Override
	public void clearBuffer() throws IOException {
		wrappedStringJspWriter.clearBuffer();
	}

	@Override
	public void close() throws IOException {
		wrappedStringJspWriter.close();
	}

	@Override
	public void flush() throws IOException {
		wrappedStringJspWriter.flush();
	}

	@Override
	public void newLine() throws IOException {
		wrappedStringJspWriter.newLine();
	}

	@Override
	public void print(boolean bln) throws IOException {
		wrappedStringJspWriter.print(bln);
	}

	@Override
	public void print(char c) throws IOException {
		wrappedStringJspWriter.print(c);
	}

	@Override
	public void print(int i) throws IOException {
		wrappedStringJspWriter.print(i);
	}

	@Override
	public void print(long l) throws IOException {
		wrappedStringJspWriter.print(l);
	}

	@Override
	public void print(float f) throws IOException {
		wrappedStringJspWriter.print(f);
	}

	@Override
	public void print(double d) throws IOException {
		wrappedStringJspWriter.print(d);
	}

	@Override
	public void print(char[] chars) throws IOException {
		wrappedStringJspWriter.print(chars);
	}

	@Override
	public void print(String string) throws IOException {
		wrappedStringJspWriter.print(string);
	}

	@Override
	public void print(Object o) throws IOException {
		wrappedStringJspWriter.print(o);
	}

	@Override
	public void println() throws IOException {
		wrappedStringJspWriter.println();
	}

	@Override
	public void println(boolean bln) throws IOException {
		wrappedStringJspWriter.println(bln);
	}

	@Override
	public void println(char c) throws IOException {
		wrappedStringJspWriter.println(c);
	}

	@Override
	public void println(int i) throws IOException {
		wrappedStringJspWriter.println(i);
	}

	@Override
	public void println(long l) throws IOException {
		wrappedStringJspWriter.println(l);
	}

	@Override
	public void println(float f) throws IOException {
		wrappedStringJspWriter.println(f);
	}

	@Override
	public void println(double d) throws IOException {
		wrappedStringJspWriter.println(d);
	}

	@Override
	public void println(char[] chars) throws IOException {
		wrappedStringJspWriter.println(chars);
	}

	@Override
	public void println(String string) throws IOException {
		wrappedStringJspWriter.println(string);
	}

	@Override
	public void println(Object o) throws IOException {
		wrappedStringJspWriter.println(o);
	}

	@Override
	public String toString() {
		return wrappedStringJspWriter.toString();
	}

	@Override
	public void write(String str) throws IOException {
		wrappedStringJspWriter.write(str);
	}

	@Override
	public void write(char[] cbuf) throws IOException {
		wrappedStringJspWriter.write(cbuf);
	}

	@Override
	public void write(int c) throws IOException {
		wrappedStringJspWriter.write(c);
	}

	@Override
	public void write(String str, int off, int len) throws IOException {
		wrappedStringJspWriter.write(str, off, len);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		wrappedStringJspWriter.write(cbuf, off, len);
	}

	@Override
	public int getBufferSize() {
		return wrappedStringJspWriter.getBufferSize();
	}

	@Override
	public boolean isAutoFlush() {
		return wrappedStringJspWriter.isAutoFlush();
	}

	@Override
	public int getRemaining() {
		return wrappedStringJspWriter.getRemaining();
	}

	@Override
	public StringJspWriter getWrapped() {
		return wrappedStringJspWriter;
	}
}
