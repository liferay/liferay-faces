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

import java.util.Date;

import com.liferay.portal.model.User;


/**
 * @author  Neil Griffin
 */
public class ChatMessageImpl implements ChatMessage {

	// Private Constants
	private static final String OUTGOING = "outgoing";
	private static final String INCOMING = "incoming";

	// Private Data Members
	private Date date;
	private User user;
	private String text;

	public ChatMessageImpl(Date date, User user, String text) {
		this.date = date;
		this.user = user;
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDirection(long userId) {

		if (userId == getUser().getUserId()) {
			return OUTGOING;
		}
		else {
			return INCOMING;
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
