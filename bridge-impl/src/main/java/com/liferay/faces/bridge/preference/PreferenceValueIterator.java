/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.preference;

import java.util.Enumeration;
import java.util.Iterator;


/**
 * @author  Neil Griffin
 */
public class PreferenceValueIterator implements Iterator<String> {

	private Enumeration<String> preferenceValues;

	public PreferenceValueIterator(Enumeration<String> preferenceValues) {
		this.preferenceValues = preferenceValues;
	}

	public boolean hasNext() {
		return preferenceValues.hasMoreElements();
	}

	public String next() {
		return preferenceValues.nextElement();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}
