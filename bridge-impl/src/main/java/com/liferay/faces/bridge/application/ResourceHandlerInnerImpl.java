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
package com.liferay.faces.bridge.application;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.io.ResourceOutputStream;


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

	// Private Constants
	private static final String RICHFACES_STATIC_RESOURCE = "org.richfaces.staticResource";

	public ResourceHandlerInnerImpl(ResourceHandler resourceHandler) {
		super(resourceHandler);
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
