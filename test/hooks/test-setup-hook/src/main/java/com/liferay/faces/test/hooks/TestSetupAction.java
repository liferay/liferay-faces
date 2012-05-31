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
package com.liferay.faces.test.hooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;


/**
 * @author  Neil Griffin
 */
public class TestSetupAction extends SimpleAction {

	private static final Log logger = LogFactory.getLog(TestSetupAction.class);

	private static final List<PortalPage> PORTAL_PAGES;

	static {
		PORTAL_PAGES = new ArrayList<PortalPage>();
		PORTAL_PAGES.add(new PortalPage("JSF2", "1_WAR_jsf2portlet_INSTANCE_"));
		PORTAL_PAGES.add(new PortalPage("JSF2-CDI", "1_WAR_jsf2cdiportlet_INSTANCE_"));
		PORTAL_PAGES.add(new PortalPage("JSF2-JSP", "1_WAR_jsf2jspportlet_INSTANCE_"));
		PORTAL_PAGES.add(new PortalPage("JSF2-PDF", "1_WAR_jsf2exportpdfportlet_INSTANCE_"));
		PORTAL_PAGES.add(new PortalPage("JSF2-EVENTS",
				new String[] {
					"customers_WAR_jsf2ipceventscustomersportlet", "bookings_WAR_jsf2ipceventsbookingsportlet"
				}));
		PORTAL_PAGES.add(new PortalPage("JSF2-PRP",
				new String[] {
					"customersPortlet_WAR_jsf2ipcpubrenderparamsportlet",
					"bookingsPortlet_WAR_jsf2ipcpubrenderparamsportlet"
				}));
		PORTAL_PAGES.add(new PortalPage("ICE3", "1_WAR_icefaces3portlet_INSTANCE_"));
		PORTAL_PAGES.add(new PortalPage("ICE3-COMPAT", "1_WAR_icefaces3compatportlet_INSTANCE_"));
		PORTAL_PAGES.add(new PortalPage("ICE3-CRUD", "1_WAR_icefaces3crudportlet_INSTANCE_"));
		PORTAL_PAGES.add(new PortalPage("ICE3-PUSH", "1_WAR_icefaces3ipcajaxpushportlet_INSTANCE_"));
		PORTAL_PAGES.add(new PortalPage("ICE3-CHAT", "1_WAR_icefaces3chatportlet_INSTANCE_"));
		PORTAL_PAGES.add(new PortalPage("ICE3-DIR", "1_WAR_icefaces3directoryportlet_INSTANCE_"));
		PORTAL_PAGES.add(new PortalPage("ICE3-DIR", "1_WAR_icefaces3documentsportlet_INSTANCE_"));
		PORTAL_PAGES.add(new PortalPage("PRIME3", "1_WAR_primefaces3portlet_INSTANCE_"));
		PORTAL_PAGES.add(new PortalPage("RICH4", "1_WAR_richfaces4portlet_INSTANCE_"));
	}

	@Override
	public void run(String[] companyIds) throws ActionException {

		try {

			for (String companyId : companyIds) {
				setupCompanyPages(Long.parseLong(companyId));
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	protected void setupCompanyPages(long companyId) throws PortalException, SystemException {

		Company company = CompanyLocalServiceUtil.getCompanyById(companyId);

		for (PortalPage portalPage : PORTAL_PAGES) {
			String defaultSiteName = PropsUtil.get(PropsKeys.VIRTUAL_HOSTS_DEFAULT_COMMUNITY_NAME);
			long groupId = GroupLocalServiceUtil.getGroup(companyId, defaultSiteName).getGroupId();
			setupPage(company.getDefaultUser().getUserId(), groupId, portalPage);
		}
	}

	protected void setupPage(long userId, long groupId, PortalPage portalPage) throws PortalException, SystemException {
		String portalPageName = portalPage.getName();
		String[] portletNames = portalPage.getPortletNames();
		Layout portalPageLayout = getPortalPageLayout(userId, groupId, portalPageName);
		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) portalPageLayout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(userId, "2_columns_i", false);

		int columnNumber = 1;

		for (String portletName : portletNames) {
			String portletId = portletName;

			if (portletName.endsWith("_INSTANCE_")) {
				portletId = portletId + "ABCD";
			}

			layoutTypePortlet.setPortletIds("column-" + columnNumber, portletId);
			columnNumber++;
		}

		LayoutLocalServiceUtil.updateLayout(portalPageLayout);

		logger.info("Updated page: " + portalPageName);
	}

	protected Layout getPortalPageLayout(long userId, long groupId, String portalPageName) throws PortalException,
		SystemException {
		Layout portalPageLayout = null;
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId, false);

		for (Layout layout : layouts) {

			if (layout.getName(Locale.US).equals(portalPageName)) {
				portalPageLayout = layout;
			}
		}

		if (portalPageLayout == null) {
			boolean privateLayout = false;
			long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;
			String type = LayoutConstants.TYPE_PORTLET;
			boolean hidden = false;
			String friendlyURL = "/" + portalPageName.toLowerCase();
			ServiceContext serviceContext = new ServiceContext();
			serviceContext.setScopeGroupId(groupId);
			portalPageLayout = LayoutLocalServiceUtil.addLayout(userId, groupId, privateLayout, parentLayoutId,
					portalPageName, portalPageName, portalPageName, type, hidden, friendlyURL, serviceContext);
			logger.info("Added page: " + portalPageName);
		}

		return portalPageLayout;
	}

}
