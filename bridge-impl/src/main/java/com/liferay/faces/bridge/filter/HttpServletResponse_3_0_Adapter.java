/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.filter;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;


/**
 * This class isolates methods that were added to {@link HttpServletResponse} in the Servlet 3.0 API.
 *
 * @author  Neil Griffin
 */
public abstract class HttpServletResponse_3_0_Adapter implements HttpServletResponse {

	public String getHeader(String name) {
		throw new UnsupportedOperationException();
	}

	public Collection<String> getHeaderNames() {
		throw new UnsupportedOperationException();
	}

	public Collection<String> getHeaders(String name) {
		throw new UnsupportedOperationException();
	}

	public int getStatus() {
		throw new UnsupportedOperationException();
	}
}
