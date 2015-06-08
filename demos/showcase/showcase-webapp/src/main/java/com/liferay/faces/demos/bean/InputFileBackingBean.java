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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UICommand;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.liferay.faces.alloy.component.inputfile.FileUploadEvent;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
@RequestScoped
@ManagedBean
public class InputFileBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputFileBackingBean.class);

	@ManagedProperty(value = "#{inputFileModelBean}")
	private InputFileModelBean inputFileModelBean;

	public void deleteUploadedFile(ActionEvent actionEvent) {

		UICommand uiCommand = (UICommand) actionEvent.getComponent();
		String fileId = (String) uiCommand.getValue();

		try {
			List<UploadedFile> uploadedFiles = inputFileModelBean.getUploadedFiles();

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

	public void handleFileUpload(FileUploadEvent fileUploadEvent) {

		List<UploadedFile> uploadedFiles = inputFileModelBean.getUploadedFiles();
		UploadedFile uploadedFile = fileUploadEvent.getUploadedFile();

		if (uploadedFile.getStatus() == UploadedFile.Status.FILE_SAVED) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage facesMessage = new FacesMessage("Received fileUploadEvent for file named '" +
					uploadedFile.getName() + "' in the " + fileUploadEvent.getPhaseId().getName() + " phase.");
			facesContext.addMessage(null, facesMessage);
			uploadedFiles.add(uploadedFile);
			logger.debug("Received fileName=[{0}] absolutePath=[{1}]", uploadedFile.getName(),
				uploadedFile.getAbsolutePath());
		}
		else {
			logger.error("Failed to receive uploaded file due to error status=[{0}] message=[{1}]",
				uploadedFile.getStatus(), uploadedFile.getMessage());
		}
	}

	public void setInputFileModelBean(InputFileModelBean inputFileModelBean) {

		// Injected via @ManagedProperty annotation
		this.inputFileModelBean = inputFileModelBean;
	}
}
