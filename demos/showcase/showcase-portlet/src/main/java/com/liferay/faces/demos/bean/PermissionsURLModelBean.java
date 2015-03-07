/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.liferay.faces.portal.context.LiferayFacesContext;

import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.permission.PortletPermissionUtil;


/**
 * @author  Vernon Singleton
 */
@RequestScoped
@ManagedBean
public class PermissionsURLModelBean {

	// Private Data Members
	private String portletResourcePrimaryKey;

	public String getPortletResourcePrimaryKey() {

		if (portletResourcePrimaryKey == null) {
			LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
			long plid = liferayFacesContext.getPlid();
			Portlet portlet = liferayFacesContext.getPortlet();
			String portletId = portlet.getPortletId();
			portletResourcePrimaryKey = PortletPermissionUtil.getPrimaryKey(plid, portletId);
		}

		return portletResourcePrimaryKey;
	}
}
