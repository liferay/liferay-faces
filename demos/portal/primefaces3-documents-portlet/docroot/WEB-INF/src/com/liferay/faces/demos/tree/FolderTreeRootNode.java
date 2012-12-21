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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;

import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import org.primefaces.model.TreeNode;


/**
 * @author  Neil Griffin, ported to Primefaces by Jacques Champliaud
 */
public class FolderTreeRootNode extends FolderTreeNode {

	// serialVersionUID
	private static final long serialVersionUID = 1582222718203645622L;

	public FolderTreeRootNode(Group group, TreeNode rootNode) {
		super(getRootDLFolder(group), rootNode, 0);
	}

	private static String getGroupName(Group group) {

		String groupName = group.getName();
		boolean longValue = true;

		try {
			Long.parseLong(groupName);
		}
		catch (NumberFormatException e) {
			longValue = false;
		}

		if (longValue) {
			String friendlyURL = group.getFriendlyURL();

			if (friendlyURL != null) {
				groupName = friendlyURL.replace(StringPool.SLASH, StringPool.BLANK);
			}
		}

		return groupName;
	}

	private static DLFolder getRootDLFolder(Group group) {
		DLFolder rootDlFolder = DLFolderUtil.create(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		rootDlFolder.setGroupId(group.getGroupId());
		rootDlFolder.setName(getGroupName(group));
		return rootDlFolder;
	}

}
