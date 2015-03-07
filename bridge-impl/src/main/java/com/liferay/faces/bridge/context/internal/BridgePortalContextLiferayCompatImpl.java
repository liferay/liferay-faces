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
package com.liferay.faces.bridge.context.internal;

import javax.portlet.PortalContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * @author  Neil Griffin
 */
public class BridgePortalContextLiferayCompatImpl extends BridgePortalContextImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgePortalContextLiferayCompatImpl.class);

	public BridgePortalContextLiferayCompatImpl(PortalContext portalContext) {
		super(portalContext);
	}

	protected boolean isLiferayNamingspacingParameters(PortletRequest portletRequest) {

		boolean liferayNamespacingParameters = false;
		String portletId = (String) portletRequest.getAttribute(WebKeys.PORTLET_ID);

		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
			Portlet portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), portletId);
			liferayNamespacingParameters = portlet.isRequiresNamespacedParameters();
		}
		catch (SystemException e) {
			logger.error(e);
		}

		return liferayNamespacingParameters;
	}
}
