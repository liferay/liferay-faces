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

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

//import com.icesoft.faces.component.tree.IceUserObject;

import com.liferay.portlet.documentlibrary.model.DLFolder;


/**
 * @author  Neil Griffin, ported to Primefaces by Jacques Champliaud
 */
public class FolderUserObject  implements Serializable {

	private static final long serialVersionUID = 3839613310386352585L;

	private DLFolder dlFolder;

	public FolderUserObject(DefaultMutableTreeNode wrappedTreeNode, DLFolder dlFolder) {
//		super(wrappedTreeNode);
		setLeafIcon("tree_document.gif");
		setBranchContractedIcon("tree_folder_closed.gif");
		setBranchExpandedIcon("tree_folder_open.gif");
		setExpanded(true);
		setText(dlFolder.getName());
		this.dlFolder = dlFolder;
	}

	private void setText(String name) {
		// TODO Auto-generated method stub
		
	}

	void setExpanded(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private void setBranchExpandedIcon(String string) {
		// TODO Auto-generated method stub
		
	}

	private void setBranchContractedIcon(String string) {
		// TODO Auto-generated method stub
		
	}

	private void setLeafIcon(String string) {
		// TODO Auto-generated method stub
		
	}

	public DLFolder getDlFolder() {
		return dlFolder;
	}

	public void setDlFolder(DLFolder dlFolder) {
		this.dlFolder = dlFolder;
	}

	public void setLeaf(boolean b) {
		// TODO Auto-generated method stub
		
	}

}
