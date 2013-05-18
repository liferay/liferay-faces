package com.liferay.portal.kernel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.PortalClassInvoker;

public class LoginUtilCompat {
	public static Object invokeLogin(
			String LOGIN_UTIL_FQCN,
			String LOGIN_METHOD,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, 
			String handle,
			String password,
			boolean rememberMe,
			String authType
		)
		throws Exception {
		return PortalClassInvoker.invoke(
				LOGIN_UTIL_FQCN,
				LOGIN_METHOD,
				new Object[] {
					(HttpServletRequest) httpServletRequest,
					(HttpServletResponse) httpServletResponse,
					handle,
					password,
					(new BooleanWrapper(rememberMe)),
					authType
				},
				false
			);
	}
}
