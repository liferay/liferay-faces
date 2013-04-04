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
package com.liferay.faces.bridge.preference;

import javax.portlet.faces.preference.Preference;

import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class MutablePreferenceMapEntry extends AbstractPropertyMapEntry<Preference> {

	Preference preference;

	public MutablePreferenceMapEntry(Preference preference, String name) {
		super(name);
		this.preference = preference;
	}

	public Preference getValue() {
		return preference;
	}

	public Preference setValue(Preference preference) {
		throw new UnsupportedOperationException();
	}
}
