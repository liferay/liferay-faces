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
package com.liferay.faces.demos.bean;

import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.bridge.model.UploadedFile;

import com.liferay.faces.portal.context.LiferayFacesContext;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.SourceFileNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;

import com.liferay.faces.demos.dto.UploadedFileWrapper;

import org.primefaces.event.FileUploadEvent; 




/**
 * @author  Neil Griffin, ported to Primefaces by Jacques Champliaud
 */
@ManagedBean(name = "docUploadBackingBean")
@RequestScoped
public class DocUploadBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DocUploadBackingBean.class);

	// Self-Injections
	private LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	// Injections
	@ManagedProperty(name = "docLibModelBean", value = "#{docLibModelBean}")
	private DocLibModelBean docLibModelBean;

	// Private Data Members
	private String maxFileSizeKB;
	private List<UploadedFileWrapper> uploadedFiles;


	public void handleFileUpload(FileUploadEvent event) {
		DLFolder dlFolder =  docLibModelBean.getSelectedNodeDlFolder();
		String uniqueFolderName = dlFolder.getName();
		org.primefaces.model.UploadedFile uploadedFile = event.getFile();
		UploadedFileWrapper uploadedFileWrapper = new UploadedFileWrapper(uploadedFile, UploadedFile.Status.FILE_SAVED,uniqueFolderName);
		//        uploadedFiles.add(uploadedFileWrapper);
		uploadSingleAttachments(uploadedFileWrapper); 
	}

	public String uploadSingleAttachments(UploadedFileWrapper fileToUpload) {
		boolean success = true;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale locale = facesContext.getViewRoot().getLocale();
		if (locale == null) {
			locale = Locale.getDefault();
		}
		ArrayList<UploadedFileWrapper> uploadedFiles = new ArrayList<UploadedFileWrapper>();
		uploadedFiles.add(fileToUpload);
		DLFolder dlFolder =  docLibModelBean.getSelectedNodeDlFolder();
		try {
			for (UploadedFileWrapper uploadedFile : uploadedFiles) {
				//					DLFolder dlFolder = folderUserObject.getDlFolder();
				String name = stripInvalidFileNameCharacters(uploadedFile.getName());
				String title = name;
				String description = null;
				String changeLog = null;
				File file = new File(uploadedFile.getAbsolutePath());
				ServiceContext serviceContext = new ServiceContext();
				// Temporary: Make the default setting be that community members can view the file. Need to develop a
				// "Viewable By" permissions Facelet composite component UI similar to
				// portal-web/docroot/html/taglib/ui/input_permissions/page.jsp
				serviceContext.setAddGroupPermissions(true);
				try {
					long fileEntryTypeId = DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT;
					Map<String, Fields> fieldsMap = new HashMap<String, Fields>();
					FileInputStream inputStream = new FileInputStream(file);
					DLFileEntryServiceUtil.addFileEntry(dlFolder.getGroupId(), dlFolder.getRepositoryId(),
							dlFolder.getFolderId(), name, uploadedFile.getContentType(), title, description, changeLog,
							fileEntryTypeId, fieldsMap, file, inputStream, file.length(), serviceContext);
					inputStream.close();
					file.delete();
				}
				catch (DuplicateFileException e) {
					success = false;
					liferayFacesContext.addGlobalErrorMessage("please-enter-a-unique-document-name");
				}
				catch (FileNameException e) {
					success = false;

					String extensions = StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS,
							StringPool.COMMA), StringPool.COMMA_AND_SPACE);
					String message = liferayFacesContext.getMessage(
							"document-names-must-end-with-one-of-the-following-extensions");
					message = message + extensions;
					liferayFacesContext.addGlobalErrorMessage(message);
				}
				catch (FileSizeException e) {
					success = false;

					String message = liferayFacesContext.getMessage("please-enter-a-file-with-a-valid-file-size");
					message = message + " (" + getMaxFileSizeKB() + "k max)";
					liferayFacesContext.addGlobalErrorMessage(message);
				}
				catch (SourceFileNameException e) {
					success = false;
					liferayFacesContext.addGlobalErrorMessage(
							"the-source-file-does-not-have-the-same-extension-as-the-original-file");
				}
			}
		}
		catch (Exception e) {
			success = false;
			logger.error(e.getMessage(), e);
			liferayFacesContext.addGlobalUnexpectedErrorMessage();
		}
		docLibModelBean.forceDocumentRequery();
		String nextPage =  success ? "/views/portletViewMode.xhtml" : "error.xhtml" ;
		return nextPage;
	}


	protected String stripInvalidFileNameCharacters(String fileName) {

		String strippedFileName = fileName;

		if (strippedFileName != null) {
			strippedFileName = strippedFileName.replaceAll("[\\\\]", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll("[/]", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll("[:]", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll("[*]", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll("[?]", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll("[\"]", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll("[<]", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll("[>]", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll("[|]", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll("[\\[]", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll("[\\]]", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll("[']", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll("[.][.]", StringPool.SPACE);
			strippedFileName = strippedFileName.replaceAll(" ", "_");
		}

		return strippedFileName;
	}

	public void setDocLibModelBean(DocLibModelBean docLibModelBean) {

		// Injected via ManagedProperty annotation
		this.docLibModelBean = docLibModelBean;
	}

	public String getMaxFileSizeKB() {

		if (maxFileSizeKB == null) {

			try {
				long maxFileSizeBytes = GetterUtil.getLong(PrefsPropsUtil.getString(PropsKeys.DL_FILE_MAX_SIZE));
				maxFileSizeKB = MessageFormat.format("{0}", (maxFileSizeBytes / 1024L));
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return maxFileSizeKB;
	}

	public List<UploadedFileWrapper> getUploadedFiles() {
		return uploadedFiles;
	}
}
