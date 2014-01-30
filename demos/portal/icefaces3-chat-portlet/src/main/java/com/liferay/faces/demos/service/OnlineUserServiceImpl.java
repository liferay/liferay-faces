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
package com.liferay.faces.demos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "onlineUserService")
@ApplicationScoped
public class OnlineUserServiceImpl implements OnlineUserService {

	/**
	 * Note: This method could be implemented in one of two appraches: 1) Get all the users from Liferay and determine
	 * which ones are online from the specified onlineUserSet. 2) Get all the users from the specified onlineUserSet and
	 * get each user from Liferay. Approach #1 works well when there are few users in the system. But if there are many
	 * users in the system, with a minority of them simultaneously online, then Approach #2 would be better. Currently
	 * this method implements Approach #1.
	 */
	public List<User> find(long companyId, long userId, int startRow, int finishRow, Set<Long> onlineUserSet)
		throws SystemException {
		List<User> allUsers = UserLocalServiceUtil.getCompanyUsers(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		int totalOnlineUsers = onlineUserSet.size();
		List<User> onlineUsers = new ArrayList<User>(totalOnlineUsers);

		for (User user : allUsers) {

			long curUserId = user.getUserId();

			if ((userId != curUserId) && onlineUserSet.contains(curUserId)) {
				onlineUsers.add(user);
			}
		}

		if ((totalOnlineUsers > 0) && (startRow != QueryUtil.ALL_POS) && (finishRow != QueryUtil.ALL_POS)) {

			if (startRow > totalOnlineUsers) {
				startRow = totalOnlineUsers;
			}

			if (finishRow > totalOnlineUsers) {
				finishRow = totalOnlineUsers;
			}

			int includeFinishRowToo = finishRow + 1;
			onlineUsers = onlineUsers.subList(startRow, includeFinishRowToo);
		}

		return onlineUsers;
	}

}
