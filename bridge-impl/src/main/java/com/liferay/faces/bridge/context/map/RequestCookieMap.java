/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context.map;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;


/**
 * @author  Neil Griffin
 */
public class RequestCookieMap implements Map<String, Object> {

	private Cookie[] cookies;

	public RequestCookieMap(Cookie[] cookies) {
		this.cookies = cookies;
	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public boolean containsKey(Object key) {
		boolean found = false;

		if (key != null) {
			String keyAsString = key.toString();

			if (cookies != null) {

				for (Cookie cookie : cookies) {
					found = cookie.getName().equals(keyAsString);

					if (found) {
						break;
					}
				}
			}
		}

		return found;
	}

	public boolean containsValue(Object value) {
		boolean found = false;

		if (cookies != null) {

			for (Cookie cookie : cookies) {
				found = (cookie == value);

				if (found) {
					break;
				}
			}
		}

		return found;
	}

	public Set<Map.Entry<String, Object>> entrySet() {
		Set<Map.Entry<String, Object>> entrySet = null;

		if (cookies != null) {
			entrySet = new HashSet<Map.Entry<String, Object>>();

			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				RequestCookieMapEntry requestCookieMapEntry = new RequestCookieMapEntry(cookieName, cookie);
				entrySet.add(requestCookieMapEntry);
			}
		}

		return entrySet;
	}

	public Cookie get(Object key) {
		Cookie value = null;

		if ((key != null) && (cookies != null)) {
			String keyAsString = key.toString();

			for (Cookie cookie : cookies) {

				if (cookie.getName().equals(keyAsString)) {
					value = cookie;

					break;
				}
			}
		}

		return value;
	}

	public Set<String> keySet() {
		Set<String> keySet = null;

		if (cookies != null) {
			keySet = new HashSet<String>();

			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName();
				keySet.add(cookieName);
			}
		}

		return keySet;
	}

	public Cookie put(String key, Object value) {
		throw new UnsupportedOperationException();
	}

	public void putAll(Map<? extends String, ? extends Object> t) {
		throw new UnsupportedOperationException();
	}

	public Cookie remove(Object key) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		int size = 0;

		if (cookies != null) {
			size = cookies.length;
		}

		return size;
	}

	public Collection<Object> values() {
		Collection<Object> values = null;

		if (cookies != null) {
			values = new HashSet<Object>();

			for (Cookie cookie : cookies) {
				values.add(cookie);
			}
		}

		return values;
	}

	public boolean isEmpty() {
		return ((cookies == null) || (cookies.length == 0));
	}
}
