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
package com.liferay.faces.alloy.component.commandlink.internal;
//J-


import javax.annotation.Generated;

import com.liferay.faces.alloy.component.commandlink.CommandLink;

import com.liferay.faces.util.render.DelegatingRendererBase;


/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class CommandLinkRendererBase extends DelegatingRendererBase {

	// Protected Constants
	protected static final String AJAX = "ajax";
	protected static final String EXECUTE = "execute";
	protected static final String PROCESS = "process";
	protected static final String RENDER = "render";
	protected static final String STYLE_CLASS = "styleClass";
	protected static final String UPDATE = "update";

	@Override
	public String getDelegateComponentFamily() {
		return CommandLink.COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return "javax.faces.Link";
	}
}
//J+
