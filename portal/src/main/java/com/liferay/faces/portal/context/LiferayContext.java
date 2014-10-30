package com.liferay.faces.portal.context;

import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

import javax.portlet.PortletRequest;

public interface LiferayContext {

	public PortletRequest getPortletRequest();

	public User getUser();

	public ThemeDisplay getThemeDisplay();
}
