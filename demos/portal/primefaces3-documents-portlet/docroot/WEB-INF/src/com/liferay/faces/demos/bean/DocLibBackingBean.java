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

//import org.primefaces.model.TreeNode;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFolder;


/**
 * @author  Neil Griffin, ported to Primefaces by Jacques Champliaud
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

	// Private Data Members
	private String folderName;
	private String folderDescription;

	public void setDocLibModelBean(DocLibModelBean docLibModelBean) {

		// Injected via ManagedProperty annotation
		this.docLibModelBean = docLibModelBean;
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
	
	public boolean isFolderPermittedTo(String action){
		// action = [ VIEW | UPDATE ADD_SHORTCUT | ADD_FOLDER | ADD_SUBFOLDER | DELETE | ADD_DOCUMENT | ADD_DOCUMENT_TYPE | ADD_DOCUMENT_TYPE ... ]
		boolean permitted=false;
		try {
			PermissionChecker permissionChecker = liferayFacesContext.getPermissionChecker();
			DLFolder selectedDLFolder =  docLibModelBean.getSelectedNodeDlFolder();
			long scopeGroupId = selectedDLFolder.getGroupId();
			long folderId = selectedDLFolder.getFolderId();
			permitted = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId, action);
		}	
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return permitted;		
	}

	public void folderView()  {
		Long folderId =  docLibModelBean.getSelectedNodeDlFolder().getFolderId();
		// TODO
	}

	public void folderEdit()  {
		Long folderId =  docLibModelBean.getSelectedNodeDlFolder().getFolderId();
		// TODO
	}

	public void folderAdd()  {
		try {
			DLFolder dlFolder =  docLibModelBean.getSelectedNodeDlFolder();
			long groupId = dlFolder.getGroupId();
			long repositoryId = dlFolder.getRepositoryId();
			boolean mountPoint = dlFolder.getMountPoint();
			long folderId = dlFolder.getFolderId();
			ServiceContext serviceContext = new ServiceContext();
			logger.info("\n----------- \033[44m\033[36mJCH_MESSAGE_PRIMEFACES folderAdd : folderName= "+folderName+" folderId "+ folderId+" selectedNodeId "+ docLibModelBean.getSelectedNodeId()+"\033[0m\n");
			// Temporary: Make the default setting be that community members can view the file. Need to develop a
			// "Viewable By" permissions Facelet composite component UI similar to
			// portal-web/docroot/html/taglib/ui/input_permissions/page.jsp
			serviceContext.setAddGroupPermissions(true);
			DLFolder added_dlFolder = DLFolderServiceUtil.addFolder(groupId, repositoryId, mountPoint, folderId, folderName,folderDescription, serviceContext);
			docLibModelBean.addExpandeNode(docLibModelBean.getSelectedNodeId());
			docLibModelBean.setSelectedNodeId(added_dlFolder.getFolderId()); 
			logger.debug("Added folderName=[{0}] description=[{1}]", folderName, folderDescription);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			liferayFacesContext.addGlobalUnexpectedErrorMessage();
		}
		docLibModelBean.forceTreeRequery();
		docLibModelBean.setRenderedPanel("documentList");
	}

	public void folderDelete()  {
		try {
			DLFolder dlFolder =  docLibModelBean.getSelectedNodeDlFolder();
			long folderId = dlFolder.getFolderId();
			ServiceContext serviceContext = new ServiceContext();
			// Temporary: Make the default setting be that community members can view the file. Need to develop a
			// "Viewable By" permissions Facelet composite component UI similar to
			// portal-web/docroot/html/taglib/ui/input_permissions/page.jsp
			serviceContext.setAddGroupPermissions(true);
			DLFolderServiceUtil.deleteFolder(folderId);
			docLibModelBean.selectParentNodeOf(folderId);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			liferayFacesContext.addGlobalUnexpectedErrorMessage();
		}
		docLibModelBean.forceTreeRequery();
		docLibModelBean.setRenderedPanel("documentList");
	}
}
