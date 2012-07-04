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

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * @author  Neil Griffin
 */
public class TestSetupAction extends SimpleAction {

	private static final Log logger = LogFactory.getLog(TestSetupAction.class);

	private static final List<PortalPage> BRIDGE_DEMO_PAGES;
	private static final List<PortalPage> PORTAL_DEMO_PAGES;

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
		PORTAL_DEMO_PAGES.add(new PortalPage("ICE3-CHAT", "1_WAR_icefaces3chatportlet"));
		PORTAL_DEMO_PAGES.add(new PortalPage("ICE3-DIR", "1_WAR_icefaces3directoryportlet"));
		PORTAL_DEMO_PAGES.add(new PortalPage("ICE3-DOC", "1_WAR_icefaces3documentsportlet"));
	}

	@Override
	public void run(String[] companyIds) throws ActionException {

		try {

			for (String companyIdAsString : companyIds) {

				long companyId = Long.parseLong(companyIdAsString);
				Company company = CompanyLocalServiceUtil.getCompanyById(companyId);
				long userId = company.getDefaultUser().getUserId();
				setupSites(companyId, userId);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	protected void addAllUsersToSite(long companyId, long groupId) throws Exception {

		List<User> users = UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		ArrayList<Long> userIdList = new ArrayList<Long>();

		for (User user : users) {

			if (!user.isDefaultUser()) {
				userIdList.add(user.getUserId());
			}
		}

		long[] userIds = new long[userIdList.size()];

		for (int i = 0; i < userIds.length; i++) {
			userIds[i] = userIdList.get(i);
		}

		UserLocalServiceUtil.addGroupUsers(groupId, userIds);
	}

	protected void setupBridgeDemosSite(long companyId, long userId) throws Exception {
		Group site = getSite(companyId, userId, "Bridge Demos");
		long groupId = site.getGroupId();
		addAllUsersToSite(companyId, groupId);

		for (PortalPage portalPage : BRIDGE_DEMO_PAGES) {
			setupPage(userId, groupId, portalPage);
		}
	}

	protected void setupBridgeTCKSite(long companyId, long userId) throws Exception, DocumentException {
		Group site = getSite(companyId, userId, "Bridge TCK");
		long groupId = site.getGroupId();
		addAllUsersToSite(companyId, groupId);

		URL configFileURL = getClass().getClassLoader().getResource("pluto-portal-driver-config.xml");
		Document document = SAXReaderUtil.read(configFileURL);
		Element rootElement = document.getRootElement();
		Element renderConfigElement = rootElement.element("render-config");
		Iterator<Element> pageElementIterator = renderConfigElement.elementIterator("page");

		while (pageElementIterator.hasNext()) {
			Element pageElement = pageElementIterator.next();
			Attribute nameAttribute = pageElement.attribute("name");
			String pageName = nameAttribute.getValue();
			Element portletElement = pageElement.element("portlet");
			nameAttribute = portletElement.attribute("name");

			String portletName = nameAttribute.getValue();
			String liferayPortletName = portletName.replaceAll(StringPool.DASH, StringPool.BLANK);
			String liferayPortletId = liferayPortletName + "_WAR_bridgetckmainportlet";
			PortalPage portalPage = new PortalPage(pageName, liferayPortletId);
			setupPage(userId, groupId, portalPage);
		}
	}

	protected void setupPage(long userId, long groupId, PortalPage portalPage) throws Exception {
		String portalPageName = portalPage.getName();
		String[] portletIds = portalPage.getPortletIds();
		Layout portalPageLayout = getPortalPageLayout(userId, groupId, portalPageName);
		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) portalPageLayout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(userId, "2_columns_i", false);

		int columnNumber = 1;

		for (String portletId : portletIds) {

			if (portletId.endsWith("_INSTANCE_")) {
				portletId = portletId + "ABCD";
			}

			layoutTypePortlet.setPortletIds("column-" + columnNumber, portletId);
			columnNumber++;
		}

		LayoutLocalServiceUtil.updateLayout(portalPageLayout);

		logger.info("Setup page: " + portalPageName);
	}

	protected void setupPortalDemosSite(long companyId, long userId) throws Exception {
		Group site = getSite(companyId, userId, "Portal Demos");
		long groupId = site.getGroupId();
		addAllUsersToSite(companyId, groupId);

		for (PortalPage portalPage : PORTAL_DEMO_PAGES) {
			setupPage(userId, groupId, portalPage);
		}
	}

	protected void setupSites(long companyId, long userId) throws Exception, DocumentException {
		setupBridgeDemosSite(companyId, userId);
		setupPortalDemosSite(companyId, userId);
		setupBridgeTCKSite(companyId, userId);
	}

	protected Layout getPortalPageLayout(long userId, long groupId, String portalPageName) throws Exception {
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
			portalPageLayout = ServiceUtil.addLayout(userId, groupId, privateLayout, parentLayoutId, portalPageName,
					portalPageName, portalPageName, type, hidden, friendlyURL);
		}

		return portalPageLayout;
	}

	protected Group getSite(long companyId, long userId, String name) throws Exception {

		Group site = null;

		try {
			site = GroupLocalServiceUtil.getGroup(companyId, name);
		}
		catch (NoSuchGroupException e) {
			ServiceUtil.addActiveOpenGroup(userId, name);
		}

		return site;
	}

}
