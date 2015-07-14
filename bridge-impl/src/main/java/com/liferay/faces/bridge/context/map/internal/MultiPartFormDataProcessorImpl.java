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
package com.liferay.faces.bridge.context.map.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.portlet.ActionRequest;
import javax.portlet.ClientDataRequest;
import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.ResourceRequest;
import javax.portlet.WindowState;
import javax.servlet.http.Cookie;

import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.InvalidFileNameException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.config.internal.PortletConfigParam;
import com.liferay.faces.util.context.map.FacesRequestParameterMap;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.UploadedFile;
import com.liferay.faces.util.model.UploadedFileFactory;


/**
 * @author  Neil Griffin
 */
public class MultiPartFormDataProcessorImpl implements MultiPartFormDataProcessor {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(MultiPartFormDataProcessorImpl.class);

	@Override
	public Map<String, List<UploadedFile>> process(ClientDataRequest clientDataRequest, PortletConfig portletConfig,
		FacesRequestParameterMap facesRequestParameterMap) {

		Map<String, List<UploadedFile>> uploadedFileMap = null;

		PortletSession portletSession = clientDataRequest.getPortletSession();

		String uploadedFilesDir = PortletConfigParam.UploadedFilesDir.getStringValue(portletConfig);

		// Using the portlet sessionId, determine a unique folder path and create the path if it does not exist.
		String sessionId = portletSession.getId();

		// FACES-1452: Non-alpha-numeric characters must be removed order to ensure that the folder will be
		// created properly.
		sessionId = sessionId.replaceAll("[^A-Za-z0-9]", "");

		File uploadedFilesPath = new File(uploadedFilesDir, sessionId);

		if (!uploadedFilesPath.exists()) {
			uploadedFilesPath.mkdirs();
		}

		// Initialize commons-fileupload with the file upload path.
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		diskFileItemFactory.setRepository(uploadedFilesPath);

		// Initialize commons-fileupload so that uploaded temporary files are not automatically deleted.
		diskFileItemFactory.setFileCleaningTracker(null);

		// Initialize the commons-fileupload size threshold to zero, so that all files will be dumped to disk
		// instead of staying in memory.
		diskFileItemFactory.setSizeThreshold(0);

		// Determine the max file upload size threshold (in bytes).
		long uploadedFileMaxSize = PortletConfigParam.UploadedFileMaxSize.getLongValue(portletConfig);

		// Parse the request parameters and save all uploaded files in a map.
		PortletFileUpload portletFileUpload = new PortletFileUpload(diskFileItemFactory);
		portletFileUpload.setFileSizeMax(uploadedFileMaxSize);
		uploadedFileMap = new HashMap<String, List<UploadedFile>>();

		// FACES-271: Include name+value pairs found in the ActionRequest.
		Set<Map.Entry<String, String[]>> actionRequestParameterSet = clientDataRequest.getParameterMap().entrySet();

		for (Map.Entry<String, String[]> mapEntry : actionRequestParameterSet) {

			String parameterName = mapEntry.getKey();
			String[] parameterValues = mapEntry.getValue();

			if (parameterValues.length > 0) {

				for (String parameterValue : parameterValues) {
					facesRequestParameterMap.addValue(parameterName, parameterValue);
				}
			}
		}

		UploadedFileFactory uploadedFileFactory = (UploadedFileFactory) BridgeFactoryFinder.getFactory(
				UploadedFileFactory.class);

		// Begin parsing the request for file parts:
		try {
			FileItemIterator fileItemIterator = null;

			if (clientDataRequest instanceof ResourceRequest) {
				ResourceRequest resourceRequest = (ResourceRequest) clientDataRequest;
				fileItemIterator = portletFileUpload.getItemIterator(new ActionRequestAdapter(resourceRequest));
			}
			else {
				ActionRequest actionRequest = (ActionRequest) clientDataRequest;
				fileItemIterator = portletFileUpload.getItemIterator(actionRequest);
			}

			if (fileItemIterator != null) {

				int totalFiles = 0;
				String namespace = facesRequestParameterMap.getNamespace();

				// For each field found in the request:
				while (fileItemIterator.hasNext()) {

					try {
						totalFiles++;

						// Get the stream of field data from the request.
						FileItemStream fieldStream = (FileItemStream) fileItemIterator.next();

						// Get field name from the field stream.
						String fieldName = fieldStream.getFieldName();

						// Get the content-type, and file-name from the field stream.
						String contentType = fieldStream.getContentType();
						boolean formField = fieldStream.isFormField();

						String fileName = null;

						try {
							fileName = fieldStream.getName();
						}
						catch (InvalidFileNameException e) {
							fileName = e.getName();
						}

						// Copy the stream of file data to a temporary file. NOTE: This is necessary even if the
						// current field is a simple form-field because the call below to diskFileItem.getString()
						// will fail otherwise.
						DiskFileItem diskFileItem = (DiskFileItem) diskFileItemFactory.createItem(fieldName,
								contentType, formField, fileName);
						Streams.copy(fieldStream.openStream(), diskFileItem.getOutputStream(), true);

						// If the current field is a simple form-field, then save the form field value in the map.
						if (diskFileItem.isFormField()) {
							String characterEncoding = clientDataRequest.getCharacterEncoding();
							String requestParameterValue = null;

							if (characterEncoding == null) {
								requestParameterValue = diskFileItem.getString();
							}
							else {
								requestParameterValue = diskFileItem.getString(characterEncoding);
							}

							facesRequestParameterMap.addValue(fieldName, requestParameterValue);
						}
						else {

							File tempFile = diskFileItem.getStoreLocation();

							// If the copy was successful, then
							if (tempFile.exists()) {

								// Copy the commons-fileupload temporary file to a file in the same temporary
								// location, but with the filename provided by the user in the upload. This has two
								// benefits: 1) The temporary file will have a nice meaningful name. 2) By copying
								// the file, the developer can have access to a semi-permanent file, because the
								// commmons-fileupload DiskFileItem.finalize() method automatically deletes the
								// temporary one.
								String tempFileName = tempFile.getName();
								String tempFileAbsolutePath = tempFile.getAbsolutePath();

								String copiedFileName = stripIllegalCharacters(fileName);

								String copiedFileAbsolutePath = tempFileAbsolutePath.replace(tempFileName,
										copiedFileName);
								File copiedFile = new File(copiedFileAbsolutePath);
								FileUtils.copyFile(tempFile, copiedFile);

								// If present, build up a map of headers. According to Hypertext Transfer Protocol --
								// HTTP/1.1 (http://www.w3.org/Protocols/rfc2616/rfc2616-sec4.html#sec4.2), header names
								// are case-insensitive. In order to support this, use a TreeMap with case insensitive
								// keys.
								Map<String, List<String>> headersMap = new TreeMap<String, List<String>>(
										String.CASE_INSENSITIVE_ORDER);
								FileItemHeaders fileItemHeaders = fieldStream.getHeaders();

								if (fileItemHeaders != null) {
									Iterator<String> headerNameItr = fileItemHeaders.getHeaderNames();

									if (headerNameItr != null) {

										while (headerNameItr.hasNext()) {
											String headerName = headerNameItr.next();
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

								// Put a valid UploadedFile instance into the map that contains all of the
								// uploaded file's attributes, along with a successful status.
								Map<String, Object> attributeMap = new HashMap<String, Object>();
								String id = Long.toString(((long) hashCode()) + System.currentTimeMillis());
								String message = null;
								UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(copiedFileAbsolutePath,
										attributeMap, diskFileItem.getCharSet(), diskFileItem.getContentType(),
										headersMap, id, message, fileName, diskFileItem.getSize(),
										UploadedFile.Status.FILE_SAVED);

								facesRequestParameterMap.addValue(fieldName, copiedFileAbsolutePath);
								addUploadedFile(uploadedFileMap, fieldName, uploadedFile);
								logger.debug("Received uploaded file fieldName=[{0}] fileName=[{1}]", fieldName,
									fileName);
							}
							else {

								if ((fileName != null) && (fileName.trim().length() > 0)) {
									Exception e = new IOException("Failed to copy the stream of uploaded file=[" +
											fileName + "] to a temporary file (possibly a zero-length uploaded file)");
									UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(e);
									addUploadedFile(uploadedFileMap, fieldName, uploadedFile);
								}
							}
						}
					}
					catch (Exception e) {
						logger.error(e);

						UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(e);
						String fieldName = Integer.toString(totalFiles);
						addUploadedFile(uploadedFileMap, fieldName, uploadedFile);
					}
				}
			}
		}

		// If there was an error in parsing the request for file parts, then put a bogus UploadedFile instance in
		// the map so that the developer can have some idea that something went wrong.
		catch (Exception e) {
			logger.error(e);

			UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(e);
			addUploadedFile(uploadedFileMap, "unknown", uploadedFile);
		}

		return uploadedFileMap;
	}

	protected void addUploadedFile(Map<String, List<UploadedFile>> uploadedFileMap, String fieldName,
		UploadedFile uploadedFile) {
		List<UploadedFile> uploadedFiles = uploadedFileMap.get(fieldName);

		if (uploadedFiles == null) {
			uploadedFiles = new ArrayList<UploadedFile>();
			uploadedFileMap.put(fieldName, uploadedFiles);
		}

		uploadedFiles.add(uploadedFile);
	}

	protected String stripIllegalCharacters(String fileName) {

		// FACES-64: Need to strip out invalid characters.
		// http://technet.microsoft.com/en-us/library/cc956689.aspx
		String strippedFileName = fileName;

		if (fileName != null) {

			int pos = fileName.lastIndexOf(".");
			strippedFileName = fileName.replaceAll("[\\\\/\\[\\]:|<>+;=.?\"]", "-");

			if (pos > 0) {
				strippedFileName = strippedFileName.substring(0, pos) + "." + strippedFileName.substring(pos + 1);
			}
		}

		return strippedFileName;
	}

	/**
	 * Since {@link PortletFileUpload#parseRequest(ActionRequest)} only works with {@link ActionRequest}, this adapter
	 * class is necessary to force commons-fileupload to work with ResourceRequest (Ajax file upload).
	 *
	 * @author  Neil Griffin
	 */
	protected class ActionRequestAdapter implements ActionRequest {

		private ResourceRequest resourceRequest;

		public ActionRequestAdapter(ResourceRequest resourceRequest) {
			this.resourceRequest = resourceRequest;
		}

		public void removeAttribute(String name) {
			resourceRequest.removeAttribute(name);
		}

		public Object getAttribute(String name) {
			return resourceRequest.getAttribute(name);
		}

		public void setAttribute(String name, Object value) {
			resourceRequest.setAttribute(name, value);
		}

		public Enumeration<String> getAttributeNames() {
			return resourceRequest.getAttributeNames();
		}

		public String getAuthType() {
			return resourceRequest.getAuthType();
		}

		public String getCharacterEncoding() {
			return resourceRequest.getCharacterEncoding();
		}

		public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
			resourceRequest.setCharacterEncoding(enc);
		}

		public int getContentLength() {
			return resourceRequest.getContentLength();
		}

		public String getContentType() {
			return resourceRequest.getContentType();
		}

		public String getContextPath() {
			return resourceRequest.getContextPath();
		}

		public Cookie[] getCookies() {
			return resourceRequest.getCookies();
		}

		public boolean isPortletModeAllowed(PortletMode mode) {
			return resourceRequest.isPortletModeAllowed(mode);
		}

		public boolean isRequestedSessionIdValid() {
			return resourceRequest.isRequestedSessionIdValid();
		}

		public boolean isWindowStateAllowed(WindowState state) {
			return resourceRequest.isWindowStateAllowed(state);
		}

		public boolean isSecure() {
			return resourceRequest.isSecure();
		}

		public boolean isUserInRole(String role) {
			return resourceRequest.isUserInRole(role);
		}

		public Locale getLocale() {
			return resourceRequest.getLocale();
		}

		public Enumeration<Locale> getLocales() {
			return resourceRequest.getLocales();
		}

		public String getMethod() {
			return resourceRequest.getMethod();
		}

		public String getParameter(String name) {
			return resourceRequest.getParameter(name);
		}

		public Map<String, String[]> getParameterMap() {
			return resourceRequest.getParameterMap();
		}

		public Enumeration<String> getParameterNames() {
			return resourceRequest.getParameterNames();
		}

		public String[] getParameterValues(String name) {
			return resourceRequest.getParameterValues(name);
		}

		public PortalContext getPortalContext() {
			return resourceRequest.getPortalContext();
		}

		public InputStream getPortletInputStream() throws IOException {
			return resourceRequest.getPortletInputStream();
		}

		public PortletMode getPortletMode() {
			return resourceRequest.getPortletMode();
		}

		public PortletSession getPortletSession() {
			return resourceRequest.getPortletSession();
		}

		public PortletSession getPortletSession(boolean create) {
			return resourceRequest.getPortletSession();
		}

		public PortletPreferences getPreferences() {
			return resourceRequest.getPreferences();
		}

		public Map<String, String[]> getPrivateParameterMap() {
			return resourceRequest.getPrivateParameterMap();
		}

		public Enumeration<String> getProperties(String name) {
			return resourceRequest.getProperties(name);
		}

		public String getProperty(String name) {
			return resourceRequest.getProperty(name);
		}

		public Enumeration<String> getPropertyNames() {
			return resourceRequest.getPropertyNames();
		}

		public Map<String, String[]> getPublicParameterMap() {
			return resourceRequest.getPublicParameterMap();
		}

		public BufferedReader getReader() throws UnsupportedEncodingException, IOException {
			return resourceRequest.getReader();
		}

		public String getRemoteUser() {
			return resourceRequest.getRemoteUser();
		}

		public String getRequestedSessionId() {
			return resourceRequest.getRequestedSessionId();
		}

		public String getResponseContentType() {
			return resourceRequest.getResponseContentType();
		}

		public Enumeration<String> getResponseContentTypes() {
			return resourceRequest.getResponseContentTypes();
		}

		public String getScheme() {
			return resourceRequest.getScheme();
		}

		public String getServerName() {
			return resourceRequest.getServerName();
		}

		public int getServerPort() {
			return resourceRequest.getServerPort();
		}

		public Principal getUserPrincipal() {
			return resourceRequest.getUserPrincipal();
		}

		public String getWindowID() {
			return resourceRequest.getWindowID();
		}

		public WindowState getWindowState() {
			return resourceRequest.getWindowState();
		}
	}
}
