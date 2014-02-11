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
package com.liferay.faces.alloy.renderkit;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.liferay.faces.util.portal.WebKeys;

import com.liferay.portal.theme.ThemeDisplay;


/**
 * This class provides a compatibility layer for JSF1/JSF2 and different versions of the Liferay Portal API.
 *
 * @author  Neil Griffin
 */
public abstract class ScriptRendererCompat extends Renderer {

	protected boolean isInline(FacesContext facesContext) {

		boolean inline = false;
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		Object themeDisplayAsObject = requestMap.get(WebKeys.THEME_DISPLAY);

		if (themeDisplayAsObject != null) {

			if (themeDisplayAsObject instanceof ThemeDisplay) {
				ThemeDisplay themeDisplay = (ThemeDisplay) themeDisplayAsObject;
				inline = (themeDisplay.isIsolated() || themeDisplay.isStateExclusive());
			}
		}

		return inline;
	}

	protected boolean isAjaxRequest(FacesContext facesContext) {

		// JSF 1.2 does not support f:ajax so return false.
		return false;
	}
}
