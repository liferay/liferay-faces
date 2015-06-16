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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.List;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.ContentTypes;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ComboResource extends Resource {

	// Public Constants
	public static final String RESOURCE_NAME = "combo";
	public static final String DUMMY_RESOURCE_NAME = "combo-resource.txt";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ComboResource.class);

	// Private Constants
	private static final String RESOURCE_PATH_BASE = "META-INF/resources/liferay-faces-reslib/";

	// Private Data Members
	private List<String> modulePaths;
	private String requestPath;

	public ComboResource() {
		setLibraryName(ResLibResourceHandler.LIBRARY_NAME);
		setResourceName(RESOURCE_NAME);
	}

	@Override
	public boolean userAgentNeedsUpdate(FacesContext facesContext) {
		return true;
	}

	protected void writeModuleBytes(String modulePath, OutputStream outputStream) throws IOException {

		String resourcePath = RESOURCE_PATH_BASE + modulePath;
		URL resourceURL = ComboResource.class.getClassLoader().getResource(resourcePath);

		logger.debug("resourcePath=[{0}] resourceURL=[{1}]", resourcePath, resourceURL);

		ReadableByteChannel readableByteChannel = null;
		WritableByteChannel writableByteChannel = null;
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

		InputStream inputStream = resourceURL.openStream();

		if (inputStream != null) {

			int responseContentLength = 0;
			readableByteChannel = Channels.newChannel(inputStream);
			writableByteChannel = Channels.newChannel(outputStream);

			int bytesRead = readableByteChannel.read(byteBuffer);

			int bytesWritten = 0;

			while (bytesRead != -1) {
				byteBuffer.rewind();
				byteBuffer.limit(bytesRead);

				do {
					bytesWritten += writableByteChannel.write(byteBuffer);
				}
				while (bytesWritten < responseContentLength);

				byteBuffer.clear();
				responseContentLength += bytesRead;
				bytesRead = readableByteChannel.read(byteBuffer);
			}

			inputStream.close();
		}
		else {
			logger.error("Unable to locate resourcePath=[{0}] resourceURL=[{1}]", resourcePath, resourceURL);
		}
	}

	@Override
	public String getContentType() {

		String contentType = ContentTypes.TEXT_PLAIN;

		if ((modulePaths != null) && (modulePaths.size() > 0)) {
			String firstModulePath = modulePaths.get(0);

			if (firstModulePath.endsWith(".css")) {
				contentType = ContentTypes.TEXT_CSS;
			}
			else if (firstModulePath.endsWith(".js")) {
				contentType = ContentTypes.TEXT_JAVASCRIPT;
			}
		}

		return contentType;
	}

	@Override
	public InputStream getInputStream() throws IOException {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		for (String modulePath : modulePaths) {

			writeModuleBytes(modulePath, byteArrayOutputStream);
		}

		byte[] byteArray = byteArrayOutputStream.toByteArray();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);

		return byteArrayInputStream;
	}

	public void setModulePaths(List<String> modulePaths) {
		this.modulePaths = modulePaths;
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
		return null;
	}

	@Override
	public URL getURL() {
		return null;
	}
}
