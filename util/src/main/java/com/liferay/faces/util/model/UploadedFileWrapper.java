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
package com.liferay.faces.util.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import javax.faces.FacesWrapper;


/**
 * @author  Neil Griffin
 */
public abstract class UploadedFileWrapper implements UploadedFile, FacesWrapper<UploadedFile> {

	public void delete() throws IOException {
		getWrapped().delete();
	}

	public void write(String fileName) throws IOException {
		getWrapped().write(fileName);
	}

	public String getAbsolutePath() {
		return getWrapped().getAbsolutePath();
	}

	public Map<String, Object> getAttributes() {
		return getWrapped().getAttributes();
	}

	public byte[] getBytes() throws IOException {
		return getWrapped().getBytes();
	}

	public String getCharSet() {
		return getWrapped().getCharSet();
	}

	public String getContentType() {
		return getWrapped().getContentType();
	}

	public String getHeader(String name) {
		return getWrapped().getHeader(name);
	}

	public Collection<String> getHeaderNames() {
		return getWrapped().getHeaderNames();
	}

	public Collection<String> getHeaders(String name) {
		return getWrapped().getHeaders(name);
	}

	public String getId() {
		return getWrapped().getId();
	}

	public InputStream getInputStream() throws IOException {
		return getWrapped().getInputStream();
	}

	public String getMessage() {
		return getWrapped().getMessage();
	}

	public String getName() {
		return getWrapped().getName();
	}

	public long getSize() {
		return getWrapped().getSize();
	}

	public Status getStatus() {
		return getWrapped().getStatus();
	}

	public abstract UploadedFile getWrapped();
}
