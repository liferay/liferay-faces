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
package com.liferay.faces.alloy.component.commandlink;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.util.render.DelegationResponseWriter;


/**
 * @author  Vernon Singleton
 */
@FacesRenderer(componentFamily = CommandLink.COMPONENT_FAMILY, rendererType = CommandLink.RENDERER_TYPE)
public class CommandLinkRenderer extends CommandLinkRendererBase {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		DelegationResponseWriter delegationResponseWriter = new CommandLinkResponseWriter(responseWriter,
				(uiComponent.getChildCount() > 0));
		super.encodeBegin(facesContext, uiComponent, delegationResponseWriter);
	}

	@Override
	public String getDelegateComponentFamily() {
		return CommandLink.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return CommandLink.DELEGATE_RENDERER_TYPE;
	}
}
