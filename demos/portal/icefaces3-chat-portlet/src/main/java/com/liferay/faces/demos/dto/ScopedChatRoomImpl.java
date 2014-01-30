/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

import com.liferay.faces.util.lang.StringPool;

import com.liferay.portal.model.User;


/**
 * @author  Neil Griffin
 */
public class ScopedChatRoomImpl extends ChatRoomWrapper implements ScopedChatRoom {

	// Private Constants
	private static final String UNREAD = "unread";
	private static final String WAITING = "waiting";

	// Private Data Members
	private ChatRoom wrappedChatRoom;
	private String friendFullName;
	private long friendUserId;
	private String friendWaiting;
	private Integer totalUnread;
	private String unread;

	public ScopedChatRoomImpl(ChatRoom chatRoom, long currentUserId) {

		this.wrappedChatRoom = chatRoom;

		User user1 = chatRoom.getUser1();
		User user2 = chatRoom.getUser2();

		if (user1.getUserId() == currentUserId) {
			this.friendFullName = user2.getFullName();
			this.friendUserId = user2.getUserId();
		}
		else {
			this.friendFullName = user1.getFullName();
			this.friendUserId = user1.getUserId();
		}
	}

	public String getFriendFullName() {
		return friendFullName;
	}

	public long getFriendUserId() {
		return friendUserId;
	}

	public String getFriendWaiting() {

		if (friendWaiting == null) {

			if (getTotalUnread() > 0) {
				friendWaiting = WAITING;
			}
			else {
				friendWaiting = StringPool.BLANK;
			}
		}

		return friendWaiting;
	}

	public int getTotalUnread() {

		if (totalUnread == null) {
			int total = 0;
			ChatMessageList chatMessageList = wrappedChatRoom.getChatMessageList();
			int size = chatMessageList.size();

			if (size > 0) {
				int lastIndex = (size - 1);

				for (int i = lastIndex; i >= 0; i--) {

					if (chatMessageList.get(i).getUser().getUserId() == friendUserId) {
						total++;
					}
					else {
						break;
					}
				}
			}

			totalUnread = new Integer(total);
		}

		return totalUnread;
	}

	public String getUnread() {

		if (unread == null) {

			if (getTotalUnread() > 0) {
				unread = UNREAD;
			}
			else {
				unread = StringPool.BLANK;
			}
		}

		return unread;
	}

	@Override
	public ChatRoom getWrapped() {
		return wrappedChatRoom;
	}
}
