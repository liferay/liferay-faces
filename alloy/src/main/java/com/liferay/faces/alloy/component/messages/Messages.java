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
package com.liferay.faces.alloy.component.messages;

import javax.faces.component.FacesComponent;

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author  Neil Griffin
 */
@FacesComponent(value = Messages.COMPONENT_TYPE)
public class Messages extends MessagesBase {

	// Private Constants
	private static final String ERROR_CLASS_NAME = "text-error";
	private static final String FATAL_CLASS_NAME = ERROR_CLASS_NAME;
	private static final String INFO_CLASS_NAME = "text-info";
	private static final String WARN_CLASS_NAME = "text-warning";

	@Override
	public String getErrorClass() {

		String errorClass = super.getErrorClass();

		return ComponentUtil.concatCssClasses(errorClass, ERROR_CLASS_NAME);
	}

	@Override
	public String getFatalClass() {

		String fatalClass = super.getFatalClass();

		return ComponentUtil.concatCssClasses(fatalClass, FATAL_CLASS_NAME);
	}

	@Override
	public String getInfoClass() {

		String infoClass = super.getInfoClass();

		return ComponentUtil.concatCssClasses(infoClass, INFO_CLASS_NAME);
	}

	@Override
	public String getWarnClass() {

		String warnClass = super.getWarnClass();

		return ComponentUtil.concatCssClasses(warnClass, WARN_CLASS_NAME);
	}
}
