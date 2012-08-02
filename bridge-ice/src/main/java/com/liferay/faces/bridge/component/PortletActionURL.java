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
package com.liferay.faces.bridge.component;

import javax.faces.component.UIOutput;


/**
 * The default renderer for this component is {@link ActionURLRenderer}. It conforms as closely as possible to the
 * requirements set forth in section PLT.26.2 of the JSR 286 Portlet Specification, Version 2.0.
 *
 * @author  Neil Griffin
 */
public class PortletActionURL extends UIOutput {

	public static final String COMPONENT_TYPE = "com.liferay.faces.bridge.component.PortletActionURL";
	public static final String DEFAULT_RENDERER_TYPE = "com.liferay.faces.bridge.renderkit.portlet.ActionURLRenderer";

	public String getComponentType() {
		return COMPONENT_TYPE;
	}

	@Override
	public String getRendererType() {
		return DEFAULT_RENDERER_TYPE;
	}

}
