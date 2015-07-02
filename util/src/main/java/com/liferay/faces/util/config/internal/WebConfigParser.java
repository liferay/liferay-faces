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
package com.liferay.faces.util.config.internal;

import java.io.IOException;
import java.io.InputStream;

import com.liferay.faces.util.config.WebConfig;


/**
 * @author  Neil Griffin
 */
public interface WebConfigParser {

	/**
	 * Parses the specified InputStream and returns a new WebConfig that contains parsed data that has been appended to
	 * the specified WebConfig. Closing the specified InputStream is the responsibility of the caller.
	 */
	public WebConfig parse(InputStream inputStream, WebConfig webConfig) throws IOException;
}
