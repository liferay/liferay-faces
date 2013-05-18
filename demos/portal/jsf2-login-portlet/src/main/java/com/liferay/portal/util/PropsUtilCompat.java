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
package com.liferay.portal.util;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.PropsUtil;


/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal. The
 * Liferay Portal 5.2.x getter methods in {@link com.liferay.portal.kernel.util.PropsUtil} (part of the portal-kernel
 * API) throw an Exception, which is not the case in newer versions.
 *
 * @author  Neil Griffin
 */
public class PropsUtilCompat {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PropsUtilCompat.class);

	public static String get(String key) {

		String value = null;

		try {
			value = PropsUtil.get(key);
		}
		catch (Exception e) {
			logger.error(e);
		}

		return value;
	}

	public static String[] getArray(String key) {

		String[] value = null;

		try {
			value = PropsUtil.getArray(key);
		}
		catch (Exception e) {
			logger.error(e);
		}

		return value;
	}

}
