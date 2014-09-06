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
package com.liferay.faces.reslib.config;

import javax.faces.context.ExternalContext;

import com.liferay.faces.util.config.ConfigParam;
import com.liferay.faces.util.config.WebConfigParamUtil;
import com.liferay.faces.util.helper.BooleanHelper;


/**
 * @author  Neil Griffin
 */
public enum ResLibConfigParam implements ConfigParam<ExternalContext> {

	ComboAllowedFileExtensions("com.liferay.faces.reslib.comboAllowedFileExtensions", ".css,.js");

	// Private Data Members
	private boolean defaultBooleanValue;
	private String defaultStringValue;
	private int defaultIntegerValue;
	private String name;

	private ResLibConfigParam(String name, String defaultStringValue) {
		this.name = name;

		if (BooleanHelper.isTrueToken(defaultStringValue)) {
			this.defaultBooleanValue = true;
			this.defaultIntegerValue = 1;
		}
		else {
			this.defaultBooleanValue = false;
			this.defaultIntegerValue = 0;
		}

		this.defaultStringValue = defaultStringValue;
	}

	private ResLibConfigParam(String name, boolean defaultBooleanValue) {
		this.name = name;

		if (defaultBooleanValue) {
			this.defaultBooleanValue = true;
			this.defaultIntegerValue = 1;
			this.defaultStringValue = Boolean.TRUE.toString();
		}
		else {
			this.defaultBooleanValue = false;
			this.defaultIntegerValue = 0;
			this.defaultStringValue = Boolean.FALSE.toString();
		}
	}

	public String getAlternateName() {
		return null;
	}

	@Override
	public boolean getBooleanValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getBooleanValue(externalContext, name, null, defaultBooleanValue);
	}

	@Override
	public String getConfiguredValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getConfiguredValue(externalContext, name, null);
	}

	@Override
	public boolean isConfigured(ExternalContext externalContext) {
		return WebConfigParamUtil.isSpecified(externalContext, name, null);
	}

	public boolean getDefaultBooleanValue() {
		return defaultBooleanValue;
	}

	public int getDefaultIntegerValue() {
		return defaultIntegerValue;
	}

	public String getDefaultStringValue() {
		return defaultStringValue;
	}

	@Override
	public int getIntegerValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getIntegerValue(externalContext, name, null, defaultIntegerValue);
	}

	public String getName() {
		return name;
	}

	@Override
	public String getStringValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getStringValue(externalContext, name, null, defaultStringValue);
	}
}
