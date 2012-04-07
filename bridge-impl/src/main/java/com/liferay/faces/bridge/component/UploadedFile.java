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
package com.liferay.faces.bridge.component;

import java.util.Enumeration;
import java.util.List;


/**
 * @author  Neil Griffin
 */
public interface UploadedFile {

	/**
	 * @author  Neil Griffin
	 */
	public static enum Status {

		INVALID, INVALID_CONTENT_TYPE, INVALID_NAME_PATTERN, SAVED, SIZE_LIMIT_EXCEEDED
	}

	public String getAbsolutePath();

	public void setAbsolutePath(String absolutePath);

	public Object getAttribute(String name);

	public void setAttribute(String name, Object value);

	public String getCharSet();

	public void setCharSet(String charSet);

	public String getContentType();

	public void setContentType(String contentType);

	public Exception getException();

	public void setException(Exception exception);

	public Enumeration<String> getHeaderNames();

	public List<String> getHeaders(String name);

	public void setHeaders(String name, List<String> headers);

	public String getId();

	public void setId(String id);

	public String getName();

	public void setName(String name);

	public long getSize();

	public void setSize(long size);

	public Status getStatus();

	public void setStatus(Status status);
}
