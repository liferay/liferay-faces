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
package com.liferay.faces.bridge.component.inputfile.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;

import javax.faces.FacesWrapper;
import javax.servlet.http.Part;

import com.liferay.faces.util.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
public class HtmlInputFilePartImpl implements Part, FacesWrapper<UploadedFile>, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 8623187629549586031L;

	// Private Data Members
	private String clientId;
	private UploadedFile wrappedUploadedFile;

	public HtmlInputFilePartImpl(UploadedFile uploadedFile, String clientId) {
		this.wrappedUploadedFile = uploadedFile;
		this.clientId = clientId;
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
	public InputStream getInputStream() throws IOException {
		return getWrapped().getInputStream();
	}

	@Override
	public String getName() {
		return clientId;
	}

	@Override
	public long getSize() {
		return getWrapped().getSize();
	}

	@Override
	public UploadedFile getWrapped() {
		return wrappedUploadedFile;
	}
}
