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
package com.liferay.faces.demos.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liferay.faces.demos.dto.CodeExample;


/**
 * @author  Neil Griffin
 */
public class CodeExampleUtil {

	// Private Constants
	private static final Pattern BLANK_LINE_PATTERN = Pattern.compile("\\t+$");
	private static final String JAVA = "java";
	private static final String JAVA_EXTENSION = ".java";
	private static final Pattern JAVA_MULTILINE_COMMENTS_PATTERN = Pattern.compile("/[*][*].*[*]/", Pattern.DOTALL);
	private static final String OUTPUTMODEL_MODELVALUE = ":outputModel:modelValue";
	private static final Pattern TEMPLATE_ATTRIBUTE_PATTERN = Pattern.compile("\\s*template=\".*\"");
	private static final String RENDER_EXAMPLE_FORM = "render=\":example:exampleForm:";
	private static final String RENDER_FORM_EXAMPLE = "render=\":exampleForm:example";
	private static final Pattern SHOWCASE_NAMESPACE_PATTERN = Pattern.compile("\\s*xmlns:showcase=\".*\"");
	private static final String RENDERED = "rendered=\"#{showcaseModelBean.selectedComponent.rendered}\"";
	private static final String XML = "xml";

	public static CodeExample read(URL sourceFileURL, String sourceFileName, boolean productionMode)
		throws IOException {

		URLConnection urlConnection = sourceFileURL.openConnection();
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

			String sourceCodeText = stringBuilder.toString();
			String fileExtension;

			if (sourceFileName.endsWith(JAVA_EXTENSION)) {

				fileExtension = JAVA;

				Matcher matcher = JAVA_MULTILINE_COMMENTS_PATTERN.matcher(sourceCodeText);
				sourceCodeText = matcher.replaceAll("");
			}
			else {

				// Reset the stringBuilder
				stringBuilder.setLength(0);
				fileExtension = XML;
				sourceCodeText = TEMPLATE_ATTRIBUTE_PATTERN.matcher(sourceCodeText).replaceFirst("");
				sourceCodeText = SHOWCASE_NAMESPACE_PATTERN.matcher(sourceCodeText).replaceAll("");

				StringReader stringReader = new StringReader(sourceCodeText);
				BufferedReader bufferedReader = new BufferedReader(stringReader);
				int trimTab = 0;
				String line;
				boolean ignoreNextLine = false;
				int defineCount = 0;

				while ((line = bufferedReader.readLine()) != null) {
					String trimmedLine = line.trim();

					if (ignoreNextLine) {
						ignoreNextLine = !trimmedLine.endsWith(">");
					}
					else {

						boolean defineColOpen = trimmedLine.startsWith("<ui:define name=\"col");

						if (!defineColOpen && trimmedLine.startsWith("<ui:define")) {
							defineCount++;
						}

						boolean defineClose = trimmedLine.startsWith("</ui:define");
						boolean defineColClose = ((defineCount == 0) && defineClose);

						if (defineClose && !defineColClose) {
							defineCount--;
						}

						if (trimmedLine.startsWith("<showcase") || defineColOpen) {
							trimTab++;
							ignoreNextLine = !trimmedLine.endsWith(">");

						}
						else if (trimmedLine.startsWith("</showcase") || defineColClose) {
							trimTab--;
						}
						else {

							for (int i = 0; i < trimTab; i++) {

								if (line.startsWith("\t")) {
									line = line.substring(1);
								}
							}

							int pos = line.indexOf(OUTPUTMODEL_MODELVALUE);

							if (pos > 0) {
								line = line.substring(0, pos) + ":modelValue" +
									line.substring(pos + OUTPUTMODEL_MODELVALUE.length());
							}

							pos = line.indexOf(RENDER_EXAMPLE_FORM);

							if (pos > 0) {
								line = line.substring(0, pos) + "render=\"" +
									line.substring(pos + RENDER_EXAMPLE_FORM.length());
							}

							pos = line.indexOf(RENDER_FORM_EXAMPLE);

							if (pos > 0) {
								line = line.substring(0, pos) + "render=\":exampleForm" +
									line.substring(pos + RENDER_FORM_EXAMPLE.length());
							}

							if (productionMode) {
								pos = line.indexOf(RENDERED);

								if (pos > 0) {
									line = line.substring(0, pos) + line.substring(pos + RENDERED.length());
								}
							}

							// Strip empty lines
							Matcher matcher = BLANK_LINE_PATTERN.matcher(line);

							if (!matcher.matches()) {
								stringBuilder.append(line);
								stringBuilder.append("\n");
							}
						}
					}
				}

				sourceCodeText = stringBuilder.toString();
			}

			sourceCodeText = sourceCodeText.trim();

			return new CodeExample(sourceFileName, fileExtension, sourceFileURL, urlConnection.getLastModified(),
					sourceCodeText);
		}
		else {
			throw new IOException("Unable to locate " + sourceFileURL);
		}
	}
}
