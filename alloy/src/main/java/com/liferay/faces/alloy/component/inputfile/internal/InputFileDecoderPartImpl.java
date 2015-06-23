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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.liferay.faces.util.HttpHeaders;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.UploadedFile;
import com.liferay.faces.util.model.UploadedFileFactory;


/**
 * @author  Neil Griffin
 */
public class InputFileDecoderPartImpl extends InputFileDecoderBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(InputFileDecoderPartImpl.class);

	@Override
	public Map<String, List<UploadedFile>> decode(FacesContext facesContext, String location) {

		Map<String, List<UploadedFile>> uploadedFileMap = null;
		ExternalContext externalContext = facesContext.getExternalContext();
		String uploadedFilesFolder = getUploadedFilesFolder(externalContext, location);

		// Using the sessionId, determine a unique folder path and create the path if it does not exist.
		String sessionId = externalContext.getSessionId(true);

		// FACES-1452: Non-alpha-numeric characters must be removed order to ensure that the folder will be
		// created properly.
		sessionId = sessionId.replaceAll("[^A-Za-z0-9]", StringPool.BLANK);

		File uploadedFilesPath = new File(uploadedFilesFolder, sessionId);

		if (!uploadedFilesPath.exists()) {
			uploadedFilesPath.mkdirs();
		}

		uploadedFileMap = new HashMap<String, List<UploadedFile>>();

		UploadedFileFactory uploadedFileFactory = (UploadedFileFactory) FactoryExtensionFinder.getFactory(
				UploadedFileFactory.class);

		// Begin parsing the request for file parts:
		try {

			HttpServletRequest httpServletRequest = (HttpServletRequest) externalContext.getRequest();
			Collection<Part> parts = httpServletRequest.getParts();
			int totalFiles = 0;

			// For each part found in the multipart/form-data request:
			for (Part part : parts) {

				try {
					totalFiles++;

					// Get field name and file name of the current part.
					String fieldName = null;
					String fileName = null;
					String safeFileName = null;

					String contentDispositionHeader = part.getHeader(HttpHeaders.CONTENT_DISPOSITION);
					String[] keyValuePairs = contentDispositionHeader.split(StringPool.SEMICOLON);

					for (String keyValuePair : keyValuePairs) {
						String trimmedKeyValuePair = keyValuePair.trim();

						if (trimmedKeyValuePair.startsWith("filename")) {
							int equalsPos = trimmedKeyValuePair.indexOf(StringPool.EQUAL);
							fileName = trimmedKeyValuePair.substring(equalsPos + 2, trimmedKeyValuePair.length() - 1);
							safeFileName = stripIllegalCharacters(fileName);
						}
						else if (trimmedKeyValuePair.startsWith("name")) {
							int equalsPos = trimmedKeyValuePair.indexOf(StringPool.EQUAL);
							fieldName = trimmedKeyValuePair.substring(equalsPos + 2, trimmedKeyValuePair.length() - 1);
						}
					}

					if ((fileName != null) && (fileName.length() > 0)) {

						try {

							// Copy the stream of file data to a file.
							File copiedFile = new File(uploadedFilesPath, safeFileName);
							String copiedFileAbsolutePath = copiedFile.getAbsolutePath();
							part.write(copiedFileAbsolutePath);

							// If present, build up a map of headers.
							Map<String, List<String>> headersMap = new HashMap<String, List<String>>();
							Collection<String> headerNames = part.getHeaderNames();

							for (String headerName : headerNames) {
								List<String> headerValues = new ArrayList<String>(part.getHeaders(headerName));
								headersMap.put(headerName, headerValues);
							}

							// Get the Content-Type header
							String contentType = part.getContentType();

							// Get the charset from the Content-Type header
							String charSet = null;

							if (contentType != null) {
								keyValuePairs = contentType.split(StringPool.SEMICOLON);

								for (String keyValuePair : keyValuePairs) {
									String trimmedKeyValuePair = keyValuePair.trim();

									if (trimmedKeyValuePair.startsWith("charset")) {
										int equalsPos = trimmedKeyValuePair.indexOf(StringPool.EQUAL);
										charSet = trimmedKeyValuePair.substring(equalsPos + 2,
												trimmedKeyValuePair.length() - 1);
									}
								}
							}

							// Put a valid UploadedFile instance into the map that contains all of the
							// uploaded file's attributes, along with a successful status.
							Map<String, Object> attributeMap = new HashMap<String, Object>();
							String id = Long.toString(((long) hashCode()) + System.currentTimeMillis());
							String message = null;
							UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(copiedFileAbsolutePath,
									attributeMap, charSet, contentType, headersMap, id, message, fileName,
									part.getSize(), UploadedFile.Status.FILE_SAVED);

							addUploadedFile(uploadedFileMap, fieldName, uploadedFile);
							logger.debug("Received uploaded file fieldName=[{0}] fileName=[{1}]", fieldName, fileName);

							// Delete temporary file created by the Servlet API.
							part.delete();
						}
						catch (IOException e) {
							UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(e);
							addUploadedFile(uploadedFileMap, fieldName, uploadedFile);
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

		// If there was an error in parsing the request for file parts, then put a bogus UploadedFile instance in
		// the map so that the developer can have some idea that something went wrong.
		catch (Exception e) {
			logger.error(e);

			UploadedFile uploadedFile = uploadedFileFactory.getUploadedFile(e);
			addUploadedFile(uploadedFileMap, "unknown", uploadedFile);
		}

		return uploadedFileMap;
	}
}
