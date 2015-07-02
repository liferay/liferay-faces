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
package com.liferay.faces.util.config;

import javax.faces.context.ExternalContext;

import com.liferay.faces.util.config.ConfigParam;
import com.liferay.faces.util.config.WebConfigParamUtil;
import com.liferay.faces.util.helper.BooleanHelper;


/**
 * @author  Neil Griffin
 */
public enum WebConfigParam implements ConfigParam<ExternalContext> {

	/** Boolean indicating whether or not XML entities should be resolved */
	ResolveXMLEntities("com.liferay.faces.util.resolveXMLEntities", "com.liferay.faces.bridge.resolveXMLEntities",
		false),

	/**
	 * Absolute path to a directory (folder) in which the uploaded file data should be written to. Default value is the
	 * value of the system property "java.io.tmpdir".
	 */
	UploadedFilesDir("com.liferay.faces.util.uploadedFilesDir", System.getProperty("java.io.tmpdir")),

	/** Maximum file size for an uploaded file. Default is 104857600 bytes (100MB) */
	UploadedFileMaxSize("com.liferay.faces.util.uploadedFileMaxSize", 104857600);

	// Private Data Members
	private String alternateName;
	private boolean defaultBooleanValue;
	private String defaultStringValue;
	private int defaultIntegerValue;
	private long defaultLongValue;
	private String name;

	private WebConfigParam(String name, String defaultStringValue) {
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

	private WebConfigParam(String name, int defaultIntegerValue) {
		this.name = name;
		this.defaultBooleanValue = (defaultIntegerValue != 0);
		this.defaultIntegerValue = defaultIntegerValue;
		this.defaultLongValue = defaultIntegerValue;
		this.defaultStringValue = Integer.toString(defaultIntegerValue);
	}

	private WebConfigParam(String name, long defaultLongValue) {
		this.name = name;
		this.defaultBooleanValue = (defaultLongValue != 0);

		if (defaultLongValue < Integer.MIN_VALUE) {
			this.defaultIntegerValue = Integer.MIN_VALUE;
		}
		else if (defaultLongValue > Integer.MAX_VALUE) {
			this.defaultIntegerValue = Integer.MAX_VALUE;
		}
		else {
			this.defaultIntegerValue = (int) defaultLongValue;
		}

		this.defaultLongValue = defaultLongValue;
		this.defaultStringValue = Long.toString(defaultLongValue);
	}

	private WebConfigParam(String name, String alternateName, boolean defaultBooleanValue) {
		this.name = name;
		this.alternateName = alternateName;

		if (defaultBooleanValue) {
			this.defaultBooleanValue = true;
			this.defaultIntegerValue = 1;
			this.defaultLongValue = 1L;
			this.defaultStringValue = Boolean.TRUE.toString();
		}
		else {
			this.defaultBooleanValue = false;
			this.defaultIntegerValue = 0;
			this.defaultLongValue = 0L;
			this.defaultStringValue = Boolean.FALSE.toString();
		}
	}

	public String getAlternateName() {
		return alternateName;
	}

	@Override
	public boolean getBooleanValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getBooleanValue(externalContext, name, alternateName, defaultBooleanValue);
	}

	@Override
	public String getConfiguredValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getConfiguredValue(externalContext, name, alternateName);
	}

	@Override
	public boolean isConfigured(ExternalContext externalContext) {
		return WebConfigParamUtil.isSpecified(externalContext, name, alternateName);
	}

	public boolean getDefaultBooleanValue() {
		return defaultBooleanValue;
	}

	public int getDefaultIntegerValue() {
		return defaultIntegerValue;
	}

	@Override
	public long getDefaultLongValue() {
		return defaultLongValue;
	}

	public String getDefaultStringValue() {
		return defaultStringValue;
	}

	@Override
	public int getIntegerValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getIntegerValue(externalContext, name, alternateName, defaultIntegerValue);
	}

	@Override
	public long getLongValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getLongValue(externalContext, name, alternateName, defaultLongValue);
	}

	public String getName() {
		return name;
	}

	@Override
	public String getStringValue(ExternalContext externalContext) {
		return WebConfigParamUtil.getStringValue(externalContext, name, alternateName, defaultStringValue);
	}
}
