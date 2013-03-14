/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.liferay.faces.bridge.BridgePhaseFactory;
import com.liferay.faces.bridge.application.view.BridgeWriteBehindSupportFactory;
import com.liferay.faces.bridge.bean.BeanManagerFactory;
import com.liferay.faces.bridge.container.PortletContainerFactory;
import com.liferay.faces.bridge.context.BridgeContextFactory;
import com.liferay.faces.bridge.context.IncongruityContextFactory;
import com.liferay.faces.bridge.context.url.BridgeURLFactory;
import com.liferay.faces.bridge.model.UploadedFileFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScopeCacheFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScopeFactory;
import com.liferay.faces.bridge.scope.BridgeRequestScopeManagerFactory;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class SAXHandlerFacesConfigPostImpl extends SAXHandlerFacesConfigPostImpl_2_0 {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(SAXHandlerFacesConfigPostImpl.class);

	// Private Constants
	private static final String BEAN_MANAGER_FACTORY = "bean-manager-factory";
	private static final String BRIDGE_CONTEXT_FACTORY = "bridge-context-factory";
	private static final String BRIDGE_PHASE_FACTORY = "bridge-phase-factory";
	private static final String BRIDGE_REQUEST_SCOPE_FACTORY = "bridge-request-scope-factory";
	private static final String BRIDGE_REQUEST_SCOPE_CACHE_FACTORY = "bridge-request-scope-cache-factory";
	private static final String BRIDGE_REQUEST_SCOPE_MANAGER_FACTORY = "bridge-request-scope-manager-factory";
	private static final String BRIDGE_WRITE_BEHIND_SUPPORT_FACTORY = "bridge-write-behind-support-factory";
	private static final String BRIDGE_URL_FACTORY = "bridge-url-factory";
	private static final String EXCLUDED_ATTRIBUTE = "excluded-attribute";
	private static final String INCONGRUITY_CONTEXT_FACTORY = "incongruity-context-factory";
	private static final String MANAGED_BEAN_CLASS = "managed-bean-class";
	private static final String MANAGED_BEAN_NAME = "managed-bean-name";
	private static final String MANAGED_BEAN_SCOPE = "managed-bean-scope";
	private static final String MODEL_EL = "model-el";
	private static final String PARAMETER = "parameter";
	private static final String PORTLET_CONTAINER_FACTORY = "portlet-container-factory";
	private static final String RENDER_RESPONSE_WRAPPER_CLASS = "render-response-wrapper-class";
	private static final String RESOURCE_RESPONSE_WRAPPER_CLASS = "resource-response-wrapper-class";
	private static final String UPLOADED_FILE_FACTORY = "uploaded-file-factory";

	// Private Data Members
	private List<ConfiguredBean> configuredBeans;
	private Set<String> excludedBridgeRequestAttributes;
	private String managedBeanClass;
	private String managedBeanName;
	private boolean parsingManagedBeanClass;
	private boolean parsingManagedBeanName;
	private boolean parsingManagedBeanScope;
	private boolean parsingBeanManagerFactory;
	private boolean parsingBridgeContextFactory;
	private boolean parsingBridgePhaseFactory;
	private boolean parsingBridgeRequestScopeFactory;
	private boolean parsingBridgeRequestScopeCacheFactory;
	private boolean parsingBridgeRequestScopeManagerFactory;
	private boolean parsingBridgeWriteBehindSupportFactory;
	private boolean parsingBridgeURLFactory;
	private boolean parsingExcludedAttribute;
	private boolean parsingIncongruityContextFactory;
	private boolean parsingModelEL;
	private boolean parsingParameter;
	private boolean parsingPortletContainerFactory;
	private boolean parsingRenderResponseWrapperClass;
	private boolean parsingResourceResponseWrapperClass;
	private String parameter;
	private boolean parsingUploadedFileFactory;
	private Map<String, String[]> publicParameterMappings;
	private String writeBehindRenderResponseWrapper;
	private String writeBehindResourceResponseWrapper;

	public SAXHandlerFacesConfigPostImpl(boolean resolveEntities, BridgeConfigAttributeMap bridgeConfigAttributeMap,
		List<ConfiguredBean> configuredBeans, Set<String> excludedBridgeRequestAttributes,
		Map<String, String[]> publicParameterMappings) {
		super(resolveEntities, bridgeConfigAttributeMap);
		this.configuredBeans = configuredBeans;
		this.excludedBridgeRequestAttributes = excludedBridgeRequestAttributes;
		this.publicParameterMappings = publicParameterMappings;
	}

	@Override
	public void endElement(String uri, String localName, String elementName) throws SAXException {

		if (parsingBeanManagerFactory) {
			setupFactory(BeanManagerFactory.class, content.toString().trim());
		}
		else if (parsingBridgeContextFactory) {
			setupFactory(BridgeContextFactory.class, content.toString().trim());
		}
		else if (parsingBridgePhaseFactory) {
			setupFactory(BridgePhaseFactory.class, content.toString().trim());
		}
		else if (parsingBridgeRequestScopeFactory) {
			setupFactory(BridgeRequestScopeFactory.class, content.toString().trim());
		}
		else if (parsingBridgeRequestScopeCacheFactory) {
			setupFactory(BridgeRequestScopeCacheFactory.class, content.toString().trim());
		}
		else if (parsingBridgeRequestScopeManagerFactory) {
			setupFactory(BridgeRequestScopeManagerFactory.class, content.toString().trim());
		}
		else if (parsingBridgeWriteBehindSupportFactory) {
			setupFactory(BridgeWriteBehindSupportFactory.class, content.toString().trim());
		}
		else if (parsingBridgeURLFactory) {
			setupFactory(BridgeURLFactory.class, content.toString().trim());
		}
		else if (parsingExcludedAttribute) {
			String attributeName = content.toString().trim();

			if (attributeName.length() > 0) {
				excludedBridgeRequestAttributes.add(attributeName);
			}
		}
		else if (parsingIncongruityContextFactory) {
			setupFactory(IncongruityContextFactory.class, content.toString().trim());
		}
		else if (parsingManagedBeanClass) {
			managedBeanClass = content.toString().trim();
			parsingManagedBeanClass = false;
		}
		else if (parsingManagedBeanName) {
			managedBeanName = content.toString().trim();
			parsingManagedBeanName = false;
		}
		else if (parsingManagedBeanScope) {
			String managedBeanScope = content.toString().trim();
			ConfiguredBean configuredBean = new ConfiguredBeanImpl(managedBeanClass, managedBeanName, managedBeanScope);
			configuredBeans.add(configuredBean);
			parsingManagedBeanScope = false;
		}
		else if (parsingModelEL) {

			String additionalValue = content.toString().trim();

			if (additionalValue.length() > 0) {
				String[] newValue = new String[] { additionalValue };
				String[] existingValue = publicParameterMappings.get(parameter);

				if (existingValue != null) {
					int total = existingValue.length + 1;
					newValue = new String[total];

					for (int i = 0; i < existingValue.length; i++) {
						newValue[i] = existingValue[i];
					}

					newValue[total - 1] = additionalValue;
				}

				publicParameterMappings.put(parameter, newValue);
			}
		}
		else if (parsingParameter) {
			parameter = content.toString().trim();
		}
		else if (parsingPortletContainerFactory) {
			setupFactory(PortletContainerFactory.class, content.toString().trim());
		}
		else if (parsingRenderResponseWrapperClass) {
			writeBehindRenderResponseWrapper = content.toString().trim();
			logger.debug("render-response-wrapper-class=[{0}]", writeBehindRenderResponseWrapper);
		}
		else if (parsingResourceResponseWrapperClass) {
			writeBehindResourceResponseWrapper = content.toString().trim();
			logger.debug("resource-response-wrapper-class=[{0}]", writeBehindResourceResponseWrapper);
		}
		else if (parsingUploadedFileFactory) {
			setupFactory(UploadedFileFactory.class, content.toString().trim());
		}
		else {
			super.endElement(uri, localName, elementName);
		}

		content = null;
		parsingBeanManagerFactory = false;
		parsingBridgeContextFactory = false;
		parsingBridgePhaseFactory = false;
		parsingBridgeRequestScopeFactory = false;
		parsingBridgeRequestScopeCacheFactory = false;
		parsingBridgeRequestScopeManagerFactory = false;
		parsingBridgeWriteBehindSupportFactory = false;
		parsingBridgeURLFactory = false;
		parsingExcludedAttribute = false;
		parsingIncongruityContextFactory = false;

		parsingModelEL = false;
		parsingParameter = false;
		parsingPortletContainerFactory = false;
		parsingRenderResponseWrapperClass = false;
		parsingResourceResponseWrapperClass = false;
		parsingUploadedFileFactory = false;
	}

	@Override
	public void logMissingElementErrors() {

		if (bridgeConfigAttributeMap.get(BeanManagerFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, BEAN_MANAGER_FACTORY);
		}

		if (bridgeConfigAttributeMap.get(BridgePhaseFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_PHASE_FACTORY);
		}

		if (bridgeConfigAttributeMap.get(BridgeContextFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_CONTEXT_FACTORY);
		}

		if (bridgeConfigAttributeMap.get(BridgeRequestScopeFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_REQUEST_SCOPE_FACTORY);
		}

		if (bridgeConfigAttributeMap.get(BridgeRequestScopeCacheFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_REQUEST_SCOPE_CACHE_FACTORY);
		}

		if (bridgeConfigAttributeMap.get(BridgeRequestScopeManagerFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_REQUEST_SCOPE_MANAGER_FACTORY);
		}

		if (bridgeConfigAttributeMap.get(BridgeWriteBehindSupportFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_WRITE_BEHIND_SUPPORT_FACTORY);
		}

		if (bridgeConfigAttributeMap.get(BridgeURLFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, BRIDGE_URL_FACTORY);
		}

		if (bridgeConfigAttributeMap.get(IncongruityContextFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, INCONGRUITY_CONTEXT_FACTORY);
		}

		if (bridgeConfigAttributeMap.get(PortletContainerFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, PORTLET_CONTAINER_FACTORY);
		}

		if (bridgeConfigAttributeMap.get(UploadedFileFactory.class.getName()) == null) {
			logger.error(FACTORY_NOT_FOUND_MSG, UPLOADED_FILE_FACTORY);
		}
	}

	@Override
	public void startElement(String uri, String localName, String elementName, Attributes attributes)
		throws SAXException {
		logger.trace("localName=[{0}]", localName);

		content = new StringBuilder();

		if (localName.equals(BEAN_MANAGER_FACTORY)) {
			parsingBeanManagerFactory = true;
		}
		else if (localName.equals(BRIDGE_CONTEXT_FACTORY)) {
			parsingBridgeContextFactory = true;
		}
		else if (localName.equals(BRIDGE_PHASE_FACTORY)) {
			parsingBridgePhaseFactory = true;
		}
		else if (localName.equals(BRIDGE_REQUEST_SCOPE_FACTORY)) {
			parsingBridgeRequestScopeFactory = true;
		}
		else if (localName.equals(BRIDGE_REQUEST_SCOPE_CACHE_FACTORY)) {
			parsingBridgeRequestScopeCacheFactory = true;
		}
		else if (localName.equals(BRIDGE_REQUEST_SCOPE_MANAGER_FACTORY)) {
			parsingBridgeRequestScopeManagerFactory = true;
		}
		else if (localName.equals(BRIDGE_WRITE_BEHIND_SUPPORT_FACTORY)) {
			parsingBridgeWriteBehindSupportFactory = true;
		}
		else if (localName.equals(BRIDGE_URL_FACTORY)) {
			parsingBridgeURLFactory = true;
		}
		else if (localName.equals(EXCLUDED_ATTRIBUTE)) {
			parsingExcludedAttribute = true;
		}
		else if (localName.equals(INCONGRUITY_CONTEXT_FACTORY)) {
			parsingIncongruityContextFactory = true;
		}
		else if (localName.equals(MANAGED_BEAN_CLASS)) {
			parsingManagedBeanClass = true;
		}
		else if (localName.equals(MANAGED_BEAN_NAME)) {
			parsingManagedBeanName = true;
		}
		else if (localName.equals(MANAGED_BEAN_SCOPE)) {
			parsingManagedBeanScope = true;
		}
		else if (localName.equals(MODEL_EL)) {
			parsingModelEL = true;
		}
		else if (localName.equals(PARAMETER)) {
			parsingParameter = true;
		}
		else if (localName.equals(PORTLET_CONTAINER_FACTORY)) {
			parsingPortletContainerFactory = true;
		}
		else if (localName.equals(RENDER_RESPONSE_WRAPPER_CLASS)) {
			parsingRenderResponseWrapperClass = true;
		}
		else if (localName.equals(RESOURCE_RESPONSE_WRAPPER_CLASS)) {
			parsingResourceResponseWrapperClass = true;
		}
		else if (localName.equals(UPLOADED_FILE_FACTORY)) {
			parsingUploadedFileFactory = true;
		}
		else {
			super.startElement(uri, localName, elementName, attributes);
		}
	}

	@Override
	public String getWriteBehindRenderResponseWrapper() {
		return writeBehindRenderResponseWrapper;
	}

	@Override
	public String getWriteBehindResourceResponseWrapper() {
		return writeBehindResourceResponseWrapper;
	}
}
