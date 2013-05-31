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

import java.io.File;
import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;

import com.liferay.faces.bridge.model.UploadedFile;
import com.liferay.faces.demos.dto.City;
import com.liferay.faces.demos.dto.UploadedFileWrapper;
import com.liferay.faces.demos.util.FacesMessageUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This is a JSF backing managed-bean for the applicant.xhtml composition.
 *
 * @author  "Neil Griffin"
 */
public class ApplicantBackingBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 3047548873495692163L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ApplicantBackingBean.class);

	// Injections
	private transient ApplicantModelBean applicantModelBean;
	private transient ApplicantViewBean applicantViewBean;
	private transient ListModelBean listModelBean;

	public void deleteUploadedFile(ActionEvent actionEvent) {

		try {
			List<UploadedFile> uploadedFiles = applicantModelBean.getUploadedFiles();

			String uploadedFileId = applicantViewBean.getUploadedFileId();

			UploadedFile uploadedFileToDelete = null;

			for (UploadedFile uploadedFile : uploadedFiles) {

				if (uploadedFile.getId().equals(uploadedFileId)) {
					uploadedFileToDelete = uploadedFile;

					break;
				}
			}

			if (uploadedFileToDelete != null) {
				uploadedFileToDelete.delete();
				uploadedFiles.remove(uploadedFileToDelete);
				logger.debug("Deleted file=[{0}]", uploadedFileToDelete.getName());
			}
			
			applicantViewBean.setPopupRendered(false);
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	public void fileUploadActionListener(ActionEvent actionEvent) {

		applicantViewBean.setPercentComplete(0);

		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			InputFile inputFile = (InputFile) actionEvent.getSource();
			FileInfo fileInfo = inputFile.getFileInfo();
			int status = fileInfo.getStatus();

			if (status == InputFile.INVALID) {
				FacesMessageUtil.addGlobalErrorMessage(facesContext, "you-have-entered-invalid-data", null);
			}
			else if ((status == InputFile.INVALID_CONTENT_TYPE) || (status == InputFile.INVALID_NAME_PATTERN)) {
				FacesMessageUtil.addGlobalErrorMessage(facesContext, "file-type-is-invalid", null);
			}
			else if (status == InputFile.SAVED) {
				List<UploadedFile> uploadedFiles = applicantModelBean.getUploadedFiles();

				UploadedFile uploadedFile = new UploadedFileWrapper(fileInfo);

				synchronized (uploadedFiles) {

					uploadedFiles.add(uploadedFile);
				}
			}
			else if (status == InputFile.SIZE_LIMIT_EXCEEDED) {
				FacesMessageUtil.addGlobalErrorMessage(facesContext, "please-enter-a-file-with-a-valid-file-size",
					null);
			}
		}
		catch (Exception e) {
			logger.error(e);
			FacesMessageUtil.addGlobalUnexpectedErrorMessage(facesContext);
		}
	}

	public void postalCodeListener(ValueChangeEvent valueChangeEvent) {

		try {
			String newPostalCode = (String) valueChangeEvent.getNewValue();
			City city = listModelBean.getCityByPostalCode(newPostalCode);

			if (city != null) {
				applicantModelBean.setAutoFillCity(city.getCityName());
				applicantModelBean.setAutoFillProvinceId(city.getProvinceId());
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessageUtil.addGlobalUnexpectedErrorMessage(FacesContext.getCurrentInstance());
		}
	}

	public String submit() {

		if (logger.isDebugEnabled()) {
			logger.debug("firstName=" + applicantModelBean.getFirstName());
			logger.debug("lastName=" + applicantModelBean.getLastName());
			logger.debug("emailAddress=" + applicantModelBean.getEmailAddress());
			logger.debug("phoneNumber=" + applicantModelBean.getPhoneNumber());
			logger.debug("dateOfBirth=" + applicantModelBean.getDateOfBirth());
			logger.debug("city=" + applicantModelBean.getCity());
			logger.debug("provinceId=" + applicantModelBean.getProvinceId());
			logger.debug("postalCode=" + applicantModelBean.getPostalCode());
			logger.debug("comments=" + applicantModelBean.getComments());

			List<UploadedFile> uploadedFiles = applicantModelBean.getUploadedFiles();

			for (UploadedFile uploadedFile : uploadedFiles) {
				logger.debug("uploadedFile=[{0}]", uploadedFile.getName());
			}
		}

		// Delete the uploaded files.
		try {
			List<UploadedFile> uploadedFiles = applicantModelBean.getUploadedFiles();

			for (UploadedFile uploadedFile : uploadedFiles) {
				File file = new File(uploadedFile.getAbsolutePath());
				file.delete();
				logger.debug("Deleted file=[{0}]", file);
			}

			applicantModelBean.clearProperties();

			return "success";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			FacesMessageUtil.addGlobalUnexpectedErrorMessage(FacesContext.getCurrentInstance());

			return "failure";
		}
	}

	public void setApplicantModelBean(ApplicantModelBean applicantModelBean) {

		// Injected via WEB-INF/faces-config.xml managed-property
		this.applicantModelBean = applicantModelBean;
	}

	public void setApplicantViewBean(ApplicantViewBean applicantViewBean) {

		// Injected via WEB-INF/faces-config.xml managed-property
		this.applicantViewBean = applicantViewBean;
	}

	public void setListModelBean(ListModelBean listModelBean) {

		// Injected via WEB-INF/faces-config.xml managed-property
		this.listModelBean = listModelBean;
	}

}
