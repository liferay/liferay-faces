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

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.portal.context.LiferayFacesContext;


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
	@ManagedProperty(name = "chatModelBean", value = "#{chatModelBean}")
	private ChatModelBean chatModelBean;

	// Private Data Members
	private SubmitActionListener submitActionListener = new SubmitActionListener();

	public void setChatModelBean(ChatModelBean chatModelBean) {

		// Injected via ManagedProperty annotation
		this.chatModelBean = chatModelBean;
	}

	public SubmitActionListener getSubmitActionListener() {
		return submitActionListener;
	}

	protected class SubmitActionListener implements ActionListener {

		public void processAction(ActionEvent event) throws AbortProcessingException {
			PortletPreferences portletPreferences = liferayFacesContext.getPortletPreferences();

			try {
				portletPreferences.store();
				liferayFacesContext.addGlobalSuccessInfoMessage();

				PortletResponse portletResponse = liferayFacesContext.getPortletResponse();

				if (BridgeUtil.getPortletRequestPhase() == Bridge.PortletPhase.ACTION_PHASE) {
					ActionResponse actionResponse = (ActionResponse) portletResponse;
					actionResponse.setPortletMode(PortletMode.VIEW);
				}

				chatModelBean.forceRequery();
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				liferayFacesContext.addGlobalUnexpectedErrorMessage();
			}
		}

	}
}
