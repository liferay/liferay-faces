package com.liferay.portal.kernel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.util.MethodKey;
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
		
		Object[] args = new Object[] {
				(HttpServletRequest) httpServletRequest,
				(HttpServletResponse) httpServletResponse,
				handle,
				password,
				rememberMe,
				authType
			};
		
		MethodKey methodKey = new MethodKey(
				LOGIN_UTIL_FQCN,
				LOGIN_METHOD,
				HttpServletRequest.class, 
				HttpServletResponse.class, 
				String.class, 
				String.class,
				boolean.class,
				String.class
			);
		
		return PortalClassInvoker.invoke(false, methodKey, args);
		
	}
}
