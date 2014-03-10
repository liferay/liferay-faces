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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletContext;

import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.config.ConfiguredElement;
import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.config.FacesConfig;
import com.liferay.faces.util.event.ApplicationStartupListener;


/**
 * @author  Neil Griffin
 */
public class BridgeConfigImpl implements BridgeConfig {

	// Private Constants
	private static final String EXCLUDED_ATTRIBUTE = "excluded-attribute";
	private static final String FACES_VIEW_ID_RENDER = "_facesViewIdRender";
	private static final String FACES_VIEW_ID_RESOURCE = "_facesViewIdResource";
	private static final String MODEL_EL = "model-el";
	private static final String PARAMETER = "parameter";
	private static final String RENDER_RESPONSE_WRAPPER_CLASS = "render-response-wrapper-class";
	private static final String RESOURCE_RESPONSE_WRAPPER_CLASS = "resource-response-wrapper-class";

	// Private Data Members
	private Map<String, Object> bridgeConfigAttributeMap;
	private List<ConfiguredServletMapping> configuredFacesServletMappings;
	@SuppressWarnings("deprecation")
	private List<ConfiguredSystemEventListener> configuredSystemEventListeners;
	private List<String> defaultSuffixes;
	private Set<String> excludedRequestAttributes;
	@SuppressWarnings("deprecation")
	private List<ServletMapping> facesServletMappings;
	private PortletContext portletContext;
	private Map<String, String[]> publicParameterMappings;
	private String viewIdRenderParameterName;
	private String viewIdResourceParameterName;
	private String writeBehindRenderResponseWrapper;
	private String writeBehindResourceResponseWrapper;

	@SuppressWarnings("deprecation")
	public BridgeConfigImpl(PortletContext portletContext) {

		// portletContext
		this.portletContext = portletContext;

		// bridgeConfigAttributeMap
		this.bridgeConfigAttributeMap = new BridgeConfigAttributeMap();

		// configuredFacesServletMappings
		ApplicationConfig applicationConfig = ApplicationStartupListener.getApplicationConfig();
		FacesConfig facesConfig = applicationConfig.getFacesConfig();
		this.configuredFacesServletMappings = facesConfig.getConfiguredFacesServletMappings();

		// configuredSystemEventListeners
		List<com.liferay.faces.util.config.ConfiguredSystemEventListener> listeners =
			facesConfig.getConfiguredSystemEventListeners();
		this.configuredSystemEventListeners = new ArrayList<ConfiguredSystemEventListener>();

		for (com.liferay.faces.util.config.ConfiguredSystemEventListener listener : listeners) {
			ConfiguredSystemEventListener configuredSystemEventListener = new ConfiguredSystemEventListenerImpl(
					listener);
			this.configuredSystemEventListeners.add(configuredSystemEventListener);
		}

		// defaultSuffixes
		this.defaultSuffixes = facesConfig.getConfiguredSuffixes();

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

		// facesServletMappings
		this.facesServletMappings = new ArrayList<ServletMapping>();

		List<ConfiguredServletMapping> configuredFacesServletMappings = getConfiguredFacesServletMappings();

		for (ConfiguredServletMapping configuredFacesServletMapping : configuredFacesServletMappings) {
			ServletMapping facesServletMapping = new ServletMappingImpl(configuredFacesServletMapping);
			this.facesServletMappings.add(facesServletMapping);
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
		this.viewIdResourceParameterName = portletContext.getInitParameter(
				BridgeConfigConstants.PARAM_VIEW_ID_RESOURCE);

		if (this.viewIdResourceParameterName == null) {
			this.viewIdResourceParameterName = FACES_VIEW_ID_RESOURCE;
		}

		// viewIdRenderParameterName
		this.viewIdRenderParameterName = portletContext.getInitParameter(BridgeConfigConstants.PARAM_VIEW_ID_RENDER);

		if (this.viewIdRenderParameterName == null) {
			this.viewIdRenderParameterName = FACES_VIEW_ID_RENDER;
		}
	}

	public Map<String, Object> getAttributes() {
		return bridgeConfigAttributeMap;
	}

	@Deprecated
	public List<String> getConfiguredExtensions() {
		return getConfiguredSuffixes();
	}

	public List<ConfiguredServletMapping> getConfiguredFacesServletMappings() {
		return configuredFacesServletMappings;
	}

	public List<String> getConfiguredSuffixes() {
		return defaultSuffixes;
	}

	@Deprecated
	public List<ConfiguredSystemEventListener> getConfiguredSystemEventListeners() {
		return configuredSystemEventListeners;
	}

	public String getContextParameter(String name) {
		return portletContext.getInitParameter(name);
	}

	public Set<String> getExcludedRequestAttributes() {
		return excludedRequestAttributes;
	}

	@Deprecated
	public List<ServletMapping> getFacesServletMappings() {
		return facesServletMappings;
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
