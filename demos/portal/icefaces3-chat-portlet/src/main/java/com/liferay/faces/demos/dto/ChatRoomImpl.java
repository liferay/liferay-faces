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
package com.liferay.faces.demos.dto;

import com.liferay.portal.model.User;


/**
 * @author  Neil Griffin
 */
public class ChatRoomImpl implements ChatRoom {

	// Private Data Members
	private ChatMessageList chatMessageList;
	private String id;
	private User user1;
	private User user2;

	public ChatRoomImpl(User user1, User user2) {
		this.chatMessageList = new ChatMessageList();
		this.id = Long.toString(user1.getUserId()) + Long.toString(user2.getUserId());
		this.user1 = user1;
		this.user2 = user2;
	}

	public boolean hasUser(long userId) {
		return ((user1.getUserId() == userId) || (user2.getUserId() == userId));
	}

	public ChatMessageList getChatMessageList() {
		return chatMessageList;
	}

	public String getId() {
		return id;
	}

	public User getUser1() {
		return user1;
	}

	public User getUser2() {
		return user2;
	}
}
