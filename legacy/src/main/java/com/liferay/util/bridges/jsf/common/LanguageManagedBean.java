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

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.language.LanguageUtil;


/**
 * <p>This class serves as a bridge between the JSF Expression Language (EL) and Liferay's Language.properties resource
 * bundle.</p>
 *
 * @deprecated  This class has been deprecated as of Liferay Faces 2.2 and will not appear in future versions. Instead,
 *              see the wiki article titled <a
 *              href="http://www.liferay.com/community/wiki/-/wiki/Main/Internationalizing+JSF+Portlets">.
 *              Internationalizing JSF Portlets</a>. The recommended way of obtaining internationalized keys is to use
 *              the i18n EL keyword.
 * @author      Neil Griffin
 */
@Deprecated
public class LanguageManagedBean implements Map<String, String> {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(LanguageManagedBean.class);

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public boolean containsKey(Object key) {
		throw new UnsupportedOperationException();
	}

	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}

	public Set<Entry<String, String>> entrySet() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated  Use the i18n EL keyword instead. For more information see the wiki article titled <a
	 *              href="http://www.liferay.com/community/wiki/-/wiki/Main/Internationalizing+JSF+Portlets">
	 *              Internationalizing JSF Portlets</a>.
	 */
	public String get(Object key) {
		String value = null;

		if (key != null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();

			Locale locale = facesContext.getViewRoot().getLocale();

			if (locale == null) {
				locale = facesContext.getApplication().getDefaultLocale();
			}

			value = LanguageUtil.get(locale, key.toString());

			if (logger.isDebugEnabled()) {
				logger.debug("{locale=" + locale + ", key=" + key + ", value=" + value);
			}
		}

		return value;
	}

	public Set<String> keySet() {
		throw new UnsupportedOperationException();
	}

	public String put(String key, String value) {
		throw new UnsupportedOperationException();
	}

	public void putAll(Map<? extends String, ? extends String> map) {
		throw new UnsupportedOperationException();
	}

	public String remove(Object key) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		throw new UnsupportedOperationException();
	}

	public Collection<String> values() {
		throw new UnsupportedOperationException();
	}

	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

}
