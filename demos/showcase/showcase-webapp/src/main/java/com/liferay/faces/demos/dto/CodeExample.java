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
package com.liferay.faces.demos.dto;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;

import com.liferay.faces.demos.util.CodeExampleUtil;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class CodeExample {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(CodeExample.class);

	// Private Data Members
	private String fileName;
	private long lastModified;
	private String rawText;
	private URL url;

	public CodeExample(String fileName, URL url, long lastModified, String rawText) {
		this.fileName = fileName;
		this.url = url;
		this.lastModified = lastModified;
		this.rawText = rawText;
	}

	public String getFileName() {
		return fileName;
	}

	public long getLastModified() {
		return lastModified;
	}

	public String getRawText() {

		boolean developmentMode = false;
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (facesContext != null) {
			developmentMode = facesContext.isProjectStage(ProjectStage.Development);
		}

		if (developmentMode) {

			try {
				URLConnection urlConnection = url.openConnection();
				long lastModified = urlConnection.getLastModified();

				if (lastModified > this.lastModified) {

					CodeExample updatedCodeExample = CodeExampleUtil.load(url, fileName);
					this.lastModified = lastModified;
					this.rawText = updatedCodeExample.getRawText();
				}
			}
			catch (IOException e) {
				logger.error(e);
			}
		}

		return rawText;
	}

	public URL getURL() {
		return url;
	}
}
