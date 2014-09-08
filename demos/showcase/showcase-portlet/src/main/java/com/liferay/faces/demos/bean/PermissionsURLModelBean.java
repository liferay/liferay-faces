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
package com.liferay.faces.demos.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.portlet.PortletRequest;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.portal.WebKeys;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * @author  Vernon Singleton
 */
@ViewScoped
@ManagedBean
public class PermissionsURLModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = -2797337210435169337L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PermissionsURLModelBean.class);

	// Private Data Members
	private String primaryKey;
	private List<Group> groups;

	public List<Group> getGroups() {

		int groupsCount = -1;

		if (groups == null) {

			try {
				groupsCount = GroupLocalServiceUtil.getGroupsCount();

				if (groupsCount < 1) {
					System.err.println("getEvents: why are there no groups? ... groupsCount = " + groupsCount);
				}
				else {
					groups = GroupLocalServiceUtil.getGroups(0, 1);
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		logger.info("getGroups: groupsCount = " + groupsCount);

		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public String getPrimaryKey() {

		if (primaryKey == null) {
			LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
			PortletRequest request = (PortletRequest) liferayFacesContext.getExternalContext().getRequest();
			Portlet portlet = (Portlet) request.getAttribute(WebKeys.RENDER_PORTLET);
			ThemeDisplay themeDisplay = liferayFacesContext.getThemeDisplay();

			long plid = themeDisplay.getPlid();
			String portletId = portlet.getPortletId();

			primaryKey = PortletPermissionUtil.getPrimaryKey(plid, portletId);
		}

		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

}
