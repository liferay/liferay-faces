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
import java.util.Date;
import java.util.List;

import com.liferay.portal.model.User;


/**
 * This class is a Data Transfer Object (DTO) that represents a chat room.
 *
 * @author  "Neil Griffin"
 */
public class ChatRoom {

	private String name;
	private List<ChatMessage> chatMessages;
	private List<User> participants;

	public ChatRoom(String name) {
		this.name = name;
		chatMessages = new ArrayList<ChatMessage>();
		participants = new ArrayList<User>();
	}

	public void addMessage(User user, String text) {
		Date now = new Date();
		ChatMessage chatMessage = new ChatMessage(now, user, text);
		chatMessages.add(chatMessage);
	}

	public void addUser(User participant) {
		participants.add(participant);
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

	public void setChatMessages(List<ChatMessage> chatMessages) {
		this.chatMessages = chatMessages;
	}

	public String getName() {
		return name;
	}
}
