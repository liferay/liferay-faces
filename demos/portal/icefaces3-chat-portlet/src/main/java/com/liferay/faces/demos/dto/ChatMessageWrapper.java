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

import java.util.Date;

import javax.faces.FacesWrapper;

import com.liferay.portal.model.User;


/**
 * @author  Neil Griffin
 */
public abstract class ChatMessageWrapper implements ChatMessage, FacesWrapper<ChatMessage> {

	public Date getDate() {
		return getWrapped().getDate();
	}

	public void setDate(Date date) {
		getWrapped().setDate(date);
	}

	public String getText() {
		return getWrapped().getText();
	}

	public void setText(String text) {
		getWrapped().setText(text);
	}

	public User getUser() {
		return getWrapped().getUser();
	}

	public void setUser(User user) {
		getWrapped().setUser(user);
	}

	public abstract ChatMessage getWrapped();

}
