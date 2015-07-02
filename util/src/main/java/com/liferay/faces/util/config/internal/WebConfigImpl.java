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
package com.liferay.faces.util.config.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.faces.util.config.ConfiguredServlet;
import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.config.WebConfig;


/**
 * @author  Neil Griffin
 */
public class WebConfigImpl implements WebConfig {

	// Private Data Members
	private Map<String, String> configuredContextParams;
	private List<ConfiguredServlet> configuredServlets;
	private List<ConfiguredServletMapping> configuredServletMappings;

	public WebConfigImpl() {
		this.configuredContextParams = new HashMap<String, String>();
		this.configuredServlets = new ArrayList<ConfiguredServlet>();
		this.configuredServletMappings = new ArrayList<ConfiguredServletMapping>();
	}

	public WebConfigImpl(Map<String, String> configuredContextParams, List<ConfiguredServlet> configuredServlets,
		List<ConfiguredServletMapping> configuredServletMappings) {
		this.configuredContextParams = configuredContextParams;
		this.configuredServlets = configuredServlets;
		this.configuredServletMappings = configuredServletMappings;
	}

	public Map<String, String> getConfiguredContextParams() {
		return configuredContextParams;
	}

	public List<ConfiguredServletMapping> getConfiguredServletMappings() {
		return configuredServletMappings;
	}

	public List<ConfiguredServlet> getConfiguredServlets() {
		return configuredServlets;
	}
}
