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
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;

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
	@ManagedProperty(name = "chatModelBean", value = "#{chatModelBean}")
	private ChatModelBean chatModelBean;

	// Self-Injections
	private LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	// Private Data Members
	private String messageText;

	public void chatWithUser(User user) {
		chatModelBean.addChatRoomWithUser(user);
	}

	public void messageTextValueChanged(ValueChangeEvent valueChangeEvent) {

		if (liferayFacesContext.getCurrentPhaseId() == PhaseId.PROCESS_VALIDATIONS) {
			valueChangeEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
			valueChangeEvent.getComponent().queueEvent(valueChangeEvent);
		}
		else {
			chatModelBean.addMessageToCurrentChatSession(messageText);
			messageText = null;
		}
	}

	public void setChatModelBean(ChatModelBean chatModelBean) {

		// Injected via ManagedProperty annotation
		this.chatModelBean = chatModelBean;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
}
