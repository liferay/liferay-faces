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
package com.liferay.faces.util.client;

import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.lang.StringPool;

import com.liferay.portal.kernel.servlet.HttpHeaders;


/**
 * See http://www.zytrax.com/tech/web/browser_ids.htm for examples.
 *
 * @author  Eduardo Lundgren
 * @author  Nate Cavanaugh
 */
public class BrowserSnifferImpl implements BrowserSniffer {

	// Private Constants
	private static final String ACCEPT_ENCODING = "Accept-Encoding";
	private static final char BACK_SLASH = '\\';
	private static final String BROWSER_SNIFFER_REVISION = "BROWSER_SNIFFER_REVISION";
	private static final String BROWSER_SNIFFER_VERSION = "BROWSER_SNIFFER_VERSION";
	private static final char COLON = ':';
	private static final String[] FIREFOX_ALIASES = {
			"firefox", "minefield", "granparadiso", "bonecho", "firebird", "phoenix", "camino"
		};
	private static final char FORWARD_SLASH = '/';
	private static final char SPACE = ' ';
	private static String[] REVISION_LEADINGS = { "rv", "it", "ra", "ie" };
	private static char[] REVISION_SEPARATORS = { BACK_SLASH, COLON, FORWARD_SLASH, SPACE };
	private static String[] VERSION_LEADINGS = { "version", "firefox", "minefield", "chrome" };
	private static char[] VERSION_SEPARATORS = { BACK_SLASH, FORWARD_SLASH };
	private static final String[] WEBKIT_ALIASES = { "khtml", "applewebkit" };
	private static final String[] WINDOWS_ALIASES = { "windows", "win32", "16bit" };

	protected static String parseVersion(String userAgent, String[] leadings, char[] separators) {

leading:
		for (String leading : leadings) {
			int index = 0;

version:
			while (true) {
				index = userAgent.indexOf(leading, index);

				if ((index < 0) || (((index += leading.length()) + 2) > userAgent.length())) {

					continue leading;
				}

				char c1 = userAgent.charAt(index);
				char c2 = userAgent.charAt(++index);

				if (((c2 >= '0') && (c2 <= '9')) || (c2 == '.')) {

					for (char separator : separators) {

						if (c1 == separator) {
							break version;
						}
					}
				}
			}

			// Major

			int majorStart = index;
			int majorEnd = index + 1;

			for (int i = majorStart; i < userAgent.length(); i++) {
				char c = userAgent.charAt(i);

				if ((c < '0') || (c > '9')) {
					majorEnd = i;

					break;
				}
			}

			String major = userAgent.substring(majorStart, majorEnd);

			if (userAgent.charAt(majorEnd) != '.') {
				return major;
			}

			// Minor

			int minorStart = majorEnd + 1;
			int minorEnd = userAgent.length();

			for (int i = minorStart; i < userAgent.length(); i++) {
				char c = userAgent.charAt(i);

				if ((c < '0') || (c > '9')) {
					minorEnd = i;

					break;
				}
			}

			String minor = userAgent.substring(minorStart, minorEnd);

			return major.concat(StringPool.PERIOD).concat(minor);
		}

		return StringPool.BLANK;
	}

	// Java 1.6+ @Override
	public boolean acceptsGzip(HttpServletRequest request) {
		String acceptEncoding = request.getHeader(ACCEPT_ENCODING);

		if ((acceptEncoding != null) && acceptEncoding.contains("gzip")) {
			return true;
		}
		else {
			return false;
		}
	}

	// Java 1.6+ @Override
	public boolean isIeOnWin32(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (isIe(userAgent) && !(userAgent.contains("wow64") || userAgent.contains("win64"))) {

			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isIeOnWin64(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (isIe(userAgent) && (userAgent.contains("wow64") || userAgent.contains("win64"))) {

			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isMozilla(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("mozilla") && !(userAgent.contains("compatible") || userAgent.contains("webkit"))) {

			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isOpera(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("opera")) {
			return true;
		}

		return false;
	}

	protected String getAccept(HttpServletRequest request) {
		String accept = StringPool.BLANK;

		if (request == null) {
			return accept;
		}

		accept = String.valueOf(request.getAttribute(HttpHeaders.ACCEPT));

		if (Validator.isNotNull(accept)) {
			return accept;
		}

		accept = request.getHeader(HttpHeaders.ACCEPT);

		if (accept != null) {
			accept = accept.toLowerCase();
		}
		else {
			accept = StringPool.BLANK;
		}

		request.setAttribute(HttpHeaders.ACCEPT, accept);

		return accept;
	}

	// Java 1.6+ @Override
	public String getBrowserId(HttpServletRequest request) {

		if (isIe(request)) {
			return BROWSER_ID_IE;
		}
		else if (isFirefox(request)) {
			return BROWSER_ID_FIREFOX;
		}
		else {
			return BROWSER_ID_OTHER;
		}
	}

	// Java 1.6+ @Override
	public boolean isMac(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("mac")) {
			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isAndroid(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("android")) {
			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isChrome(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("chrome")) {
			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isIe(HttpServletRequest request) {
		return isIe(getUserAgent(request));
	}

	// Java 1.6+ @Override
	public boolean isIphone(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("iphone")) {
			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isMobile(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("mobile") || (isAndroid(request) && userAgent.contains("nexus"))) {

			return true;
		}

		return false;
	}

	protected boolean isIe(String userAgent) {

		if (userAgent.contains("msie") && !userAgent.contains("opera")) {
			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isRtf(HttpServletRequest request) {

		if (isAndroid(request)) {
			return true;
		}

		if (isChrome(request)) {
			return true;
		}

		float majorVersion = getMajorVersion(request);

		if (isIe(request) && (majorVersion >= 5.5)) {
			return true;
		}

		if (isMozilla(request) && (majorVersion >= 1.3)) {
			return true;
		}

		if (isOpera(request)) {

			if (isMobile(request) && (majorVersion >= 10.0)) {
				return true;
			}
			else if (!isMobile(request)) {
				return true;
			}
		}

		if (isSafari(request)) {

			if (isMobile(request) && (majorVersion >= 5.0)) {
				return true;
			}
			else if (!isMobile(request) && (majorVersion >= 3.0)) {
				return true;
			}
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isSafari(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (isWebKit(request) && userAgent.contains("safari")) {
			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isWapXhtml(HttpServletRequest request) {
		String accept = getAccept(request);

		if (accept.contains("wap.xhtml")) {
			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isWml(HttpServletRequest request) {
		String accept = getAccept(request);

		if (accept.contains("wap.wml")) {
			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public float getMajorVersion(HttpServletRequest request) {
		return GetterUtil.getFloat(getVersion(request));
	}

	// Java 1.6+ @Override
	public boolean isSun(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("sunos")) {
			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isGecko(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("gecko")) {
			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isWap(HttpServletRequest request) {
		return isWapXhtml(request);
	}

	// Java 1.6+ @Override
	public boolean isAir(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("adobeair")) {
			return true;
		}

		return false;
	}

	// Java 1.6+ @Override
	public String getRevision(HttpServletRequest request) {
		String revision = (String) request.getAttribute(BROWSER_SNIFFER_REVISION);

		if (revision != null) {
			return revision;
		}

		revision = parseVersion(getUserAgent(request), REVISION_LEADINGS, REVISION_SEPARATORS);

		request.setAttribute(BROWSER_SNIFFER_REVISION, revision);

		return revision;
	}

	// Java 1.6+ @Override
	public boolean isWindows(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		for (String windowsAlias : WINDOWS_ALIASES) {

			if (userAgent.contains(windowsAlias)) {
				return true;
			}
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isWebKit(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		for (String webKitAlias : WEBKIT_ALIASES) {

			if (userAgent.contains(webKitAlias)) {
				return true;
			}
		}

		return false;
	}

	protected String getUserAgent(HttpServletRequest request) {
		String userAgent = StringPool.BLANK;

		if (request == null) {
			return userAgent;
		}

		userAgent = String.valueOf(request.getAttribute(HttpHeaders.USER_AGENT));

		if (Validator.isNotNull(userAgent)) {
			return userAgent;
		}

		userAgent = request.getHeader(HttpHeaders.USER_AGENT);

		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
		}
		else {
			userAgent = StringPool.BLANK;
		}

		request.setAttribute(HttpHeaders.USER_AGENT, userAgent);

		return userAgent;
	}

	// Java 1.6+ @Override
	public String getVersion(HttpServletRequest request) {
		String version = (String) request.getAttribute(BROWSER_SNIFFER_VERSION);

		if (version != null) {
			return version;
		}

		String userAgent = getUserAgent(request);

		version = parseVersion(userAgent, VERSION_LEADINGS, VERSION_SEPARATORS);

		if (version.isEmpty()) {
			version = parseVersion(userAgent, REVISION_LEADINGS, REVISION_SEPARATORS);
		}

		request.setAttribute(BROWSER_SNIFFER_VERSION, version);

		return version;
	}

	// Java 1.6+ @Override
	public boolean isFirefox(HttpServletRequest request) {

		if (!isMozilla(request)) {
			return false;
		}

		String userAgent = getUserAgent(request);

		for (String firefoxAlias : FIREFOX_ALIASES) {

			if (userAgent.contains(firefoxAlias)) {
				return true;
			}
		}

		return false;
	}

	// Java 1.6+ @Override
	public boolean isLinux(HttpServletRequest request) {
		String userAgent = getUserAgent(request);

		if (userAgent.contains("linux")) {
			return true;
		}

		return false;
	}

	protected static class GetterUtil {

		public static float getFloat(String value) {

			float floatValue = 0f;

			try {
				floatValue = Float.valueOf(value);
			}
			catch (NumberFormatException e) {
				// ignore
			}

			return floatValue;
		}
	}

	protected static class Validator {

		public static boolean isNotNull(String value) {
			return (value != null);
		}
	}
}
