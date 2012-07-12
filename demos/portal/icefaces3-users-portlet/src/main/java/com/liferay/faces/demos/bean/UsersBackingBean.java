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
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.liferay.faces.util.helper.LongHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.portal.context.LiferayFacesContext;

import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "usersBackingBean")
@RequestScoped
public class UsersBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UsersBackingBean.class);

	// Injections
	@ManagedProperty(name = "usersModelBean", value = "#{usersModelBean}")
	private UsersModelBean usersModelBean;

	@ManagedProperty(name = "usersViewBean", value = "#{usersViewBean}")
	private UsersViewBean usersViewBean;

	// Self-Injections
	private LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	// Private Data Members
	private CancelUserActionListener cancelUserActionListener = new CancelUserActionListener();
	private SaveUserActionListener saveUserActionListener = new SaveUserActionListener();
	private SearchActionListener searchActionListener = new SearchActionListener();
	private SelectUserActionListener selectUserActionListener = new SelectUserActionListener();

	public CancelUserActionListener getCancelUserActionListener() {
		return cancelUserActionListener;
	}

	public SaveUserActionListener getSaveUserActionListener() {
		return saveUserActionListener;
	}

	public SearchActionListener getSearchActionListener() {
		return searchActionListener;
	}

	public SelectUserActionListener getSelectUserActionListener() {
		return selectUserActionListener;
	}

	public void setUsersModelBean(UsersModelBean usersModelBean) {

		// Injected via ManagedProperty annotation
		this.usersModelBean = usersModelBean;
	}

	public void setUsersViewBean(UsersViewBean usersViewBean) {

		// Injected via ManagedProperty annotation
		this.usersViewBean = usersViewBean;
	}

	protected class CancelUserActionListener implements ActionListener {

		public void processAction(ActionEvent event) throws AbortProcessingException {

			usersViewBean.setFormRendered(false);
		}

	}

	protected class SaveUserActionListener implements ActionListener {

		public void processAction(ActionEvent event) throws AbortProcessingException {

			User user = usersModelBean.getSelectedUser();

			try {
				UserLocalServiceUtil.updateActive(user.getUserId(), user.isActive());
				UserLocalServiceUtil.updateUser(user);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				liferayFacesContext.addGlobalUnexpectedErrorMessage();
			}

			usersViewBean.setFormRendered(false);
			usersModelBean.forceListReload();
		}

	}

	protected class SearchActionListener implements ActionListener {

		public void processAction(ActionEvent event) throws AbortProcessingException {

			usersModelBean.forceListReload();
		}

	}

	protected class SelectUserActionListener implements ActionListener {

		public void processAction(ActionEvent event) throws AbortProcessingException {

			try {
				long userId = LongHelper.toLong(liferayFacesContext.getExternalContext().getRequestParameterMap().get(
							"userId"), 0L);
				User user = UserLocalServiceUtil.getUser(userId);
				usersModelBean.setSelectedUser(user);
				usersViewBean.setFormRendered(true);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				liferayFacesContext.addGlobalUnexpectedErrorMessage();
			}
		}

	}

}
