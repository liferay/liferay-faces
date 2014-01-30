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
package com.liferay.faces.bridge.bean;

import java.lang.reflect.Method;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class MojarraInjectionProvider {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(MojarraInjectionProvider.class);

	// Private Constants
	private static final String INVOKE_PRE_DESTROY = "invokePreDestroy";

	// Private Data Members
	private Method invokePreDestroyMethod;
	private Object wrappedInjectionProvider;

	public MojarraInjectionProvider(Object injectionProvider) {

		this.wrappedInjectionProvider = injectionProvider;

		try {
			this.invokePreDestroyMethod = injectionProvider.getClass().getMethod(INVOKE_PRE_DESTROY,
					new Class[] { Object.class });
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	public void invokePreDestroy(Object managedBean) throws Exception {

		if (invokePreDestroyMethod != null) {
			invokePreDestroyMethod.invoke(wrappedInjectionProvider, managedBean);
		}
	}

	@Override
	public String toString() {
		return wrappedInjectionProvider.toString();
	}
}
