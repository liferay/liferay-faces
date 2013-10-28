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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class UploadedFileInputStream extends FileInputStream {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UploadedFileInputStream.class);

	// Private Data Members
	private String absolutePath;

	public UploadedFileInputStream(String absolutePath) throws FileNotFoundException {
		super(absolutePath);
		this.absolutePath = absolutePath;
	}

	@Override
	public void close() throws IOException {
		super.close();

		try {
			File file = new File(absolutePath);
			file.delete();
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

}
