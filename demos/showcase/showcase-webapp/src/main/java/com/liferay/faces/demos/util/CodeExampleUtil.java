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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liferay.faces.demos.dto.CodeExample;
import com.liferay.faces.util.io.TextResource;
import com.liferay.faces.util.io.TextResourceUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class CodeExampleUtil {

	// Private Constants
	private static final String JAVA = "java";
	private static final String JAVA_EXTENSION = ".java";
	private static final Pattern JAVA_MULTILINE_COMMENTS_PATTERN = Pattern.compile("/[*][*].*[*]/", Pattern.DOTALL);
	private static final Pattern TAB_OPEN_TAG_PATTERN = Pattern.compile("\t<");
	private static final Pattern TEMPLATE_ATTRIBUTE_PATTERN = Pattern.compile("\\s*template=\".*\"");
	private static final Pattern UI_DEFINE_OPEN_TAG_PATTERN = Pattern.compile("\n\t*<ui:define name=\".*\">");
	private static final Pattern UI_DEFINE_CLOSE_TAG_PATTERN = Pattern.compile("\n\t*</ui:define>");
	private static final String XML = "xml";

	public static CodeExample read(URL sourceFileURL, String sourceFileName) throws IOException {

		TextResource textResource = TextResourceUtil.read(sourceFileURL);
		String sourceCodeText = textResource.getText();

		if (sourceCodeText != null) {

			String fileExtension;

			if (sourceFileName.endsWith(JAVA_EXTENSION)) {
				Matcher matcher = JAVA_MULTILINE_COMMENTS_PATTERN.matcher(sourceCodeText);
				sourceCodeText = matcher.replaceAll(StringPool.BLANK);
				fileExtension = JAVA;
			}
			else {
				sourceCodeText = TEMPLATE_ATTRIBUTE_PATTERN.matcher(sourceCodeText).replaceAll(StringPool.BLANK);
				sourceCodeText = UI_DEFINE_OPEN_TAG_PATTERN.matcher(sourceCodeText).replaceAll(StringPool.BLANK);
				sourceCodeText = UI_DEFINE_CLOSE_TAG_PATTERN.matcher(sourceCodeText).replaceAll(StringPool.BLANK);
				sourceCodeText = TAB_OPEN_TAG_PATTERN.matcher(sourceCodeText).replaceAll(StringPool.LESS_THAN);
				fileExtension = XML;
			}

			sourceCodeText = sourceCodeText.trim();

			return new CodeExample(sourceFileName, fileExtension, sourceFileURL, textResource.getLastModified(),
					sourceCodeText);
		}
		else {
			throw new IOException("Unable to locate " + sourceFileURL);
		}
	}
}
