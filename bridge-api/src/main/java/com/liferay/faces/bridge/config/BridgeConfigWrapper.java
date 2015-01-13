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
package com.liferay.faces.bridge.config;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesWrapper;

import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.config.ConfiguredSystemEventListener;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeConfigWrapper implements BridgeConfig, FacesWrapper<BridgeConfig> {

	public Map<String, Object> getAttributes() {
		return getWrapped().getAttributes();
	}

	public List<ConfiguredServletMapping> getConfiguredFacesServletMappings() {
		return getWrapped().getConfiguredFacesServletMappings();
	}

	public List<String> getConfiguredSuffixes() {
		return getWrapped().getConfiguredSuffixes();
	}

	public List<ConfiguredSystemEventListener> getConfiguredSystemEventListeners() {
		return getWrapped().getConfiguredSystemEventListeners();
	}

	public String getContextParameter(String name) {
		return getWrapped().getContextParameter(name);
	}

	public Set<String> getExcludedRequestAttributes() {
		return getWrapped().getExcludedRequestAttributes();
	}

	public Map<String, String[]> getPublicParameterMappings() {
		return getWrapped().getPublicParameterMappings();
	}

	public String getViewIdRenderParameterName() {
		return getWrapped().getViewIdRenderParameterName();
	}

	public String getViewIdResourceParameterName() {
		return getWrapped().getViewIdResourceParameterName();
	}

	public abstract BridgeConfig getWrapped();

	public String getWriteBehindRenderResponseWrapper() {
		return getWrapped().getWriteBehindRenderResponseWrapper();
	}

	public String getWriteBehindResourceResponseWrapper() {
		return getWrapped().getWriteBehindResourceResponseWrapper();
	}
}
