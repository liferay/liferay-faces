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
import javax.faces.component.html.HtmlInputText;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class InputFileBase extends HtmlInputText implements Styleable, ClientComponent {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.inputfile.InputFile";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.inputfile.InputFileRenderer";

	// Protected Enumerations
	protected enum InputFilePropertyKeys {
		appendNewFiles,
		auto,
		clientKey,
		fileUploadListener,
		location,
		multiple,
		showPreview,
		showProgress,
		styleClass
	}

	public InputFileBase() {
		super();
		setRendererType(RENDERER_TYPE);
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

	@Override
	public String getLabel() {

		String label = super.getLabel();

		if (label == null) {

			javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();

			if (facesContext.getCurrentPhaseId() == javax.faces.event.PhaseId.PROCESS_VALIDATIONS) {
				label = com.liferay.faces.util.component.ComponentUtil.getComponentLabel(this);
			}
		}

		return label;
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

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(InputFilePropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(InputFilePropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-input-file", "field");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(InputFilePropertyKeys.styleClass, styleClass);
	}
}
//J+
