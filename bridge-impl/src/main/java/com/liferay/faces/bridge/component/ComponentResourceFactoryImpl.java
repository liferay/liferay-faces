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
package com.liferay.faces.bridge.component;

import java.util.Map;

import javax.faces.component.UIComponent;

import com.liferay.faces.util.application.ResourceConstants;


/**
 * @author  Neil Griffin
 */
public class ComponentResourceFactoryImpl extends ComponentResourceFactory {

	@Override
	public ComponentResource getComponentResource(UIComponent uiComponentResource) {

		Map<String, Object> attributes = uiComponentResource.getAttributes();
		String library = (String) attributes.get(ResourceConstants.LIBRARY);
		String name = (String) attributes.get(ResourceConstants.NAME);
		String id = ComponentResourceUtil.getId(library, name);
		boolean renderable = true;

		return new ComponentResourceImpl(id, library, name, renderable);
	}

	@Override
	public ComponentResourceFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
