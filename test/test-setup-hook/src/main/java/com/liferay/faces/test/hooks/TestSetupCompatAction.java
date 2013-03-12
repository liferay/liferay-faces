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
package com.liferay.faces.test.hooks;

import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.security.permission.PermissionChecker;


/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal. The
 * purpose of this class is to minimize source code differences between different branches.
 *
 * @author  Neil Griffin
 */
public abstract class TestSetupCompatAction extends SimpleAction {

	protected void addPortlet(LayoutTypePortlet layoutTypePortlet, long userId, int columnNumber, String portletId) {
		layoutTypePortlet.setPortletIds("column-" + columnNumber, portletId);
	}

	/**
	 * This method clears the {@link PermissionChecker} that was setup via the {@link #setupPermissionChecker(long)}
	 * method.
	 */
	protected void clearPermissionChecker() {
		// This is a no-op for Liferay Portal 6.0
	}

	/**
	 * This method sets up the {@link PermissionChecker} {@link ThreadLocal} prior to performing additional test setup.
	 */
	protected void setupPermissionChecker(long companyId) {
		// This is a no-op for Liferay Portal 6.0
	}
}
