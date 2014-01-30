/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.icefaces.ace.component.fileentry.FileEntryResults.FileInfo;

import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


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

	// Private Data Members
	private String editor1Impl;
	private String editor2Impl;

	public BackingBean() {

		boolean valid = true;

		Product liferayPortal = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL);
		if (liferayPortal.getMajorVersion() < 6) {
			valid = false;
		}
		else if ((liferayPortal.getMajorVersion() == 6) && (liferayPortal.getMinorVersion() == 0) && (liferayPortal.getRevisionVersion() < 12)) {
			valid = false;
		}
		
		if (!valid) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "This test is only valid on Liferay 6.0.12+", null));
		}
	}

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

	public String getEditor1Impl() {
		
		if (editor1Impl == null) {
			Product liferayPortal = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL);
			if (liferayPortal.getMajorVersion() == 6) {
				
				editor1Impl = "com.liferay.faces.support.editor1";

				if ((liferayPortal.getMinorVersion() == 0) && (liferayPortal.getRevisionVersion() < 12)) {
					editor1Impl = "ckeditor";
				}
			}
			else {
				editor1Impl = "unsupported-liferay-version-" + liferayPortal.getVersion();
			}
		}

		return editor1Impl;
	}

	public String getEditor2Impl() {
		
		if (editor2Impl == null) {
			Product liferayPortal = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL);
			if (liferayPortal.getMajorVersion() == 6) {
				
				editor2Impl = "com.liferay.faces.support.editor2";

				if ((liferayPortal.getMinorVersion() == 0) && (liferayPortal.getRevisionVersion() < 12)) {
					editor2Impl = "ckeditor";
				}
			}
			else {
				editor2Impl = "unsupported-liferay-version-" + liferayPortal.getVersion();
			}
		}
		
		return editor2Impl;
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
