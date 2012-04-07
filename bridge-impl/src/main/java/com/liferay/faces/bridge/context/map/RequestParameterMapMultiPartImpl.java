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
package com.liferay.faces.bridge.context.map;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.render.ResponseStateManager;
import javax.portlet.ActionRequest;
import javax.portlet.PortletContext;
import javax.portlet.PortletSession;

import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.apache.commons.io.FileUtils;

import com.liferay.faces.bridge.UploadedFileImpl;
import com.liferay.faces.bridge.component.UploadedFile;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;
import com.liferay.faces.bridge.util.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class RequestParameterMapMultiPartImpl extends RequestParameterMap {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(RequestParameterMapMultiPartImpl.class);

	// Private Constants
	private static final String CONTEXT_PARAM_UPLOADED_FILES_DIR = "javax.faces.UPLOADED_FILES_DIR";
	private static final String CONTEXT_PARAM_UPLOADED_FILE_MAX_SIZE = "javax.faces.UPLOADED_FILE_MAX_SIZE";
	private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
	private static final int DEFAULT_FILE_MAX_SIZE = 104857600; // 100MB

	// Private Data Members
	private Map<String, String> requestParameterMap;
	private Map<String, UploadedFile> requestParameterFileMap;

	public RequestParameterMapMultiPartImpl(BridgeContext bridgeContext, ActionRequest actionRequest) {

		try {

			PortletSession portletSession = actionRequest.getPortletSession();
			PortletContext portletContext = portletSession.getPortletContext();

			// Determine the uploaded files directory path according to the JSF 2.2 proposal:
			// https://javaserverfaces-spec-public.dev.java.net/issues/show_bug.cgi?id=690
			String uploadedFilesDir = portletContext.getInitParameter(CONTEXT_PARAM_UPLOADED_FILES_DIR);

			if (uploadedFilesDir == null) {
				uploadedFilesDir = System.getProperty(JAVA_IO_TMPDIR);

				if (logger.isDebugEnabled()) {
					logger.debug(
						"The web.xml context-param name=[{0}] not found, using default system property=[{1}] value=[{2}]",
						new Object[] { CONTEXT_PARAM_UPLOADED_FILES_DIR, JAVA_IO_TMPDIR, uploadedFilesDir });
				}
			}
			else {

				if (logger.isDebugEnabled()) {
					logger.debug("Using web.xml context-param name=[{0}] value=[{1}]",
						new Object[] { CONTEXT_PARAM_UPLOADED_FILES_DIR, uploadedFilesDir });
				}
			}

			// Using the portlet sessionId, determine a unique folder path and create the path if it does not exist.
			String sessionId = portletSession.getId();
			uploadedFilesDir = uploadedFilesDir + "/" + sessionId;

			File uploadedFilesPath = new File(uploadedFilesDir);

			if (!uploadedFilesPath.exists()) {

				try {
					uploadedFilesPath.mkdirs();
				}
				catch (SecurityException e) {
					uploadedFilesDir = System.getProperty(JAVA_IO_TMPDIR) + "/" + sessionId;
					logger.error(
						"Security exception message=[{0}] when trying to create unique path=[{1}] so using default system property=[{2}] value=[{3}]",
						new Object[] { e.getMessage(), uploadedFilesPath.toString(), JAVA_IO_TMPDIR, uploadedFilesDir });
					uploadedFilesPath = new File(uploadedFilesDir);
					uploadedFilesPath.mkdirs();
				}
			}

			// Initialize commons-fileupload with the file upload path.
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			diskFileItemFactory.setRepository(uploadedFilesPath);

			// Initialize commons-fileupload so that uploaded temporary files are not automatically deleted.
			diskFileItemFactory.setFileCleaningTracker(null);

			// Initialize the commons-fileupload size threshold to zero, so that all files will be dumped to disk
			// instead of staying in memory.
			diskFileItemFactory.setSizeThreshold(0);

			// Determine the max file upload size threshold in bytes.
			String uploadedFilesMaxSize = portletContext.getInitParameter(CONTEXT_PARAM_UPLOADED_FILE_MAX_SIZE);
			int fileMaxSize = DEFAULT_FILE_MAX_SIZE;

			if (uploadedFilesMaxSize == null) {

				if (logger.isDebugEnabled()) {
					logger.debug("The web.xml context-param name=[{0}] not found, using default=[{1}] bytes",
						new Object[] { CONTEXT_PARAM_UPLOADED_FILE_MAX_SIZE, DEFAULT_FILE_MAX_SIZE });
				}
			}
			else {

				try {
					fileMaxSize = Integer.parseInt(uploadedFilesMaxSize);

					if (logger.isDebugEnabled()) {
						logger.debug("Using web.xml context-param name=[{0}] value=[{1}] bytes",
							new Object[] { CONTEXT_PARAM_UPLOADED_FILE_MAX_SIZE, fileMaxSize });
					}
				}
				catch (NumberFormatException e) {
					logger.error("Invalid value=[{0}] for web.xml context-param name=[{1}] using default=[{2}] bytes.",
						new Object[] {
							uploadedFilesMaxSize, CONTEXT_PARAM_UPLOADED_FILE_MAX_SIZE, DEFAULT_FILE_MAX_SIZE
						});
				}
			}

			// Parse the request parameters and save all uploaded files in a map.
			PortletFileUpload portletFileUpload = new PortletFileUpload(diskFileItemFactory);
			portletFileUpload.setFileSizeMax(fileMaxSize);
			requestParameterMap = new HashMap<String, String>();
			requestParameterFileMap = new HashMap<String, UploadedFile>();

			// FACES-271: Include name+value pairs found in the ActionRequest.
			PortletContainer portletContainer = bridgeContext.getPortletContainer();
			Set<Map.Entry<String, String[]>> actionRequestParameterSet = actionRequest.getParameterMap().entrySet();

			for (Map.Entry<String, String[]> mapEntry : actionRequestParameterSet) {

				String parameterName = mapEntry.getKey();
				String[] parameterValues = mapEntry.getValue();

				if (parameterValues.length > 0) {
					String fixedRequestParameterValue = portletContainer.fixRequestParameterValue(parameterValues[0]);
					requestParameterMap.put(parameterName, fixedRequestParameterValue);
					logger.debug("Found in ActionRequest: {0}=[{1}]", parameterName, fixedRequestParameterValue);
				}
			}

			@SuppressWarnings("unchecked")
			List<DiskFileItem> diskFileItems = portletFileUpload.parseRequest(actionRequest);

			boolean foundAtLeastOneUploadedFile = false;

			if (diskFileItems != null) {

				for (DiskFileItem diskFileItem : diskFileItems) {

					String fieldName = diskFileItem.getFieldName();

					if (diskFileItem.isFormField()) {
						String requestParameterValue = diskFileItem.getString();
						String fixedRequestParameterValue = portletContainer.fixRequestParameterValue(
								requestParameterValue);
						requestParameterMap.put(fieldName, fixedRequestParameterValue);
						logger.debug("{0}=[{1}]", fieldName, fixedRequestParameterValue);
					}
					else {

						// Copy the commons-fileupload temporary file to a file in the same temporary location, but
						// with the filename provided by the user in the upload. This has two benefits:
						// 1) The temporary file will have a nice meaningful name.
						// 2) By copying the file, the developer can have access to a semi-permanent file, because the
						// commmons-fileupload DiskFileItem.finalize() method automatically deletes the temporary one.
						File tempFile = diskFileItem.getStoreLocation();

						if (tempFile.exists()) {
							foundAtLeastOneUploadedFile = true;

							String tempFileName = tempFile.getName();
							String tempFileAbsolutePath = tempFile.getAbsolutePath();

							String copiedFileName = stripIllegalCharacters(diskFileItem.getName());

							String copiedFileAbsolutePath = tempFileAbsolutePath.replace(tempFileName, copiedFileName);
							File copiedFile = new File(copiedFileAbsolutePath);
							FileUtils.copyFile(tempFile, copiedFile);

							UploadedFileImpl uploadedFile = new UploadedFileImpl();

							// absoluteFilePath
							uploadedFile.setAbsolutePath(copiedFileAbsolutePath);

							// charSet
							uploadedFile.setCharSet(diskFileItem.getCharSet());

							// contentType
							uploadedFile.setContentType(diskFileItem.getContentType());

							// headersMap
							Map<String, List<String>> headersMap = new HashMap<String, List<String>>();
							FileItemHeaders fileItemHeaders = diskFileItem.getHeaders();

							if (fileItemHeaders != null) {
								@SuppressWarnings("unchecked")
								Iterator<String> headerNameItr = fileItemHeaders.getHeaderNames();

								if (headerNameItr != null) {

									while (headerNameItr.hasNext()) {
										String headerName = headerNameItr.next();
										@SuppressWarnings("unchecked")
										Iterator<String> headerValuesItr = fileItemHeaders.getHeaders(headerName);
										List<String> headerValues = new ArrayList<String>();

										if (headerValuesItr != null) {

											while (headerValuesItr.hasNext()) {
												String headerValue = headerValuesItr.next();
												headerValues.add(headerValue);
											}
										}

										headersMap.put(headerName, headerValues);
									}
								}
							}

							uploadedFile.setHeadersMap(headersMap);

							// name
							String fileName = diskFileItem.getName();
							uploadedFile.setName(fileName);

							// size
							uploadedFile.setSize(diskFileItem.getSize());

							requestParameterMap.put(fieldName, copiedFileAbsolutePath);
							requestParameterFileMap.put(fieldName, uploadedFile);
							logger.debug("Received uploaded file fieldName=[{0}] fileName=[{1}]", fieldName, fileName);
						}
					}
				}

				actionRequest.setAttribute(PARAM_UPLOADED_FILES, requestParameterFileMap);
			}

			if (!foundAtLeastOneUploadedFile) {
				logger.warn("No uploaded files are found in the request");
			}

			// If not found in the request, Section 6.9 of the Bridge spec requires that the value of the
			// ResponseStateManager.RENDER_KIT_ID_PARAM request parameter be set to the value of the
			// "javax.portlet.faces.<portletName>.defaultRenderKitId" PortletContext attribute.
			String renderKitIdParam = requestParameterMap.get(ResponseStateManager.RENDER_KIT_ID_PARAM);

			if (renderKitIdParam == null) {
				renderKitIdParam = bridgeContext.getDefaultRenderKitId();

				if (renderKitIdParam != null) {
					requestParameterMap.put(ResponseStateManager.RENDER_KIT_ID_PARAM, renderKitIdParam);
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	protected AbstractPropertyMapEntry<String> createPropertyMapEntry(String name) {
		return new RequestParameterMapEntryMultiPart(name, requestParameterMap);
	}

	@Override
	protected void removeProperty(String name) {
		throw new UnsupportedOperationException();
	}

	protected String stripIllegalCharacters(String fileName) {

		// FACES-64: Need to strip out invalid characters.
		// http://technet.microsoft.com/en-us/library/cc956689.aspx
		String strippedFileName = fileName;

		if (fileName != null) {
			strippedFileName = fileName.replaceAll("[\\\\/\\[\\]:|<>+;=.?\"]", "-");
		}

		return strippedFileName;
	}

	@Override
	protected String getProperty(String name) {
		return requestParameterMap.get(name);
	}

	@Override
	protected void setProperty(String name, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Enumeration<String> getPropertyNames() {

		// Note#1: Section 6.9 of the Bridge spec requires that a parameter name be added to the return value of
		// ExternalContext.getRequestParameterNames() for ResponseStateManager.RENDER_KIT_ID_PARAM. This will
		// automatically be the case because this class builds up its own internal requestParameterMap in the
		// constructor that will contain the ResponseStateManager.RENDER_KIT_ID_PARAM if required.

		// Note#2: This can't be cached because the caller basically wants a new enumeration to iterate over each time.
		return Collections.enumeration(requestParameterMap.keySet());
	}

}
