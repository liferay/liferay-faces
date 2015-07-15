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
package com.liferay.faces.bridge.application.internal;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.util.application.ResourceValidator;
import com.liferay.faces.util.application.ResourceValidatorFactory;
import com.liferay.faces.util.io.ResourceOutputStream;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * Unlike the {@link ResourceHandlerOuterImpl} class, this class is designed to be the innermost {@link ResourceHandler}
 * in the chain-of-responsibility (only the Mojarra/MyFaces ResourceHandlerImpl has a more inner status). In order to
 * achive this innermost status, it is registered in the application section of the bridge's faces-config.xml
 * descriptor. It is responsible for wrapping resources created by Mojarra/MyFaces so that resource URLs will work in a
 * portlet environment. It is also responsible for serving up resources via the {@link
 * #handleResourceRequest(FacesContext)} method.
 *
 * @author  Neil Griffin
 */
public class ResourceHandlerInnerImpl extends ResourceHandlerBridgeImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ResourceHandlerInnerImpl.class);

	// Private Constants
	private static final String RICHFACES_STATIC_RESOURCE = "org.richfaces.staticResource";

	public ResourceHandlerInnerImpl(ResourceHandler resourceHandler) {
		super(resourceHandler);
	}

	@Override
	public void handleResourceRequest(FacesContext facesContext) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();

		String resourceName = requestParameterMap.get("javax.faces.resource");

		if (resourceName == null) {
			resourceName = "";
		}

		String libraryName = requestParameterMap.get("ln");

		if (libraryName == null) {
			libraryName = "";
		}

		String resourceId = libraryName + "/" + resourceName;

		ResourceValidatorFactory resourceValidatorFactory = (ResourceValidatorFactory) BridgeFactoryFinder.getFactory(
				ResourceValidatorFactory.class);
		ResourceValidator resourceValidator = resourceValidatorFactory.getResourceValidator();

		// If the resource name or library name contains a banned path like WEB-INF or META-INF, then do not serve
		// the resource.
		if (resourceValidator.containsBannedPath(resourceId)) {

			logger.warn("Invalid path for resourceId=[{0}]", resourceId);
			externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
		}

		// Otherwise, if the resource name or library name contains a banned sequence like double slashes, etc. then
		// do not serve the resource.
		else if (resourceValidator.isBannedSequence(resourceId)) {

			logger.warn("Invalid sequence for resourceId=[{0}]", resourceId);
			externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
		}

		// Otherwise, if the resource name or library name targets a Facelet document, then do not serve the
		// resource.
		else if (resourceValidator.isFaceletDocument(facesContext, resourceId)) {

			logger.warn("Invalid request for Facelet document resourceId=[{0}]", resourceId);
			externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		else if (!resourceValidator.isValidResourceName(resourceName)) {

			logger.warn("Invalid request due to invalid resourceName=[{0}]", resourceName);
			externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		else if (!resourceValidator.isValidLibraryName(libraryName)) {

			logger.warn("Invalid request due to invalid libraryName=[{0}]", libraryName);
			externalContext.setResponseStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		else {
			super.handleResourceRequest(facesContext);
		}
	}

	@Override
	protected ResourceOutputStream getResourceOutputStream(Resource resource, int size) {

		String resourceName = resource.getResourceName();

		// If this is a RichFaces static resource, then return a resource output stream that knows hot to filter
		// RichFaces static resources.
		if (resourceName.startsWith(RICHFACES_STATIC_RESOURCE)) {
			return new ResourceOutputStreamRichFacesImpl(resource, size);
		}

		// Otherwise, return a normal resource output stream.
		else {
			return super.getResourceOutputStream(resource, size);
		}
	}

}
