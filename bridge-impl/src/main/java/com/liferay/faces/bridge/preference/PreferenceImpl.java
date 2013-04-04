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

import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.faces.preference.Preference;


/**
 * @author  Neil Griffin
 */
public class PreferenceImpl implements Preference {

	private String name;
	private PortletPreferences portletPreferences;
	private PreferenceValuesList preferenceValuesList;

	public PreferenceImpl(PortletPreferences portletPreferences, String name) {
		this.portletPreferences = portletPreferences;
		this.name = name;
	}

	public void reset() throws ReadOnlyException {
		portletPreferences.reset(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return portletPreferences.getValue(name, null);
	}

	public void setValue(String value) throws ReadOnlyException {
		portletPreferences.setValue(name, value);
	}

	public List<String> getValues() {

		if (preferenceValuesList == null) {
			preferenceValuesList = new PreferenceValuesList(portletPreferences, name);
		}

		return preferenceValuesList;
	}

	public void setValues(String[] values) throws ReadOnlyException {
		portletPreferences.setValues(name, values);
	}

	public boolean isReadOnly() {
		return portletPreferences.isReadOnly(name);
	}
}
