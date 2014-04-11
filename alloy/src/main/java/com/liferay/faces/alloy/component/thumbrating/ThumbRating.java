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
package com.liferay.faces.alloy.component.thumbrating;

import javax.faces.component.FacesComponent;

import com.liferay.faces.alloy.component.rating.Rating;

/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = ThumbRating.COMPONENT_TYPE)
public class ThumbRating extends Rating {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.alloy.component.thumbrating";
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.thumbrating.ThumbRating";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.thumbrating.ThumbRatingRenderer";

	public ThumbRating() {

		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
}
