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
package com.liferay.faces.demos.dto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

import javax.faces.FacesWrapper;
import javax.servlet.http.Part;

import com.liferay.faces.util.HttpHeaders;
import com.liferay.faces.util.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
public class UploadedFilePart implements UploadedFile, FacesWrapper<Part> {

	// Private Data Members
	private Map<String, Object> attributeMap;
	private String id;
	private String message;
	private String name;
	private Status status;
	private Part wrappedPart;

	public UploadedFilePart(Part part, String id, Status status) {
		this(part, id, status, null);
	}

	public UploadedFilePart(Part part, String id, Status status, String message) {
		this.wrappedPart = part;
		this.id = id;
		this.status = status;
		this.message = message;

		String contentDisposition = getWrapped().getHeader(HttpHeaders.CONTENT_DISPOSITION);

		if (contentDisposition != null) {
			String[] nameValuePairs = contentDisposition.split(";");

			for (String nameValuePair : nameValuePairs) {
				nameValuePair = nameValuePair.trim();

				if (nameValuePair.startsWith("filename")) {
					int pos = nameValuePair.indexOf("=");

					if (pos > 0) {
						name = nameValuePair.substring(pos + 1).replace("\"", "");
					}
				}
			}
		}
	}

	@Override
	public void delete() throws IOException {
		getWrapped().delete();
	}

	@Override
	public void write(String fileName) throws IOException {
		getWrapped().write(fileName);
	}

	@Override
	public String getAbsolutePath() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributeMap;
	}

	@Override
	public byte[] getBytes() throws IOException {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		InputStream inputStream = getWrapped().getInputStream();
		byte[] byteBuffer = new byte[1024];
		int bytesRead = inputStream.read(byteBuffer);

		while (bytesRead != -1) {
			byteArrayOutputStream.write(byteBuffer);
			bytesRead = inputStream.read(byteBuffer);
		}

		byteArrayOutputStream.flush();

		return byteArrayOutputStream.toByteArray();
	}

	@Override
	public String getCharSet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getContentType() {
		return getWrapped().getContentType();
	}

	@Override
	public String getHeader(String name) {
		return getWrapped().getHeader(name);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return getWrapped().getHeaderNames();
	}

	@Override
	public Collection<String> getHeaders(String name) {
		return getWrapped().getHeaders(name);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return getWrapped().getInputStream();
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public long getSize() {
		return getWrapped().getSize();
	}

	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public Part getWrapped() {
		return wrappedPart;
	}
}
