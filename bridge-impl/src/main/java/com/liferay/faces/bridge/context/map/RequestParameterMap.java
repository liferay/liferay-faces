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
package com.liferay.faces.bridge.context.map;

import com.liferay.faces.bridge.util.AbstractPropertyMap;


/**
 * This is simply a marker abstract class that represents a map of request parameters.
 */
public abstract class RequestParameterMap extends AbstractPropertyMap<String> {

	// Public Constants
	public static final String PARAM_UPLOADED_FILES = "com.liferay.faces.bridge.UPLOADED_FILES";

}
