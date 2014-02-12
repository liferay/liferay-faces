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
package com.liferay.util.bridges.jsf.common;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.portlet.PortletPreferences;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @deprecated  This class has been deprecated as of Liferay Faces 2.2 and will not appear in future versions. Instead,
 *              use the portletPreferencesValues or mutablePortletPreferencesValues standard JSR 329 EL keywords.
 * @author      Neil Griffin
 */
@Deprecated
public class PortletPreferencesManagedBean {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(PortletPreferencesManagedBean.class);

	// Private Data Members
	private PortletPreferences portletPreferences;
	private Map<String, String> preferences;

	public PortletPreferencesManagedBean() {

		// Store the portlet preferences as a bean property because of ICE-1625.
		// When using normal JSF, this constructor will get called each time a
		// request is made. This is a little inefficient, but it's a coding
		// tradeoff to make things work with both normal JSF and ICEfaces 1.6.0.

		FacesContext facesContext = FacesContext.getCurrentInstance();

		portletPreferences = JSFPortletUtil.getPortletPreferences(facesContext);

		// Portlet preferences are backed by a map of string arrays. This makes
		// the JSP syntax a little funky, so in order to make the syntax easier,
		// copy each name and its first value into a new map where the name and
		// value are both strings.

		preferences = new HashMap<String, String>();

		Enumeration<String> enu = portletPreferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			String value = portletPreferences.getValue(name, null);

			preferences.put(name, value);

			if (logger.isDebugEnabled()) {
				logger.debug("{name=" + name + ", value=" + value + "}");
			}
		}
	}

	public String resetDefaultValues() {

		try {
			Enumeration<String> enu = portletPreferences.getNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				if (!portletPreferences.isReadOnly(name)) {
					portletPreferences.reset(name);

					String value = portletPreferences.getValue(name, null);

					preferences.put(name, value);

					portletPreferences.store();
				}
			}

			addInfoMessage("you-have-successfully-reset-your-preferences");

			return ActionOutcomes.SUCCESS;
		}
		catch (Exception e) {
			logger.error(e);

			addErrorMessage("an-error-occurred-while-resetting-your-preferences");

			return ActionOutcomes.FAILURE;
		}
	}

	public String submit() {

		try {
			Enumeration<String> enu = portletPreferences.getNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				if (!portletPreferences.isReadOnly(name)) {
					String value = preferences.get(name);

					portletPreferences.setValue(name, value);
				}
			}

			portletPreferences.store();

			addInfoMessage("you-have-successfully-updated-your-preferences");

			return ActionOutcomes.SUCCESS;
		}
		catch (Exception e) {
			logger.error(e);

			addErrorMessage("an-error-occurred-while-updating-your-preferences");

			return ActionOutcomes.FAILURE;
		}
	}

	protected void addErrorMessage(String key) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		FacesMessageUtil.error(facesContext, key);
	}

	protected void addInfoMessage(String key) {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		FacesMessageUtil.info(facesContext, key);
	}

	public Map<String, String> getPreferences() {
		return preferences;
	}

}
