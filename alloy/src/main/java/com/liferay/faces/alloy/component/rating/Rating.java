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
package com.liferay.faces.alloy.component.rating;

import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = Rating.COMPONENT_TYPE)
public class Rating extends RatingBase {

	// Public Constants
	public static final String COMPONENT_FAMILY = "com.liferay.faces.alloy.component.rating";
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.rating.Rating";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.rating.RatingRenderer";

	public Rating() {

		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	protected void validateValue(FacesContext context, Object value) {

		// AlloyUI sets the initial value of its hidden input to -1
		// -1 has been modified by this point in the lifecycle to be "" to play nice with JSF.  Unfortunately "" would
		// be invalid, if it is not in the list of rating options, so we will force validateValue to see that the value
		// is null instead of "".  JSF will allow null as valid.
		if (value instanceof String) {
			String valueString = (String) value;

			if (StringPool.BLANK.equals(valueString)) {
				value = null;
			}
		}

		super.validateValue(context, value);
	}

	@Override
	public String getBoundingBox() {

		String boundingBox = super.getBoundingBox();

		if (boundingBox == null) {
			boundingBox = StringPool.POUND + ComponentUtil.escapeClientId(getClientId());
		}

		return boundingBox;
	}

	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	@Override
	public Boolean isWidgetRender() {
		return (Boolean) getStateHelper().eval(WIDGET_RENDER, true);
	}

}
