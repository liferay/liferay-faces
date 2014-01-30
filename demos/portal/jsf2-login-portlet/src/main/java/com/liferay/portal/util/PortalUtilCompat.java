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

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;


/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal.
 *
 * @author  Vernon Singleton
 */
public class PortalUtilCompat extends PortalUtil {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PortalUtilCompat.class);

	public static String escapeRedirect(String url) {

		if (Validator.isNull(url) || !HttpUtil.hasDomain(url)) {
			return url;
		}

		try {
			String securityMode = PropsValuesCompat.REDIRECT_URL_SECURITY_MODE;

			String domain = StringUtil.split(HttpUtil.getDomain(url), StringPool.COLON)[0];

			if (securityMode.equals("domain")) {
				String[] allowedDomains = PropsValuesCompat.REDIRECT_URL_DOMAINS_ALLOWED;

				if ((allowedDomains.length > 0) && !ArrayUtil.contains(allowedDomains, domain)) {

					if (logger.isDebugEnabled()) {
						logger.debug("Redirect URL " + url + " is not allowed");
					}

					url = null;
				}
			}
			else if (securityMode.equals("ip")) {
				String[] allowedIps = PropsValuesCompat.REDIRECT_URL_IPS_ALLOWED;

				InetAddress inetAddress = InetAddress.getByName(domain);

				if ((allowedIps.length > 0) && !ArrayUtil.contains(allowedIps, inetAddress.getHostAddress())) {

					String serverIp = getComputerAddress();

					if (!serverIp.equals(inetAddress.getHostAddress()) ||
							!ArrayUtil.contains(allowedIps, "SERVER_IP")) {

						if (logger.isDebugEnabled()) {
							logger.debug("Redirect URL " + url + " is not allowed");
						}

						url = null;
					}
				}
			}
		}
		catch (UnknownHostException uhe) {

			if (logger.isDebugEnabled()) {
				logger.debug("Unable to determine IP for redirect URL " + url);
			}

			url = null;
		}

		return url;
	}

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
