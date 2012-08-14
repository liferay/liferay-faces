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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.liferay.faces.demos.dto.ChatRoom;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;


/**
 * This is a JSF managed-bean that is ApplicationScoped in order to keep track of online users and chat rooms. It's set
 * to "eager" so that it will be instantiated when the portlet is registered with Liferay, and so be ready to receive
 * messages.
 *
 * @author  "Neil Griffin"
 */
@ManagedBean(name = "applicationModelBean", eager = true)
@ApplicationScoped
public class ApplicationModelBean extends Observable implements MessageListener {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ApplicationModelBean.class);

	// Private Data Members
	private HashSet<Long> onlineUserSet = new HashSet<Long>();
	private List<ChatRoom> chatRooms = Collections.synchronizedList(new ArrayList<ChatRoom>());

	@Override
	public void finalize() {
		MessageBusUtil.unregisterMessageListener(DestinationNames.LIVE_USERS, this);
	}

	@PostConstruct
	public void postConstruct() {
		logger.info(
			"Registering for LIVE_USERS message -- if not received, then make sure live.users.enabled=true in portal-ext.properties");
		MessageBusUtil.registerMessageListener(DestinationNames.LIVE_USERS, this);
	}

	public void receive(Message message) {

		if (message.getDestinationName().equals(DestinationNames.LIVE_USERS)) {

			try {
				JSONObject jsonObject = (JSONObject) JSONFactoryUtil.createJSONObject((String) message.getPayload());

				Long userId = jsonObject.getLong("userId");

				if (userId != null) {
					String command = jsonObject.getString("command");

					if (command != null) {

						if (command.equals("signIn")) {

							if (!onlineUserSet.contains(userId)) {
								onlineUserSet.add(userId);
								setChanged();
								notifyObservers();
							}
						}

						if (command.equals("signOut")) {

							if (onlineUserSet.contains(userId)) {
								onlineUserSet.remove(userId);
								setChanged();
								notifyObservers();
							}
						}
					}
				}
			}
			catch (JSONException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public List<ChatRoom> getChatRooms() {
		return chatRooms;
	}

	/**
	 * Returns a thread-safe deep-copy of the online user set.
	 */
	public Set<Long> getOnlineUserSetDeepCopy() {

		Set<Long> onlineUserSetCopy = null;

		synchronized (onlineUserSet) {
			onlineUserSetCopy = new HashSet<Long>(onlineUserSet.size());

			for (Long userId : onlineUserSet) {
				onlineUserSetCopy.add(new Long(userId.longValue()));
			}
		}

		return onlineUserSetCopy;
	}

}
