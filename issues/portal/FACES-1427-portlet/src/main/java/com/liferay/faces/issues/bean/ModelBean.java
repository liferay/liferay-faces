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
package com.liferay.faces.issues.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.icefaces.ace.component.fileentry.FileEntryResults.FileInfo;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@ViewScoped
public class ModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 8535723765072680249L;

	// Private Data Members
	private String comments1;
	private String comments2;
	private List<FileInfo> uploadedFiles;

	public ModelBean() {
		clearProperties();
	}

	public void clearProperties() {
		comments1 = "comments1-initial-value";
		comments2 = "comments2-initial-value";
		uploadedFiles = new ArrayList<FileInfo>();
	}

	public String getComments1() {
		return comments1;
	}

	public void setComments1(String comments1) {
		this.comments1 = comments1;
	}

	public String getComments2() {
		return comments2;
	}

	public void setComments2(String comments2) {
		this.comments2 = comments2;
	}

	public List<FileInfo> getUploadedFiles() {
		return uploadedFiles;
	}
}
