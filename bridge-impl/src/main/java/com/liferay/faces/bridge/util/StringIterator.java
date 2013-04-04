/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.util;

import java.util.Enumeration;
import java.util.Iterator;


/**
 * @author  Neil Griffin
 */
public class StringIterator implements Iterator<String> {

	private Enumeration<String> stringEnumeration;

	public StringIterator(Enumeration<String> stringEnumeration) {
		this.stringEnumeration = stringEnumeration;
	}

	public boolean hasNext() {
		return stringEnumeration.hasMoreElements();
	}

	public String next() {
		return stringEnumeration.nextElement();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}
