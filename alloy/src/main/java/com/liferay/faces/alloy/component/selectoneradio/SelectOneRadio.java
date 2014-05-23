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
package com.liferay.faces.alloy.component.selectoneradio;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlSelectOneRadio;


/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = SelectOneRadio.COMPONENT_TYPE)
public class SelectOneRadio extends HtmlSelectOneRadio {
	
	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.selectoneradio.SelectOneRadio";
	public static final String DELEGATE_COMPONENT_FAMILY = COMPONENT_FAMILY;
	public static final String DELEGATE_RENDERER_TYPE = "javax.faces.Radio";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.selectoneradio.SelectOneRadioRenderer";

	public SelectOneRadio() {
		super();
		setRendererType(RENDERER_TYPE);
	}
	
}
