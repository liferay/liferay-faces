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
		BRIDGE_DEMO_PAGES.add(new PortalPage("JSF2", "1_WAR_jsf2portlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("JSF2-CDI", "1_WAR_jsf2cdiportlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("JSF2-JSP", "1_WAR_jsf2jspportlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("JSF2-PDF", "1_WAR_jsf2exportpdfportlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("JSF2-EVENTS",
				new String[] {
					"customers_WAR_jsf2ipceventscustomersportlet", "bookings_WAR_jsf2ipceventsbookingsportlet"
				}));
		BRIDGE_DEMO_PAGES.add(new PortalPage("JSF2-PRP",
				new String[] {
					"customersPortlet_WAR_jsf2ipcpubrenderparamsportlet",
					"bookingsPortlet_WAR_jsf2ipcpubrenderparamsportlet"
				}));
		BRIDGE_DEMO_PAGES.add(new PortalPage("ICE3", "1_WAR_icefaces3portlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("ICE3-COMPAT", "1_WAR_icefaces3compatportlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("ICE3-CRUD", "1_WAR_icefaces3crudportlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("ICE3-IPC",
				new String[] { "1_WAR_icefaces3ipcajaxpushportlet", "2_WAR_icefaces3ipcajaxpushportlet" }));
		BRIDGE_DEMO_PAGES.add(new PortalPage("PRIME3", "1_WAR_primefaces3portlet_INSTANCE_"));
		BRIDGE_DEMO_PAGES.add(new PortalPage("RICH4", "1_WAR_richfaces4portlet_INSTANCE_"));
	}

	static {
		PORTAL_DEMO_PAGES = new ArrayList<PortalPage>();
		PORTAL_DEMO_PAGES.add(new PortalPage("ICE3-DIR", "1_WAR_icefaces3directoryportlet"));
		PORTAL_DEMO_PAGES.add(new PortalPage("ICE3-DOC", "1_WAR_icefaces3documentsportlet"));
	}
}
