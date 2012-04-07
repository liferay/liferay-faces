/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.demos.service;

import java.util.List;
import java.util.Set;

import com.liferay.faces.portal.context.LiferayFacesContext;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;


/**
 * @author  Neil Griffin
 */
public class OnlineUserServiceUtil {

	private static OnlineUserService onlineUserService;

	public static List<User> find(long companyId, long userId, int startRow, int finishRow, Set<Long> onlineUserSet)
		throws SystemException {
		return getService().find(companyId, userId, startRow, finishRow, onlineUserSet);
	}

	public static OnlineUserService getService() {

		if (onlineUserService == null) {

			// Currently using the JSF managed bean facility to keep track of services. Perhaps in the future when CDI
			// (JBoss Weld) works in portlets, we can use CDI for dependency injection.
			LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
			onlineUserService = (OnlineUserService) liferayFacesContext.resolveExpression("onlineUserService");
		}

		return onlineUserService;
	}
}
