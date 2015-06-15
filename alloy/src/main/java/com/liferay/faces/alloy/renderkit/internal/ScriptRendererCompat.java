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
package com.liferay.faces.alloy.renderkit.internal;

import java.lang.reflect.Method;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.portal.WebKeys;


/**
 * This class provides a compatibility layer for JSF1/JSF2 and different versions of the Liferay Portal API.
 *
 * @author      Neil Griffin
 * @deprecated  See {@link com.liferay.faces.alloy.renderkit.internal.ScriptRenderer}.
 */
@Deprecated
public abstract class ScriptRendererCompat extends Renderer {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ScriptRendererCompat.class);

	protected void addScriptToBottomOfPage(Script script) {

		FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
		facesRequestContext.addScript(script);
	}

	protected boolean isBottomOfPageSupported() {
		return true;
	}

	private boolean isIsolated(Object themeDisplay) {

		boolean isolated = false;

		try {
			Method isIsolated = themeDisplay.getClass().getMethod("isIsolated");

			if (isIsolated != null) {
				isolated = (Boolean) isIsolated.invoke(themeDisplay);
			}
		}
		catch (Exception e) {
			logger.error(e);
		}

		return isolated;
	}

	protected boolean isInline(FacesContext facesContext) {

		boolean inline = false;
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		Object themeDisplay = requestMap.get(WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {

			inline = isIsolated(themeDisplay) || isStateExclusive(themeDisplay);
		}

		return inline;
	}

	private boolean isStateExclusive(Object themeDisplay) {

		boolean stateExclusive = false;

		try {
			Method isStateExclusive = themeDisplay.getClass().getMethod("isStateExclusive");

			if (isStateExclusive != null) {
				stateExclusive = (Boolean) isStateExclusive.invoke(themeDisplay);
			}
		}
		catch (Exception e) {
			logger.error(e);
		}

		return stateExclusive;
	}

	protected boolean isAjaxRequest(FacesContext facesContext) {
		return facesContext.getPartialViewContext().isAjaxRequest();
	}
}
