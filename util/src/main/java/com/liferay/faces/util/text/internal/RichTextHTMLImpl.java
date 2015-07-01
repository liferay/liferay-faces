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
package com.liferay.faces.util.text.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author  Neil Griffin
 */
public class RichTextHTMLImpl extends RichTextBaseImpl {

	// Private Constants
	private static final Pattern TAG_PATTERN = Pattern.compile("<.+?>");
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

	public RichTextHTMLImpl(String value) {
		super(value);
	}

	@Override
	public int getPlainTextLength() {

		int count = 0;

		String value = getValue();

		if (value != null) {
			String cleanString = null;

			if (value instanceof String) {
				cleanString = (String) value;
			}
			else {
				cleanString = value.toString();
			}

			Matcher tagMatcher = TAG_PATTERN.matcher(cleanString);

			cleanString = tagMatcher.replaceAll("");
			cleanString = cleanString.replaceAll("&nbsp;", " ");
			cleanString = WHITESPACE_PATTERN.matcher(cleanString).replaceAll(" ");
			cleanString = cleanString.trim();
			count = cleanString.length();
		}

		return count;
	}

	@Override
	public Type getType() {
		return Type.HTML;
	}
}
