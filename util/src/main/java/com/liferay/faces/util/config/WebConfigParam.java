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
package com.liferay.faces.util.config;

import javax.servlet.ServletContext;


/**
 * @author  Neil Griffin
 */
public enum WebConfigParam implements ConfigParam<ServletContext> {

	ResolveXMLEntities("com.liferay.faces.util.resolveXMLEntities", false);

	// Private Data Members
	private String alternateName;
	private boolean defaultBooleanValue;
	private String defaultStringValue;
	private int defaultIntegerValue;
	private String name;

	private WebConfigParam(String name, boolean defaultBooleanValue) {
		this.name = name;
		this.alternateName = null;

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
		return alternateName;
	}

	@Override
	public boolean getBooleanValue(ServletContext servletContext) {
		return WebConfigParamUtil.getBooleanValue(servletContext, name, alternateName, defaultBooleanValue);
	}

	@Override
	public String getConfiguredValue(ServletContext servletContext) {
		return WebConfigParamUtil.getConfiguredValue(servletContext, name, alternateName);
	}

	@Override
	public boolean isConfigured(ServletContext servletContext) {
		return WebConfigParamUtil.isSpecified(servletContext, name, alternateName);
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
	public int getIntegerValue(ServletContext servletContext) {
		return WebConfigParamUtil.getIntegerValue(servletContext, name, alternateName, defaultIntegerValue);
	}

	public String getName() {
		return name;
	}

	@Override
	public String getStringValue(ServletContext servletContext) {
		return WebConfigParamUtil.getStringValue(servletContext, name, alternateName, defaultStringValue);
	}
}
