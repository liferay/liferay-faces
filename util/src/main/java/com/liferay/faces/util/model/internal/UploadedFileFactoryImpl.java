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
package com.liferay.faces.util.model.internal;

import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.FileUploadIOException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;

import com.liferay.faces.util.model.UploadedFile;
import com.liferay.faces.util.model.UploadedFileFactory;


/**
 * @author  Neil Griffin
 */
public class UploadedFileFactoryImpl extends UploadedFileFactory {

	@Override
	public UploadedFile getUploadedFile(Exception e) {

		UploadedFile uploadedFile = null;
		FileUploadException fileUploadException = null;

		if (e instanceof FileUploadException) {
			fileUploadException = (FileUploadException) e;
		}

		if (e instanceof FileUploadIOException) {
			Throwable causeThrowable = e.getCause();

			if (causeThrowable instanceof FileUploadException) {
				fileUploadException = (FileUploadException) causeThrowable;
			}
		}

		if (fileUploadException != null) {

			if (fileUploadException instanceof SizeLimitExceededException) {
				uploadedFile = new UploadedFileErrorImpl(fileUploadException.getMessage(),
						UploadedFile.Status.REQUEST_SIZE_LIMIT_EXCEEDED);
			}
			else if (fileUploadException instanceof FileSizeLimitExceededException) {
				uploadedFile = new UploadedFileErrorImpl(fileUploadException.getMessage(),
						UploadedFile.Status.FILE_SIZE_LIMIT_EXCEEDED);
			}
			else {
				uploadedFile = new UploadedFileErrorImpl(fileUploadException.getMessage());
			}
		}
		else {
			uploadedFile = new UploadedFileErrorImpl(e.getMessage());
		}

		return uploadedFile;
	}

	@Override
	public UploadedFile getUploadedFile(String absolutePath, Map<String, Object> attributes, String charSet,
		String contentType, Map<String, List<String>> headers, String id, String message, String name, long size,
		UploadedFile.Status status) {

		UploadedFile uploadedFile = null;

		if (uploadedFile == null) {
			uploadedFile = new UploadedFileImpl(absolutePath, attributes, charSet, contentType, headers, id, message,
					name, size, status);
		}

		return uploadedFile;
	}

	public UploadedFileFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}
}
