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
package com.liferay.faces.util.context;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.PartialResponseWriter;


/**
 * @author  Neil Griffin
 */
public class PartialResponseWriterWrapper extends PartialResponseWriter {

	// Private Data Members
	private PartialResponseWriter wrappedPartialResponseWriter;

	public PartialResponseWriterWrapper(PartialResponseWriter partialResponseWriter) {
		super(partialResponseWriter.getWrapped());
		this.wrappedPartialResponseWriter = partialResponseWriter;
	}

	@Override
	public void delete(String targetId) throws IOException {
		wrappedPartialResponseWriter.delete(targetId);
	}

	@Override
	public void endDocument() throws IOException {
		wrappedPartialResponseWriter.endDocument();
	}

	@Override
	public void endError() throws IOException {
		wrappedPartialResponseWriter.endError();
	}

	@Override
	public void endEval() throws IOException {
		wrappedPartialResponseWriter.endEval();
	}

	@Override
	public void endExtension() throws IOException {
		wrappedPartialResponseWriter.endExtension();
	}

	@Override
	public void endInsert() throws IOException {
		wrappedPartialResponseWriter.endInsert();
	}

	@Override
	public void endUpdate() throws IOException {
		wrappedPartialResponseWriter.endUpdate();
	}

	@Override
	public void redirect(String url) throws IOException {
		wrappedPartialResponseWriter.redirect(url);
	}

	@Override
	public void startDocument() throws IOException {
		wrappedPartialResponseWriter.startDocument();
	}

	@Override
	public void startError(String errorName) throws IOException {
		wrappedPartialResponseWriter.startError(errorName);
	}

	@Override
	public void startEval() throws IOException {
		wrappedPartialResponseWriter.startEval();
	}

	@Override
	public void startExtension(Map<String, String> attributes) throws IOException {
		wrappedPartialResponseWriter.startExtension(attributes);
	}

	@Override
	public void startInsertAfter(String targetId) throws IOException {
		wrappedPartialResponseWriter.startInsertAfter(targetId);
	}

	@Override
	public void startInsertBefore(String targetId) throws IOException {
		wrappedPartialResponseWriter.startInsertBefore(targetId);
	}

	@Override
	public void startUpdate(String targetId) throws IOException {
		wrappedPartialResponseWriter.startUpdate(targetId);
	}

	@Override
	public void updateAttributes(String targetId, Map<String, String> attributes) throws IOException {
		wrappedPartialResponseWriter.updateAttributes(targetId, attributes);
	}

}
