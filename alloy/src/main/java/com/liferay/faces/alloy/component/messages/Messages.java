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
package com.liferay.faces.alloy.component.messages;

import javax.faces.component.FacesComponent;

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = Messages.COMPONENT_TYPE)
public class Messages extends MessagesBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.messages.Messages";
	public static final String DELEGATE_COMPONENT_FAMILY = COMPONENT_FAMILY;
	public static final String DELEGATE_RENDERER_TYPE = "javax.faces.Messages";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.messages.MessagesRenderer";
	public static final String ERROR_CLASS = "errorClass";
	public static final String ERROR_CLASS_NAME = "text-error";
	public static final String FATAL_CLASS = "fatalClass";
	public static final String FATAL_CLASS_NAME = ERROR_CLASS_NAME;
	public static final String INFO_CLASS = "infoClass";
	public static final String INFO_CLASS_NAME = "text-info";
	public static final String WARN_CLASS = "warnClass";
	public static final String WARN_CLASS_NAME = "text-warning";
	public static final String STYLE_CLASS_NAME = "alloy-messages";

	public Messages() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getErrorClass() {

		String errorClass = (String) getStateHelper().eval(ERROR_CLASS, null);

		return ComponentUtil.concatCssClasses(errorClass, ERROR_CLASS_NAME);
	}

	@Override
	public void setErrorClass(String errorClass) {
		getStateHelper().put(ERROR_CLASS, errorClass);
	}

	@Override
	public String getFatalClass() {

		String fatalClass = (String) getStateHelper().eval(FATAL_CLASS, null);

		return ComponentUtil.concatCssClasses(fatalClass, FATAL_CLASS_NAME);
	}

	@Override
	public void setFatalClass(String fatalClass) {
		getStateHelper().put(FATAL_CLASS, fatalClass);
	}

	@Override
	public String getInfoClass() {

		String infoClass = (String) getStateHelper().eval(INFO_CLASS, null);

		return ComponentUtil.concatCssClasses(infoClass, INFO_CLASS_NAME);
	}

	@Override
	public void setInfoClass(String infoClass) {
		getStateHelper().put(INFO_CLASS, infoClass);
	}

	@Override
	public String getStyleClass() {

		// getStateHelper().eval(STYLE_CLASS, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}

	@Override
	public String getWarnClass() {

		String warnClass = (String) getStateHelper().eval(WARN_CLASS, null);

		return ComponentUtil.concatCssClasses(warnClass, WARN_CLASS_NAME);
	}

	@Override
	public void setWarnClass(String warnClass) {
		getStateHelper().put(WARN_CLASS, warnClass);
	}
}
