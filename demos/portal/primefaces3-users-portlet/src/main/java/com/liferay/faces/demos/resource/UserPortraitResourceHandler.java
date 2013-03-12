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
package com.liferay.faces.demos.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletSession;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * This class serves as a JSF2 {@link ResourceHandler} that has the ability to write the contents of either the uploaded
 * file or the portrait image from the Liferay database to the underlying portlet {@link ResourceResponse}.
 *
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class UserPortraitResourceHandler extends ResourceHandlerWrapper {

	// Public Constants
	public static final String LIBRARY_NAME = "userPortraitResources";
	public static final String PARAM_NAME_USER_ID = "UserId";
	public static final String PARAM_NAME_UPLOADED_FILE_ID = "uploadedFileId";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(UserPortraitResourceHandler.class);

	// Private Data Members
	private ResourceHandler wrappedResourceHandler;

	public UserPortraitResourceHandler(ResourceHandler wrappedResourceHandler) {
		this.wrappedResourceHandler = wrappedResourceHandler;
	}

	@Override
	public Resource createResource(String resourceName, String libraryName) {

		// If the specified library name is known by this resource handler, then
		if (LIBRARY_NAME.equals(libraryName)) {

			// If the specified resource name is a portrait resource, then
			if (UserPortraitResource.RESOURCE_NAME.equals(resourceName)) {

				// Assume that this method is being called by the handleResourceRequest method. In this case, this
				// resource handler was invoked by a JSF2 resource URL that was determined by the
				// UserPortraitResource.getRequestPath() method. The resource URL will contain a "userId" parameter and
				// optionally an "uploadedFileId" parameter.
				FacesContext facesContext = FacesContext.getCurrentInstance();
				Map<String, String> requestParameterMap = facesContext.getExternalContext().getRequestParameterMap();
				String userId = requestParameterMap.get(PARAM_NAME_USER_ID);
				String uploadedFileId = requestParameterMap.get(PARAM_NAME_UPLOADED_FILE_ID);

				// Determine the current "sessionId" so that the UserPortraitResource class will be able to find the
				// uploaded file.
				ExternalContext externalContext = facesContext.getExternalContext();
				PortletSession portletSession = (PortletSession) externalContext.getSession(true);
				String sessionId = portletSession.getId();

				// Get the user associated with the "userId" URL parameter.
				User user = null;

				try {
					user = UserLocalServiceUtil.getUser(Long.parseLong(userId));
				}
				catch (Exception e) {
					logger.error(e);
				}

				// Return an instance of UserPortraitResource that has the ability to retrieve the uploaded file or
				// portrait image from the Liferay database.
				return new UserPortraitResource(user, uploadedFileId, sessionId);
			}

			// Otherwise, delegate creation of the resource to the delegation-chain.
			else {
				return wrappedResourceHandler.createResource(resourceName, libraryName);
			}
		}

		// Otherwise, delegate creation of the resource to the delegation-chain.
		else {
			return wrappedResourceHandler.createResource(resourceName, libraryName);
		}
	}

	@Override
	public void handleResourceRequest(FacesContext facesContext) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		String libraryName = externalContext.getRequestParameterMap().get("ln");
		String resourceName = externalContext.getRequestParameterMap().get("javax.faces.resource");

		if (LIBRARY_NAME.equals(libraryName) && UserPortraitResource.RESOURCE_NAME.equals(resourceName)) {

			Resource resource = createResource(resourceName, libraryName);

			ReadableByteChannel readableByteChannel = null;
			WritableByteChannel writableByteChannel = null;
			InputStream inputStream = null;
			int bufferSize = 1024;
			ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);

			try {

				// Open an input stream in order to read the resource's contents/data.
				inputStream = resource.getInputStream();

				if (inputStream != null) {

					// Set the response buffer size.
					int responseBufferSize = byteBuffer.capacity();
					externalContext.setResponseBufferSize(responseBufferSize);

					String responseContentType = resource.getContentType();

					if (responseContentType != null) {
						externalContext.setResponseContentType(responseContentType);
					}

					// Copy the bytes in the resource's input stream to the response's output stream.
					int responseContentLength = 0;
					readableByteChannel = Channels.newChannel(inputStream);
					writableByteChannel = Channels.newChannel(externalContext.getResponseOutputStream());

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

						if (logger.isTraceEnabled()) {

							// Surround with isTraceEnabled check in order to avoid unnecessary conversion
							// of int to String.
							logger.trace("Handling - MORE bytesRead=[{0}]", Integer.toString(bytesRead));
						}
					}

					// Now that we know how big the file is, set the response content length.
					externalContext.setResponseContentLength(responseContentLength);
					externalContext.setResponseStatus(HttpServletResponse.SC_OK);

					logger.debug(
						"HANDLED (SC_OK) resourceName=[{0}], libraryName[{1}], responseContentType=[{2}], responseContentLength=[{3}]",
						new Object[] { resourceName, libraryName, responseContentType, responseContentLength });
				}
			}
			catch (IOException e) {
				externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
				logger.error("NOT HANDLED (SC_NOT_FOUND) resourceName=[{0}], libraryName[{1}], errorMessage=[{2}]",
					new Object[] { resourceName, libraryName, e.getMessage() }, e);
			}
			finally {

				if (writableByteChannel != null) {
					writableByteChannel.close();
				}

				if (readableByteChannel != null) {
					readableByteChannel.close();
				}

				if (inputStream != null) {
					inputStream.close();
				}
			}
		}
		else {
			super.handleResourceRequest(facesContext);
		}
	}

	@Override
	public boolean libraryExists(String libraryName) {

		if (LIBRARY_NAME.equals(libraryName)) {
			return true;
		}
		else {
			return super.libraryExists(libraryName);
		}
	}

	@Override
	public ResourceHandler getWrapped() {
		return wrappedResourceHandler;
	}

}
