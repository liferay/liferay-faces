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
package com.liferay.faces.alloy.component.inputfile.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.webapp.FacesServlet;

import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.config.ConfiguredServlet;
import com.liferay.faces.util.config.MultiPartConfig;
import com.liferay.faces.util.config.WebConfig;
import com.liferay.faces.util.config.WebConfigParam;
import com.liferay.faces.util.model.UploadedFile;


/**
 * @author  Neil Griffin
 */
public abstract class InputFileDecoderBase implements InputFileDecoder {

	// Private Constants
	private static final String FACES_SERVLET_FQCN = FacesServlet.class.getName();

	@Override
	public abstract Map<String, List<UploadedFile>> decode(FacesContext facesContext, String location);

	protected void addUploadedFile(Map<String, List<UploadedFile>> uploadedFileMap, String fieldName,
		UploadedFile uploadedFile) {
		List<UploadedFile> uploadedFiles = uploadedFileMap.get(fieldName);

		if (uploadedFiles == null) {
			uploadedFiles = new ArrayList<UploadedFile>();
			uploadedFileMap.put(fieldName, uploadedFiles);
		}

		uploadedFiles.add(uploadedFile);
	}

	protected String stripIllegalCharacters(String fileName) {

		// FACES-64: Need to strip out invalid characters.
		// http://technet.microsoft.com/en-us/library/cc956689.aspx
		String strippedFileName = fileName;

		if (fileName != null) {

			int pos = fileName.lastIndexOf(".");
			strippedFileName = fileName.replaceAll("[\\\\/\\[\\]:|<>+;=.?\"]", "-");

			if (pos > 0) {
				strippedFileName = strippedFileName.substring(0, pos) + "." +
					strippedFileName.substring(pos + 1);
			}
		}

		return strippedFileName;
	}

	protected MultiPartConfig getFacesServletMultiPartConfig(ExternalContext externalContext) {

		MultiPartConfig facesServletMultiPartConfig = null;

		String appConfigAttrName = ApplicationConfig.class.getName();
		Map<String, Object> applicationMap = externalContext.getApplicationMap();
		ApplicationConfig applicationConfig = (ApplicationConfig) applicationMap.get(appConfigAttrName);
		WebConfig webConfig = applicationConfig.getWebConfig();
		List<ConfiguredServlet> configuredServlets = webConfig.getConfiguredServlets();

		for (ConfiguredServlet configuredServlet : configuredServlets) {

			if (FACES_SERVLET_FQCN.equals(configuredServlet.getServletClass())) {
				facesServletMultiPartConfig = configuredServlet.getMultiPartConfig();
			}
		}

		return facesServletMultiPartConfig;
	}

	protected String getUploadedFilesFolder(ExternalContext externalContext, String location) {
		String uploadedFilesDir = null;

		if ((location != null) && (location.length() > 0)) {
			uploadedFilesDir = location;
		}
		else {
			MultiPartConfig facesServletMultiPartConfig = getFacesServletMultiPartConfig(externalContext);

			if (facesServletMultiPartConfig != null) {
				uploadedFilesDir = facesServletMultiPartConfig.getLocation();
			}

			if (uploadedFilesDir == null) {
				uploadedFilesDir = WebConfigParam.UploadedFilesDir.getStringValue(externalContext);
			}
		}

		return uploadedFilesDir;
	}
}
