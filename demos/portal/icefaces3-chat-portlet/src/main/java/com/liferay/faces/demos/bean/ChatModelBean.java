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
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.CustomScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.icefaces.application.PortableRenderer;
import org.icefaces.application.PushRenderer;

import com.liferay.faces.demos.dto.ChatMessage;
import com.liferay.faces.demos.dto.ChatMessageImpl;
import com.liferay.faces.demos.dto.ChatRoom;
import com.liferay.faces.demos.dto.ChatRoomImpl;
import com.liferay.faces.demos.dto.ChatRoomList;
import com.liferay.faces.demos.dto.ScopedChatRoom;
import com.liferay.faces.demos.dto.ScopedChatRoomImpl;
import com.liferay.faces.demos.list.OnlineUserDataModel;
import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.lang.Observable;
import com.liferay.faces.util.lang.ObservableList;
import com.liferay.faces.util.lang.Observer;

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

	// Private Data Members
	private ScopedChatRoom currentScopedChatRoom;
	private OnlineUserDataModel onlineUsers;
	private PortableRenderer portableRenderer;
	private List<ScopedChatRoom> scopedChatRooms;

	public ChatModelBean() {
		PushRenderer.addCurrentSession(AJAX_PUSH_SESSION_NAME);
		portableRenderer = PushRenderer.getPortableRenderer();
	}

	public void addChatRoomWithUser(User friend) {
		
		LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
		User currentUser = liferayFacesContext.getUser();
		boolean chatRoomExists = false;
		long userId1 = currentUser.getUserId();
		long userId2 = friend.getUserId();
		ChatRoomList chatRoomList = applicationModelBean.getChatRoomList();

		for (ChatRoom chatRoom : chatRoomList) {

			if (chatRoom.hasUser(userId1) && chatRoom.hasUser(userId2)) {
				chatRoomExists = true;
			}
		}

		if (!chatRoomExists) {
			ChatRoom chatRoom = new ChatRoomImpl(currentUser, friend);
			chatRoomList.add(chatRoom);
		}

		forceReload();
		PushRenderer.render(AJAX_PUSH_SESSION_NAME);
	}

	public void addMessage(String messageText) {
		LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
		User currentUser = liferayFacesContext.getUser();
		ChatMessage chatMessage = new ChatMessageImpl(Calendar.getInstance().getTime(), currentUser, messageText);

		if (currentScopedChatRoom != null) {
			currentScopedChatRoom.getChatMessageList().add(chatMessage);
			forceReload();
			PushRenderer.render(AJAX_PUSH_SESSION_NAME);
		}
	}

	@PostConstruct
	public void postConstruct() {
		applicationModelBean.addObserver(this);

		ChatRoomList chatRoomList = applicationModelBean.getChatRoomList();
		chatRoomList.addObserver(this);
	}

	@PreDestroy
	public void preDestroy() {
		applicationModelBean.removeObserver(this);

		ChatRoomList chatRoomList = applicationModelBean.getChatRoomList();
		chatRoomList.removeObserver(this);
	}

	public void receiveNotification(Observable observable, Object... args) {

		if (currentScopedChatRoom != null) {

			if (observable instanceof ChatRoomList) {

				if (args[0] == ObservableList.Action.REMOVE) {
					ChatRoom removedChatRoom = (ChatRoom) args[1];

					if (removedChatRoom.getId() == currentScopedChatRoom.getId()) {
						currentScopedChatRoom = null;
					}
				}
			}
		}

		forceReload();
		portableRenderer.render(AJAX_PUSH_SESSION_NAME);
	}

	public void removeScopedChatRoom(ScopedChatRoom scopedChatRoom) {

		ChatRoom chatRoomToDelete = null;
		ChatRoomList chatRoomList = applicationModelBean.getChatRoomList();

		for (ChatRoom chatRoom : chatRoomList) {

			if (chatRoom.getId().equals(scopedChatRoom.getId())) {
				chatRoomToDelete = chatRoom;

				break;
			}
		}

		chatRoomList.remove(chatRoomToDelete);

		if (currentScopedChatRoom == scopedChatRoom) {
			currentScopedChatRoom = null;
		}

		forceReload();

		PushRenderer.render(AJAX_PUSH_SESSION_NAME);
	}

	protected void forceReload() {
		onlineUsers = null;
		scopedChatRooms = null;
	}

	public void setApplicationModelBean(ApplicationModelBean applicationModelBean) {

		// Injected via ManagedProperty annotation
		this.applicationModelBean = applicationModelBean;
	}

	public ScopedChatRoom getCurrentScopedChatRoom() {
		return currentScopedChatRoom;
	}

	public void setCurrentScopedChatRoom(ScopedChatRoom scopedChatRoom) {

		if (this.currentScopedChatRoom != null) {
			this.currentScopedChatRoom.getChatMessageList().removeObserver(this);
		}

		scopedChatRoom.getChatMessageList().addObserver(this);
		this.currentScopedChatRoom = scopedChatRoom;
	}

	public OnlineUserDataModel getOnlineUsers() {

		if (onlineUsers == null) {
			LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
			int rowsPerPage = SearchContainer.DEFAULT_DELTA;
			Set<Long> onlineUserSet = applicationModelBean.getOnlineUserSetDeepCopy();
			long currentUserId = liferayFacesContext.getUserId();

			if (!onlineUserSet.contains(currentUserId)) {
				onlineUserSet.add(currentUserId);
			}

			onlineUsers = new OnlineUserDataModel(liferayFacesContext.getCompanyId(), currentUserId, rowsPerPage,
					onlineUserSet);
		}

		return onlineUsers;
	}

	public List<ScopedChatRoom> getScopedChatRooms() {

		if (scopedChatRooms == null) {
			
			LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
			long currentUserId = liferayFacesContext.getUserId();
			scopedChatRooms = new ArrayList<ScopedChatRoom>();

			ChatRoomList chatRoomList = applicationModelBean.getChatRoomList();

			for (ChatRoom chatRoom : chatRoomList) {

				if (chatRoom.hasUser(currentUserId)) {
					scopedChatRooms.add(new ScopedChatRoomImpl(chatRoom, currentUserId));
				}
			}
		}

		return scopedChatRooms;
	}

}
