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
package com.liferay.faces.util.model.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.liferay.faces.util.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
public class UploadedFileImpl implements Serializable, UploadedFile {

	// serialVersionUID
	private static final long serialVersionUID = 3983489321274295576L;

	// Private Data Members
	private String absolutePath;
	private Map<String, Object> attributeMap;
	private String charSet;
	private String contentType;
	private String id;
	private Map<String, List<String>> headersMap;
	private String message;
	private String name;
	private long size;
	private Status status;

	public UploadedFileImpl(String absolutePath, Map<String, Object> attributeMap, String charSet, String contentType,
		Map<String, List<String>> headersMap, String id, String message, String name, long size, Status status) {
		this.absolutePath = absolutePath;
		this.attributeMap = attributeMap;
		this.charSet = charSet;
		this.contentType = contentType;
		this.id = id;
		this.headersMap = headersMap;
		this.message = message;
		this.name = name;
		this.size = size;
		this.status = status;
	}

	public void delete() throws IOException {

		if (absolutePath != null) {
			File file = new File(absolutePath);
			file.delete();
		}
	}

	@Override
	public String toString() {
		return this.absolutePath;
	}

	public void write(String fileName) throws IOException {
		OutputStream outputStream = new FileOutputStream(fileName);
		outputStream.write(getBytes());
		outputStream.close();
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public Map<String, Object> getAttributes() {
		return attributeMap;
	}

	public byte[] getBytes() throws IOException {

		byte[] bytes = null;

		try {

			if (absolutePath != null) {
				File file = new File(absolutePath);

				if (file.exists()) {
					RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
					bytes = new byte[(int) randomAccessFile.length()];
					randomAccessFile.readFully(bytes);
					randomAccessFile.close();
				}
			}
		}
		catch (Exception e) {
			throw new IOException(e.getMessage());
		}

		return bytes;
	}

	public String getCharSet() {
		return charSet;
	}

	public String getContentType() {
		return contentType;
	}

	public String getHeader(String name) {

		String header = null;
		List<String> headers = headersMap.get(name);

		if ((headers != null) && (headers.size() > 0)) {
			header = headers.get(0);
		}

		return header;
	}

	public Collection<String> getHeaderNames() {
		return headersMap.keySet();
	}

	public Collection<String> getHeaders(String name) {
		return headersMap.get(name);
	}

	public String getId() {
		return id;
	}

	public InputStream getInputStream() throws IOException {

		String absolutePath = getAbsolutePath();

		if (absolutePath == null) {
			return null;
		}
		else {
			return new FileInputStream(absolutePath);
		}
	}

	public String getMessage() {
		return message;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public Status getStatus() {
		return status;
	}
}
