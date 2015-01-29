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

import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;

import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.config.ConfiguredSystemEventListener;


/**
 * <p>This interface defines a contract for getting portlet bridge configuration values.</p>
 *
 * @author  Neil Griffin
 */
public interface BridgeConfig {

	/**
	 * Returns a map of bridge configuration attributes.
	 */
	public Map<String, Object> getAttributes();

	/**
	 * Returns a list of servlet-mapping entries from the WEB-INF/web.xml descriptor that are mapped to the Faces
	 * Servlet.
	 */
	public List<ConfiguredServletMapping> getConfiguredFacesServletMappings();

	/**
	 * Returns a list of filename extensions that the developer may have specified in the WEB-INF/web.xml descriptor
	 * with the "javax.faces.DEFAULT_SUFFIX" init-param.
	 */
	public List<String> getConfiguredSuffixes();

	/**
	 * <p>Returns a list of configured system-event-listener entries that are found in META-INF/faces-config.xml or
	 * WEB-INF/faces-config.xml descriptors.</p>
	 */
	public List<ConfiguredSystemEventListener> getConfiguredSystemEventListeners();

	/**
	 * Returns the value associated with the specified parameter name from the WEB-INF/web.xml descriptor.
	 *
	 * @param   name  The name of the parameter, corresponding to the param-name element nested within the context-param
	 *                element.
	 *
	 * @return  The value of the parameter, corresponding to the param-value element nested within the context-param
	 *          element.
	 */
	public String getContextParameter(String name);

	/**
	 * Returns a list of attribute names that are to be excluded from the {@link BridgeRequestScope} as defined in the
	 * bridge:excluded-attributes element within the faces-config.xml descriptor.
	 */
	public Set<String> getExcludedRequestAttributes();

	/**
	 * Returns an immutable {@link Map} of Public Render Parameter mappings as defined in the
	 * bridge:public-parameter-mappings element of the faces-config.xml descriptor.
	 */
	public Map<String, String[]> getPublicParameterMappings();

	/**
	 * Returns the parameter name used for storing the value of the target viewId in a {@link RenderRequest}.
	 */
	public String getViewIdRenderParameterName();

	/**
	 * Returns the parameter name used for storing the value of the target viewId in a {@link ResourceRequest}.
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
