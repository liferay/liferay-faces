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
package com.liferay.portal.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;


/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal. It
 * contains constants that appear in Liferay Portal's implementation but are not exposed in Liferay Portal's API.
 *
 * @author  Neil Griffin
 */
public class PropsValuesCompat {

	public static final boolean COMPANY_SECURITY_AUTH_REQUIRES_HTTPS = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.COMPANY_SECURITY_AUTH_REQUIRES_HTTPS));
	public static final boolean PORTAL_JAAS_ENABLE = GetterUtil.getBoolean(PropsUtil.get(PropsKeys.PORTAL_JAAS_ENABLE));
	public static final boolean SESSION_ENABLE_PHISHING_PROTECTION = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.SESSION_ENABLE_PHISHING_PROTECTION));
}
