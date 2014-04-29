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
package com.liferay.faces.alloy.component.button;

/**
 * This interface contains the getters and setters for JSF attributes that are common between {@link
 * com.liferay.faces.alloy.component.button.Button} and {@link
 * com.liferay.faces.alloy.component.commandbutton.CommandButton} and are necessary for rendering Button and
 * CommandButton in {@link com.liferay.faces.alloy.component.button.ButtonRenderer}.
 *
 * @author  kylestiemann
 */
public interface FacesButton {

	// Public Constants
	public static final String BUTTON = "button";
	public static final String DISABLED = "disabled";
	public static final String IMAGE = "image";
	public static final String ONBLUR = "onblur";
	public static final String ONFOCUS = "onfocus";

	public boolean isDisabled();

	public String getImage();

	public String getOnblur();

	public String getOnclick();

	public String getOnfocus();

	public String getType();

	public Object getValue();
}
