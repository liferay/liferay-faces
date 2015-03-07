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


/**
 * @author  Neil Griffin
 */
public interface UploadedFile {

	/**
	 * @author  Neil Griffin
	 */
	public static enum Status {
		ERROR, FILE_SIZE_LIMIT_EXCEEDED, FILE_INVALID_NAME_PATTERN, FILE_SAVED, REQUEST_SIZE_LIMIT_EXCEEDED
	}

	public void delete() throws IOException;

	public void write(String fileName) throws IOException;

	public String getAbsolutePath();

	public Map<String, Object> getAttributes();

	public byte[] getBytes() throws IOException;

	public String getCharSet();

	public String getContentType();

	public String getHeader(String name);

	public Collection<String> getHeaderNames();

	public Collection<String> getHeaders(String name);

	public String getId();

	public InputStream getInputStream() throws IOException;

	public String getMessage();

	public String getName();

	public long getSize();

	public Status getStatus();
}
