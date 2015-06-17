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
package com.liferay.faces.bridge.component.inputfile;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlInputText;

/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputFileBase extends HtmlInputText {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.bridge.component.inputfile.InputFile";
	public static final String RENDERER_TYPE = "com.liferay.faces.bridge.component.inputfile.InputFileRenderer";

	// Protected Enumerations
	protected enum InputFilePropertyKeys {
		auto,
		fileUploadListener,
		multiple
	}

	protected enum PropertyKeys {
		styleClass
	}

	// Public Constants
	public static final String FILE_UPLOAD_LISTENER = "fileUploadListener";

	public InputFileBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public boolean isAuto() {
		return (Boolean) getStateHelper().eval(InputFilePropertyKeys.auto, false);
	}

	public void setAuto(boolean auto) {
		getStateHelper().put(InputFilePropertyKeys.auto, auto);
	}

	public javax.el.MethodExpression getFileUploadListener() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputFilePropertyKeys.fileUploadListener, null);
	}

	public void setFileUploadListener(javax.el.MethodExpression fileUploadListener) {
		getStateHelper().put(InputFilePropertyKeys.fileUploadListener, fileUploadListener);
	}

	public String getMultiple() {
		return (String) getStateHelper().eval(InputFilePropertyKeys.multiple, null);
	}

	public void setMultiple(String multiple) {
		getStateHelper().put(InputFilePropertyKeys.multiple, multiple);
	}

	public abstract com.liferay.faces.util.component.StateHelper getStateHelper();
}
//J+
