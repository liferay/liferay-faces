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

import java.util.Map;

import javax.faces.component.UIComponent;

import com.liferay.faces.bridge.component.ComponentResource;
import com.liferay.faces.bridge.component.ComponentResourceImpl;
import com.liferay.faces.bridge.component.ComponentResourceUtil;
import com.liferay.faces.util.application.ResourceConstants;


/**
 * @author      Neil Griffin
 * @deprecated  Use {@link ComponentResourceImpl} instead.
 */
@Deprecated
public class ResourceInfo extends ComponentResourceImpl {

	// Private Data Members
	private String className;

	public ResourceInfo(UIComponent uiComponentResource) {
		this(uiComponentResource.getAttributes(), true);
		this.className = uiComponentResource.getClass().getName();
	}

	public ResourceInfo(Map<String, Object> componentAttributes, boolean renderable) {
		this((String) componentAttributes.get(ResourceConstants.LIBRARY),
			(String) componentAttributes.get(ResourceConstants.NAME), renderable);
	}

	public ResourceInfo(String library, String name, boolean renderable) {
		super(ComponentResourceUtil.getId(library, name), library, name, renderable);
	}

	/**
	 * @deprecated  Call {@link ComponentResource#getComponentClassName()} instead.
	 */
	@Deprecated
	public String getClassName() {
		return className;
	}
}
