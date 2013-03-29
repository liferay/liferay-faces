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
package com.liferay.faces.issues.bean;

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

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.icefaces.ace.component.fileentry.FileEntryResults.FileInfo;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@ManagedBean
@ViewScoped
public class BackingBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2947548873495692163L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BackingBean.class);

	// Private Constants
	private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";

	// Injections
	@ManagedProperty(value = "#{modelBean}")
	private transient ModelBean modelBean;

	public void handleFileUpload(FileEntryEvent fileEntryEvent) {

		try {
			FileEntry fileEntry = (FileEntry) fileEntryEvent.getSource();
			FileEntryResults results = fileEntry.getResults();

			boolean success = true;

			for (FileEntryResults.FileInfo fileInfo : results.getFiles()) {

				if (fileInfo.getStatus().isSuccess()) {
					modelBean.getUploadedFiles().add(fileInfo);
				}
				else {
					success = false;
				}

				logger.debug("Uploaded file=[{0}]", fileInfo.getFileName());
			}

			LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

			if (!success) {
				liferayFacesContext.addGlobalUnexpectedErrorMessage();
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void submit(ActionEvent actionEvent) {

		logger.debug("Submitted form");
		logger.debug("comments1=" + modelBean.getComments1());
		logger.debug("comments2=" + modelBean.getComments2());

		List<FileInfo> uploadedFiles = modelBean.getUploadedFiles();

		for (FileInfo fileInfo : uploadedFiles) {
			logger.debug("Uploaded file=" + fileInfo.getFileName());
		}

		LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();
		liferayFacesContext.addGlobalSuccessInfoMessage();
	}

	public String getFileUploadAbsolutePath() {
		return System.getProperty(JAVA_IO_TMPDIR);
	}

	public void setModelBean(ModelBean modelBean) {

		// Injected via @ManagedProperty annotation
		this.modelBean = modelBean;
	}
}
