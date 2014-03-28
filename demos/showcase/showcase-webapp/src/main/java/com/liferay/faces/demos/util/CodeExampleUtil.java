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
import java.net.URL;

import com.liferay.faces.demos.dto.CodeExample;
import com.liferay.faces.util.io.TextResource;
import com.liferay.faces.util.io.TextResourceUtil;


/**
 * @author  Neil Griffin
 */
public class CodeExampleUtil {

	public static CodeExample read(URL sourceFileURL, String sourceFileName) throws IOException {

		TextResource textResource = TextResourceUtil.read(sourceFileURL);
		String sourceCodeText = textResource.getText();

		if (sourceCodeText != null) {
			sourceCodeText = sourceCodeText.trim();
			sourceCodeText = sourceCodeText.replaceAll("\n+$", "");
			sourceCodeText = sourceCodeText.replaceAll("\n", "\\\\n");
			sourceCodeText = sourceCodeText.replaceAll("[\"]", "\\\\\"");
			System.err.println("!@#$ -------------------------------------------------------------------");
			System.err.println(sourceCodeText);

			return new CodeExample(sourceFileName, sourceFileURL, textResource.getLastModified(), sourceCodeText);
		}
		else {
			throw new IOException("Unable to locate " + sourceFileURL);
		}
	}
}
