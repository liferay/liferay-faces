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
package com.liferay.faces.demos.resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import com.liferay.faces.demos.util.UploadedFileUtil;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.model.Image;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserConstants;
import com.liferay.portal.service.ImageServiceUtil;


/**
 * This class serves as a JSF2 {@link Resource} for a Liferay {@link User} portrait (image).
 *
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class UserPortraitResource extends Resource {

	// Public Constants
	public static final String RESOURCE_NAME = "portrait";

	// Private Constants
	private static final String PARAM_LIBRARY_NAME = "ln";
	
	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UserPortraitResource.class);

	// Private Data Members
	private String imagePath;
	private String requestPath;
	private String sessionId;
	private String uploadedFileId;
	private User user;

	/**
	 * This constructor should be called during the JSF lifecycle when it is necessary to determine the URL of the
	 * resource by subsequently calling {@link #getRequestPath()}.
	 *
	 * @param  imagePath       The URL prefix to the Liferay image servlet.
	 * @param  user            The user associated with the portrait image.
	 * @param  uploadedFileId  The id of the uploaded file. If a file has not been uploaded then the value should be
	 *                         null.
	 */
	public UserPortraitResource(String imagePath, User user, String uploadedFileId) {
		this(imagePath, user, uploadedFileId, null);
	}

	/**
	 * This constructor should be called outside of the JSF lifecycle by a {@link ResourceHandler} that subsequently
	 * needs to call {@link #getInputStream()}.
	 *
	 * @param  user            The user associated with the portrait image.
	 * @param  uploadedFileId  The id of the uploaded file as specified in the request parameter.
	 * @param  sessionId       The current session id.
	 */
	public UserPortraitResource(User user, String uploadedFileId, String sessionId) {
		this(null, user, uploadedFileId, sessionId);
	}

	private UserPortraitResource(String imagePath, User user, String uploadedFileId, String sessionId) {

		this.imagePath = imagePath;
		this.user = user;
		this.uploadedFileId = uploadedFileId;
		this.sessionId = sessionId;
		setLibraryName(UserPortraitResourceHandler.LIBRARY_NAME);
		setResourceName(RESOURCE_NAME);
	}

	@Override
	public boolean userAgentNeedsUpdate(FacesContext context) {

		// Since the portrait image might come from the Liferay database or from a temporary file that is uploaded by
		// the user, always return true.
		return true;
	}

	/**
	 * This method returns the data contained in the specified file as an array of bytes.
	 */
	protected byte[] getBytes(File file) throws IOException {

		if ((file == null) || !file.exists()) {
			return null;
		}

		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
		byte[] bytes = new byte[(int) randomAccessFile.length()];
		randomAccessFile.readFully(bytes);
		randomAccessFile.close();

		return bytes;
	}

	/**
	 * This method returns an {@link InputStream} to either the uploaded file or the portrait image from the Liferay
	 * database. It is designed to be called by a {@link ResourceHandler} outside of the JSF lifecycle.
	 */
	@Override
	public InputStream getInputStream() throws IOException {

		byte[] byteArray = null;

		// If the end-user didn't upload a file, then get the file from the Liferay database.
		if (uploadedFileId == null) {

			try {

				long imageId = user.getPortraitId();
				Image image = ImageServiceUtil.getImage(imageId);
				setContentType(image.getType());
				byteArray = image.getTextObj();
			}
			catch (Exception e) {

				logger.error(e);
				throw new IOException(e.getMessage());
			}
		}

		// Otherwise, get the uploaded file from the temporary directory.
		else {

			String absolutePath = getUploadedFile().getAbsolutePath();
			File file = new File(absolutePath);
			byteArray = getBytes(file);
		}

		return new ByteArrayInputStream(byteArray);
	}

	/**
	 * This method returns a the URL of the resource. It is designed to be called during the JSF lifecycle. For the sake
	 * of speed this method uses lazy initialization for the return value.
	 */
	@Override
	public String getRequestPath() {

		if (requestPath == null) {

			StringBuilder buf = new StringBuilder();

			// If the user doesn't have a portrait image in the Liferay database and the end-user hasn't uploaded a
			// file, then if the user is male, then return a URL to a male silhouette. Otherwise, return a URL to a
			// female silhouette.
			long portraitId = user.getPortraitId();

			if ((portraitId == 0L) && (uploadedFileId == null)) {

				boolean male = true;

				try {
					male = user.isMale();
				}
				catch (Exception e) {
					logger.error(e);
				}

				String portraitURL = UserConstants.getPortraitURL(imagePath, male, portraitId);
				buf.append(portraitURL);
			}

			// Otherwise, return a JSF2 resource URL that will invoke the portlet RESOURCE_PHASE, and the resource
			// handler delegation-chain.
			else {

				buf.append(ResourceHandler.RESOURCE_IDENTIFIER);
				buf.append(StringPool.FORWARD_SLASH);
				buf.append(getResourceName());
				buf.append(StringPool.QUESTION);
				buf.append(PARAM_LIBRARY_NAME);
				buf.append(StringPool.EQUAL);
				buf.append(getLibraryName());
				buf.append(StringPool.AMPERSAND);
				buf.append(UserPortraitResourceHandler.PARAM_NAME_USER_ID);
				buf.append(StringPool.EQUAL);
				buf.append(user.getUserId());

				if (uploadedFileId != null) {

					buf.append(StringPool.AMPERSAND);
					buf.append(UserPortraitResourceHandler.PARAM_NAME_UPLOADED_FILE_ID);
					buf.append(StringPool.EQUAL);
					buf.append(uploadedFileId);
				}

			}

			requestPath = buf.toString();
		}

		return requestPath;
	}

	@Override
	public Map<String, String> getResponseHeaders() {
		return null;
	}

	protected File getUploadedFile() {
		File uploadedFile = null;

		if (uploadedFileId != null) {

			String uploadedFileName = UploadedFileUtil.createFileName(uploadedFileId);
			String tempDir = UploadedFileUtil.getTempDir();
			File parent = new File(tempDir, sessionId);
			uploadedFile = new File(parent, uploadedFileName);
		}

		return uploadedFile;
	}

	@Override
	public URL getURL() {
		return null;
	}
}
