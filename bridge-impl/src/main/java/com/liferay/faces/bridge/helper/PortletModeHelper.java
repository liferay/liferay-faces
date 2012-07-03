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
package com.liferay.faces.bridge.helper;

import javax.portlet.PortletMode;


/**
 * @author  Neil Griffin
 */
public class PortletModeHelper {

	// Public Constants
	public static final String PORTLET_MODE_VIEW = PortletMode.VIEW.toString();
	public static final String PORTLET_MODE_EDIT = PortletMode.EDIT.toString();
	public static final String PORTLET_MODE_HELP = PortletMode.HELP.toString();
	public static final String[] PORTLET_MODE_NAMES = new String[] {
			PORTLET_MODE_VIEW, PORTLET_MODE_EDIT, PORTLET_MODE_HELP
		};

}
