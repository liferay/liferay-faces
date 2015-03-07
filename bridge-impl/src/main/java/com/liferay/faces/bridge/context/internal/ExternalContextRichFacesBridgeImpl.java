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
package com.liferay.faces.bridge.context.internal;

import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextWrapper;


/**
 * This class serves as a fix for FACES-2133 such that the {@link #getRequestPathInfo()} and {@link
 * #getRequestServletPath()} methods work around an implicit servlet dependency in the a4j:mediaOutput component.
 *
 * @author  Kyle Stiemann
 */
public class ExternalContextRichFacesBridgeImpl extends ExternalContextWrapper {

	// Private Data Members
	private ExternalContext wrappedExternalContext;

	public ExternalContextRichFacesBridgeImpl(ExternalContext wrappedExternalContext) {
		this.wrappedExternalContext = wrappedExternalContext;
	}

	/**
	 * When this method returns null, {@link org.richfaces.resource.ResourceUtils#getMappingForRequest()} assumes that
	 * the Faces Servlet URL mapping is extension based and strips the extension off the value returned by {@link
	 * ExternalContextRichFacesBridgeImpl#getRequestServletPath()}. This method always returns null to avoid adding code
	 * in this class to detect the actual Faces Servlet URL mapping since RichFaces never uses it.
	 */
	@Override
	public String getRequestPathInfo() {
		return null;
	}

	/**
	 * This method returns "/rfRes/org.richfaces.resource.MediaOutputResource.faces" so that {@link
	 * org.richfaces.resource.ResourceUtils#decodeResourceURL()} will return
	 * "/rfRes/org.richfaces.resource.MediaOutputResource" to {@link
	 * org.richfaces.resource.ResourceHandlerImpl#getResourcePathFromRequest()} which will cause {@link
	 * org.richfaces.resource.ResourceHandlerImpl} to handle the resource in this request as a {@link
	 * org.richfaces.resource.MediaOutputResource}.
	 */
	@Override
	public String getRequestServletPath() {
		return "/rfRes/org.richfaces.resource.MediaOutputResource.faces";
	}

	@Override
	public ExternalContext getWrapped() {
		return wrappedExternalContext;
	}
}
