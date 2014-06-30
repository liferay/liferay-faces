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
package com.liferay.faces.alloy.component.outputlabel;

import javax.faces.render.FacesRenderer;


/**
 * @author  Vernon Singleton
 */
@FacesRenderer(componentFamily = OutputLabel.COMPONENT_FAMILY, rendererType = OutputLabel.RENDERER_TYPE)
public class OutputLabelRenderer extends OutputLabelRendererBase {

	@Override
	public String getDelegateComponentFamily() {
		return OutputLabel.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return OutputLabel.DELEGATE_RENDERER_TYPE;
	}
}
