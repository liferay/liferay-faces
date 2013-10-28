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
package com.liferay.faces.bridge.renderkit.primefaces;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

import org.apache.commons.fileupload.FileItem;

import com.liferay.faces.bridge.model.UploadedFile;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class PrimeFacesFileItem implements FileItem {

	// serialVersionUID
	private static final long serialVersionUID = 4243775660521293895L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PrimeFacesFileItem.class);

	// Private Data Members
	private String clientId;
	private UploadedFile uploadedFile;

	public PrimeFacesFileItem(String clientId, UploadedFile uploadedFile) {
		this.clientId = clientId;
		this.uploadedFile = uploadedFile;
	}

	public void delete() {

		// Will never be called by the PrimeFaces UploadedFile interface.
		throw new UnsupportedOperationException();
	}

	public byte[] get() {

		byte[] bytes = null;

		try {
			File file = new File(uploadedFile.getAbsolutePath());

			if (file.exists()) {
				RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
				bytes = new byte[(int) randomAccessFile.length()];
				randomAccessFile.readFully(bytes);
				randomAccessFile.close();
				file.delete();
			}
		}
		catch (Exception e) {
			logger.error(e);
		}

		return bytes;
	}

	public void write(File file) throws Exception {

		// Will never be called by the PrimeFaces UploadedFile interface.
		throw new UnsupportedOperationException();
	}

	public String getContentType() {
		return uploadedFile.getContentType();
	}

	public boolean isFormField() {
		return false;
	}

	public String getFieldName() {
		return clientId;
	}

	public void setFieldName(String name) {
		clientId = name;
	}

	public void setFormField(boolean state) {

		// Will never be called by the PrimeFaces UploadedFile interface.
		throw new UnsupportedOperationException();
	}

	public InputStream getInputStream() throws IOException {
		return new UploadedFileInputStream(uploadedFile.getAbsolutePath());
	}

	public String getName() {
		return uploadedFile.getName();
	}

	public OutputStream getOutputStream() throws IOException {

		// Will never be called by the PrimeFaces UploadedFile interface.
		throw new UnsupportedOperationException();
	}

	public long getSize() {
		return uploadedFile.getSize();
	}

	public String getString() {
		return getString(StringPool.UTF8);
	}

	public String getString(String encoding) {
		String stringValue = null;
		byte[] bytes = get();

		if (bytes != null) {

			try {
				stringValue = new String(bytes, encoding);
			}
			catch (UnsupportedEncodingException e) {
				logger.error(e);
			}
		}

		return stringValue;
	}

	public boolean isInMemory() {
		return false;
	}

}
