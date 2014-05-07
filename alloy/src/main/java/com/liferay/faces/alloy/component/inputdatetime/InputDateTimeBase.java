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
package com.liferay.faces.alloy.component.inputdatetime;

import com.liferay.faces.alloy.component.inputtext.InputText;


/**
 * @author  Kyle Stiemann
 */
public class InputDateTimeBase extends InputText {

	// Public Constants
	public static final String SHOW_ON = "showOn";

	// Protected Constants
	protected static final String BUTTON_ICON = "buttonIcon";

	// Private Constants
	private static final String FOCUS = "focus";

	public String getButtonIconName() {
		return (String) getStateHelper().eval(BUTTON_ICON, null);
	}

	public String getShowOn() {
		return (String) getStateHelper().eval(SHOW_ON, FOCUS);
	}

	public void setShowOn(String showOn) {
		getStateHelper().put(SHOW_ON, showOn);
	}
}
