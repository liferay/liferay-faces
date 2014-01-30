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
package com.liferay.faces.bridge.context.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.faces.render.ResponseStateManager;
import javax.portlet.ActionRequest;
import javax.portlet.ClientDataRequest;
import javax.portlet.PortalContext;
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
import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.model.UploadedFile;
import com.liferay.faces.bridge.model.UploadedFileFactory;
import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.map.AbstractPropertyMapEntry;

import com.liferay.portal.kernel.util.StringPool;


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
	private NamespacedParameterMap namespacedParameterMap;
	private Map<String, List<UploadedFile>> requestParameterFileMap;

	@SuppressWarnings("unchecked")
	public RequestParameterMapMultiPartImpl(BridgeContext bridgeContext, ClientDataRequest clientDataRequest) {

		super(bridgeContext);

		try {

			PortletSession portletSession = clientDataRequest.getPortletSession();

			// Determine the uploaded files directory path according to the JSF 2.2 proposal:
			// https://javaserverfaces-spec-public.dev.java.net/issues/show_bug.cgi?id=690
			String uploadedFilesDir = bridgeContext.getInitParameter(CONTEXT_PARAM_UPLOADED_FILES_DIR);

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

			// FACES-1452: Non-alpha-numeric characters must be removed order to ensure that the folder will be
			// created properly.
			sessionId = sessionId.replaceAll("[^A-Za-z0-9]", StringPool.BLANK);

			File uploadedFilesPath = new File(uploadedFilesDir, sessionId);

			if (!uploadedFilesPath.exists()) {

				try {
					uploadedFilesPath.mkdirs();
				}
				catch (SecurityException e) {
					uploadedFilesDir = System.getProperty(JAVA_IO_TMPDIR);
					logger.error(
						"Security exception message=[{0}] when trying to create unique path=[{1}] so using default system property=[{2}] value=[{3}]",
						new Object[] { e.getMessage(), uploadedFilesPath.toString(), JAVA_IO_TMPDIR, uploadedFilesDir });
					uploadedFilesPath = new File(uploadedFilesDir, sessionId);
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
			String uploadedFilesMaxSize = bridgeContext.getInitParameter(CONTEXT_PARAM_UPLOADED_FILE_MAX_SIZE);
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

			// Get the namespace that might be found in request parameter names.
			String namespace = bridgeContext.getPortletContainer().getResponseNamespace();

			// Parse the request parameters and save all uploaded files in a map.
			PortletFileUpload portletFileUpload = new PortletFileUpload(diskFileItemFactory);
			portletFileUpload.setFileSizeMax(fileMaxSize);
			namespacedParameterMap = new NamespacedParameterMap(namespace);
			requestParameterFileMap = new HashMap<String, List<UploadedFile>>();

			// FACES-271: Include name+value pairs found in the ActionRequest.
			PortletContainer portletContainer = bridgeContext.getPortletContainer();
			Set<Map.Entry<String, String[]>> actionRequestParameterSet = clientDataRequest.getParameterMap().entrySet();

			for (Map.Entry<String, String[]> mapEntry : actionRequestParameterSet) {

				String parameterName = mapEntry.getKey();
				String[] parameterValues = mapEntry.getValue();

				if (parameterValues.length > 0) {
					String fixedRequestParameterValue = portletContainer.fixRequestParameterValue(parameterValues[0]);
					namespacedParameterMap.append(parameterName, fixedRequestParameterValue);
					logger.debug("Found in ActionRequest: {0}=[{1}]", parameterName, fixedRequestParameterValue);
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

				boolean optimizeNamespace = BooleanHelper.toBoolean(bridgeContext.getInitParameter(
							BridgeConfigConstants.PARAM_OPTIMIZE_PORTLET_NAMESPACE1), true);

				if (fileItemIterator != null) {

					int totalFiles = 0;

					// For each field found in the request:
					while (fileItemIterator.hasNext()) {

						try {
							totalFiles++;

							// Get the stream of field data from the request.
							FileItemStream fieldStream = (FileItemStream) fileItemIterator.next();

							// Get field name from the field stream.
							String fieldName = fieldStream.getFieldName();

							// If namespace optimization is enabled and the namespace is present in the field name,
							// then remove the portlet namespace from the field name.
							if (optimizeNamespace) {
								int pos = fieldName.indexOf(namespace);

								if (pos >= 0) {
									fieldName = fieldName.substring(pos + namespace.length());
								}
							}

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

								String fixedRequestParameterValue = portletContainer.fixRequestParameterValue(
										requestParameterValue);
								namespacedParameterMap.append(fieldName, fixedRequestParameterValue);
								logger.debug("{0}=[{1}]", fieldName, fixedRequestParameterValue);
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

									// If present, build up a map of headers.
									Map<String, List<String>> headersMap = new HashMap<String, List<String>>();
									FileItemHeaders fileItemHeaders = fieldStream.getHeaders();

									if (fileItemHeaders != null) {
										Iterator<String> headerNameItr = fileItemHeaders.getHeaderNames();

										if (headerNameItr != null) {

											while (headerNameItr.hasNext()) {
												String headerName = headerNameItr.next();
												Iterator<String> headerValuesItr = fileItemHeaders.getHeaders(
														headerName);
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
									UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(
											copiedFileAbsolutePath, attributeMap, diskFileItem.getCharSet(),
											diskFileItem.getContentType(), headersMap, id, message, fileName,
											diskFileItem.getSize(), UploadedFile.Status.FILE_SAVED);

									namespacedParameterMap.append(fieldName, copiedFileAbsolutePath);
									addUploadedFile(fieldName, uploadedFile);
									logger.debug("Received uploaded file fieldName=[{0}] fileName=[{1}]", fieldName,
										fileName);
								}
								else {

									if ((fileName != null) && (fileName.trim().length() > 0)) {
										Exception e = new IOException("Failed to copy the stream of uploaded file=[" +
												fileName +
												"] to a temporary file (possibly a zero-length uploaded file)");
										UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(e);
										addUploadedFile(fieldName, uploadedFile);
									}
								}
							}
						}
						catch (Exception e) {
							logger.error(e);

							UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(e);
							String fieldName = Integer.toString(totalFiles);
							addUploadedFile(fieldName, uploadedFile);
						}
					}
				}
			}

			// If there was an error in parsing the request for file parts, then put a bogus UploadedFile instance in
			// the map so that the developer can have some idea that something went wrong.
			catch (Exception e) {
				logger.error(e);

				UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(e);
				addUploadedFile("unknown", uploadedFile);
			}

			clientDataRequest.setAttribute(PARAM_UPLOADED_FILES, requestParameterFileMap);

			// If not found in the request, Section 6.9 of the Bridge spec requires that the value of the
			// ResponseStateManager.RENDER_KIT_ID_PARAM request parameter be set to the value of the
			// "javax.portlet.faces.<portletName>.defaultRenderKitId" PortletContext attribute.
			String renderKitIdParam = namespacedParameterMap.getFirst(ResponseStateManager.RENDER_KIT_ID_PARAM);

			if (renderKitIdParam == null) {
				renderKitIdParam = bridgeContext.getDefaultRenderKitId();

				if (renderKitIdParam != null) {
					namespacedParameterMap.append(ResponseStateManager.RENDER_KIT_ID_PARAM, renderKitIdParam);
				}
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	protected void addUploadedFile(String fieldName, UploadedFile uploadedFile) {
		List<UploadedFile> uploadedFiles = requestParameterFileMap.get(fieldName);

		if (uploadedFiles == null) {
			uploadedFiles = new ArrayList<UploadedFile>();
			requestParameterFileMap.put(fieldName, uploadedFiles);
		}

		uploadedFiles.add(uploadedFile);
	}

	@Override
	protected AbstractPropertyMapEntry<String> createPropertyMapEntry(String name) {
		return new RequestParameterMapEntryMultiPart(name, namespacedParameterMap);
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
		return namespacedParameterMap.getFirst(name);
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
		return Collections.enumeration(namespacedParameterMap.keySet());
	}

	@Override
	protected String getRequestParameter(String name) {
		return namespacedParameterMap.getFirst(name);
	}

	@Override
	protected Map<String, String[]> getRequestParameterMap() {
		return namespacedParameterMap;
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
