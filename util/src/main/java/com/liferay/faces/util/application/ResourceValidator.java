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

import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public interface ResourceValidator {

	public boolean containsBannedPath(String resourceId);

	public boolean isBannedSequence(String resourceId);

	public boolean isValidLibraryName(String libraryName);

	public boolean isValidResourceName(String resourceName);

	public boolean isSelfReferencing(FacesContext facesContext, String resourceId);

	public boolean isFaceletDocument(FacesContext facesContext, String resourceId);
}
