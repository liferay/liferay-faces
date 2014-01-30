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

/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal. It
 * contains constants that appear in Liferay Portal's implementation but are not exposed in Liferay Portal's API.
 *
 * @author  Neil Griffin
 */
public class PropsKeysCompat {

	public static final String COMPANY_SECURITY_AUTH_REQUIRES_HTTPS = "company.security.auth.requires.https";
	public static final String PORTAL_JAAS_ENABLE = "portal.jaas.enable";
	public static final String REDIRECT_URL_DOMAINS_ALLOWED = "redirect.url.domains.allowed";
	public static final String REDIRECT_URL_IPS_ALLOWED = "redirect.url.ips.allowed";
	public static final String REDIRECT_URL_SECURITY_MODE = "redirect.url.security.mode";
	public static final String SESSION_ENABLE_PHISHING_PROTECTION = "session.enable.phishing.protection";

}
