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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.icefaces.application.PortableRenderer;
import org.icefaces.application.PushRenderer;

import com.liferay.faces.demos.dto.ChatMessage;
import com.liferay.faces.demos.dto.ChatRoom;
import com.liferay.faces.demos.dto.ChatRoomImpl;
import com.liferay.faces.demos.dto.ChatSession;
import com.liferay.faces.demos.list.UserLazyDataModel;
import com.liferay.faces.portal.context.LiferayFacesContext;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.model.User;


/**
 * This class is a JSF model managed-bean for managing datamodels of online users and status. When a user signs-in or
 * signs-out of the portal, this bean will use ICEfaces Ajax Push to update the DOM in the participating browsers.
 *
 * @author  "Neil Griffin"
 */

@ManagedBean(name = "chatModelBean")
@CustomScoped(value = "#{window}")
public class ChatModelBean implements Observer {

	// Private Constants
	private static final String AJAX_PUSH_SESSION_NAME = "chatSession";

	// Injections
	@ManagedProperty(name = "applicationModelBean", value = "#{applicationModelBean}")
	private ApplicationModelBean applicationModelBean;

	// Self-Injections
	private final LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	// Private Data Members
	private PortableRenderer portableRenderer;
	private UserLazyDataModel userDataModel;

	// Private Data Members
	private ChatSession currentChatSession;

	public ChatModelBean() {
		PushRenderer.addCurrentSession(AJAX_PUSH_SESSION_NAME);
		portableRenderer = PushRenderer.getPortableRenderer();
	}

	public void addChatRoomWithUser(User friend) {

		User currentUser = liferayFacesContext.getUser();
		boolean chatRoomExists = false;
		long userId1 = currentUser.getUserId();
		long userId2 = friend.getUserId();
		List<ChatRoom> chatRooms = applicationModelBean.getChatRooms();

		for (ChatRoom chatRoom : chatRooms) {

			if (chatRoom.hasUser(userId1) && chatRoom.hasUser(userId2)) {
				chatRoomExists = true;
			}
		}

		if (!chatRoomExists) {
			ChatRoom chatRoom = new ChatRoomImpl();
			List<User> participants = chatRoom.getParticipants();
			participants.add(currentUser);
			participants.add(friend);

			synchronized (applicationModelBean) {
				chatRooms.add(chatRoom);
			}

			PushRenderer.render(AJAX_PUSH_SESSION_NAME);
		}
	}

	public void addMessageToCurrentChatSession(String messageText) {
		User currentUser = liferayFacesContext.getUser();
		ChatMessage chatMessage = new ChatMessage(Calendar.getInstance().getTime(), currentUser, messageText);
		currentChatSession.getChatMessages().add(chatMessage);
		PushRenderer.render(AJAX_PUSH_SESSION_NAME);
	}

	public void forceRequery() {
		userDataModel = null;
	}

	@PostConstruct
	public void postConstruct() {
		applicationModelBean.addObserver(this);
	}

	@PreDestroy
	public void preDestroy() {
		applicationModelBean.deleteObserver(this);
	}

	public void update(Observable observable, Object arg1) {
		userDataModel = null;
		portableRenderer.render(AJAX_PUSH_SESSION_NAME);
	}

	public void setApplicationModelBean(ApplicationModelBean applicationModelBean) {

		// Injected via ManagedProperty annotation
		this.applicationModelBean = applicationModelBean;
	}

	public ChatSession getCurrentChatSession() {
		return currentChatSession;
	}

	public void setCurrentChatSession(ChatSession chatSession) {
		this.currentChatSession = chatSession;
	}

	public UserLazyDataModel getDataModel() {

		if (userDataModel == null) {
			int rowsPerPage = SearchContainer.DEFAULT_DELTA;
			Set<Long> onlineUserSet = applicationModelBean.getOnlineUserSetDeepCopy();
			long currentUserId = liferayFacesContext.getUserId();

			if (!onlineUserSet.contains(currentUserId)) {
				onlineUserSet.add(currentUserId);
			}

			userDataModel = new UserLazyDataModel(liferayFacesContext.getCompanyId(), currentUserId, rowsPerPage,
					onlineUserSet);
		}

		return userDataModel;
	}

	public List<ChatSession> getMyChatSessions() {

		long currentUserId = liferayFacesContext.getUserId();
		List<ChatSession> myChatSessions = new ArrayList<ChatSession>();
		List<ChatRoom> chatRooms = applicationModelBean.getChatRooms();

		for (ChatRoom chatRoom : chatRooms) {

			if (chatRoom.hasUser(currentUserId)) {
				myChatSessions.add(new ChatSession(chatRoom, currentUserId));
			}
		}

		return myChatSessions;
	}

}
