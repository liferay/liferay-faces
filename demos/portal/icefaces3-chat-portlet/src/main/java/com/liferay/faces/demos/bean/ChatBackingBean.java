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

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.liferay.faces.demos.dto.ChatRoom;
import com.liferay.faces.portal.context.LiferayFacesContext;

import com.liferay.portal.model.User;


/**
 * This class is a backing bean for the iceChat.xhtml Facelet view.
 *
 * @author  "Neil Griffin"
 */

@ManagedBean(name = "chatBackingBean")
@RequestScoped
public class ChatBackingBean {

	// Injections
	@ManagedProperty(name = "icePushModelBean", value = "#{icePushModelBean}")
	private ICEPushModelBean icePushModelBean;

	// Self-Injections
	private LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	// Private Data Members
	private String messageText;

	public void addMessage(ChatRoom chatRoom) {

		if ((messageText != null) && (messageText.trim().length() > 0)) {
			icePushModelBean.addMessageToChatRoom(messageText, chatRoom);
		}

		messageText = null;
	}

	public void chatWithUser(User user) {

		// Don't allow chat with oneself -- only with others.
		if (user.getUserId() != liferayFacesContext.getUserId()) {
			icePushModelBean.addChatRoomWithUser(user);
		}
	}

	public void closeChat(ChatRoom chatRoom) {
		icePushModelBean.removeChatRoom(chatRoom);
	}

	public void setIcePushModelBean(ICEPushModelBean icePushModelBean) {

		// Injected via ManagedProperty annotation
		this.icePushModelBean = icePushModelBean;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
}
