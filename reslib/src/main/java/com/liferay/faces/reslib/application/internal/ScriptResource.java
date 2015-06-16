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
package com.liferay.faces.reslib.application.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.ContentTypes;


/**
 * @author  Neil Griffin
 */
public class ScriptResource extends Resource {

	// Public Constants
	public static final String BASE_PATH = "build/";
	public static final String RESOURCE_NAME = "script";
	public static final String DUMMY_RESOURCE_NAME = "script-resource.txt";

	// Private Data Members
	private Resource internalResource;
	private String modulePath;
	private String requestPath;

	public ScriptResource() {
		setLibraryName(ResLibResourceHandler.LIBRARY_NAME);
		setResourceName(RESOURCE_NAME);
		setContentType(ContentTypes.TEXT_JAVASCRIPT);
	}

	@Override
	public boolean userAgentNeedsUpdate(FacesContext facesContext) {
		return getInternalResource().userAgentNeedsUpdate(facesContext);
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return getInternalResource().getInputStream();
	}

	protected Resource getInternalResource() {

		if (internalResource == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceHandler resourceHandlerChain = facesContext.getApplication().getResourceHandler();
			String resourceName = BASE_PATH + modulePath;
			internalResource = resourceHandlerChain.createResource(resourceName, ResLibResourceHandler.LIBRARY_NAME);
		}

		return internalResource;
	}

	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}

	@Override
	public String getRequestPath() {

		if (requestPath == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceHandler resourceHandlerChain = facesContext.getApplication().getResourceHandler();
			Resource dummyResource = resourceHandlerChain.createResource(DUMMY_RESOURCE_NAME, getLibraryName());
			String dummyResourceRequestPath = dummyResource.getRequestPath();
			requestPath = dummyResourceRequestPath.replace(DUMMY_RESOURCE_NAME, RESOURCE_NAME);
		}

		return requestPath;
	}

	@Override
	public Map<String, String> getResponseHeaders() {
		return getInternalResource().getResponseHeaders();
	}

	@Override
	public URL getURL() {
		return null;
	}
}
