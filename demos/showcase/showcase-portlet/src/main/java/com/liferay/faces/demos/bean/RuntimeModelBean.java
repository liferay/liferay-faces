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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.liferay.portal.kernel.util.StringUtil;


/**
 * @author  Juan Gonzalez
 */
@ViewScoped
@ManagedBean
public class RuntimeModelBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String portletName;
	private String portletNamePreferences;
	private boolean showBorders;

	public String getDefaultPreferences() {

		return "<portlet-preferences><preference><name>portletSetupShowBorders</name>" + "<value>" + isShowBorders() +
			"</value>" + "</preference></portlet-preferences>";
	}

	public String getPortletName() {

		if (portletName == null) {
			portletName = "3_INSTANCE_" + StringUtil.randomString(12);
		}

		return portletName;
	}

	public void setPortletName(String portletName) {
		this.portletName = portletName;
	}

	public String getPortletNamePreferences() {

		if (portletNamePreferences == null) {
			portletNamePreferences = "73_INSTANCE_" + StringUtil.randomString(12);
		}

		return portletNamePreferences;
	}

	public void setPortletNamePreferences(String portletNamePreferences) {
		this.portletNamePreferences = portletNamePreferences;
	}

	public boolean isShowBorders() {
		return showBorders;
	}

	public void setShowBorders(boolean showBorders) {
		this.showBorders = showBorders;
	}

}
