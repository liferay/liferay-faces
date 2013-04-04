/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.component;

import javax.el.MethodExpression;
import javax.faces.component.UIInput;


/**
 * @author  Neil Griffin
 */
public class HtmlInputFileCompat extends UIInput {

	// Public Constants
	public static final String FILE_UPLOAD_LISTENER = "fileUploadListener";

	public MethodExpression getFileUploadListener() {
		return (MethodExpression) getStateHelper().eval(FILE_UPLOAD_LISTENER, null);
	}

	public void setFileUploadListener(MethodExpression fileUploadListener) {
		getStateHelper().put(FILE_UPLOAD_LISTENER, fileUploadListener);
	}
}
