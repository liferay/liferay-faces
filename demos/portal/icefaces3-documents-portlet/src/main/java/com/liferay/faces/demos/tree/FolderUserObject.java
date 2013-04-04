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

import javax.swing.tree.DefaultMutableTreeNode;

import com.icesoft.faces.component.tree.IceUserObject;

import com.liferay.portlet.documentlibrary.model.DLFolder;


/**
 * @author  Neil Griffin
 */
public class FolderUserObject extends IceUserObject {

	private static final long serialVersionUID = 3839613310386352585L;

	private DLFolder dlFolder;

	public FolderUserObject(DefaultMutableTreeNode wrappedTreeNode, DLFolder dlFolder) {
		super(wrappedTreeNode);
		setLeafIcon("tree_document.gif");
		setBranchContractedIcon("tree_folder_closed.gif");
		setBranchExpandedIcon("tree_folder_open.gif");
		setExpanded(true);
		setText(dlFolder.getName());
		this.dlFolder = dlFolder;
	}

	public DLFolder getDlFolder() {
		return dlFolder;
	}

	public void setDlFolder(DLFolder dlFolder) {
		this.dlFolder = dlFolder;
	}

}
