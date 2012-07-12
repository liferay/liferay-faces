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
package com.liferay.faces.demos.hook;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletURLFactoryUtil;


/**
 * This is an action hook that changes provides a new value for the href attribute of the "Create Account" link of the
 * Liferay Sign-In portlet.
 *
 * @author  Neil Griffin
 */
public class ServicePreAction extends Action {

	Logger logger = LoggerFactory.getLogger(ServicePreAction.class);

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ActionException {

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			String portletName = "1_WAR_jsf2registrationportlet";
			PortletURL urlCreateAccount = PortletURLFactoryUtil.create(request, portletName, themeDisplay.getPlid(),
					PortletRequest.ACTION_PHASE);

			urlCreateAccount.setWindowState(WindowState.MAXIMIZED);
			urlCreateAccount.setPortletMode(PortletMode.VIEW);

			urlCreateAccount.setParameter("saveLastPath", "0");
			urlCreateAccount.setParameter("struts_action", "/login/create_account");
			themeDisplay.setURLCreateAccount(urlCreateAccount);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
