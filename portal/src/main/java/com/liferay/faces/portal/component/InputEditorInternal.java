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
package com.liferay.faces.portal.component;

import javax.faces.component.UIOutput;


/**
 * @author  Neil Griffin
 */
public class InputEditorInternal extends UIOutput {

	private static final String COMPONENT_TYPE = "com.liferay.faces.portal.InputEditorInternal";
	private static final String RENDERER_TYPE = "com.liferay.faces.portal.InputEditorInternalRenderer";

	public InputEditorInternal() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getFamily() {
		return COMPONENT_TYPE;
	}

}
