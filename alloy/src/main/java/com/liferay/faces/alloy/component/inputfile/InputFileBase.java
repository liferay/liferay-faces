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
package com.liferay.faces.alloy.component.inputfile;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlInputFile;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputFileBase extends HtmlInputFile implements Styleable, ClientComponent {

	// Protected Enumerations
	protected enum InputFilePropertyKeys {
		appendNewFiles,
		auto,
		clientKey,
		fileUploadListener,
		location,
		multiple,
		showPreview,
		showProgress
	}

	public boolean isAppendNewFiles() {
		return (Boolean) getStateHelper().eval(InputFilePropertyKeys.appendNewFiles, false);
	}

	public void setAppendNewFiles(boolean appendNewFiles) {
		getStateHelper().put(InputFilePropertyKeys.appendNewFiles, appendNewFiles);
	}

	public boolean isAuto() {
		return (Boolean) getStateHelper().eval(InputFilePropertyKeys.auto, false);
	}

	public void setAuto(boolean auto) {
		getStateHelper().put(InputFilePropertyKeys.auto, auto);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(InputFilePropertyKeys.clientKey, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(InputFilePropertyKeys.clientKey, clientKey);
	}

	public javax.el.MethodExpression getFileUploadListener() {
		return (javax.el.MethodExpression) getStateHelper().eval(InputFilePropertyKeys.fileUploadListener, null);
	}

	public void setFileUploadListener(javax.el.MethodExpression fileUploadListener) {
		getStateHelper().put(InputFilePropertyKeys.fileUploadListener, fileUploadListener);
	}

	public String getLocation() {
		return (String) getStateHelper().eval(InputFilePropertyKeys.location, null);
	}

	public void setLocation(String location) {
		getStateHelper().put(InputFilePropertyKeys.location, location);
	}

	public String getMultiple() {
		return (String) getStateHelper().eval(InputFilePropertyKeys.multiple, null);
	}

	public void setMultiple(String multiple) {
		getStateHelper().put(InputFilePropertyKeys.multiple, multiple);
	}

	public boolean isShowPreview() {
		return (Boolean) getStateHelper().eval(InputFilePropertyKeys.showPreview, false);
	}

	public void setShowPreview(boolean showPreview) {
		getStateHelper().put(InputFilePropertyKeys.showPreview, showPreview);
	}

	public boolean isShowProgress() {
		return (Boolean) getStateHelper().eval(InputFilePropertyKeys.showProgress, false);
	}

	public void setShowProgress(boolean showProgress) {
		getStateHelper().put(InputFilePropertyKeys.showProgress, showProgress);
	}
}
//J+
