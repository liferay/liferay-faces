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
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
package com.liferay.portal.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal.
 *
 * @author  Vernon Singleton
 */
public class PortalUtilCompat extends PortalUtil {

	public static String getPortalURL(HttpServletRequest httpServletRequest) {

		String portalURL = null;
		HttpSession session = httpServletRequest.getSession();
		Boolean httpsInitial = (Boolean) session.getAttribute(WebKeys.HTTPS_INITIAL);

		if (PropsValuesCompat.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS &&
				!PropsValuesCompat.SESSION_ENABLE_PHISHING_PROTECTION && (httpsInitial != null) &&
				!httpsInitial.booleanValue()) {

			portalURL = PortalUtil.getPortalURL(httpServletRequest, false);
		}
		else {
			portalURL = PortalUtil.getPortalURL(httpServletRequest);
		}

		return portalURL;
	}
}
