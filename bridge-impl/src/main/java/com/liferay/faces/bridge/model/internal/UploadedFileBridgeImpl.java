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
package com.liferay.faces.bridge.model.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.liferay.faces.bridge.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
public class UploadedFileBridgeImpl implements Serializable, UploadedFile {

	// serialVersionUID
	private static final long serialVersionUID = 2492812271137403881L;

	// Private Data Members
	private com.liferay.faces.util.model.UploadedFile wrappedUploadedFile;

	public UploadedFileBridgeImpl(com.liferay.faces.util.model.UploadedFile uploadedFile) {
		this.wrappedUploadedFile = uploadedFile;
	}

	// Java 1.6+ @Override
	public void delete() throws IOException {
		wrappedUploadedFile.delete();
	}

	// Java 1.6+ @Override
	public void write(String fileName) throws IOException {
		wrappedUploadedFile.write(fileName);
	}

	// Java 1.6+ @Override
	public String getAbsolutePath() {
		return wrappedUploadedFile.getAbsolutePath();
	}

	// Java 1.6+ @Override
	public Map<String, Object> getAttributes() {
		return wrappedUploadedFile.getAttributes();
	}

	// Java 1.6+ @Override
	public byte[] getBytes() throws IOException {
		return wrappedUploadedFile.getBytes();
	}

	// Java 1.6+ @Override
	public String getCharSet() {
		return wrappedUploadedFile.getCharSet();
	}

	// Java 1.6+ @Override
	public String getContentType() {
		return wrappedUploadedFile.getContentType();
	}

	// Java 1.6+ @Override
	public String getHeader(String name) {
		return wrappedUploadedFile.getHeader(name);
	}

	// Java 1.6+ @Override
	public Collection<String> getHeaderNames() {
		return wrappedUploadedFile.getHeaderNames();
	}

	// Java 1.6+ @Override
	public Collection<String> getHeaders(String name) {
		return wrappedUploadedFile.getHeaders(name);
	}

	// Java 1.6+ @Override
	public String getId() {
		return wrappedUploadedFile.getId();
	}

	// Java 1.6+ @Override
	public InputStream getInputStream() throws IOException {
		return wrappedUploadedFile.getInputStream();
	}

	// Java 1.6+ @Override
	public String getMessage() {
		return wrappedUploadedFile.getMessage();
	}

	// Java 1.6+ @Override
	public String getName() {
		return wrappedUploadedFile.getName();
	}

	// Java 1.6+ @Override
	public long getSize() {
		return wrappedUploadedFile.getSize();
	}

	// Java 1.6+ @Override
	public Status getStatus() {

		com.liferay.faces.util.model.UploadedFile.Status wrappedStatus = wrappedUploadedFile.getStatus();

		return UploadedFile.Status.valueOf(wrappedStatus.name());
	}
}
