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
package com.liferay.faces.bridge.bean.internal;

/**
 * @author  Neil Griffin
 */
public interface PreDestroyInvoker {

	/**
	 * This method services as a convenience routine for invoking all methods of the specified managed-bean are marked
	 * with the {@link javax.annotation.PreDestroy} or {@link javax.portlet.faces.annotation.BridgePreDestroy}
	 * annotation. The JavaDocs state that if an exception is thrown by any of the pre-destroy annotated methods, they
	 * are required to be caught and NOT re-thrown. Instead, exceptions are logged.
	 *
	 * @param  managedBean       The managed-bean that is to have its {@link javax.annotation.PreDestroy} or {@link
	 *                           javax.portlet.faces.annotation.BridgePreDestroy} annotated method(s) invoked, if any.
	 * @param  preferPreDestroy  Flag indicating that methods annotated with {@link javax.annotation.PreDestroy} should
	 *                           be preferably invoked over those annotated with {@link
	 *                           javax.portlet.faces.annotation.BridgePreDestroy}.
	 */
	public void invokeAnnotatedMethods(Object managedBean, boolean preferPreDestroy);
}
