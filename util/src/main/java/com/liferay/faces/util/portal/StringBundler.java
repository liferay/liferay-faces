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
package com.liferay.faces.util.portal;

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
	private static Method appendMethod1;
	private static Method appendMethod2;
	private static Method lengthMethod;

	static {

		if (ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL).getBuildId() >= 5208) {

			try {
				stringBundlerClass = Class.forName("com.liferay.portal.kernel.util.StringBundler");
				appendMethod1 = stringBundlerClass.getMethod("append", new Class[] { String.class });
				appendMethod2 = stringBundlerClass.getMethod("append", new Class[] { stringBundlerClass });
				lengthMethod = stringBundlerClass.getMethod("length", new Class[] {});
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
	}

	// Private Data Members
	private Object wrapped;

	public StringBundler() {
		this.wrapped = createInstance();
	}

	public StringBundler(Object stringBundler) {
		
		if (stringBundler == null) {
			this.wrapped = createInstance();
		}
		else {
			this.wrapped = stringBundler;
		}
	}

	public void append(String value) {

		try {

			if (wrapped instanceof StringBuilder) {
				StringBuilder stringBuilder = (StringBuilder) wrapped;
				stringBuilder.append(value);
			}
			else {
				appendMethod1.invoke(wrapped, new Object[] { value });
			}
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	public void append(StringBundler value) {

		try {

			if (wrapped instanceof StringBuilder) {
				StringBuilder stringBuilder = (StringBuilder) wrapped;
				stringBuilder.append(value.toString());
			}
			else {
				appendMethod2.invoke(wrapped, new Object[] { value.getWrapped() });
			}
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	public int length() {

		int len = 0;

		try {

			if (wrapped instanceof StringBuilder) {
				StringBuilder stringBuilder = (StringBuilder) wrapped;
				len = stringBuilder.length();
			}
			else {
				Integer lengthValue = (Integer) lengthMethod.invoke(wrapped, (Object[]) null);
				len = lengthValue.intValue();
			}
		}
		catch (Exception e) {
			logger.error(e);
		}

		return len;
	}

	@Override
	public String toString() {
		return wrapped.toString();
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
		return wrapped;
	}

}
