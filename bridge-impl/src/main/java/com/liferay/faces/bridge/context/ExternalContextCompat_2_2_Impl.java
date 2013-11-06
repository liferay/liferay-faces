/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;


/**
 * This class provides a compatibility layer that isolates differences between JSF 2.1 and JSF 2.2.
 *
 * @author  Neil Griffin
 */
public abstract class ExternalContextCompat_2_2_Impl extends ExternalContextCompat_2_1_Impl {

	public ExternalContextCompat_2_2_Impl(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {
		super(portletContext, portletRequest, portletResponse);
	}
}
