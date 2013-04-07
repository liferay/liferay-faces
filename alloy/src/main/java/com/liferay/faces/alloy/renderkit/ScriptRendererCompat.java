/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;


/**
 * This class provides a compatibility layer for JSF1/JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class ScriptRendererCompat extends Renderer {

	protected boolean isAjaxRequest(FacesContext facesContext) {
		return facesContext.getPartialViewContext().isAjaxRequest();
	}
}
