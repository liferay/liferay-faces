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
package com.liferay.portal.kernel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.PortalClassInvoker;


/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal.
 * 
 * @author  Neil Griffin
 */
public class LoginUtilCompat {

	// Private Constants
	private static final String LOGIN_UTIL_FQCN = "com.liferay.portlet.login.util.LoginUtil";
	private static final String LOGIN_METHOD = "login";

	public static Object invokeLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
		String handle, String password, boolean rememberMe, String authType) throws Exception {

		Object[] args = new Object[] {
				httpServletRequest, httpServletResponse, handle, password, rememberMe, authType
			};

		MethodKey methodKey = new MethodKey(LOGIN_UTIL_FQCN, LOGIN_METHOD, HttpServletRequest.class,
				HttpServletResponse.class, String.class, String.class, boolean.class, String.class);

		return PortalClassInvoker.invoke(false, methodKey, args);
	}
}
