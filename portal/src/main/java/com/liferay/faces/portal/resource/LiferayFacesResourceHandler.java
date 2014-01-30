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
package com.liferay.faces.portal.resource;

import java.io.IOException;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.bridge.application.ResourceHandlerBridgeImpl;
import com.liferay.faces.util.application.ResourceConstants;


/**
 * @author  Neil Griffin
 * @author  Joe Ssemwogerere
 */
public class LiferayFacesResourceHandler extends ResourceHandlerBridgeImpl {

	// public constants
	public static final String LIBRARY_NAME = "liferayfaces";

	public LiferayFacesResourceHandler(ResourceHandler resourceHandler) {
		super(resourceHandler);
	}

	@Override
	public Resource createResource(String resourceName, String libraryName) {

		if (LIBRARY_NAME.equals(libraryName)) {

			if (CaptchaResource.RESOURCE_NAME.equals(resourceName)) {
				return new CaptchaResource();
			}
			else {
				return super.createResource(resourceName, libraryName);
			}
		}
		else {
			return super.createResource(resourceName, libraryName);
		}
	}

	@Override
	public void handleResourceRequest(FacesContext facesContext) throws IOException {

		ExternalContext externalContext = facesContext.getExternalContext();
		String libraryName = externalContext.getRequestParameterMap().get(ResourceConstants.LN);
		String resourceName = externalContext.getRequestParameterMap().get(ResourceConstants.JAVAX_FACES_RESOURCE);

		if (LIBRARY_NAME.equals(libraryName) && CaptchaResource.RESOURCE_NAME.equals(resourceName)) {

			Resource resource = createResource(resourceName, libraryName);

			handleResource(facesContext, resource);
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
}
