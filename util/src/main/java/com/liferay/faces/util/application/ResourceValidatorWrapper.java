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
package com.liferay.faces.util.application;

import javax.faces.FacesWrapper;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public abstract class ResourceValidatorWrapper implements ResourceValidator, FacesWrapper<ResourceValidator> {

	@Override
	public boolean containsBannedPath(String resourceId) {
		return getWrapped().containsBannedPath(resourceId);
	}

	@Override
	public boolean isBannedSequence(String resourceId) {
		return getWrapped().isBannedSequence(resourceId);
	}

	@Override
	public boolean isValidLibraryName(String libraryName) {
		return getWrapped().isValidLibraryName(libraryName);
	}

	@Override
	public boolean isValidResourceName(String resourceName) {
		return getWrapped().isValidResourceName(resourceName);
	}

	@Override
	public boolean isSelfReferencing(FacesContext facesContext, String resourceId) {
		return getWrapped().isSelfReferencing(facesContext, resourceId);
	}

	@Override
	public boolean isFaceletDocument(FacesContext facesContext, String resourceId) {
		return getWrapped().isFaceletDocument(facesContext, resourceId);
	}

	@Override
	public abstract ResourceValidator getWrapped();
}
