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
package com.liferay.faces.bridge.container.liferay;

import java.lang.reflect.Method;

import javax.faces.FacesWrapper;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * Liferay Portal 5.2.7 (and older) keep &lt;head&gt;...&lt;/head&gt; resources in a {@link java.lang.StringBuilder}
 * object. But as a performance improvement, Liferay Portal 5.2.8 (and newer) keep them in a custom object named
 * StringBundler. This class serves as a compatibility layer for all versions of Liferay 5.2.x.
 *
 * @author  Neil Griffin
 */
public class StringBundler implements FacesWrapper<Object> {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(StringBundler.class);

	// Private Constants
	private static Class<?> stringBundlerClass;
	private static Method appendMethod;
	private static Method lengthMethod;

	static {

		if (ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL).getBuildId() >= 5208) {

			try {
				stringBundlerClass = Class.forName("com.liferay.portal.kernel.util.StringBundler");
				appendMethod = stringBundlerClass.getMethod("append", new Class[] { String.class });
				lengthMethod = stringBundlerClass.getMethod("length", new Class[] {});
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
	}

	// Private Data Members
	private Object wrappedStringBundler;

	public StringBundler() {
		this.wrappedStringBundler = createInstance();
	}

	public StringBundler(Object stringBundler) {
		this.wrappedStringBundler = stringBundler;
	}

	public void append(String value) {

		try {

			if (stringBundlerClass == null) {

				if (wrappedStringBundler == null) {
					wrappedStringBundler = new StringBuilder();
				}

				StringBuilder stringBuilder = (StringBuilder) wrappedStringBundler;
				stringBuilder.append(value);
			}
			else {

				if (wrappedStringBundler == null) {
					wrappedStringBundler = stringBundlerClass.newInstance();
				}

				appendMethod.invoke(wrappedStringBundler, new Object[] { value });
			}

		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	public int length() {

		int len = 0;

		try {

			if (wrappedStringBundler != null) {

				if (stringBundlerClass == null) {
					StringBuilder stringBuilder = (StringBuilder) wrappedStringBundler;
					len = stringBuilder.length();
				}
				else {
					Integer lengthValue = (Integer) lengthMethod.invoke(wrappedStringBundler, (Object[]) null);
					len = lengthValue.intValue();
				}
			}
		}
		catch (Exception e) {
			logger.error(e);
		}

		return len;
	}

	@Override
	public String toString() {

		if (wrappedStringBundler == null) {
			return null;
		}
		else {
			return wrappedStringBundler.toString();
		}
	}

	protected Object createInstance() {

		Object instance = null;

		try {

			if (stringBundlerClass == null) {
				instance = new StringBuilder();
			}
			else {
				instance = stringBundlerClass.newInstance();
			}
		}
		catch (Exception e) {
			logger.error(e);
		}

		return instance;
	}

	public Object getWrapped() {
		return wrappedStringBundler;
	}

}
