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

/**
 * This interface defines a contract for utility methods for beans that are managed by the JSF managed-bean facility.
 *
 * @author  Neil Griffin
 */
public interface BeanManager {

	/**
	 * This method services as a convenience routine for invoking all methods of the specified managed-bean are marked
	 * with the javax.annotation.PreDestroy annotation. The JavaDocs state that if an exception is thrown by any of the
	 * PreDestroy annotated methods, they are required to be caught and NOT re-thrown. Instead, exceptions may be
	 * logged.
	 *
	 * @param  managedBean       The managed-bean that is to have its PreDestroy annotated method(s) invoked, if any.
	 * @param  preferPreDestroy  Boolean flag indicating that methods annotated with &#064;PreDestroy should be
	 *                           preferably invoked over those annotated with &#064;BridgePreDestroy.
	 */
	public void invokePreDestroyMethods(Object managedBean, boolean preferPreDestroy);

	/**
	 * Determines whether or not the specified value is a bean that is managed by the JSF managed-bean facility.
	 */
	public boolean isManagedBean(String name, Object value);

}
