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

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;

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
@ManagedBean(name = "applicantBackingBean")
@RequestScoped
public class ApplicantBackingBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2947548873495692163L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ApplicantBackingBean.class);

	// Private Constants
	private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";

	// Injections
	@ManagedProperty(value = "#{applicantModelBean}")
	private transient ApplicantModelBean applicantModelBean;
	@ManagedProperty(value = "#{applicantViewBean}")
	private transient ApplicantViewBean applicantViewBean;
	@ManagedProperty(value = "#{listModelBean}")
	private transient ListModelBean listModelBean;

	// Private Data Members
	private String fileUploadAbsolutePath;

	public void cityListener(ValueChangeEvent valueChangeEvent) {
		String cityNameStartsWith = (String) valueChangeEvent.getNewValue();
		listModelBean.filterSelectItems(cityNameStartsWith);

		City city = listModelBean.getCityByName(cityNameStartsWith);

		if (city != null) {
			applicantModelBean.setAutoFillCity(city.getCityName());
			applicantModelBean.setAutoFillProvinceId(city.getProvinceId());
			applicantModelBean.setAutoFillPostalCode(city.getPostalCode());
		}
	}

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
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	public void handleFileUpload(FileEntryEvent fileEntryEvent) {

		try {
			List<UploadedFile> uploadedFiles = applicantModelBean.getUploadedFiles();
			FileEntry fileEntry = (FileEntry) fileEntryEvent.getSource();
			FileEntryResults results = fileEntry.getResults();

			for (FileEntryResults.FileInfo fileInfo : results.getFiles()) {

				UploadedFileWrapper uploadedFile = new UploadedFileWrapper(fileInfo);

				if (uploadedFile.getStatus() == UploadedFile.Status.FILE_SAVED) {

					synchronized (uploadedFiles) {
						uploadedFiles.add(uploadedFile);
					}
				}
				else {
					logger.error("Uploaded file status=[" + uploadedFile.getStatus().toString() + "] " +
						uploadedFile.getMessage());
					FacesMessageUtil.addGlobalUnexpectedErrorMessage(FacesContext.getCurrentInstance());
				}
			}
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	public void postalCodeListener(ValueChangeEvent valueChangeEvent) {

		try {
			String newPostalCode = (String) valueChangeEvent.getNewValue();
			City city = listModelBean.getCityByPostalCode(newPostalCode);

			if (city != null) {

				// Workaround for FACES-1537: Need to set the submitted value in the component tree for ice:inputText.
				UIComponent postalCodeInputComponent = valueChangeEvent.getComponent();
				UIInput cityInputComponent = (UIInput) postalCodeInputComponent.findComponent("city");
				String autoFillCity = city.getCityName();
				cityInputComponent.setSubmittedValue(autoFillCity);
				cityInputComponent.setValid(true);
				cityInputComponent.setValue(null);
				applicantModelBean.setAutoFillCity(autoFillCity);

				// Workaround for FACES-1558: Need to set the submitted value and the value in the component tree for
				// ice:selectInputText.
				UIInput provinceIdInputComponent = (UIInput) postalCodeInputComponent.findComponent("provinceId");
				long autoFillProvinceId = city.getProvinceId();
				provinceIdInputComponent.setSubmittedValue(Long.toString(autoFillProvinceId));
				provinceIdInputComponent.setValid(true);
				provinceIdInputComponent.setValue(autoFillProvinceId);
				applicantModelBean.setAutoFillProvinceId(autoFillProvinceId);
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

			if (uploadedFiles != null) {

				for (UploadedFile uploadedFile : uploadedFiles) {

					if (logger.isDebugEnabled()) {
						logger.debug("uploadedFile=" + uploadedFile.getName());
					}
				}
			}
		}

		// Delete the uploaded files.
		try {
			List<UploadedFile> uploadedFiles = applicantModelBean.getUploadedFiles();

			for (UploadedFile uploadedFile : uploadedFiles) {
				uploadedFile.delete();
				logger.debug("Deleted file=[{0}]", uploadedFile.getName());
			}

			// Store the applicant's first name in JSF 2 Flash Scope so that it can be picked up
			// for use inside of confirmation.xhtml
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.getExternalContext().getFlash().put("firstName", applicantModelBean.getFirstName());

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

		// Injected via @ManagedProperty annotation
		this.applicantModelBean = applicantModelBean;
	}

	public void setApplicantViewBean(ApplicantViewBean applicantViewBean) {

		// Injected via @ManagedProperty annotation
		this.applicantViewBean = applicantViewBean;
	}

	public String getFileUploadAbsolutePath() {

		if (fileUploadAbsolutePath == null) {
			fileUploadAbsolutePath = System.getProperty(JAVA_IO_TMPDIR);
		}

		return fileUploadAbsolutePath;
	}

	public void setListModelBean(ListModelBean listModelBean) {

		// Injected via @ManagedProperty annotation
		this.listModelBean = listModelBean;
	}

}
