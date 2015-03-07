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
package com.liferay.faces.bridge.renderkit.richfaces.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
public class RichFacesUploadedFileHandler implements InvocationHandler, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 8136440019333815546L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(RichFacesUploadedFileHandler.class);

	// Private Constants
	private static final String METHOD_DELETE = "delete";
	private static final String METHOD_GET_CONTENT_TYPE = "getContentType";
	private static final String METHOD_GET_DATA = "getData";
	private static final String METHOD_GET_INPUT_STREAM = "getInputStream";
	private static final String METHOD_GET_NAME = "getName";
	private static final String METHOD_GET_SIZE = "getSize";
	private static final String METHOD_WRITE = "write";

	// Private Data Members
	private UploadedFile uploadedFile;

	public RichFacesUploadedFileHandler(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		String methodName = method.getName();

		if (METHOD_DELETE.equals(methodName)) {
			File file = new File(uploadedFile.getAbsolutePath());
			file.delete();

			return null;
		}
		else if (METHOD_GET_CONTENT_TYPE.equals(methodName)) {
			return uploadedFile.getContentType();
		}
		else if (METHOD_GET_DATA.equals(methodName)) {
			return getBytes();
		}
		else if (METHOD_GET_INPUT_STREAM.equals(methodName)) {
			return new FileInputStream(uploadedFile.getAbsolutePath());
		}
		else if (METHOD_GET_NAME.equals(methodName)) {
			return uploadedFile.getName();
		}
		else if (METHOD_GET_SIZE.equals(methodName)) {
			return uploadedFile.getSize();
		}
		else if (METHOD_WRITE.equals(methodName)) {
			String fileName = (String) args[0];
			OutputStream outputStream = new FileOutputStream(fileName);
			outputStream.write(getBytes());
			outputStream.close();

			return null;
		}
		else {

			// Unsupported method.
			return null;
		}
	}

	protected byte[] getBytes() {
		byte[] bytes = null;

		try {
			File file = new File(uploadedFile.getAbsolutePath());

			if (file.exists()) {
				RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
				bytes = new byte[(int) randomAccessFile.length()];
				randomAccessFile.readFully(bytes);
				randomAccessFile.close();
			}
		}
		catch (Exception e) {
			logger.error(e);
		}

		return bytes;
	}
}
