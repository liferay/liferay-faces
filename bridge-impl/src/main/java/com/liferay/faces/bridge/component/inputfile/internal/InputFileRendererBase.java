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
package com.liferay.faces.bridge.component.inputfile.internal;
//J-


import javax.annotation.Generated;


import com.liferay.faces.util.render.DelegatingRendererBase;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputFileRendererBase extends DelegatingRendererBase {

	// Protected Constants
	protected static final String AUTO = "auto";
	protected static final String FILE_UPLOAD_LISTENER = "fileUploadListener";
	protected static final String MULTIPLE = "multiple";

	@Override
	public String getDelegateComponentFamily() {
		return javax.faces.component.html.HtmlInputText.COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return "javax.faces.Text";
	}
}
//J+
