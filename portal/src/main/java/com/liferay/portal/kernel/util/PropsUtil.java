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
package com.liferay.portal.kernel.util;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * <p>This class has been copied here from portal-service, except that "throws Exception" have been removed from method
 * signatures in order to mirror the signatures found in the Liferay 6.x codebase. This helps eliminate code differences
 * between different branches of Liferay Faces.</p>
 * 
 * <a href="PropsUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Neil Griffin
 */
public class PropsUtil {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

	// Private Constants
	private static final String _CLASS = "com.liferay.portal.util.PropsUtil";
	private static final String _METHOD_GET = "get";
	private static final String _METHOD_GET_ARRAY = "getArray";

	public static String get(String key) {

		try {
			Object returnObj = PortalClassInvoker.invoke(_CLASS, _METHOD_GET, key, false);

			if (returnObj != null) {
				return (String) returnObj;
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			logger.error(e);

			return null;
		}
	}

	public static String[] getArray(String key) {

		try {
			Object returnObj = PortalClassInvoker.invoke(_CLASS, _METHOD_GET_ARRAY, key, false);

			if (returnObj != null) {
				return (String[]) returnObj;
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			logger.error(e);

			return null;
		}
	}

}
