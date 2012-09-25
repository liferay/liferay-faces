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
package com.liferay.faces.portal.resource;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;


/**
 * This class serves as a JSF {@link ResourceHandler} that has the ability to create resources with the "liferayportal"
 * library name.
 *
 * @author  Neil Griffin
 */
public class LiferayPortalResourceHandler extends ResourceHandlerWrapper {

	// Private Constants
	private static final String LIFERAY_PORTAL = "liferayportal";

	// Private Data Members
	private ResourceHandler wrappedResourceHandler;

	public LiferayPortalResourceHandler(ResourceHandler resourceHandler) {
		this.wrappedResourceHandler = resourceHandler;
	}

	@Override
	public Resource createResource(String resourceName, String libraryName) {

		if (LIFERAY_PORTAL.equals(libraryName)) {
			return new LiferayPortalResource(resourceName);
		}
		else {
			return super.createResource(resourceName, libraryName);
		}
	}

	@Override
	public ResourceHandler getWrapped() {
		return wrappedResourceHandler;
	}

}
