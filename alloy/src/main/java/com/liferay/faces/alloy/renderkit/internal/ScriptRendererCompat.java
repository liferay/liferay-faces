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

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.liferay.faces.util.client.Script;


/**
 * This class provides a compatibility layer for JSF1/JSF2 and different versions of the Liferay Portal API.
 *
 * @author      Neil Griffin
 * @deprecated  See {@link com.liferay.faces.alloy.renderkit.internal.ScriptRenderer}.
 */
@Deprecated
public abstract class ScriptRendererCompat extends Renderer {

	protected void addScriptToBottomOfPage(Script script) {
		// Unsupported for JSF 1.2 due to the lack of FacesRequestContext
	}

	protected boolean isBottomOfPageSupported() {

		// Unsupported for JSF 1.2 due to the lack of FacesRequestContext
		return false;
	}

	protected boolean isInline(FacesContext facesContext) {

		// Must always be inline for JSF 1.2 due to the lack of FacesRequestContext
		return true;
	}

	protected boolean isAjaxRequest(FacesContext facesContext) {
		return false; // Ajax not supported with JSF 1.2
	}
}
