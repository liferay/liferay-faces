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
package com.liferay.faces.bridge;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.faces.bridge.component.UploadedFile;


/**
 * @author  Neil Griffin
 */
public class UploadedFileImpl implements Serializable, UploadedFile {

	private static final long serialVersionUID = 5511555773653537284L;

	private String absolutePath;
	private Map<String, Object> attributeMap;
	private String charSet;
	private String contentType;
	private Exception exception;
	private Map<String, List<String>> headersMap;
	private String name;
	private String id;
	private long size;

	private Status status;

	public UploadedFileImpl() {
		attributeMap = new HashMap<String, Object>();
		headersMap = new HashMap<String, List<String>>();
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
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
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Enumeration<String> getHeaderNames() {
		return Collections.enumeration(headersMap.keySet());
	}

	public List<String> getHeaders(String name) {
		return headersMap.get(name);
	}

	public void setHeaders(String name, List<String> headers) {
		headersMap.put(name, headers);
	}

	public Map<String, List<String>> getHeadersMap() {
		return headersMap;
	}

	public void setHeadersMap(Map<String, List<String>> headersMap) {
		this.headersMap = headersMap;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
