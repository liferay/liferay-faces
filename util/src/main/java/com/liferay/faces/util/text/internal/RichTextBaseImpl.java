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

import com.liferay.faces.util.text.RichText;


/**
 * @author  Neil Griffin
 */
public abstract class RichTextBaseImpl implements RichText {

	// Private Data Members
	private String value;

	public RichTextBaseImpl(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public int getPlainTextLength() {

		int count = 0;

		if (value != null) {
			count = value.length();
		}

		return count;
	}

	public String getValue() {
		return value;
	}
}
