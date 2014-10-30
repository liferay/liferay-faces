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
package com.liferay.faces.portal.context.internal;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.portal.context.LiferayContext;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@ApplicationScoped
public class LiferayContextImpl implements LiferayContext {

	protected ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	protected Map<String, Object> getRequestMap() {
		return getExternalContext().getRequestMap();
	}

	@Override
	public PortletRequest getPortletRequest() {
		return (PortletRequest) getExternalContext().getRequest();
	}

	@Override
	public ThemeDisplay getThemeDisplay() {
		return (ThemeDisplay) getRequestMap().get(WebKeys.THEME_DISPLAY);
	}

	@Override
	public User getUser() {
		return getThemeDisplay().getUser();
	}
}
