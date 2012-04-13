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

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;


/**
 * @author  Neil Griffin
 */
public class FolderTreeNode extends DefaultMutableTreeNode {

	// serialVersionUID
	private static final long serialVersionUID = 4155954567325991515L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FolderTreeNode.class);

	public FolderTreeNode(DLFolder dlFolder, boolean expanded) {
		super();

		FolderUserObject folderUserObject = new FolderUserObject(this, dlFolder);
		folderUserObject.setLeaf(false);
		folderUserObject.setExpanded(expanded);
		setUserObject(folderUserObject);
		setAllowsChildren(true);

		try {
			List<DLFolder> childDlFolders = DLFolderServiceUtil.getFolders(dlFolder.getGroupId(),
					dlFolder.getFolderId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			if (childDlFolders != null) {

				for (DLFolder childDlFolder : childDlFolders) {
					FolderTreeNode childFolderTreeNode = new FolderTreeNode(childDlFolder, false);
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
