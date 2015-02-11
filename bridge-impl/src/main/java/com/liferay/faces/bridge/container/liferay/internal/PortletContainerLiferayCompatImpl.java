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
package com.liferay.faces.bridge.container.liferay.internal;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.container.internal.PortletContainerImpl;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public class PortletContainerLiferayCompatImpl extends PortletContainerImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PortletContainerLiferayCompatImpl.class);

	public PortletContainerLiferayCompatImpl(PortletRequest portletRequest, PortletConfig portletConfig) {
		super(portletRequest, portletConfig);
	}

	protected boolean isPortletRequiresNamespacedParameters(PortletRequest portletRequest, ThemeDisplay themeDisplay) {

		boolean portletRequiresNamespacedParameters = false;

		String portletId = (String) portletRequest.getAttribute(WebKeys.PORTLET_ID);

		try {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), portletId);
			portletRequiresNamespacedParameters = portlet.isRequiresNamespacedParameters();
		}
		catch (SystemException e) {
			logger.error(e);
		}

		return portletRequiresNamespacedParameters;
	}

}
