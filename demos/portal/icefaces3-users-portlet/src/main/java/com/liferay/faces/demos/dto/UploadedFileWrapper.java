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
package com.liferay.faces.demos.dto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesWrapper;

import org.icefaces.ace.component.fileentry.FileEntryResults.FileInfo;
import org.icefaces.ace.component.fileentry.FileEntryStatus;

import com.liferay.faces.bridge.model.UploadedFile;
import com.liferay.faces.demos.util.UploadedFileUtil;


/**
 * This class provides a convenient mechanism for converting an ICEfaces {@link FileInfo} object to an instance of the
 * {@link UploadedFile} interface provided by the Liferay Faces Bridge implementation.
 *
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class UploadedFileWrapper implements Serializable, UploadedFile, FacesWrapper<FileInfo> {

	// serialVersionUID
	private static final long serialVersionUID = 4395129564014802592L;

	// Private Data Members
	Map<String, Object> attributeMap;
	private String id;
	private File renamedFile;
	private UploadedFile.Status status;
	private FileInfo wrappedFileInfo;

	public UploadedFileWrapper(FileInfo fileInfo) {
		this.wrappedFileInfo = fileInfo;
		this.attributeMap = new HashMap<String, Object>();
		this.id = Long.toString(((long) hashCode()) + System.currentTimeMillis());

		File icefacesFile = fileInfo.getFile();
		String parent = icefacesFile.getParent();

		// Rename the file to a predictable filename so that the UserPortraitResource class will be able to find the
		// uploaded file in the temporary folder.
		String newFileName = UploadedFileUtil.createFileName(this.id);
		this.renamedFile = new File(parent, newFileName);
		icefacesFile.renameTo(this.renamedFile);

		FileEntryStatus fileEntryStatus = wrappedFileInfo.getStatus();

		if (fileEntryStatus.isSuccess()) {
			status = Status.FILE_SAVED;
		}
		else {
			status = Status.ERROR;
		}
	}

	public void delete() throws IOException {
		renamedFile.delete();
	}

	public void write(String fileName) throws IOException {
		OutputStream outputStream = new FileOutputStream(fileName);
		outputStream.write(getBytes());
		outputStream.close();
	}

	public String getAbsolutePath() {
		return renamedFile.getAbsolutePath();
	}

	public Map<String, Object> getAttributes() {
		return attributeMap;
	}

	public byte[] getBytes() throws IOException {

		byte[] bytes = null;

		try {
			File file = renamedFile;

			if (file.exists()) {
				RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
				bytes = new byte[(int) randomAccessFile.length()];
				randomAccessFile.readFully(bytes);
				randomAccessFile.close();
			}
		}
		catch (Exception e) {
			throw new IOException(e.getMessage());
		}

		return bytes;
	}

	public String getCharSet() {
		throw new UnsupportedOperationException();
	}

	public String getContentType() {
		return wrappedFileInfo.getContentType();
	}

	public String getHeader(String name) {
		throw new UnsupportedOperationException();
	}

	public Collection<String> getHeaderNames() {
		throw new UnsupportedOperationException();
	}

	public Collection<String> getHeaders(String name) {
		throw new UnsupportedOperationException();
	}

	public String getId() {
		return id;
	}

	public InputStream getInputStream() throws IOException {
		return new FileInputStream(renamedFile);
	}

	public String getMessage() {
		throw new UnsupportedOperationException();
	}

	public String getName() {
		return wrappedFileInfo.getFileName();
	}

	public long getSize() {
		return wrappedFileInfo.getSize();
	}

	public Status getStatus() {
		return status;
	}

	public FileInfo getWrapped() {
		return wrappedFileInfo;
	}
}
