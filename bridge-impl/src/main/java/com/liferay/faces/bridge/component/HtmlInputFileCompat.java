/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public class HtmlInputFileCompat extends UIInput {

	// Public Constants
	public static final String FILE_UPLOAD_LISTENER = "fileUploadListener";

	// Private Data Members
	private MethodExpression fileUploadListener;
	private String multiple;
	private String style;
	private String styleClass;

	@Override
	public void restoreState(FacesContext context, Object state) {
		Object[] values = (Object[]) state;
		super.restoreState(context, values[0]);
		fileUploadListener = (javax.el.MethodExpression) values[1];
		multiple = (java.lang.String) values[2];
		style = (java.lang.String) values[3];
		styleClass = (java.lang.String) values[4];
	}

	@Override
	public Object saveState(FacesContext context) {
		Object[] values = new Object[5];
		values[0] = super.saveState(context);
		values[1] = fileUploadListener;
		values[2] = multiple;
		values[3] = style;
		values[4] = styleClass;

		return ((Object) values);
	}

	public MethodExpression getFileUploadListener() {
		return fileUploadListener;
	}

	public void setFileUploadListener(MethodExpression fileUploadListener) {
		this.fileUploadListener = fileUploadListener;
	}
}
