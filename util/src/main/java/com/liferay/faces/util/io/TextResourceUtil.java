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
package com.liferay.faces.util.io;

import com.liferay.faces.util.io.internal.TextResourceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author  Neil Griffin
 */
public class TextResourceUtil {

	public static TextResource read(URL url) throws IOException {

		URLConnection urlConnection = url.openConnection();
		long lastModified = urlConnection.getLastModified();
		InputStream resourceAsStream = urlConnection.getInputStream();
		StringBuilder stringBuilder = new StringBuilder();

		if (resourceAsStream != null) {
			byte[] bytes = new byte[1024];
			int bytesRead;

			while ((bytesRead = resourceAsStream.read(bytes)) != -1) {
				String bytesAsString = new String(bytes, 0, bytesRead);
				stringBuilder.append(bytesAsString);
			}

			resourceAsStream.close();
		}

		return new TextResourceImpl(url, stringBuilder.toString(), lastModified);
	}
}
