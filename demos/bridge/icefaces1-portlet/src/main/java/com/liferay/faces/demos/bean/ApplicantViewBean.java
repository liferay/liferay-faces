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
package com.liferay.faces.demos.bean;

import java.util.EventObject;

import javax.faces.event.ActionEvent;

import com.icesoft.faces.component.inputfile.InputFile;


/**
 * @author  Neil Griffin
 */
public class ApplicantViewBean {

	// JavaBeans Properties for UI
	private boolean commentsRendered;
	private int percentComplete;
	private boolean popupRendered;
	private String uploadedFileId;

	public void fileUploadProgressListener(EventObject eventObject) {
		InputFile inputFile = (InputFile) eventObject.getSource();
		percentComplete = inputFile.getFileInfo().getPercent();
	}

	public void toggleComments(ActionEvent actionEvent) {
		commentsRendered = !commentsRendered;
	}

	public void togglePopup(ActionEvent actionEvent) {
		popupRendered = !popupRendered;
	}

	public void setCommentsRendered(boolean commentsRendered) {
		this.commentsRendered = commentsRendered;
	}

	public boolean isCommentsRendered() {
		return commentsRendered;
	}

	public boolean isPopupRendered() {
		return popupRendered;
	}

	public int getPercentComplete() {
		return percentComplete;
	}

	public void setPercentComplete(int percentComplete) {
		this.percentComplete = percentComplete;
	}

	public void setPopupRendered(boolean popupRendered) {
		this.popupRendered = popupRendered;
	}

	public String getUploadedFileId() {
		return uploadedFileId;
	}

	public void setUploadedFileId(String uploadedFileId) {
		this.uploadedFileId = uploadedFileId;
	}

}
