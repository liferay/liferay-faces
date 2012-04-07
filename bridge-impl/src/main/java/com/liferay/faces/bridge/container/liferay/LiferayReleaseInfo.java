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

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class LiferayReleaseInfo {

	// Private Constants
	private static final String FQCN_RELEASE_INFO = "com.liferay.portal.kernel.util.ReleaseInfo";
	private static final String METHOD_GET_BUILD_NUMBER = "getBuildNumber";

	// Logger
	Logger logger = LoggerFactory.getLogger(LiferayReleaseInfo.class);

	// Private Data Members
	private Integer buildNumber = new Integer(0);

	public LiferayReleaseInfo() {

		try {
			Class<?> clazz = Class.forName(FQCN_RELEASE_INFO);
			Method method = clazz.getMethod(METHOD_GET_BUILD_NUMBER, (Class[]) null);
			buildNumber = (Integer) method.invoke(clazz, (Object[]) null);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public int getBuildNumber() {
		return buildNumber.intValue();
	}

}
