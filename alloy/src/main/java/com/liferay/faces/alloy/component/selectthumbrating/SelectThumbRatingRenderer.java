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
package com.liferay.faces.alloy.component.selectthumbrating;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.selectstarrating.SelectStarRatingRenderer;


/**
 * @author  Vernon Singleton
 */
@FacesRenderer(componentFamily = SelectThumbRating.COMPONENT_FAMILY, rendererType = SelectThumbRating.RENDERER_TYPE)
@ResourceDependencies(
		{
			@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
			@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
			@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
		}
	)
public class SelectThumbRatingRenderer extends SelectStarRatingRenderer {

	// Private Constants
	private static final String THUMBRATING_CLASS_NAME = "ThumbRating";

	@Override
	public String getAlloyClassName() {
		return THUMBRATING_CLASS_NAME;
	}
}
