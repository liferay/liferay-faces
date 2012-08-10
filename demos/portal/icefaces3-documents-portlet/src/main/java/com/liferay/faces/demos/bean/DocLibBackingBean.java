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
import java.text.MessageFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;

import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.FileNameException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.documentlibrary.SourceFileNameException;

import com.liferay.faces.bridge.model.UploadedFile;
import com.liferay.faces.demos.dto.UploadedFileWrapper;
import com.liferay.faces.demos.tree.FolderTreeNode;
import com.liferay.faces.demos.tree.FolderUserObject;
import com.liferay.faces.portal.context.LiferayFacesContext;
import com.liferay.faces.util.helper.LongHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;


/**
 * @author  Neil Griffin
 */
@ManagedBean(name = "docLibBackingBean")
@RequestScoped
public class DocLibBackingBean {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(DocLibBackingBean.class);

	// Private Constants
	private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";

	// Self-Injections
	private LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	// Injections
	@ManagedProperty(name = "docLibModelBean", value = "#{docLibModelBean}")
	private DocLibModelBean docLibModelBean;

	@ManagedProperty(name = "docLibViewBean", value = "#{docLibViewBean}")
	private DocLibViewBean docLibViewBean;

	// Private Data Members
	private String fileUploadAbsolutePath;
	private String folderName;
	private String folderDescription;
	private String maxFileSizeKB;
	private Boolean permittedToAddDocument;
	private Boolean permittedToAddFolder;

	public void addFolder(ActionEvent actionEvent) throws AbortProcessingException {

		try {
			FolderUserObject folderUserObject = docLibModelBean.getSelectedFolderUserObject();
			DLFolder dlFolder = folderUserObject.getDlFolder();

			// Set the permissions such that community members can view the file.
			ServiceContext serviceContext = new ServiceContext();
			serviceContext.setAddCommunityPermissions(true);

			DLFolderServiceUtil.addFolder(dlFolder.getGroupId(), dlFolder.getFolderId(), folderName, folderDescription,
				serviceContext);

			docLibModelBean.forceTreeRequery();
			docLibViewBean.setPopupRendered(false);
			logger.debug("Added folderName=[{0}] description=[{1}]", folderName, folderDescription);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			liferayFacesContext.addGlobalUnexpectedErrorMessage();
		}
	}

	public void handleFileUpload(FileEntryEvent fileEntryEvent) {

		try {
			FileEntry fileEntry = (FileEntry) fileEntryEvent.getSource();
			FileEntryResults results = fileEntry.getResults();

			for (FileEntryResults.FileInfo fileInfo : results.getFiles()) {

				UploadedFileWrapper uploadedFile = new UploadedFileWrapper(fileInfo);

				if (uploadedFile.getStatus() == UploadedFile.Status.FILE_SAVED) {

					FolderUserObject folderUserObject = docLibModelBean.getSelectedFolderUserObject();

					try {

						DLFolder dlFolder = folderUserObject.getDlFolder();

						String name = stripInvalidFileNameCharacters(uploadedFile.getName());
						String title = name;
						String description = null;
						String changeLog = null;
						File file = new File(uploadedFile.getAbsolutePath());
						ServiceContext serviceContext = new ServiceContext();

						// Temporary: Make the default setting be that community members can view the file. Need to
						// develop a "Viewable By" permissions Facelet composite component UI similar to
						// portal-web/docroot/html/taglib/ui/input_permissions/page.jsp
						serviceContext.setAddCommunityPermissions(true);

						try {
							String extraSettings = null;
							DLFileEntryServiceUtil.addFileEntry(dlFolder.getGroupId(), dlFolder.getFolderId(), name,
								title, description, changeLog, extraSettings, file, serviceContext);
							docLibModelBean.forceDocumentRequery();
							file.delete();
						}
						catch (DuplicateFileException e) {
							liferayFacesContext.addGlobalErrorMessage("please-enter-a-unique-document-name");
						}
						catch (FileNameException e) {

							String extensions = StringUtil.merge(PrefsPropsUtil.getStringArray(
										PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), StringPool.COMMA_AND_SPACE);
							String message = liferayFacesContext.getMessage(
									"document-names-must-end-with-one-of-the-following-extensions");
							message = message + extensions;
							liferayFacesContext.addGlobalErrorMessage(message);
						}
						catch (FileSizeException e) {

							String message = liferayFacesContext.getMessage(
									"please-enter-a-file-with-a-valid-file-size");
							message = message + " (" + getMaxFileSizeKB() + "k max)";
							liferayFacesContext.addGlobalErrorMessage(message);
						}
						catch (SourceFileNameException e) {
							liferayFacesContext.addGlobalErrorMessage(
								"the-source-file-does-not-have-the-same-extension-as-the-original-file");
						}

					}
					catch (Exception e) {
						logger.error(e.getMessage(), e);
						liferayFacesContext.addGlobalUnexpectedErrorMessage();
					}
				}
			}
		}
		catch (Exception e) {
			logger.error(e);
		}

		docLibViewBean.setPopupRendered(false);
	}

	public void treeNodeSelected(ActionEvent actionEvent) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		long folderId = LongHelper.toLong(externalContext.getRequestParameterMap().get("folderId"), 0L);
		FolderTreeNode folderTreeNode = docLibModelBean.getFolderTreeModel().findFolderTreeNode(folderId);
		FolderUserObject folderUserObject = folderTreeNode.getFolderUserObject();
		docLibModelBean.setSelectedUserObject(folderUserObject);
		permittedToAddFolder = null;
		permittedToAddDocument = null;
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
		}

		return strippedFileName;
	}

	public void setDocLibModelBean(DocLibModelBean docLibModelBean) {

		// Injected via ManagedProperty annotation
		this.docLibModelBean = docLibModelBean;
	}

	public void setDocLibViewBean(DocLibViewBean docLibViewBean) {
		this.docLibViewBean = docLibViewBean;
	}

	public String getFileUploadAbsolutePath() {

		if (fileUploadAbsolutePath == null) {
			fileUploadAbsolutePath = System.getProperty(JAVA_IO_TMPDIR);
		}

		return fileUploadAbsolutePath;
	}

	public String getFolderDescription() {
		return folderDescription;
	}

	public void setFolderDescription(String folderDescription) {
		this.folderDescription = folderDescription;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
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

	public boolean isPermittedToAddFolder() {

		if (permittedToAddFolder == null) {

			try {
				PermissionChecker permissionChecker = liferayFacesContext.getPermissionChecker();
				DLFolder selectedDLFolder = docLibModelBean.getSelectedFolderUserObject().getDlFolder();
				long scopeGroupId = selectedDLFolder.getGroupId();
				long folderId = selectedDLFolder.getFolderId();
				permittedToAddFolder = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId,
						ActionKeys.ADD_FOLDER);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return permittedToAddFolder;
	}

	public boolean isPermittedToAddDocument() {

		if (permittedToAddDocument == null) {

			try {
				PermissionChecker permissionChecker = liferayFacesContext.getPermissionChecker();
				DLFolder selectedDLFolder = docLibModelBean.getSelectedFolderUserObject().getDlFolder();
				long scopeGroupId = selectedDLFolder.getGroupId();
				long folderId = selectedDLFolder.getFolderId();
				permittedToAddDocument = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId,
						ActionKeys.ADD_DOCUMENT);
				permittedToAddFolder = DLFolderPermission.contains(permissionChecker, scopeGroupId, folderId,
						ActionKeys.ADD_FOLDER);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		return permittedToAddDocument;
	}

}
