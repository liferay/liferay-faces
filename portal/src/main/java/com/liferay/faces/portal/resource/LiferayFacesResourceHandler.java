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
package com.liferay.faces.portal.resource;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;

import com.liferay.faces.util.resource.ResourceHandlerBase;


/**
 * @author  Neil Griffin
 * @author  Joe Ssemwogerere
 */
public class LiferayFacesResourceHandler extends ResourceHandlerBase {

	// public constants
	public static final String LIBRARY_NAME = "liferayfaces";

	public LiferayFacesResourceHandler(ResourceHandler wrappedResourceHandler) {
		super(wrappedResourceHandler);
	}

	@Override
	protected String getLibraryName() {
		return LIBRARY_NAME;
	}

	@Override
	protected Map<String, Resource> getResourceMap() {

		return new HashMap<String, Resource>() {

				{
					put(CaptchaResource.RESOURCE_NAME, new CaptchaResource());
				}
			};
	}
}
