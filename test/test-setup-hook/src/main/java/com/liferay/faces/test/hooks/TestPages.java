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
package com.liferay.faces.test.hooks;

import java.util.ArrayList;
import java.util.List;


/**
 * The purpose of this class is to isolate source code differences between different versions of Liferay Portal.
 *
 * @author  Neil Griffin
 */
public class TestPages {

	public static final List<PortalPage> BRIDGE_DEMO_PAGES;
	public static final List<PortalPage> PORTAL_DEMO_PAGES;

	static {
		BRIDGE_DEMO_PAGES = new ArrayList<PortalPage>();
		BRIDGE_DEMO_PAGES.add(new PortalPage("JSF1", "1_WAR_jsf1portlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("JSF1-JSP", "1_WAR_jsf1jspportlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("JSF1-EVENTS",
				new String[] {
					"customers_WAR_jsf1ipceventscustomersportlet", "bookings_WAR_jsf1ipceventsbookingsportlet"
				}));
		BRIDGE_DEMO_PAGES.add(new PortalPage("JSF1-PRP",
				new String[] {
					"customersPortlet_WAR_jsf1ipcpubrenderparamsportlet",
					"bookingsPortlet_WAR_jsf1ipcpubrenderparamsportlet"
				}));
		BRIDGE_DEMO_PAGES.add(new PortalPage("ICE1", "1_WAR_icefaces1portlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("ICE1-IPC",
				new String[] { "1_WAR_icefaces1ipcajaxpushportlet", "2_WAR_icefaces1ipcajaxpushportlet" }));
	}

	static {
		PORTAL_DEMO_PAGES = new ArrayList<PortalPage>();
	}
}
