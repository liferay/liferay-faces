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
package com.liferay.faces.util.context.internal;

import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.context.FacesRequestContextFactory;


/**
 * @author  Kyle Stiemann
 */
public class FacesRequestContextFactoryImpl extends FacesRequestContextFactory {

	@Override
	public FacesRequestContext getFacesRequestContext() {
		return new FacesRequestContextImpl();
	}

	@Override
	public FacesRequestContextFactory getWrapped() {
		return null;
	}
}
