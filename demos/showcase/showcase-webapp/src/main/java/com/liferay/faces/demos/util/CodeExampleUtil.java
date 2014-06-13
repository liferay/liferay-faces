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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
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
	private static final String MYFORM_OUTPUTMODEL_MODELVALUE = "myForm:outputModel:modelValue";
	private static final Pattern TEMPLATE_ATTRIBUTE_PATTERN = Pattern.compile("\\s*template=\".*\"");
	private static final String XML = "xml";

	public static CodeExample read(URL sourceFileURL, String sourceFileName) throws IOException {

		TextResource textResource = TextResourceUtil.read(sourceFileURL);
		String sourceCodeText = textResource.getText();

		if (sourceCodeText != null) {

			String fileExtension;

			if (sourceFileName.endsWith(JAVA_EXTENSION)) {
				fileExtension = JAVA;

				Matcher matcher = JAVA_MULTILINE_COMMENTS_PATTERN.matcher(sourceCodeText);
				sourceCodeText = matcher.replaceAll(StringPool.BLANK);
			}
			else {
				fileExtension = XML;
				sourceCodeText = TEMPLATE_ATTRIBUTE_PATTERN.matcher(sourceCodeText).replaceAll(StringPool.BLANK);

				StringReader stringReader = new StringReader(sourceCodeText);
				StringBuffer buf = new StringBuffer();
				BufferedReader bufferedReader = new BufferedReader(stringReader);
				int trimTab = 0;
				String line;
				boolean ignoreNextLine = false;

				while ((line = bufferedReader.readLine()) != null) {
					String trimmedLine = line.trim();

					if (ignoreNextLine) {
						ignoreNextLine = !trimmedLine.endsWith(StringPool.GREATER_THAN);
					}
					else {

						if (trimmedLine.startsWith("<showcase") || trimmedLine.startsWith("<ui:define")) {
							trimTab++;
							ignoreNextLine = !trimmedLine.endsWith(StringPool.GREATER_THAN);
						}
						else if (trimmedLine.startsWith("</showcase") || trimmedLine.startsWith("</ui:define")) {
							trimTab--;
						}
						else {

							for (int i = 0; i < trimTab; i++) {

								if (line.startsWith(StringPool.TAB)) {
									line = line.substring(1);
								}
							}

							int pos = line.indexOf(MYFORM_OUTPUTMODEL_MODELVALUE);

							if (pos > 0) {
								line = line.substring(0, pos) + "myForm:modelValue" +
									line.substring(pos + MYFORM_OUTPUTMODEL_MODELVALUE.length());
							}

							buf.append(line);
							buf.append(StringPool.NEW_LINE);
						}
					}
				}

				sourceCodeText = buf.toString();
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
