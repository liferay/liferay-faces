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
package com.liferay.faces.bridge.config;

import java.util.Map;
import java.util.Set;

import javax.faces.FacesWrapper;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeConfigWrapper implements BridgeConfig, FacesWrapper<BridgeConfig> {

	public Map<String, Object> getAttributes() {
		return getWrapped().getAttributes();
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
