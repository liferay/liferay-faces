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
package com.liferay.faces.alloy.component.messages.internal;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.messages.Messages;


/**
 * @author  Neil Griffin
 */
@FacesRenderer(componentFamily = Messages.COMPONENT_FAMILY, rendererType = Messages.RENDERER_TYPE)
@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.css")
public class MessagesRenderer extends MessagesRendererBase {

	// Private Constants
	private static final String MOJARRA_BOGUS_WARNING = "needs to have a UIForm in its ancestry";

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		if (!facesContext.isProjectStage(ProjectStage.Production)) {

			Iterator<FacesMessage> messages = facesContext.getMessages();

			while (messages.hasNext()) {
				FacesMessage facesMessage = messages.next();

				if ((facesMessage.getSeverity() == FacesMessage.SEVERITY_WARN) &&
						facesMessage.getSummary().contains(MOJARRA_BOGUS_WARNING)) {
					messages.remove();
				}
			}
		}

		super.encodeBegin(facesContext, uiComponent);
	}
}
