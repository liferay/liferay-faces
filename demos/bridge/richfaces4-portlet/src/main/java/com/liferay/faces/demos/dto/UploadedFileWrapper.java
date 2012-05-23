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

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.FacesWrapper;

import com.liferay.faces.bridge.component.UploadedFile;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class UploadedFileWrapper implements UploadedFile, FacesWrapper<org.richfaces.model.UploadedFile> {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UploadedFileWrapper.class);

	// Private Data Members
	private String absolutePath;
	private Map<String, Object> attributeMap;
	private String charSet;
	private Exception exception;
	private String id;
	private Status status;
	private org.richfaces.model.UploadedFile wrappedUploadedFile;

	public UploadedFileWrapper(org.richfaces.model.UploadedFile uploadedFile) {
		this.wrappedUploadedFile = uploadedFile;
		this.attributeMap = new HashMap<String, Object>();
	}

	public String getAbsolutePath() {

		if (absolutePath == null) {

			try {
				File tempFolder = new File(System.getProperty("java.io.tmpdir"));
				File tempFile = File.createTempFile("upload", ".dat", tempFolder);
				absolutePath = tempFile.getAbsolutePath();
				wrappedUploadedFile.write(absolutePath);
				wrappedUploadedFile.delete();
			}
			catch (IOException e) {
				logger.error(e);
			}
		}

		return absolutePath;
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
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getContentType() {
		return wrappedUploadedFile.getContentType();
	}

	public void setContentType(String contentType) {
		throw new UnsupportedOperationException();
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
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
		return wrappedUploadedFile.getName();
	}

	public void setName(String name) {
		throw new UnsupportedOperationException();
	}

	public long getSize() {
		return wrappedUploadedFile.getSize();
	}

	public void setSize(long size) {
		throw new UnsupportedOperationException();
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public org.richfaces.model.UploadedFile getWrapped() {
		return wrappedUploadedFile;
	}

}
