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
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.icesoft.faces.async.render.SessionRenderer;

import com.liferay.faces.demos.dto.ChatRoom;

import com.liferay.portal.model.User;


/**
 * This class is a JSF model managed-bean for managing chat room participation. The bean is ViewScopeded so that the
 * bean is associated to a specific user session.
 *
 * @author  "Neil Griffin"
 */
@ManagedBean(name = "icePushModelBean")
@ViewScoped
public class ICEPushModelBean {

	// Private Constants
	private static final String AJAX_PUSH_GROUP_NAME = "chatRoomsModel";

	// Injections
	@ManagedProperty(name = "applicationModelBean", value = "#{applicationModelBean}")
	private ApplicationModelBean applicationModelBean;
	@ManagedProperty(name = "user", value = "#{liferay.user}")
	private User user;

	public ICEPushModelBean() {
		SessionRenderer.addCurrentSession(AJAX_PUSH_GROUP_NAME);
	}

	public void addChatRoomWithUser(User friend) {
		String chatRoomName = user.getFirstName() + " / " + friend.getFirstName();

		if (!applicationModelBean.getChatRooms().containsKey(chatRoomName)) {
			ChatRoom chatRoom = new ChatRoom(chatRoomName);
			chatRoom.addUser(user);
			chatRoom.addUser(friend);

			synchronized (applicationModelBean) {
				applicationModelBean.getChatRooms().put(chatRoomName, chatRoom);
			}

			SessionRenderer.render(AJAX_PUSH_GROUP_NAME);
		}
	}

	public void addMessageToChatRoom(String messageText, ChatRoom chatRoom) {
		chatRoom.addMessage(user, messageText);
		SessionRenderer.render(AJAX_PUSH_GROUP_NAME);
	}

	public void removeChatRoom(ChatRoom chatRoom) {
		applicationModelBean.getChatRooms().remove(chatRoom.getName());
		SessionRenderer.render(AJAX_PUSH_GROUP_NAME);
	}

	public void setApplicationModelBean(ApplicationModelBean applicationModelBean) {

		// Injected via ManagedProperty annotation
		this.applicationModelBean = applicationModelBean;
	}

	public List<ChatRoom> getRoomsWithUser() {

		List<ChatRoom> rooms = new ArrayList<ChatRoom>();
		Set<Entry<String, ChatRoom>> chatRoomSet = applicationModelBean.getChatRooms().entrySet();
		Iterator<Entry<String, ChatRoom>> iterator = chatRoomSet.iterator();

		while (iterator.hasNext()) {
			ChatRoom chatRoom = iterator.next().getValue();

			if (chatRoom.hasUser(user.getUserId())) {
				rooms.add(chatRoom);
			}
		}

		return rooms;
	}

	public void setUser(User user) {

		// Injected via ManagedProperty annotation
		this.user = user;
	}

}
