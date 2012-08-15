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

import java.util.Date;

import com.liferay.portal.model.User;


/**
 * This interface defines the contract for a Data Transfer Object (DTO) that represents a chat message.
 *
 * @author  "Neil Griffin"
 */
public interface ChatMessage {

	public Date getDate();

	public void setDate(Date date);

	public String getDirection(long userId);

	public String getText();

	public void setText(String text);

	public User getUser();

	public void setUser(User user);
}
