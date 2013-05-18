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

import com.liferay.portal.kernel.util.GetterUtil;


/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal. It
 * contains constants that appear in Liferay Portal's implementation but are not exposed in Liferay Portal's API.
 *
 * @author  Neil Griffin
 */
public class PropsValuesCompat {

	public static final boolean COMPANY_SECURITY_AUTH_REQUIRES_HTTPS = GetterUtil.getBoolean(PropsUtilCompat.get(
				PropsKeysCompat.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS));
	public static final boolean PORTAL_JAAS_ENABLE = GetterUtil.getBoolean(PropsUtilCompat.get(
				PropsKeysCompat.PORTAL_JAAS_ENABLE));
	public static final String[] REDIRECT_URL_DOMAINS_ALLOWED = PropsUtilCompat.getArray(
			PropsKeysCompat.REDIRECT_URL_DOMAINS_ALLOWED);
	public static final String[] REDIRECT_URL_IPS_ALLOWED = PropsUtilCompat.getArray(
			PropsKeysCompat.REDIRECT_URL_IPS_ALLOWED);
	public static final String REDIRECT_URL_SECURITY_MODE = PropsUtilCompat.get(
			PropsKeysCompat.REDIRECT_URL_SECURITY_MODE);
	public static final boolean SESSION_ENABLE_PHISHING_PROTECTION = GetterUtil.getBoolean(PropsUtilCompat.get(
				PropsKeysCompat.SESSION_ENABLE_PHISHING_PROTECTION));

}
