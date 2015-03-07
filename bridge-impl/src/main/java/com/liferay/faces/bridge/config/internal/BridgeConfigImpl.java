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
package com.liferay.faces.bridge.config.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.config.ConfiguredElement;
import com.liferay.faces.util.config.FacesConfig;


/**
 * @author  Neil Griffin
 */
public class BridgeConfigImpl implements BridgeConfig {

	// Private Constants
	private static final String EXCLUDED_ATTRIBUTE = "excluded-attribute";
	private static final String MODEL_EL = "model-el";
	private static final String PARAMETER = "parameter";
	private static final String RENDER_RESPONSE_WRAPPER_CLASS = "render-response-wrapper-class";
	private static final String RESOURCE_RESPONSE_WRAPPER_CLASS = "resource-response-wrapper-class";

	// Private Data Members
	private Map<String, Object> bridgeConfigAttributeMap;
	private Set<String> excludedRequestAttributes;
	private Map<String, String[]> publicParameterMappings;
	private String viewIdRenderParameterName;
	private String viewIdResourceParameterName;
	private String writeBehindRenderResponseWrapper;
	private String writeBehindResourceResponseWrapper;

	public BridgeConfigImpl(PortletConfig portletConfig) {

		// bridgeConfigAttributeMap
		this.bridgeConfigAttributeMap = new BridgeConfigAttributeMap();

		// configuredFacesServletMappings
		String appConfigAttrName = ApplicationConfig.class.getName();
		PortletContext portletContext = portletConfig.getPortletContext();
		ApplicationConfig applicationConfig = (ApplicationConfig) portletContext.getAttribute(appConfigAttrName);
		FacesConfig facesConfig = applicationConfig.getFacesConfig();
		bridgeConfigAttributeMap.put(BridgeConfigAttributeMap.CONFIGURED_FACES_SERVLET_MAPPINGS,
			facesConfig.getConfiguredFacesServletMappings());

		// configuredSystemEventListeners
		bridgeConfigAttributeMap.put(BridgeConfigAttributeMap.CONFIGURED_SYSTEM_EVENT_LISTENERS,
			facesConfig.getConfiguredSystemEventListeners());

		// configuredSuffixes
		bridgeConfigAttributeMap.put(BridgeConfigAttributeMap.CONFIGURED_SUFFIXES, facesConfig.getConfiguredSuffixes());

		// excludedRequestAttributes
		this.excludedRequestAttributes = new HashSet<String>();

		List<ConfiguredElement> configuredApplicationExtensions = facesConfig.getConfiguredApplicationExtensions();

		for (ConfiguredElement configuredElement : configuredApplicationExtensions) {
			String configuredElementName = configuredElement.getName();

			if (EXCLUDED_ATTRIBUTE.equals(configuredElementName)) {
				String excludedAttributeName = configuredElement.getValue();
				this.excludedRequestAttributes.add(excludedAttributeName);
			}
		}

		// publicParameterMappings
		this.publicParameterMappings = new HashMap<String, String[]>();

		String parameter = null;
		String modelEL = null;

		for (ConfiguredElement configuredElement : configuredApplicationExtensions) {
			String configuredElementName = configuredElement.getName();

			if (PARAMETER.equals(configuredElementName)) {
				parameter = configuredElement.getValue();
			}
			else if (MODEL_EL.equals(configuredElementName)) {

				modelEL = configuredElement.getValue();

				if ((parameter != null) && (modelEL != null)) {

					if (modelEL.length() > 0) {
						String[] newValue = new String[] { modelEL };
						String[] existingValue = publicParameterMappings.get(parameter);

						if (existingValue != null) {
							int total = existingValue.length + 1;
							newValue = new String[total];

							for (int i = 0; i < existingValue.length; i++) {
								newValue[i] = existingValue[i];
							}

							newValue[total - 1] = modelEL;
						}

						this.publicParameterMappings.put(parameter, newValue);
					}
				}
			}
		}

		// writeBehindRenderResponseWrapper
		for (ConfiguredElement configuredElement : configuredApplicationExtensions) {
			String configuredElementName = configuredElement.getName();

			if (RENDER_RESPONSE_WRAPPER_CLASS.equals(configuredElementName)) {
				this.writeBehindRenderResponseWrapper = configuredElement.getValue();
			}
		}

		// writeBehindResourceResponseWrapper
		for (ConfiguredElement configuredElement : configuredApplicationExtensions) {
			String configuredElementName = configuredElement.getName();

			if (RESOURCE_RESPONSE_WRAPPER_CLASS.equals(configuredElementName)) {
				this.writeBehindResourceResponseWrapper = configuredElement.getValue();
			}
		}

		// viewIdResourceParameterName
		this.viewIdResourceParameterName = PortletConfigParam.ViewIdResourceParameterName.getStringValue(portletConfig);

		// viewIdRenderParameterName
		this.viewIdRenderParameterName = PortletConfigParam.ViewIdRenderParameterName.getStringValue(portletConfig);
	}

	public Map<String, Object> getAttributes() {
		return bridgeConfigAttributeMap;
	}

	public Set<String> getExcludedRequestAttributes() {
		return excludedRequestAttributes;
	}

	public Map<String, String[]> getPublicParameterMappings() {
		return publicParameterMappings;
	}

	public String getViewIdRenderParameterName() {
		return viewIdRenderParameterName;
	}

	public String getViewIdResourceParameterName() {
		return viewIdResourceParameterName;
	}

	public String getWriteBehindRenderResponseWrapper() {
		return writeBehindRenderResponseWrapper;
	}

	public String getWriteBehindResourceResponseWrapper() {
		return writeBehindResourceResponseWrapper;
	}
}
