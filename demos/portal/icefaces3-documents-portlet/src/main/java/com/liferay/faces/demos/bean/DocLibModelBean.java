/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.liferay.faces.demos.list.DocumentDataModel;
import com.liferay.faces.demos.tree.FolderTreeModel;
import com.liferay.faces.demos.tree.FolderTreeNode;
import com.liferay.faces.demos.tree.FolderTreeRootNode;
import com.liferay.faces.demos.tree.FolderUserObject;
import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.helper.LongHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "docLibModelBean")
@ViewScoped
public class DocLibModelBean implements Serializable {

	// serialVersionUID Note: This class implements Serializable only to avoid extraneous stacktraces from being thrown
	// by Mojarra. All of the private data members are marked as transient, because it's not possible to de-serialize
	// instances of Liferay's DLFolderImpl class due to classloader prolems.
	private static final long serialVersionUID = 6145332622204857486L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DocLibModelBean.class);

	// Private Data Members
	private transient DocumentDataModel documentDataModel;
	private transient FolderTreeModel folderTreeModel;
	private transient FolderUserObject selectedFolderUserObject;

	public void forceDocumentRequery() {
		setDocumentDataModel(null);
	}

	public void forceTreeRequery() {

		// Remember the folderId associated with the selected folder.
		long previouslySelectedFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		FolderUserObject selected = getSelectedFolderUserObject();

		if (selected != null) {
			previouslySelectedFolderId = selected.getDlFolder().getFolderId();
		}

		logger.debug("previouslySelectedFolderId=[{0}]", previouslySelectedFolderId);

		// Remember all of the folders that were expanded.
		List<Long> expandedFolderIds = new ArrayList<Long>();
		FolderTreeRootNode folderTreeRootNode = (FolderTreeRootNode) getFolderTreeModel().getRoot();
		@SuppressWarnings("unchecked")
		Enumeration<FolderTreeNode> folderTreeNodes = (Enumeration<FolderTreeNode>)
			folderTreeRootNode.depthFirstEnumeration();

		while (folderTreeNodes.hasMoreElements()) {
			FolderTreeNode folderTreeNode = folderTreeNodes.nextElement();
			FolderUserObject folderUserObject = folderTreeNode.getFolderUserObject();

			if (folderUserObject.isExpanded()) {
				long previouslyExpandedFolderId = folderUserObject.getDlFolder().getFolderId();
				expandedFolderIds.add(previouslyExpandedFolderId);
				logger.debug("previouslyExpandedFolderId=[{0}]", previouslyExpandedFolderId);
			}
		}

		// Clear the document list model, but clear-and-requery the folder tree model.
		setDocumentDataModel(null);
		setFolderTreeModel(null);
		getFolderTreeModel();

		if (folderTreeModel != null) {
			FolderTreeNode previouslySelectedFolderTreeNode = folderTreeModel.findFolderTreeNode(
					previouslySelectedFolderId);

			if (previouslySelectedFolderTreeNode != null) {

				// Attempt to re-expand the previously expanded folders.
				for (Long folderId : expandedFolderIds) {
					FolderTreeNode previouslyExpandedFolderTreeNode = folderTreeModel.findFolderTreeNode(folderId);

					if (previouslyExpandedFolderTreeNode != null) {
						logger.debug("Re-expanding folderId=[{0}]", folderId);
						previouslyExpandedFolderTreeNode.getFolderUserObject().setExpanded(true);
					}
				}

				// Attempt to re-select the previously selected folder.
				FolderUserObject folderUserObject = previouslySelectedFolderTreeNode.getFolderUserObject();
				logger.debug("Re-selecting folderId=[{0}]", previouslySelectedFolderId);
				setSelectedUserObject(folderUserObject);
			}
		}
	}

	public DocumentDataModel getDocumentDataModel() {

		if (documentDataModel == null) {

			LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
			DLFolder dlFolder = getSelectedFolderUserObject().getDlFolder();
			int rowsPerPage = liferayFacesContext.getPortletPreferenceAsInt("rowsPerPage",
					SearchContainer.DEFAULT_DELTA);
			ThemeDisplay themeDisplay = liferayFacesContext.getThemeDisplay();
			String portalURL = themeDisplay.getPortalURL();
			String pathContext = themeDisplay.getPathContext();
			PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();

			documentDataModel = new DocumentDataModel(dlFolder, rowsPerPage, portalURL, pathContext, permissionChecker);
		}

		return documentDataModel;
	}

	public void setDocumentDataModel(DocumentDataModel documentDataModel) {
		this.documentDataModel = documentDataModel;
	}

	public FolderTreeModel getFolderTreeModel() {

		if (folderTreeModel == null) {

			try {
				LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
				Group scopeGroup = liferayFacesContext.getThemeDisplay().getScopeGroup();
				logger.debug("Querying folders for scopeGroupId=[" + scopeGroup.getGroupId() + "] scopeGroupName=[" +
					scopeGroup.getName() + "]");

				String scopeGroupIdPreference = (String) liferayFacesContext.getPortletPreference("scopeGroupId", null);

				if (scopeGroupIdPreference != null) {
					long scopeGroupId = LongHelper.toLong(scopeGroupIdPreference, 0L);

					if (scopeGroupId != 0L) {

						try {
							scopeGroup = GroupServiceUtil.getGroup(scopeGroupId);
							logger.debug("Overriding with preference scopeGroupId=[" + scopeGroup.getGroupId() +
								"] scopeGroupName=[" + scopeGroup.getName() + "]");
						}
						catch (NoSuchGroupException e) {
							logger.error(e.getMessage() + " using current group scopeId=[{0}]",
								scopeGroup.getGroupId());
						}
					}
				}

				folderTreeModel = new FolderTreeModel(scopeGroup);

			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return folderTreeModel;
	}

	public void setFolderTreeModel(FolderTreeModel folderTreeModel) {
		this.folderTreeModel = folderTreeModel;
	}

	public FolderUserObject getSelectedFolderUserObject() {

		if (selectedFolderUserObject == null) {
			FolderTreeRootNode folderTreeRootNode = (FolderTreeRootNode) getFolderTreeModel().getRoot();
			selectedFolderUserObject = (FolderUserObject) folderTreeRootNode.getUserObject();
		}

		return selectedFolderUserObject;
	}

	public void setSelectedUserObject(FolderUserObject selectedFolderUserObject) {
		this.selectedFolderUserObject = selectedFolderUserObject;
		forceDocumentRequery();
	}
}
