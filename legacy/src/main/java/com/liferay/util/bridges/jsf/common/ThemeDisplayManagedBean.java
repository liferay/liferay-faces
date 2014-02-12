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
package com.liferay.util.bridges.jsf.common;

import javax.faces.context.FacesContext;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * <p>This class is designed to be a convenient JSF managed bean that can be used to get portal related information
 * (such as the user's time zone).</p>
 *
 * @deprecated  This class has been deprecated as of Liferay Faces 2.2 and will not appear in future versions.
 * @author      Neil Griffin
 */
@Deprecated
public class ThemeDisplayManagedBean {

	private static Logger logger = LoggerFactory.getLogger(User.class);

	/**
	 * @deprecated  Call {@link LiferayFacesContext#getUser()} instead.
	 */
	public User getUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		String remoteUser = facesContext.getExternalContext().getRemoteUser();

		try {
			long userId = GetterUtil.getLong(remoteUser);

			return UserLocalServiceUtil.getUserById(userId);
		}
		catch (Exception e) {
			logger.error(e);
		}

		return null;
	}

}
