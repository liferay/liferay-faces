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
package com.liferay.faces.alloy.config.internal;

import javax.faces.context.ExternalContext;

import com.liferay.faces.util.config.WebConfigParamUtil;


/**
 * @author  Kyle Stiemann
 */
public enum AlloyWebConfigParam {

	AlloyTagDecoratorEnabled("com.liferay.faces.util.AlloyTagDecoratorEnabled", true);

	// Private Data Members
	private boolean defaultBooleanValue;
	private String name;

	private AlloyWebConfigParam(String name, boolean defaultBooleanValue) {
		this.name = name;
		this.defaultBooleanValue = defaultBooleanValue;
	}

	public boolean getBooleanValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getBooleanValue(externalContext, name, null, defaultBooleanValue);
	}
}
