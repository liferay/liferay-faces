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
package com.liferay.portal.kernel.log;

/**
 * Since the Liferay CDI Portlet Bridge has a dependency on the Liferay Portal logging API, this class is necessary to
 * provide a compatibility layer with the Liferay Faces logging API.
 *
 * @author  Neil Griffin
 */
public class LogFactoryUtil {

	public static Log getLog(Class<?> c) {
		return new LogImpl(c);
	}

	public static Log getLog(String name) {
		return new LogImpl(name);
	}

}
