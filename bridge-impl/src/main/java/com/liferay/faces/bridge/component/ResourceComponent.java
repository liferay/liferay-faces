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
package com.liferay.faces.bridge.component;

import java.util.Map;

import javax.faces.application.ResourceHandler;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;


/**
 * This class represents a JSF 2 component with a resource dependency. For example, if the <code>resourceName</code>
 * ends with CSS, then this component would be rendered with the Mojarra {@link
 * com.sun.faces.renderkit.html_basic.StyleSheetRenderer} class.
 *
 * @author  Neil Griffin
 */
public class ResourceComponent extends UIOutput {

	// Private Constants
	private static final String NAME = "name";
	private static final String LIBRARY = "library";
	private static final String TARGET = "target";

	public ResourceComponent(FacesContext facesContext, String resourceName, String resourceLibrary, String target) {
		ResourceHandler resourceHandler = facesContext.getApplication().getResourceHandler();
		setRendererType(resourceHandler.getRendererTypeForResourceName(resourceName));

		Map<String, Object> attributes = this.getAttributes();
		attributes.put(NAME, resourceName);
		attributes.put(LIBRARY, resourceLibrary);
		attributes.put(TARGET, target);
	}
}
