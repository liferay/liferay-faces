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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;


/**
 * This class provides access to Liferay Portal Service Layer methods in order to isolate API method signature
 * differences between version 5.2, 6.0, and 6.1.
 *
 * @author  Neil Griffin
 */
public class ServiceUtil {

	public static void addActiveOpenGroup(long userId, String name) throws Exception {

		boolean active = true;
		String description = name;
		String friendlyURL = StringPool.FORWARD_SLASH +
			name.toLowerCase().replaceAll(StringPool.SPACE, StringPool.DASH);
		int type = GroupConstants.TYPE_COMMUNITY_OPEN;
		GroupLocalServiceUtil.addGroup(userId, (String) null, 0L, name, description, type, friendlyURL, active,
			new ServiceContext());
	}

	public static Layout addLayout(long userId, long groupId, boolean privateLayout, long parentLayoutId, String name,
		String title, String description, String type, boolean hidden, String friendlyURL) throws Exception {

		return LayoutLocalServiceUtil.addLayout(userId, groupId, privateLayout, parentLayoutId, name, title,
				description, type, hidden, friendlyURL);
	}

}
