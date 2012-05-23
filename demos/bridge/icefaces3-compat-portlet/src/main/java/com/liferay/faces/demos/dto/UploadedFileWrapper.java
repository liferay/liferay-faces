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
package com.liferay.faces.demos.dto;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesWrapper;

import org.icefaces.ace.component.fileentry.FileEntryResults.FileInfo;

import com.liferay.faces.bridge.component.UploadedFile;


/**
 * This class provides a convenient mechanism for converting an ICEfaces {@link FileInfo} object to an instance of the
 * {@link UploadedFile} interface provided by the Liferay Faces Bridge implementation.
 *
 * @author  Neil Griffin
 */
public class UploadedFileWrapper implements UploadedFile, FacesWrapper<FileInfo> {

	// Private Data Members
	private Map<String, Object> attributeMap;
	private String id;
	private FileInfo wrappedFileInfo;

	public UploadedFileWrapper(String id, FileInfo fileInfo) {
		this.id = id;
		this.wrappedFileInfo = fileInfo;
		this.attributeMap = new HashMap<String, Object>();
	}

	public String getAbsolutePath() {
		return wrappedFileInfo.getFile().getAbsolutePath();
	}

	public void setAbsolutePath(String absolutePath) {
		throw new UnsupportedOperationException();
	}

	public Object getAttribute(String name) {
		return attributeMap.get(name);
	}

	public void setAttribute(String name, Object value) {
		attributeMap.put(name, value);
	}

	public String getCharSet() {
		throw new UnsupportedOperationException();
	}

	public void setCharSet(String charSet) {
		throw new UnsupportedOperationException();
	}

	public String getContentType() {
		return wrappedFileInfo.getContentType();
	}

	public void setContentType(String contentType) {
		throw new UnsupportedOperationException();
	}

	public Exception getException() {
		throw new UnsupportedOperationException();
	}

	public void setException(Exception exception) {
		throw new UnsupportedOperationException();
	}

	public Enumeration<String> getHeaderNames() {
		throw new UnsupportedOperationException();
	}

	public List<String> getHeaders(String name) {
		throw new UnsupportedOperationException();
	}

	public void setHeaders(String name, List<String> headers) {
		throw new UnsupportedOperationException();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return wrappedFileInfo.getFileName();
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	public long getSize() {
		return wrappedFileInfo.getSize();
	}

	public void setSize(long size) {
		throw new UnsupportedOperationException();
	}

	public Status getStatus() {

		if (wrappedFileInfo.getStatus().isSuccess()) {
			return Status.SAVED;
		}
		else {
			return Status.INVALID;
		}
	}

	public void setStatus(Status status) {
		throw new UnsupportedOperationException();
	}

	public FileInfo getWrapped() {
		return wrappedFileInfo;
	}
}
