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
package com.liferay.faces.portal.resource;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;


/**
 * @author  Neil Griffin
 * @author  Joe Ssemwogerere
 */
public class LiferayFacesResourceHandler extends ResourceHandlerWrapper {

	// Public Constants
	public static final String LIBRARY_NAME = "liferayfaces";

	// Private Data Members
	private ResourceHandler wrappedResourceHandler;

	public LiferayFacesResourceHandler(ResourceHandler resourceHandler) {
		this.wrappedResourceHandler = resourceHandler;
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
