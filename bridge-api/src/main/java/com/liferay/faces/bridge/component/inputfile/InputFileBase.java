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
package com.liferay.faces.bridge.component.inputfile;
//J-

import javax.annotation.Generated;
import javax.el.MethodExpression;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputFileBase extends HtmlInputText {

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

	// Private Data Members
	private boolean auto;
	private MethodExpression fileUploadListener;
	private String multiple;

	@Override
	public void restoreState(FacesContext context, Object state) {
		Object[] values = (Object[]) state;
		super.restoreState(context, values[0]);
		auto = (java.lang.Boolean)values[1];
		fileUploadListener = (javax.el.MethodExpression) values[2];
		multiple = (java.lang.String) values[3];
	}

	@Override
	public Object saveState(FacesContext context) {
		Object[] values = new Object[4];
		values[0] = super.saveState(context);
		values[1] = auto;
		values[2] = fileUploadListener;
		values[3] = multiple;

		return ((Object) values);
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public MethodExpression getFileUploadListener() {
		return fileUploadListener;
	}

	public void setFileUploadListener(MethodExpression fileUploadListener) {
		this.fileUploadListener = fileUploadListener;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public StateHelperCompat getStateHelper() {
		return new StateHelperCompat();
	}

	protected class StateHelperCompat {

		public Object eval(PropertyKeys propertyKey, String defaultValue) {
			if (propertyKey == PropertyKeys.styleClass) {
				return InputFileBase.super.getStyleClass();
			}
			else {
				return null;
			}
		}
	}
}
//J+
