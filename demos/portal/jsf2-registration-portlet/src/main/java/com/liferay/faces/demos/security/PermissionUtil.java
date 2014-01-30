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
package com.liferay.faces.demos.security;

import java.util.Arrays;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;


/**
 * Utilities for granting Liferay permissions.
 *
 * @author  Neil Griffin
 */
public class PermissionUtil {

	public static void grantPermissions(long companyId, long roleId, String resourceId, int scope, String primKey,
		String[] actionKeys) throws PortalException, SystemException {

		for (int i = 0; i < actionKeys.length; i++) {
			ResourceActionLocalServiceUtil.checkResourceActions(resourceId, Arrays.asList(actionKeys));
			ResourcePermissionLocalServiceUtil.setResourcePermissions(companyId, resourceId, scope, primKey, roleId,
				actionKeys);
		}
	}
}
