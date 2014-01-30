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
package com.liferay.faces.demos.hook;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.faces.FacesWrapper;

import com.liferay.portal.util.Portal;


/**
 * @author  Neil Griffin
 */
public abstract class PortalWrapper implements InvocationHandler, FacesWrapper<Portal> {

	public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {

		try {
			Object returnValue = method.invoke(getWrapped(), arguments);

			return returnValue;
		}
		catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	public abstract Portal getWrapped();
}
