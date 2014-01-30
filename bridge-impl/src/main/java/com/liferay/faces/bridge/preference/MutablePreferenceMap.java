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
package com.liferay.faces.bridge.preference;

import java.util.Enumeration;

import javax.portlet.PortletPreferences;
import javax.portlet.faces.preference.Preference;

import com.liferay.faces.util.map.AbstractPropertyMap;
import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class MutablePreferenceMap extends AbstractPropertyMap<Preference> {

	private PortletPreferences portletPreferences;

	public MutablePreferenceMap(PortletPreferences portletPreferences) {
		this.portletPreferences = portletPreferences;
	}

	@Override
	protected AbstractPropertyMapEntry<Preference> createPropertyMapEntry(String name) {
		Preference preference = new PreferenceImpl(portletPreferences, name);

		return new MutablePreferenceMapEntry(preference, name);
	}

	@Override
	protected void removeProperty(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Preference getProperty(String name) {
		Preference preference = new PreferenceImpl(portletPreferences, name);

		return preference;
	}

	@Override
	protected void setProperty(String name, Preference value) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Enumeration<String> getPropertyNames() {
		return portletPreferences.getNames();
	}
}
