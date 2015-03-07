/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Part;

import com.liferay.faces.demos.dto.UploadedFilePart;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
@RequestScoped
@ManagedBean
public class HtmlInputFileBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(HtmlInputFileBackingBean.class);

	// Injections
	@ManagedProperty(value = "#{htmlInputFileModelBean}")
	private HtmlInputFileModelBean htmlInputFileModelBean;

	// Private Data Members
	private Part uploadedPart;

	public void deleteUploadedFile(ActionEvent actionEvent) {

		UICommand uiCommand = (UICommand) actionEvent.getComponent();
		String fileId = (String) uiCommand.getValue();

		try {
			List<UploadedFile> uploadedFiles = htmlInputFileModelBean.getUploadedFiles();

			UploadedFile uploadedFileToDelete = null;

			for (UploadedFile uploadedFile : uploadedFiles) {

				if (uploadedFile.getId().equals(fileId)) {
					uploadedFileToDelete = uploadedFile;

					break;
				}
			}

			if (uploadedFileToDelete != null) {
				uploadedFileToDelete.delete();
				uploadedFiles.remove(uploadedFileToDelete);
				logger.debug("Deleted file=[{0}]", uploadedFileToDelete.getName());
			}
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	public void setHtmlInputFileModelBean(HtmlInputFileModelBean htmlInputFileModelBean) {

		// Injected via @ManagedProperty annotation
		this.htmlInputFileModelBean = htmlInputFileModelBean;
	}

	public Part getUploadedPart() {
		return uploadedPart;
	}

	public void setUploadedPart(Part uploadedPart) {
		this.uploadedPart = uploadedPart;

		String id = Long.toString(((long) hashCode()) + System.currentTimeMillis());
		UploadedFile uploadedFile = new UploadedFilePart(uploadedPart, id, UploadedFile.Status.FILE_SAVED);
		htmlInputFileModelBean.getUploadedFiles().add(uploadedFile);
	}
}
