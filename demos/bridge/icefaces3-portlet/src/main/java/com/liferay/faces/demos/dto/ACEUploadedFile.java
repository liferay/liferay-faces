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
package com.liferay.faces.demos.dto;

import java.io.File;

import org.icefaces.ace.component.fileentry.FileEntryResults.FileInfo;
import org.icefaces.ace.component.fileentry.FileEntryStatus;

import com.liferay.faces.bridge.UploadedFileImpl;
import com.liferay.faces.bridge.component.UploadedFile;


/**
 * This class provides a convenient mechanism for converting an ICEfaces {@link FileInfo} object to an instance of the
 * {@link UploadedFile} interface provided by the Liferay Faces Bridge implementation.
 *
 * @author  Neil Griffin
 */
public class ACEUploadedFile extends UploadedFileImpl {

	// serialVersionUID
	private static final long serialVersionUID = 2754946572315892739L;

	public ACEUploadedFile(String id, FileInfo fileInfo) {
		super();

		this.setId(id);

		if (fileInfo != null) {
			this.setContentType(fileInfo.getContentType());

			File file = fileInfo.getFile();

			if (file != null) {
				this.setAbsolutePath(file.getAbsolutePath());
			}

			this.setName(fileInfo.getFileName());
			this.setSize(fileInfo.getSize());

			FileEntryStatus fileEntryStatus = fileInfo.getStatus();

			if (fileEntryStatus.isSuccess()) {
				this.setStatus(Status.SAVED);
			}
			else {
				this.setStatus(Status.INVALID);
			}

		}
	}
}
