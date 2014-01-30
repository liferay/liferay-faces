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
import javax.faces.application.ResourceHandlerWrapper;


/**
 * Unlike the {@link ResourceHandlerInnerImpl} class, this class is designed to be the outermost {@link ResourceHandler}
 * in the chain-of-responsibility. In order to achieve this outermost status, this class does not appear in the bridge's
 * faces-config.xml descriptor. Rather, it is created by the {@link ApplicationImpl#getResourceHandler()} method. If
 * necessary, it wraps resources with {@link ResourceHandlerWrapper} instances that override the {@link
 * Resource#getRequestPath()} method. This provides the bridge with the opportunity to have the final authority
 * regarding the format of resource URLs created by ResourceHandlers like the ones provided by ICEfaces, PrimeFaces, and
 * RichFaces.
 *
 * @author  Neil Griffin
 */
public class ResourceHandlerOuterImpl extends ResourceHandlerWrapper {

	// Private Constants
	private static final String ORG_RICHFACES_RESOURCE = "org.richfaces.resource";

	// Private Data Members
	private ResourceHandler wrappedResourceHandler;

	public ResourceHandlerOuterImpl(ResourceHandler resourceHandler) {
		this.wrappedResourceHandler = resourceHandler;
	}

	@Override
	public Resource createResource(String resourceName) {

		Resource resource = super.createResource(resourceName);

		if ((resource != null) && resource.getClass().getName().startsWith(ORG_RICHFACES_RESOURCE)) {
			resource = new ResourceRichFacesImpl(resource);
		}

		return resource;
	}

	@Override
	public Resource createResource(String resourceName, String libraryName) {

		Resource resource = super.createResource(resourceName, libraryName);

		if ((resource != null) && resource.getClass().getName().startsWith(ORG_RICHFACES_RESOURCE)) {
			resource = new ResourceRichFacesImpl(resource);
		}

		return resource;
	}

	@Override
	public Resource createResource(String resourceName, String libraryName, String contentType) {

		Resource resource = super.createResource(resourceName, libraryName, contentType);

		if ((resource != null) && resource.getClass().getName().startsWith(ORG_RICHFACES_RESOURCE)) {
			resource = new ResourceRichFacesImpl(resource);
		}

		return resource;
	}

	@Override
	public ResourceHandler getWrapped() {
		return wrappedResourceHandler;
	}

}
