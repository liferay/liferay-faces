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
package com.liferay.faces.bridge.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;


/**
 * @author  Neil Griffin
 */
public class LocaleIterator implements Iterator<Locale> {

	private Enumeration<Locale> localeEnumeration;

	public LocaleIterator(Enumeration<Locale> localeEnumeration) {
		this.localeEnumeration = localeEnumeration;
	}

	public boolean hasNext() {
		return localeEnumeration.hasMoreElements();
	}

	public Locale next() {
		return localeEnumeration.nextElement();
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}
