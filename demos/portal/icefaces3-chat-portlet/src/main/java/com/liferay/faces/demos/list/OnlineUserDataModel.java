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
package com.liferay.faces.demos.list;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.liferay.faces.demos.service.OnlineUserServiceUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.LazyDataModel;

import com.liferay.portal.model.User;


/**
 * This class provides a JSF DataModel layer of abstraction around the Liferay service layer and supports lazy fetches
 * when used in conjunction with a JSF component tag like h:dataTable.
 *
 * @author  "Neil Griffin"
 */
public class OnlineUserDataModel extends LazyDataModel<User> {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(OnlineUserDataModel.class);

	// Private Data Members
	private long companyId;
	private Set<Long> onlineUserSet;
	private long userId;

	public OnlineUserDataModel(long companyId, long userId, int rowsPerPage, Set<Long> onlineUserSet) {
		this.companyId = companyId;
		this.userId = userId;
		setRowsPerPage(rowsPerPage);
		this.onlineUserSet = onlineUserSet;
	}

	@Override
	public int countRows() {
		int count = -1;

		if (onlineUserSet != null) {

			// Subtract one for the current user.
			count = onlineUserSet.size() - 1;
		}

		return count;
	}

	@Override
	public void deleteRow(Object primaryKey) throws IOException {
		// Never want to delete a user. This method is only here to fulfill the contract.
	}

	@Override
	public List<User> findRows(int startRow, int finishRow) {
		List<User> users = null;

		try {
			users = OnlineUserServiceUtil.find(companyId, userId, startRow, finishRow, onlineUserSet);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return users;
	}

	@Override
	public Object getPrimaryKey(User user) {
		return user.getUserId();
	}

}
