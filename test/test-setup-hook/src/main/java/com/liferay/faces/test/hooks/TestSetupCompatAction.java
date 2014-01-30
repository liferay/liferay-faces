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
package com.liferay.faces.test.hooks;

import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal. The
 * purpose of this class is to minimize source code differences between different branches.
 *
 * @author  Neil Griffin
 */
public abstract class TestSetupCompatAction extends SimpleAction {

	// Private Data Members
	boolean fixedPermissionChecker = false;

	protected void addPortlet(LayoutTypePortlet layoutTypePortlet, long userId, int columnNumber, String portletId)
		throws PortalException, SystemException {

		String columnNumberLabel = Integer.toString(columnNumber);

		// Liferay 6.2 changed the expected value for the String-based column number. Previous versions didn't
		// require the "column-" prefix.
		columnNumberLabel = "column-" + columnNumber;

		// NOTE: In Liferay 6.1.x the following call was to setPortletIds() but that method was removed in 6.2.x
		layoutTypePortlet.addPortletId(userId, portletId, columnNumberLabel, 1);

	}

	/**
	 * This method clears the {@link PermissionChecker} that was setup via the {@link #setupPermissionChecker(long)}
	 * method.
	 */
	protected void clearPermissionChecker() {

		if (fixedPermissionChecker) {
			PermissionThreadLocal.setPermissionChecker(null);
		}
	}

	/**
	 * This method sets up the {@link PermissionChecker} {@link ThreadLocal} prior to performing additional test setup.
	 *
	 * @throws  SystemException
	 * @throws  PortalException
	 */
	protected void setupPermissionChecker(long companyId) throws PortalException, SystemException {
		PermissionChecker permissionChecker = PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			Role administratorRole = RoleLocalServiceUtil.getRole(companyId, RoleConstants.ADMINISTRATOR);
			User administratorUser = UserLocalServiceUtil.getRoleUsers(administratorRole.getRoleId()).get(0);

			try {
				permissionChecker = PermissionCheckerFactoryUtil.create(administratorUser);

				PermissionThreadLocal.setPermissionChecker(permissionChecker);
				fixedPermissionChecker = true;
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

	}
}
