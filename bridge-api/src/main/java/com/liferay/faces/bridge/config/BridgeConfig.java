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


/**
 * @author  Neil Griffin
 */
public interface BridgeConfig {

	/**
	 * Returns a map of bridge configuration attributes.
	 */
	public Map<String, Object> getAttributes();

	/**
	 * Returns a list of attribute names that are to be excluded from the {@link
	 * com.liferay.faces.bridge.scope.BridgeRequestScope} as defined in the bridge:excluded-attributes element within
	 * the faces-config.xml descriptor.
	 */
	public Set<String> getExcludedRequestAttributes();

	/**
	 * Returns an immutable {@link Map} of Public Render Parameter mappings as defined in the
	 * bridge:public-parameter-mappings element of the faces-config.xml descriptor.
	 */
	public Map<String, String[]> getPublicParameterMappings();

	/**
	 * Returns the parameter name used for storing the value of the target viewId in a {@link
	 * javax.portlet.RenderRequest}.
	 */
	public String getViewIdRenderParameterName();

	/**
	 * Returns the parameter name used for storing the value of the target viewId in a {@link
	 * javax.portlet.ResourceRequest}.
	 */
	public String getViewIdResourceParameterName();

	/**
	 * Returns the FQCN specified in the <code>render-response-wrapper-class</code> element of the faces-config.xml
	 * descriptor.
	 */
	public String getWriteBehindRenderResponseWrapper();

	/**
	 * Returns the FQCN specified in the <code>resource-response-wrapper-class</code> element of the faces-config.xml
	 * descriptor.
	 */
	public String getWriteBehindResourceResponseWrapper();
}
