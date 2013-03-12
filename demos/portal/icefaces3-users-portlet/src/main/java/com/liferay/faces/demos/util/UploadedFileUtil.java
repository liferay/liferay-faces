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
package com.liferay.faces.demos.util;

/**
 * @author  Kyle Stiemann
 */
public class UploadedFileUtil {

	// Public Constants
	public static final String USER_PORTRAIT = "user-portrait-";
	
	// Private Constants
	private static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
	private static final String TMP = ".tmp";

	public static String createFileName(String uploadedFileId) {
		return USER_PORTRAIT + uploadedFileId + TMP;
	}

	public static String getTempDir() {
		return System.getProperty(JAVA_IO_TMPDIR);
	}
}
