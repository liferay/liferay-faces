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
package com.liferay.faces.demos.tree;

import java.io.IOException;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

// added by jch
import com.liferay.faces.demos.tree.PrimefacesFolder;
// end of added by jch

/**
 * @author  Neil Griffin, ported to Primefaces by Jacques Champliaud
 */
public class FolderTreeNode extends DefaultMutableTreeNode {

	// serialVersionUID
	private static final long serialVersionUID = 4155954567325991515L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FolderTreeNode.class);

	public FolderTreeNode(DLFolder dlFolder, TreeNode pere, int level) {
		super();
		long folderId=dlFolder.getFolderId();
		long repositoryId=dlFolder.getRepositoryId();
//		FolderUserObject folderUserObject = new FolderUserObject(this, dlFolder);
//		folderUserObject.setLeaf(false);
//		folderUserObject.setExpanded(expanded);
//		setUserObject(folderUserObject);
//		setAllowsChildren(true);
		try {
			String type = "default";
			TreeNode node = new DefaultTreeNode(type, new PrimefacesFolder(folderId, "level"+level, dlFolder), pere);
			List<DLFolder> childDlFolders = DLFolderServiceUtil.getFolders(dlFolder.getGroupId(),
					dlFolder.getFolderId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			if (childDlFolders != null) {

				for (DLFolder childDlFolder : childDlFolders) {
					FolderTreeNode childFolderTreeNode = new FolderTreeNode(childDlFolder, node, level+1);
					add(childFolderTreeNode);
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public FolderUserObject getFolderUserObject() {
		return (FolderUserObject) getUserObject();
	}
}
