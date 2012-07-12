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
import javax.faces.bean.ViewScoped;

import com.icesoft.faces.async.render.SessionRenderer;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.demos.list.UserLazyDataModel;
import com.liferay.faces.portal.context.LiferayFacesContext;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;


/**
 * This class is a JSF model managed-bean for managing datamodels of online users and status. The bean is ViewScoped so
 * that the bean is tied to a specific user session. In the constructor, the bean registers itself to listen for
 * LIVE_USERS messages from Liferay Portal. When a user signs-in or signs-out of the portal, this bean will use ICEfaces
 * Ajax Push to update the DOM in the participating browsers.
 *
 * @author  "Neil Griffin"
 */

@ManagedBean(name = "chatModelBean")
@ViewScoped
public class ChatModelBean implements MessageListener {

	// Private Constants
	private static final String AJAX_PUSH_SESSION_NAME = "chatSession";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ChatModelBean.class);

	// Injections
	@ManagedProperty(name = "applicationModelBean", value = "#{applicationModelBean}")
	private ApplicationModelBean applicationModelBean;

	// Self-Injections
	private final LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	// Private Data Members
	private UserLazyDataModel userDataModel;

	public ChatModelBean() {
		MessageBusUtil.registerMessageListener(DestinationNames.LIVE_USERS, this);
		SessionRenderer.addCurrentSession(AJAX_PUSH_SESSION_NAME);
	}

	public void forceRequery() {
		userDataModel = null;
	}

	public void receive(Message message) {

		if (message.getDestinationName().equals(DestinationNames.LIVE_USERS)) {

			try {
				JSONObject jsonObject = (JSONObject) JSONFactoryUtil.createJSONObject((String) message.getPayload());
				Long userId = jsonObject.getLong("userId");

				if (userId != null) {

					Boolean onlineStatus = getDataModel().getOnlineStatus().get(userId);

					if (onlineStatus != null) {

						String command = jsonObject.getString("command");

						if (command != null) {

							if (command.equals("signIn")) {
								getDataModel().getOnlineStatus().put(userId, Boolean.TRUE);
							}

							if (command.equals("signOut")) {
								getDataModel().getOnlineStatus().put(userId, Boolean.FALSE);
							}
						}

						SessionRenderer.render(AJAX_PUSH_SESSION_NAME);
					}
				}
			}
			catch (JSONException e) {
				logger.error(e.getMessage(), e);
			}

		}
	}

	public void setApplicationModelBean(ApplicationModelBean applicationModelBean) {

		// Injected via ManagedProperty annotation
		this.applicationModelBean = applicationModelBean;
	}

	public UserLazyDataModel getDataModel() {

		if (userDataModel == null) {
			int rowsPerPage = liferayFacesContext.getPortletPreferenceAsInt("userRowsPerPage",
					SearchContainer.DEFAULT_DELTA);
			userDataModel = new UserLazyDataModel(liferayFacesContext.getCompanyId(), liferayFacesContext.getUserId(),
					rowsPerPage, applicationModelBean.getOnlineUserSetDeepCopy());
		}

		return userDataModel;
	}

}
