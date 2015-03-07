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
package com.liferay.faces.util.el;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.liferay.faces.util.context.ExtFacesContext;


/**
 * @author  Neil Griffin
 */
public class I18N extends I18NCompat {

	// Private Constants
	private static final Enumeration<String> EMPTY_KEYS = Collections.enumeration(new ArrayList<String>());

	// Private Data Members
	private Map<String, String> cache;

	public I18N() {
		super();
		this.cache = new ConcurrentHashMap<String, String>();
	}

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
		ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
		Locale locale = extFacesContext.getLocale();

		return extFacesContext.getMessage(locale, messageId, arg1);
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
		ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
		Locale locale = extFacesContext.getLocale();

		return extFacesContext.getMessage(locale, messageId, arg1, arg2);
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
		ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
		Locale locale = extFacesContext.getLocale();

		return extFacesContext.getMessage(locale, messageId, arg1, arg2, arg3);
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
		ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
		Locale locale = extFacesContext.getLocale();

		return extFacesContext.getMessage(locale, messageId, arg1, arg2, arg3, arg4);
	}

	@Override
	protected Object handleGetObject(String key) {

		String message = null;

		if (key != null) {

			ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
			Locale locale = extFacesContext.getLocale();

			if (cacheEnabled) {

				String messageKey = key;

				if (locale != null) {
					messageKey = locale.toString().concat(key);
				}

				message = cache.get(messageKey);

				if (message == null) {
					message = extFacesContext.getMessage(locale, key);

					if (message != null) {
						cache.put(messageKey, message);
					}
				}
			}
			else {
				message = extFacesContext.getMessage(locale, key);
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
