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
package com.liferay.faces.alloy.component.selectstarrating;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.selectrating.SelectRating;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class SelectStarRatingBase extends SelectRating implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.selectstarrating.SelectStarRating";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.selectstarrating.SelectStarRatingRenderer";

	// Protected Enumerations
	protected enum SelectStarRatingPropertyKeys {
		clientKey,
		styleClass
	}

	public SelectStarRatingBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(SelectStarRatingPropertyKeys.clientKey, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(SelectStarRatingPropertyKeys.clientKey, clientKey);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(SelectStarRatingPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(SelectStarRatingPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-select-star-rating");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(SelectStarRatingPropertyKeys.styleClass, styleClass);
	}
}
//J+
