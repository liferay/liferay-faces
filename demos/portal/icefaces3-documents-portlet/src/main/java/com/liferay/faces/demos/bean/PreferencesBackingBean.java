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
package com.liferay.faces.demos.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletResponse;
import javax.portlet.faces.Bridge;
import javax.portlet.faces.BridgeUtil;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.portal.context.LiferayFacesContext;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupServiceUtil;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "preferencesBackingBean")
@RequestScoped
public class PreferencesBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PreferencesBackingBean.class);

	// Self-Injections
	private LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	// Injections
	@ManagedProperty(name = "docLibModelBean", value = "#{docLibModelBean}")
	private DocLibModelBean docLibModelBean;

	// Private Data Members
	private SubmitActionListener submitActionListener = new SubmitActionListener();

	public void setDocLibModelBean(DocLibModelBean docLibModelBean) {

		// Injected via ManagedProperty annotation
		this.docLibModelBean = docLibModelBean;
	}

	public List<Group> getGroups() {

		List<Group> groups = new ArrayList<Group>();

		try {
//          groups = GroupLocalServiceUtil.getGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			User user = liferayFacesContext.getUser();
			groups = user.getGroups();

			List<Organization> organizations = user.getOrganizations();
			groups.addAll(GroupServiceUtil.getOrganizationsGroups(organizations));
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return groups;
	}

	public SubmitActionListener getSubmitActionListener() {
		return submitActionListener;
	}

	protected class SubmitActionListener implements ActionListener {

		public void processAction(ActionEvent event) throws AbortProcessingException {
			PortletPreferences portletPreferences = liferayFacesContext.getPortletPreferences();
			logger.debug("Saving portlet preference scopeGroupId=[{0}]",
				portletPreferences.getValue("scopeGroupId", null));

			try {
				portletPreferences.store();
				liferayFacesContext.addGlobalSuccessInfoMessage();

				PortletResponse portletResponse = liferayFacesContext.getPortletResponse();

				if (BridgeUtil.getPortletRequestPhase() == Bridge.PortletPhase.ACTION_PHASE) {
					ActionResponse actionResponse = (ActionResponse) portletResponse;
					actionResponse.setPortletMode(PortletMode.VIEW);
				}

				docLibModelBean.forceTreeRequery();
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				liferayFacesContext.addGlobalUnexpectedErrorMessage();
			}
		}

	}
}
