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
package com.liferay.faces.demos.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.liferay.faces.bridge.model.UploadedFile;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
@ViewScoped
@ManagedBean
public class BridgeInputFileModelBean implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 211172404377673109L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeInputFileModelBean.class);

	// Private Data Members
	private List<UploadedFile> uploadedFiles;

	@PostConstruct
	public void postConstruct() {
		this.uploadedFiles = new ArrayList<UploadedFile>();
	}

	@PreDestroy
	public void preDestroy() {

		for (UploadedFile uploadedFile : uploadedFiles) {

			try {
				uploadedFile.delete();
			}
			catch (IOException e) {
				logger.error(e);
			}
		}

		uploadedFiles = null;
	}

	public List<UploadedFile> getUploadedFiles() {
		return uploadedFiles;
	}
}
