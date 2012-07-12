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
package com.liferay.faces.bridge.application;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class is here to help log error messages regarding missing resources.
 *
 * @author  "Neil Griffin"
 */
public class MissingResourceImpl extends Resource {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(MissingResourceImpl.class);

	// Private Constants
	private static final String ERROR_MSG =
		"Resource handler=[{0}] was unable to create a resource for resourceName=[{1}] libraryName=[{2}] contentType=[{3}]";
	private static final String RES_NOT_FOUND = "RES_NOT_FOUND";

	// Private Data Members
	private String contentType;
	ResourceHandler failedResourceHandler;
	private String libraryName;
	private String resourceName;

	public MissingResourceImpl(ResourceHandler failedResourceHandler, String resourceName) {
		this.failedResourceHandler = failedResourceHandler;
		this.resourceName = resourceName;
	}

	public MissingResourceImpl(ResourceHandler failedResourceHandler, String resourceName, String libraryName) {
		this.failedResourceHandler = failedResourceHandler;
		this.resourceName = resourceName;
		this.libraryName = libraryName;
	}

	public MissingResourceImpl(ResourceHandler failedResourceHandler, String resourceName, String libraryName,
		String contentType) {
		this.failedResourceHandler = failedResourceHandler;
		this.resourceName = resourceName;
		this.libraryName = libraryName;
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		return RES_NOT_FOUND;
	}

	@Override
	public boolean userAgentNeedsUpdate(FacesContext context) {
		logger.error(ERROR_MSG, failedResourceHandler, resourceName, libraryName, contentType);

		return false;
	}

	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		logger.error(ERROR_MSG, failedResourceHandler, resourceName, libraryName, contentType);

		return new ByteArrayInputStream(new byte[] {});
	}

	@Override
	public String getLibraryName() {
		return libraryName;
	}

	@Override
	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	@Override
	public String getRequestPath() {
		logger.error(ERROR_MSG, failedResourceHandler, resourceName, libraryName, contentType);

		return RES_NOT_FOUND;
	}

	@Override
	public String getResourceName() {
		return resourceName;
	}

	@Override
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Override
	public Map<String, String> getResponseHeaders() {
		logger.error(ERROR_MSG, failedResourceHandler, resourceName, libraryName, contentType);

		return new HashMap<String, String>();
	}

	@Override
	public URL getURL() {
		logger.error(ERROR_MSG, failedResourceHandler, resourceName, libraryName, contentType);

		return null;
	}

}
