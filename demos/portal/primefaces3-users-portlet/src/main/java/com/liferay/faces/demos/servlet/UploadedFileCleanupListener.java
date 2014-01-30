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
package com.liferay.faces.demos.servlet;

import java.io.File;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.liferay.faces.demos.util.UploadedFileUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides the ability to listen for session destroyed events so that temporary files can be deleted.
 *
 * @author  Kyle Stiemann
 */
public class UploadedFileCleanupListener implements HttpSessionListener {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UploadedFileCleanupListener.class);

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		// no-op
	}

	/**
	 * This method will be called by the servlet container when either the session expires or the user logs out of the
	 * portal. Its purpose is to delete any uploaded temporary files from the file system.
	 */
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

		try {

			String sessionId = httpSessionEvent.getSession().getId();
			String parent = UploadedFileUtil.getTempDir();
			String folderName = sessionId;
			File folder = new File(parent, folderName);
			File[] fileList = folder.listFiles().clone();

			for (File file : fileList) {

				if (file.getName().startsWith(UploadedFileUtil.USER_PORTRAIT)) {
					file.delete();
				}
			}

		}
		catch (Exception e) {
			logger.error(e);
		}
	}
}
