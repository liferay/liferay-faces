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
package com.liferay.faces.demos.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.icefaces.ace.event.SelectEvent;

import com.liferay.faces.bridge.model.UploadedFile;
import com.liferay.faces.demos.dto.UploadedFileWrapper;
import com.liferay.faces.demos.util.UploadedFileUtil;
import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * This class serves as a backing bean for the users.xhtml Facelet view. The bean is kept in request scope since it
 * doesn't maintain any model data.
 *
 * @author  Neil Griffin
 * @author  Kyle Stiemann
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
	private String fileUploadAbsolutePath;
	private String uploadedFileId;
	private UploadedFile uploadedFile;

	public void cancel(ActionEvent actionEvent) {
		usersViewBean.setFormRendered(false);
	}

	public void handleFileUpload(FileEntryEvent fileEntryEvent) {

		try {
			FileEntry fileEntry = (FileEntry) fileEntryEvent.getSource();
			FileEntryResults results = fileEntry.getResults();
			FileEntryResults.FileInfo fileInfo = results.getFiles().get(0);
			UploadedFileWrapper uploadedFile = new UploadedFileWrapper(fileInfo);

			if (uploadedFile.getStatus() == UploadedFile.Status.FILE_SAVED) {
				usersModelBean.setUploadedFile(uploadedFile);
			}
			else {
				logger.error("Uploaded file status=[" + uploadedFile.getStatus().toString() + "] " +
					uploadedFile.getMessage());
			}
		}
		catch (Exception e) {
			logger.error(e);
			liferayFacesContext.addGlobalUnexpectedErrorMessage();
		}
	}

	public void save(ActionEvent actionEvent) {

		try {

			// Update the selected user in the Liferay database.
			User user = usersModelBean.getSelectedUser();
			long userId = user.getUserId();
			UserLocalServiceUtil.updateStatus(userId, user.getStatus());
			UserLocalServiceUtil.updateUser(user);

			// If the end-user uploaded a portrait, then update the portrait in the Liferay database. Note that there is
			// a bug in ICEfaces such that if the end-user forgot to click on the "Upload Portrait" button first, then
			// the selected file will not be saved.
			UploadedFile modelUploadedFile = usersModelBean.getUploadedFile();

			if (modelUploadedFile != null) {
				byte[] imageBytes = modelUploadedFile.getBytes();
				UserLocalServiceUtil.updatePortrait(userId, imageBytes);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			liferayFacesContext.addGlobalUnexpectedErrorMessage();
		}

		usersViewBean.setFormRendered(false);
		usersModelBean.forceListReload();
	}

	public void search(ActionEvent actionEvent) {
		usersModelBean.forceListReload();
	}

	public void selectUser(SelectEvent selectEvent) {

		try {
			User selectedUser = (User) selectEvent.getObject();
			usersModelBean.setSelectedUser(selectedUser);
			usersViewBean.setFormRendered(true);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			liferayFacesContext.addGlobalUnexpectedErrorMessage();
		}
	}

	public String getFileUploadAbsolutePath() {

		if (fileUploadAbsolutePath == null) {
			fileUploadAbsolutePath = UploadedFileUtil.getTempDir();
		}

		return fileUploadAbsolutePath;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public String getUploadedFileId() {
		return uploadedFileId;
	}

	public void setUploadedFileId(String uploadedFileId) {
		this.uploadedFileId = uploadedFileId;
	}

	public void setUsersModelBean(UsersModelBean usersModelBean) {

		// Injected via ManagedProperty annotation
		this.usersModelBean = usersModelBean;
	}

	public void setUsersViewBean(UsersViewBean usersViewBean) {

		// Injected via ManagedProperty annotation
		this.usersViewBean = usersViewBean;
	}
}
