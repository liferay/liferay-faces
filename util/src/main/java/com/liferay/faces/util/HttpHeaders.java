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
package com.liferay.faces.util;

/**
 * @author  Brian Wing Shun Chan
 */
public interface HttpHeaders {

	public static final String ACCEPT = "ACCEPT";

	public static final String ACCEPT_ENCODING = "Accept-Encoding";

	public static final String CACHE_CONTROL = "Cache-Control";

	public static final String CACHE_CONTROL_NO_CACHE_VALUE = "private, no-cache, no-store, must-revalidate";

	public static final String CONTENT_DISPOSITION = "Content-Disposition";

	public static final String IF_MODIFIED_SINCE = "If-Modified-Since";

	public static final String USER_AGENT = "User-Agent";
}
