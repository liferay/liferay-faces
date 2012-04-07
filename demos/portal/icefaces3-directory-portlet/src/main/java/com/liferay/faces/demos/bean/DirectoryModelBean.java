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

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;

import com.liferay.faces.demos.list.SearchCriteria;
import com.liferay.faces.demos.list.UserLazyDataModel;
import com.liferay.faces.portal.context.LiferayFacesContext;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.model.User;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "directoryModelBean")
@ViewScoped
public class DirectoryModelBean implements Serializable {

	// serialVersionUID Note: This class implements Serializable only to avoid extraneous stacktraces from being thrown
	// by Mojarra. All of the private data members are marked as transient, because it's not possible to de-serialize
	// instances of Liferay's User class due to classloader prolems.
	private static final long serialVersionUID = 7763293248647832200L;

	// Self-Injections
	private LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	// Private Data Members
	private transient DataModel<User> userDataModel;
	private transient SearchCriteria searchCriteria;
	private transient User selectedUser;

	public void forceListReload() {
		userDataModel = null;
	}

	public DataModel<User> getDataModel() {

		if (userDataModel == null) {
			int rowsPerPage = liferayFacesContext.getPortletPreferenceAsInt("rowsPerPage",
					SearchContainer.DEFAULT_DELTA);
			userDataModel = new UserLazyDataModel(liferayFacesContext.getCompanyId(), rowsPerPage, searchCriteria);
		}

		return userDataModel;
	}

	public SearchCriteria getSearchCriteria() {

		if (searchCriteria == null) {
			searchCriteria = new SearchCriteria();
		}

		return searchCriteria;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
}
