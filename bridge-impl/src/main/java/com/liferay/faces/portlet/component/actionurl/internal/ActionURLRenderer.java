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
package com.liferay.faces.portlet.component.actionurl.internal;

// JSF 2: import javax.faces.render.FacesRenderer;

import javax.faces.component.UIComponent;
import javax.portlet.ActionRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;

import com.liferay.faces.portlet.component.actionurl.ActionURL;


/**
 * @author  Kyle Stiemann
 */
//J-
// JSF 2: @FacesRenderer(componentFamily = ActionURL.COMPONENT_FAMILY, rendererType = ActionURL.RENDERER_TYPE)
//J+
public class ActionURLRenderer extends ActionURLRendererBase {

	@Override
	protected PortletURL getPortletURL(MimeResponse mimeResponse, UIComponent uiComponent) {

		PortletURL actionURL = mimeResponse.createActionURL();
		ActionURL actionURLComponent = (ActionURL) uiComponent;
		String name = actionURLComponent.getName();

		if (name != null) {
			actionURL.setParameter(ActionRequest.ACTION_NAME, name);
		}

		return actionURL;
	}
}
