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
package com.liferay.faces.demos.service;

import java.util.List;
import java.util.Set;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;


/**
 * @author  Neil Griffin
 */
public interface OnlineUserService {

	/**
	 * Returns a list of users according to the specified criteria.
	 *
	 * @param   companyId      The current companyId.
	 * @param   userId         The current userId.
	 * @param   startRow       The first row that is to be found.
	 * @param   finishRow      The last row that is to be found.
	 * @param   onlineUserSet  The set of userIds representing users that are currently online.
	 *
	 * @return  The list of users.
	 *
	 * @throws  SystemException
	 */
	public List<User> find(long companyId, long userId, int startRow, int finishRow, Set<Long> onlineUserSet)
		throws SystemException;
}
