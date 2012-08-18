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
package com.liferay.faces.test.hooks;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * This class provides access to Liferay Portal Service Layer methods in order to isolate API method signature
 * differences between version 5.2, 6.0, and 6.1.
 *
 * @author  Neil Griffin
 */
public class ServiceUtil {

	// Logger
	private static final Log log = LogFactory.getLog(ServiceUtil.class);

	public static Group addActiveOpenGroup(long userId, String name) throws Exception {

		boolean active = true;
		String description = name;
		String friendlyURL = StringPool.FORWARD_SLASH +
			name.toLowerCase().replaceAll(StringPool.SPACE, StringPool.DASH);
		int type = GroupConstants.TYPE_COMMUNITY_OPEN;

		return GroupLocalServiceUtil.addGroup(userId, (String) null, 0L, name, description, type, friendlyURL, active, new ServiceContext());
	}

	public static Layout addLayout(long userId, long groupId, boolean privateLayout, long parentLayoutId, String name,
		String title, String description, String type, boolean hidden, String friendlyURL) throws Exception {

		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setScopeGroupId(groupId);

		return LayoutLocalServiceUtil.addLayout(userId, groupId, privateLayout, parentLayoutId, name, title,
				description, type, hidden, friendlyURL, serviceContext);
	}

	public static User addUser(long creatorUserId, long companyId, String firstName, String lastName) throws Exception {

		boolean autoPassword = false;
		String password1 = "test";
		String password2 = password1;
		boolean autoScreenName = false;
		String screenName = firstName.toLowerCase() + StringPool.PERIOD + lastName.toLowerCase();
		String emailAddress = screenName + StringPool.AT + "liferay.com";
		long facebookId = 0L;
		String openId = StringPool.BLANK;
		Locale locale = Locale.ENGLISH;
		String middleName = StringPool.BLANK;
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = 1;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] groupIds = new long[] {};
		long[] organizationIds = new long[] {};
		long[] roleIds = new long[] {};
		long[] userGroupIds = new long[] {};
		boolean sendEmail = false;
		ServiceContext serviceContext = new ServiceContext();

		User user = null;
		
		try {
			user = UserLocalServiceUtil.getUserByScreenName(companyId, screenName);
		}
		catch (NoSuchUserException e) {
			user = UserLocalServiceUtil.addUser(creatorUserId, companyId, autoPassword, password1, password2,
					autoScreenName, screenName, emailAddress, facebookId, openId, locale, firstName, middleName, lastName,
					prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds, organizationIds,
					roleIds, userGroupIds, sendEmail, serviceContext);
			log.info("Added user: " + screenName);
		}

		return user;
	}
}
