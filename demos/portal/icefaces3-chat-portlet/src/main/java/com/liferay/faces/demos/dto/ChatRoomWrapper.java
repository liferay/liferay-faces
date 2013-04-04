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

import javax.faces.FacesWrapper;

import com.liferay.portal.model.User;


/**
 * @author  Neil Griffin
 */
public abstract class ChatRoomWrapper implements ChatRoom, FacesWrapper<ChatRoom> {

	public boolean hasUser(long userId) {
		return getWrapped().hasUser(userId);
	}

	public ChatMessageList getChatMessageList() {
		return getWrapped().getChatMessageList();
	}

	public String getId() {
		return getWrapped().getId();
	}

	public User getUser1() {
		return getWrapped().getUser1();
	}

	public User getUser2() {
		return getWrapped().getUser2();
	}

	public abstract ChatRoom getWrapped();

}
