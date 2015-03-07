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
package com.liferay.faces.bridge.filter.liferay.internal;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;


/**
 * See class-level JavaDoc for {@link LiferayURLGeneratorBaseImpl}.
 *
 * @author  Neil Griffin
 */
public class LiferayURLGeneratorActionImpl extends LiferayURLGeneratorBaseImpl {

	// Private Constants
	public static final String LIFECYCLE_ACTION_PHASE_ID = "1";

	public LiferayURLGeneratorActionImpl(String actionURL, PortletMode portletMode, String responseNamespace,
		WindowState windowState) {
		super(actionURL, portletMode, responseNamespace, windowState);
	}

	public String getPortletLifecycleId() {
		return LIFECYCLE_ACTION_PHASE_ID;
	}

}
