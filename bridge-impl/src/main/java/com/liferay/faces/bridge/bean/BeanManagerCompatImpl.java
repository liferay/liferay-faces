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
package com.liferay.faces.bridge.bean;

/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class BeanManagerCompatImpl implements BeanManager {

	/**
	 * Determines whether or not the specified object is annotated as a JSF managed-bean.
	 *
	 * @param   obj  The object to check.
	 *
	 * @return  true if the specified object is annotated as a JSF managed-bean, otherwise false.
	 */
	protected boolean hasManagedBeanAnnotation(Object object) {

		// This is a no-op for JSF 1.2 since the @ManagedBean annotation was introduced in JSF 2.0.
		return false;
	}

}
