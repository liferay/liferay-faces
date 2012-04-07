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
package com.liferay.faces.portal.el;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.liferay.faces.portal.context.LiferayFacesContext;


/**
 * @author  Neil Griffin
 */
public class I18N extends ResourceBundle {

	private Enumeration<String> EMPTY_KEYS = Collections.enumeration(new ArrayList<String>());

	private Locale cacheLocale;

	private Map<String, String> cacheHashMap;

	/**
	 * Gets the message associated with the specified messageId according to the locale in the current FacesContext.
	 * This method is primarily meant to be called via EL, providing the implementation supports passing parameters
	 * (like JBoss EL).
	 *
	 * @param   messageId  The message key.
	 * @param   arg1       The first argument, assuming that the messageId has a {0} token.
	 *
	 * @return  The internationalized message.
	 */
	public String replace(String messageId, String arg1) {
		LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
		Locale locale = liferayFacesContext.getLocale();

		return liferayFacesContext.getMessage(locale, messageId, arg1);
	}

	/**
	 * Gets the message associated with the specified messageId according to the locale in the current FacesContext.
	 * This method is primarily meant to be called via EL, providing the implementation supports passing parameters
	 * (like JBoss EL).
	 *
	 * @param   messageId  The message key.
	 * @param   arg1       The first argument, assuming that the messageId has a {0} token.
	 * @param   arg2       The second argument, assuming that the messageId has a {1} token.
	 *
	 * @return  The internationalized message.
	 */
	public String replace(String messageId, String arg1, String arg2) {
		LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
		Locale locale = liferayFacesContext.getLocale();

		return liferayFacesContext.getMessage(locale, messageId, arg1, arg2);
	}

	/**
	 * Gets the message associated with the specified messageId according to the locale in the current FacesContext.
	 * This method is primarily meant to be called via EL, providing the implementation supports passing parameters
	 * (like JBoss EL).
	 *
	 * @param   messageId  The message key.
	 * @param   arg1       The first argument, assuming that the messageId has a {0} token.
	 * @param   arg2       The second argument, assuming that the messageId has a {1} token.
	 * @param   arg3       The third argument, assuming that the messageId has a {2} token.
	 *
	 * @return  The internationalized message.
	 */
	public String replace(String messageId, String arg1, String arg2, String arg3) {
		LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
		Locale locale = liferayFacesContext.getLocale();

		return liferayFacesContext.getMessage(locale, messageId, arg1, arg2, arg3);
	}

	/**
	 * Gets the message associated with the specified messageId according to the locale in the current FacesContext.
	 * This method is primarily meant to be called via EL, providing the implementation supports passing parameters
	 * (like JBoss EL).
	 *
	 * @param   messageId  The message key.
	 * @param   arg1       The first argument, assuming that the messageId has a {0} token.
	 * @param   arg2       The second argument, assuming that the messageId has a {1} token.
	 * @param   arg3       The third argument, assuming that the messageId has a {2} token.
	 * @param   arg4       The fourth argument, assuming that the messageId has a {3} token.
	 *
	 * @return  The internationalized message.
	 */
	public String replace(String messageId, String arg1, String arg2, String arg3, String arg4) {
		LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
		Locale locale = liferayFacesContext.getLocale();

		return liferayFacesContext.getMessage(locale, messageId, arg1, arg2, arg4);
	}

	@Override
	protected Object handleGetObject(String key) {
		String message = null;

		if (key != null) {

			LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
			Locale locale = liferayFacesContext.getLocale();
			boolean localeChanged = false;

			if ((cacheLocale != null) || (locale != null)) {

				if (cacheLocale != null) {
					localeChanged = !cacheLocale.equals(locale);
				}
				else {
					localeChanged = !locale.equals(cacheLocale);
				}
			}

			cacheLocale = locale;

			if ((cacheHashMap == null) || localeChanged) {
				cacheHashMap = new HashMap<String, String>();
			}
			else {
				message = cacheHashMap.get(key);
			}

			if (message == null) {
				message = liferayFacesContext.getMessage(locale, key, (Object[]) null);

				if (message != null) {
					cacheHashMap.put(key, message);
				}
			}
		}

		return message;
	}

	/**
	 * This method is required by the ResourceBundle abstract class, but it will never be called in the normal running
	 * of a JSF webapp using the EL. Therefore, it just returns an empty Enumeration of Strings.
	 */
	@Override
	public Enumeration<String> getKeys() {
		return EMPTY_KEYS;
	}
}
