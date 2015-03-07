/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.client.internal;

/**
 * @author  Neil Griffin
 */
public class LiferayPortalBrowserSnifferCompat {

	// Private Constants
	protected static final String ACCEPT_ENCODING = "Accept-Encoding";
	protected static final char BACK_SLASH = '\\';
	protected static final String BROWSER_SNIFFER_REVISION = "BROWSER_SNIFFER_REVISION";
	protected static final String BROWSER_SNIFFER_VERSION = "BROWSER_SNIFFER_VERSION";
	protected static final char COLON = ':';
	protected static final String[] FIREFOX_ALIASES = {
			"firefox", "minefield", "granparadiso", "bonecho", "firebird", "phoenix", "camino"
		};
	protected static final char FORWARD_SLASH = '/';
	protected static final char SPACE = ' ';
	protected static String[] REVISION_LEADINGS = { "rv", "it", "ra", "ie" };
	protected static char[] REVISION_SEPARATORS = { BACK_SLASH, COLON, FORWARD_SLASH, SPACE };
	protected static String[] VERSION_LEADINGS = { "version", "firefox", "minefield", "chrome" };
	protected static char[] VERSION_SEPARATORS = { BACK_SLASH, FORWARD_SLASH };
	protected static final String[] WEBKIT_ALIASES = { "khtml", "applewebkit" };
	protected static final String[] WINDOWS_ALIASES = { "windows", "win32", "16bit" };

	protected static class CharPool {

		public static final char BACK_SLASH = '\\';
		public static final char COLON = ':';
		public static final char FORWARD_SLASH = '/';
		public static final char LOWER_CASE_L = 'l';
		public static final char LOWER_CASE_N = 'n';
		public static final char LOWER_CASE_U = 'u';
		public static final char SLASH = FORWARD_SLASH;
		public static final char SPACE = ' ';
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

	protected static class StringUtil {

		public static String toLowerCase(String value) {
			return value.toLowerCase();
		}
	}

	protected static class Validator {

		public static boolean isNotNull(String s) {
			return (!isNull(s));
		}

		public static boolean isNull(String s) {

			if (s == null) {
				return true;
			}

			int counter = 0;

			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);

				if (c == CharPool.SPACE) {
					continue;
				}
				else if (counter > 3) {
					return false;
				}

				if (counter == 0) {

					if (c != CharPool.LOWER_CASE_N) {
						return false;
					}
				}
				else if (counter == 1) {

					if (c != CharPool.LOWER_CASE_U) {
						return false;
					}
				}
				else if ((counter == 2) || (counter == 3)) {

					if (c != CharPool.LOWER_CASE_L) {
						return false;
					}
				}

				counter++;
			}

			if ((counter == 0) || (counter == 4)) {
				return true;
			}

			return false;
		}
	}

	protected static class WebKeys {

		public static final String BROWSER_SNIFFER_REVISION = "BROWSER_SNIFFER_REVISION";

		public static final String BROWSER_SNIFFER_VERSION = "BROWSER_SNIFFER_VERSION";
	}
}
