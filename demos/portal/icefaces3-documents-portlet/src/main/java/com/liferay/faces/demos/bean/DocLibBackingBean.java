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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import com.liferay.faces.util.helper.LongHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.demos.tree.FolderTreeNode;
import com.liferay.faces.demos.tree.FolderUserObject;
import com.liferay.faces.portal.context.LiferayFacesContext;

import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "docLibBackingBean")
@RequestScoped
public class DocLibBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DocLibBackingBean.class);

	// Self-Injections
	private LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	// Injections
	@ManagedProperty(name = "docLibModelBean", value = "#{docLibModelBean}")
	private DocLibModelBean docLibModelBean;
	@ManagedProperty(name = "docLibViewBean", value = "#{docLibViewBean}")
	private DocLibViewBean docLibViewBean;

	// Private Data Members
	private String folderName;
	private String folderDescription;
	private Boolean permittedToAddDocument;
	private Boolean permittedToAddFolder;

	// Action Listeners
	private AddFolderActionListener addFolderActionListener = new AddFolderActionListener();
	private PopDownActionListener popDownActionListener = new PopDownActionListener();
	private PopUpActionListener popUpActionListener = new PopUpActionListener();

	public void treeNodeSelected(ActionEvent actionEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		long folderId = LongHelper.toLong(externalContext.getRequestParameterMap().get("folderId"), 0L);
		FolderTreeNode folderTreeNode = docLibModelBean.getFolderTreeModel().findFolderTreeNode(folderId);
		FolderUserObject folderUserObject = folderTreeNode.getFolderUserObject();
		docLibModelBean.setSelectedUserObject(folderUserObject);
		permittedToAddFolder = null;
		permittedToAddDocument = null;
	}

	public AddFolderActionListener getAddFolderActionListener() {
		return addFolderActionListener;
	}

	public void setDocLibModelBean(DocLibModelBean docLibModelBean) {

		// Injected via ManagedProperty annotation
		this.docLibModelBean = docLibModelBean;
	}

	public void setDocLibViewBean(DocLibViewBean docLibViewBean) {

		// Injected via ManagedProperty annotation.
		this.docLibViewBean = docLibViewBean;
	}

	public String getFolderDescription() {
		return folderDescription;
	}

	public void setFolderDescription(String folderDescription) {
		this.folderDescription = folderDescription;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public PopDownActionListener getPopDownActionListener() {
		return popDownActionListener;
	}

	public PopUpActionListener getPopUpActionListener() {
		return popUpActionListener;
	}

	public boolean isPermittedToAddFolder() {

		if (permittedToAddFolder == null) {

			try {
				PermissionChecker permissionChecker = liferayFacesContext.getPermissionChecker();
				DLFolder selectedDLFolder = docLibModelBean.getSelectedFolderUserObject().getDlFolder();
				long scopeGroupId = selectedDLFolder.getGroupId();
				long folderId = selectedDLFolder.getFolderId();
				permittedToAddFolder = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId,
						ActionKeys.ADD_FOLDER);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return permittedToAddFolder;
	}

	public boolean isPermittedToAddDocument() {

		if (permittedToAddDocument == null) {

			try {
				PermissionChecker permissionChecker = liferayFacesContext.getPermissionChecker();
				DLFolder selectedDLFolder = docLibModelBean.getSelectedFolderUserObject().getDlFolder();
				long scopeGroupId = selectedDLFolder.getGroupId();
				long folderId = selectedDLFolder.getFolderId();
				permittedToAddDocument = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId,
						ActionKeys.ADD_DOCUMENT);
				permittedToAddFolder = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId,
						ActionKeys.ADD_FOLDER);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return permittedToAddDocument;
	}

	protected class AddFolderActionListener implements ActionListener {
		public void processAction(ActionEvent actionEvent) throws AbortProcessingException {

			try {
				FolderUserObject folderUserObject = docLibModelBean.getSelectedFolderUserObject();
				DLFolder dlFolder = folderUserObject.getDlFolder();
				long groupId = dlFolder.getGroupId();
				long repositoryId = dlFolder.getRepositoryId();
				boolean mountPoint = dlFolder.getMountPoint();
				long parentFolderId = dlFolder.getFolderId();
				ServiceContext serviceContext = new ServiceContext();

				// Temporary: Make the default setting be that community members can view the file. Need to develop a
				// "Viewable By" permissions Facelet composite component UI similar to
				// portal-web/docroot/html/taglib/ui/input_permissions/page.jsp
				serviceContext.setAddGroupPermissions(true);
				DLFolderServiceUtil.addFolder(groupId, repositoryId, mountPoint, parentFolderId, folderName,
					folderDescription, serviceContext);
				docLibModelBean.forceTreeRequery();
				logger.debug("Added folderName=[{0}] description=[{1}]", folderName, folderDescription);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				liferayFacesContext.addGlobalUnexpectedErrorMessage();
			}

			docLibViewBean.setPopupRendered(false);
		}
	}

	protected class PopDownActionListener implements ActionListener {

		public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
			docLibViewBean.setPopupRendered(false);
		}
	}

	protected class PopUpActionListener implements ActionListener {

		public void processAction(ActionEvent actionEvent) throws AbortProcessingException {
			docLibViewBean.setPopupRendered(true);
		}

	}

}
