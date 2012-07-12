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
package com.liferay.faces.bridge.container.liferay;

import java.lang.reflect.Method;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This is a utility class that uses reflection to work with instances of Liferay's {@link StringBundler} class so as to
 * avoid a compile-time dependency on Liferay's API.
 *
 * @author  Neil Griffin
 */
public class StringBundlerUtil {

	// Private Constants
	private static final String FQCN_STRING_BUNDLER = "com.liferay.portal.kernel.util.StringBundler";
	private static final String METHOD_NAME_APPEND = "append";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(StringBundlerUtil.class);

	/**
	 * Appends the specified String value to the specified StringBundler.
	 */
	public static void append(Object stringBundler, String value) {

		try {
			Method method = stringBundler.getClass().getMethod(METHOD_NAME_APPEND, new Class[] { String.class });
			method.invoke(stringBundler, new Object[] { value });
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * Returns a new instance of StringBundler.
	 */
	public static Object create() {

		Object stringBundler = null;

		try {
			Class<?> clazz = Class.forName(FQCN_STRING_BUNDLER);
			stringBundler = clazz.newInstance();
		}
		catch (ClassNotFoundException e) {

			// Liferay 5.2.3 and older use StringBuilder, whereas newer versions use StringBundler.
			// See http://issues.liferay.com/browse/LPS-6072
			stringBundler = new StringBuilder();
		}
		catch (Exception e) {
			logger.error(e);
		}

		return stringBundler;
	}
}
