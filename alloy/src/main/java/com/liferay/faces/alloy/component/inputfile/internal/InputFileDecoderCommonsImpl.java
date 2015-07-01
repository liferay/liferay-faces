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
package com.liferay.faces.alloy.component.inputfile.internal;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.InvalidFileNameException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;

import com.liferay.faces.util.config.WebConfigParam;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.UploadedFile;
import com.liferay.faces.util.model.UploadedFileFactory;


/**
 * @author  Neil Griffin
 */
public class InputFileDecoderCommonsImpl extends InputFileDecoderBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputFileDecoderCommonsImpl.class);

	@Override
	public Map<String, List<UploadedFile>> decode(FacesContext facesContext, String location) {

		Map<String, List<UploadedFile>> uploadedFileMap = null;
		ExternalContext externalContext = facesContext.getExternalContext();
		String uploadedFilesFolder = getUploadedFilesFolder(externalContext, location);

		// Using the sessionId, determine a unique folder path and create the path if it does not exist.
		String sessionId = getSessionId(externalContext);

		// FACES-1452: Non-alpha-numeric characters must be removed order to ensure that the folder will be
		// created properly.
		sessionId = sessionId.replaceAll("[^A-Za-z0-9]", " ");

		File uploadedFilesPath = new File(uploadedFilesFolder, sessionId);

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
		int uploadedFileMaxSize = WebConfigParam.UploadedFileMaxSize.getIntegerValue(externalContext);

		// Parse the request parameters and save all uploaded files in a map.
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setFileSizeMax(uploadedFileMaxSize);
		uploadedFileMap = new HashMap<String, List<UploadedFile>>();

		UploadedFileFactory uploadedFileFactory = (UploadedFileFactory) FactoryExtensionFinder.getFactory(
				UploadedFileFactory.class);

		// Begin parsing the request for file parts:
		try {
			FileItemIterator fileItemIterator = null;

			HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();
			fileItemIterator = servletFileUpload.getItemIterator(httpServletRequest);

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

						// If the current field is a file, then
						if (!diskFileItem.isFormField()) {

							// Get the location of the temporary file that was copied from the request.
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

	protected String getSessionId(ExternalContext externalContext) {

		String sessionId = null;

		try {
			Object session = externalContext.getSession(true);

			Method getIdMethod = session.getClass().getMethod("getId", new Class[] {});
			sessionId = (String) getIdMethod.invoke(session, new Object[] {});
		}
		catch (Exception e) {
			logger.error(e);
		}

		return sessionId;
	}
}
