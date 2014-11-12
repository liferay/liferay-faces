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
package com.liferay.faces.portal.component.buttonrow;

import javax.faces.render.FacesRenderer;


/**
 * @author  Vernon Singleton
 */
//J-
@FacesRenderer(componentFamily = ButtonRow.COMPONENT_FAMILY, rendererType = ButtonRow.RENDERER_TYPE)
//J+
public class ButtonRowRenderer extends ButtonRowRendererBase {

	@Override
	public String getDelegateComponentFamily() {
		return ButtonRow.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return ButtonRow.DELEGATE_RENDERER_TYPE;
	}
}
