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

import java.io.IOException;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.commandlink.CommandLink;
import com.liferay.faces.alloy.render.internal.AlloyRendererUtil;
import com.liferay.faces.util.render.internal.DelegationResponseWriter;


/**
 * @author  Vernon Singleton
 */
@FacesRenderer(componentFamily = CommandLink.COMPONENT_FAMILY, rendererType = CommandLink.RENDERER_TYPE)
@ListenerFor(systemEventClass = PostAddToViewEvent.class, sourceClass = CommandLink.class)
@ResourceDependency(library = "javax.faces", name = "jsf.js")
public class CommandLinkRenderer extends CommandLinkRendererBase implements ComponentSystemEventListener {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		DelegationResponseWriter delegationResponseWriter = new CommandLinkResponseWriter(responseWriter,
				(uiComponent.getChildCount() > 0));
		super.encodeBegin(facesContext, uiComponent, delegationResponseWriter);
	}

	@Override
	public void processEvent(ComponentSystemEvent componentSystemEvent) throws AbortProcessingException {

		CommandLink commandLink = (CommandLink) componentSystemEvent.getComponent();

		if (commandLink.isAjax()) {
			AlloyRendererUtil.addDefaultAjaxBehavior(commandLink, commandLink.getExecute(), commandLink.getProcess(),
				"@all", commandLink.getRender(), commandLink.getUpdate(), "@none");
		}
	}
}
