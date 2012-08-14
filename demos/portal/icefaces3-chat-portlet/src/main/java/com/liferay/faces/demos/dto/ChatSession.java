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

import java.util.List;

import com.liferay.portal.model.User;


/**
 * @author  Neil Griffin
 */
public class ChatSession extends ChatRoomWrapper {

	// Private Data Members
	private ChatRoom wrappedChatRoom;
	private String friendFullName;
	private long friendUserId;

	public ChatSession(ChatRoom chatRoom, long excludedUserId) {
		this.wrappedChatRoom = chatRoom;

		List<User> participants = chatRoom.getParticipants();

		for (User participant : participants) {

			if (participant.getUserId() != excludedUserId) {
				this.friendFullName = participant.getFullName();
				this.friendUserId = participant.getUserId();

				break;
			}
		}

	}

	public String getFriendFullName() {
		return friendFullName;
	}

	public long getFriendUserId() {
		return friendUserId;
	}

	@Override
	public ChatRoom getWrapped() {
		return wrappedChatRoom;
	}
}
