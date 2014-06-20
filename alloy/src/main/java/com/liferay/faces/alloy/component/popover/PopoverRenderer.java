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
package com.liferay.faces.alloy.component.popover;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Vernon Singleton
 */
//J-
@FacesRenderer(componentFamily = Popover.COMPONENT_FAMILY, rendererType = Popover.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
	}
)
//J+
public class PopoverRenderer extends PopoverRendererBase {

	// Private Constants
	private static final String ALIGN = "align";
	private static final String NODE = "node";

	protected void encodeAlign(ResponseWriter responseWriter, Popover popover, boolean first) throws IOException {

		encodeNonEscapedObject(responseWriter, ALIGN, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		String for_ = popover.getFor();
		encodeClientId(responseWriter, NODE, for_, popover, true);

		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, Popover popover,
		boolean first) throws IOException {
		encodeAlign(responseWriter, popover, first);
		first = false;

		encodeOverlayHiddenAttributes(facesContext, responseWriter, popover, first);
	}

	@Override
	protected void encodeZIndex(ResponseWriter responseWriter, Popover popover, Integer zIndex, boolean first)
		throws IOException {
		encodeOverlayZIndex(responseWriter, popover, zIndex, first);
	}

	@Override
	protected boolean isForRequired() {
		return true;
	}

	@Override
	public String getDelegateComponentFamily() {
		return Popover.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return Popover.DELEGATE_RENDERER_TYPE;
	}
}
