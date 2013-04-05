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
package com.liferay.faces.bridge.context;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


/**
 * This class supports the render-redirect feature by queuing up a list of all write operations. Calling the {@link
 * #render()} method causes the queued operations to be invoked. Conversely, not calling the method will prevent the
 * queued operations from being invoked. This is necessary because in the case of a render-redirect, any markup written
 * to the response in the initially rendered view must be discarded.
 *
 * @author  Neil Griffin
 */
public class RenderRedirectWriterImpl extends RenderRedirectWriter {

	// Private Data Members
	private Writer wrappedWriter;
	private List<OutputOperation> outputOperationList;

	public RenderRedirectWriterImpl(Writer writer) {
		this.wrappedWriter = writer;
		this.outputOperationList = new ArrayList<OutputOperation>();
	}

	@Override
	public void close() throws IOException {
		outputOperationList.add(new CloseOperation());
	}

	@Override
	public void discard() {
		this.outputOperationList = new ArrayList<OutputOperation>();
	}

	@Override
	public void flush() throws IOException {
		outputOperationList.add(new FlushOperation());
	}

	@Override
	public void render() throws IOException {

		for (OutputOperation outputOperation : outputOperationList) {
			outputOperation.invoke();
		}
	}

	@Override
	public void write(char[] cbuf) throws IOException {

		if (cbuf != null) {
			outputOperationList.add(new CbufWriteOperation(cbuf));
		}
	}

	@Override
	public void write(int c) throws IOException {
		outputOperationList.add(new IntWriteOperation(c));
	}

	@Override
	public void write(String str) throws IOException {

		if (str != null) {
			outputOperationList.add(new StrWriteOperation(str));
		}
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {

		if (cbuf != null) {
			outputOperationList.add(new CBufOffLenOutputOperation(cbuf, off, len));
		}
	}

	@Override
	public void write(String str, int off, int len) throws IOException {

		if (str != null) {
			outputOperationList.add(new StrOffLenWriteOperation(str, off, len));
		}
	}

	public Writer getWrapped() {
		return wrappedWriter;
	}

	protected interface OutputOperation {
		void invoke() throws IOException;
	}

	protected class CBufOffLenOutputOperation implements OutputOperation {

		private char[] cbuf;
		private int off;
		private int len;

		public CBufOffLenOutputOperation(char[] cbuf, int off, int len) {
			this.cbuf = cbuf.clone();
			this.off = off;
			this.len = len;
		}

		public void invoke() throws IOException {
			wrappedWriter.write(cbuf, off, len);
		}
	}

	protected class CbufWriteOperation implements OutputOperation {

		private char[] cbuf;

		public CbufWriteOperation(char[] cbuf) {
			this.cbuf = cbuf.clone();
		}

		public void invoke() throws IOException {
			wrappedWriter.write(cbuf);
		}
	}

	protected class CloseOperation implements OutputOperation {
		public void invoke() throws IOException {
			wrappedWriter.close();
		}
	}

	protected class FlushOperation implements OutputOperation {
		public void invoke() throws IOException {
			wrappedWriter.flush();
		}
	}

	protected class IntWriteOperation implements OutputOperation {

		private int c;

		public IntWriteOperation(int c) {
			this.c = c;
		}

		public void invoke() throws IOException {
			wrappedWriter.write(c);
		}
	}

	protected class StrOffLenWriteOperation implements OutputOperation {

		private String str;
		private int off;
		private int len;

		public StrOffLenWriteOperation(String str, int off, int len) {
			this.str = str;
			this.off = off;
			this.len = len;
		}

		public void invoke() throws IOException {
			wrappedWriter.write(str, off, len);
		}
	}

	protected class StrWriteOperation implements OutputOperation {

		private String str;

		public StrWriteOperation(String str) {
			this.str = str;
		}

		public void invoke() throws IOException {
			wrappedWriter.write(str);
		}
	}
}
