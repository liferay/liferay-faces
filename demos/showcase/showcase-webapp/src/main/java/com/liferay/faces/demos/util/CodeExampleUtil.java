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
package com.liferay.faces.demos.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.liferay.faces.demos.dto.CodeExample;


/**
 * @author  Neil Griffin
 */
public class CodeExampleUtil {

	public static CodeExample load(URL sourceFileURL, String sourceFileName) throws IOException {

		URLConnection sourceFileURLConnection = sourceFileURL.openConnection();
		long lastModified = sourceFileURLConnection.getLastModified();
		InputStream resourceAsStream = sourceFileURLConnection.getInputStream();

		if (resourceAsStream != null) {
			byte[] bytes = new byte[1024];
			int bytesRead;
			StringBuilder rawTextBuf = new StringBuilder();

			while ((bytesRead = resourceAsStream.read(bytes)) != -1) {
				String bytesAsString = new String(bytes, 0, bytesRead);
				rawTextBuf.append(bytesAsString);
			}

			resourceAsStream.close();

			String rawText = rawTextBuf.toString().trim();
			rawText = rawText.replaceAll("\n+$", "");
			rawText = rawText.replaceAll("\n", "\\\\n");
			rawText = rawText.replaceAll("[\"]", "\\\\\"");
			System.err.println("!@#$ -------------------------------------------------------------------");
			System.err.println(rawText);
			resourceAsStream.close();

			return new CodeExample(sourceFileName, sourceFileURL, lastModified, rawText);
		}
		else {
			throw new IOException("Unable to locate " + sourceFileURL);
		}
	}
}
