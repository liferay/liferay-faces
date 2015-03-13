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
import java.net.URL;


/**
 * @author  Neil Griffin
 */
public interface FacesConfigDescriptorParser {

	/**
	 * Parses the specified InputStream and returns a new FacesConfigDescriptor. Closing the specified InputStream is
	 * the responsibility of the caller.
	 */
	public FacesConfigDescriptor parse(InputStream inputStream, URL url) throws IOException;

	public FacesConfigDescriptor parse(InputStream inputStream, String path) throws IOException;
}
