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
package com.liferay.faces.demos.dto;

import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.model.User;


/**
 * @author  Neil Griffin
 */
public class ChatRoomImpl implements ChatRoom {
	private List<ChatMessage> chatMessages;
	private List<User> participants;

	public ChatRoomImpl() {
		this.chatMessages = new ArrayList<ChatMessage>();
		this.participants = new ArrayList<User>();
	}

	public User findUser(long userId) {

		for (User user : participants) {

			if (user.getUserId() == userId) {
				return user;
			}
		}

		return null;
	}

	public boolean hasUser(long userId) {

		if (findUser(userId) != null) {
			return true;
		}

		return false;
	}

	public List<ChatMessage> getChatMessages() {
		return chatMessages;
	}

	public List<User> getParticipants() {
		return participants;
	}
}
