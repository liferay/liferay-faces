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
package com.liferay.faces.demos.tree;

import java.util.Enumeration;

import javax.swing.tree.DefaultTreeModel;

import com.liferay.portal.model.Group;


/**
 * @author  Neil Griffin
 */
public class FolderTreeModel extends DefaultTreeModel {

	private static final long serialVersionUID = 5153865009246109269L;

	public FolderTreeModel(Group group) {
		super(new FolderTreeRootNode(group));
	}

	public FolderTreeNode findFolderTreeNode(long folderId) {

		FolderTreeNode folderTreeNode = null;
		FolderTreeRootNode folderTreeRootNode = (FolderTreeRootNode) getRoot();
		@SuppressWarnings("unchecked")
		Enumeration<FolderTreeNode> folderTreeNodes = (Enumeration<FolderTreeNode>)
			folderTreeRootNode.depthFirstEnumeration();

		if (folderTreeNodes != null) {

			while (folderTreeNodes.hasMoreElements()) {
				FolderTreeNode curFolderTreeNode = folderTreeNodes.nextElement();
				FolderUserObject curFolderUserObject = (FolderUserObject) curFolderTreeNode.getUserObject();

				if (curFolderUserObject.getDlFolder().getFolderId() == folderId) {
					folderTreeNode = curFolderTreeNode;
				}
			}
		}

		return folderTreeNode;
	}
}
