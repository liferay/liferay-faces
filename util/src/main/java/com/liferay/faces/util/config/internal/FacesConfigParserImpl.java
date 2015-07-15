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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.liferay.faces.util.config.ConfiguredElement;
import com.liferay.faces.util.config.ConfiguredManagedBean;
import com.liferay.faces.util.config.ConfiguredServletMapping;
import com.liferay.faces.util.config.ConfiguredSystemEventListener;
import com.liferay.faces.util.config.FacesConfig;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.xml.internal.SAXHandlerBase;


/**
 * @author  Neil Griffin
 */
public class FacesConfigParserImpl extends SAXHandlerBase implements FacesConfigParser {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(FacesConfigParserImpl.class);

	// Private Constants
	private static final String APPLICATION_EXTENSION = "application-extension";
	private static final String FACTORY_EXTENSION = "factory-extension";
	private static final String MANAGED_BEAN_CLASS = "managed-bean-class";
	private static final String MANAGED_BEAN_NAME = "managed-bean-name";
	private static final String MANAGED_BEAN_SCOPE = "managed-bean-scope";
	private static final String SOURCE_CLASS = "source-class";
	private static final String SYSTEM_EVENT_CLASS = "system-event-class";
	private static final String SYSTEM_EVENT_LISTENER = "system-event-listener";
	private static final String SYSTEM_EVENT_LISTENER_CLASS = "system-event-listener-class";

	// Private Data Members
	private List<ConfiguredElement> configuredApplicationExtensions;
	private List<ConfiguredElement> configuredFactoryExtensions;
	private List<ConfiguredServletMapping> configuredFacesServletMappings;
	private List<ConfiguredManagedBean> configuredManagedBeans;
	private List<String> configuredSuffixes;
	private List<ConfiguredSystemEventListener> configuredSystemEventListeners;
	private String managedBeanClass;
	private String managedBeanName;
	private boolean parsingApplicationExtension;
	private boolean parsingFactoryExtension;
	private boolean parsingManagedBeanClass;
	private boolean parsingManagedBeanName;
	private boolean parsingManagedBeanScope;
	private boolean parsingSourceClass;
	private boolean parsingSystemEventClass;
	private boolean parsingSystemEventListener;
	private boolean parsingSystemEventListenerClass;
	private SAXParser saxParser;
	private String sourceClass;
	private String systemEventClass;
	private String systemEventListenerClass;

	public FacesConfigParserImpl(SAXParser saxParser, boolean resolveEntities) {
		super(resolveEntities);
		this.saxParser = saxParser;
	}

	@Override
	public void endElement(String uri, String localName, String elementName) throws SAXException {

		if (parsingApplicationExtension) {

			if (localName.equals(APPLICATION_EXTENSION)) {
				parsingApplicationExtension = false;
			}

			// NOTE: system-event-listener can appear inside of application-extension for backward compatibility with
			// JSF 1.x
			else if (parsingSourceClass) {
				sourceClass = content.toString().trim();
				parsingSourceClass = false;
			}
			else if (parsingSystemEventClass) {
				systemEventClass = content.toString().trim();
				parsingSystemEventClass = false;
			}
			else if (parsingSystemEventListenerClass) {
				systemEventListenerClass = content.toString().trim();
				parsingSystemEventListenerClass = false;
			}
			else if (parsingSystemEventListener) {

				// NOTE: This has to appear last since system-event-listener is the surrounding element.
				ConfiguredSystemEventListener configuredSystemEventListener = new ConfiguredSystemEventListenerImpl(
						sourceClass, systemEventClass, systemEventListenerClass);
				configuredSystemEventListeners.add(configuredSystemEventListener);
				parsingSystemEventListener = false;
			}
			else {
				String elementValue = null;

				if (content != null) {
					elementValue = content.toString().trim();
				}

				ConfiguredElement configuredElement = new ConfiguredElementImpl(localName, elementValue);
				configuredApplicationExtensions.add(configuredElement);
			}
		}
		else if (parsingFactoryExtension) {

			if (localName.equals(FACTORY_EXTENSION)) {
				parsingFactoryExtension = false;
			}
			else {
				String factoryName = localName;
				String factoryClass = null;

				if (content != null) {
					factoryClass = content.toString().trim();
				}

				ConfiguredElement configuredElement = new ConfiguredElementImpl(factoryName, factoryClass);
				configuredFactoryExtensions.add(configuredElement);
			}
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
			ConfiguredManagedBean configuredManagedBean = new ConfiguredManagedBeanImpl(managedBeanClass,
					managedBeanName, managedBeanScope);
			configuredManagedBeans.add(configuredManagedBean);
			parsingManagedBeanScope = false;
		}
		else if (parsingSourceClass) {
			sourceClass = content.toString().trim();
			parsingSourceClass = false;
		}
		else if (parsingSystemEventClass) {
			systemEventClass = content.toString().trim();
			parsingSystemEventClass = false;
		}
		else if (parsingSystemEventListenerClass) {
			systemEventListenerClass = content.toString().trim();
			parsingSystemEventListenerClass = false;
		}
		else if (parsingSystemEventListener) {

			// NOTE: This has to appear last since system-event-listener is the surrounding element.
			ConfiguredSystemEventListener configuredSystemEventListener = new ConfiguredSystemEventListenerImpl(
					sourceClass, systemEventClass, systemEventListenerClass);
			configuredSystemEventListeners.add(configuredSystemEventListener);
			parsingSystemEventListener = false;
		}
		else {
			super.endElement(uri, localName, elementName);
		}

		content = null;
	}

	public FacesConfig parse(InputStream inputStream, FacesConfig facesConfig) throws IOException {

		List<ConfiguredElement> configuredApplicationExtensions = facesConfig.getConfiguredApplicationExtensions();
		this.configuredApplicationExtensions = new ArrayList<ConfiguredElement>(configuredApplicationExtensions);

		List<ConfiguredElement> configuredFactoryExtensions = facesConfig.getConfiguredFactoryExtensions();
		this.configuredFactoryExtensions = new ArrayList<ConfiguredElement>(configuredFactoryExtensions);

		List<ConfiguredManagedBean> configuredManagedBeans = facesConfig.getConfiguredManagedBeans();
		this.configuredManagedBeans = new ArrayList<ConfiguredManagedBean>(configuredManagedBeans);

		// Note: Assume that the configured FacesServlet mappings have already been discovered. There is no need to
		// make a copy of the list since this parser does not "discover" new servlet mappings.
		this.configuredFacesServletMappings = facesConfig.getConfiguredFacesServletMappings();

		// Note: Assume that the configured suffixes have already been discovered. There is no need to make a copy of
		// the list since this parser does not "discover" new suffixes.
		this.configuredSuffixes = facesConfig.getConfiguredSuffixes();

		List<ConfiguredSystemEventListener> configuredSystemEventListeners =
			facesConfig.getConfiguredSystemEventListeners();
		this.configuredSystemEventListeners = new ArrayList<ConfiguredSystemEventListener>(
				configuredSystemEventListeners);

		try {
			saxParser.parse(inputStream, this);

			facesConfig = new FacesConfigImpl(this.configuredApplicationExtensions, this.configuredFactoryExtensions,
					this.configuredFacesServletMappings, this.configuredManagedBeans, this.configuredSuffixes,
					this.configuredSystemEventListeners);
			saxParser.reset();

			return facesConfig;
		}
		catch (SAXException e) {
			logger.error(e);
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void startElement(String uri, String localName, String elementName, Attributes attributes)
		throws SAXException {

		content = new StringBuilder();

		if (localName.equals(APPLICATION_EXTENSION)) {
			parsingApplicationExtension = true;
		}
		else if (localName.equals(FACTORY_EXTENSION)) {
			parsingFactoryExtension = true;
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
		else if (localName.equals(SOURCE_CLASS)) {
			parsingSourceClass = true;
		}
		else if (localName.equals(SYSTEM_EVENT_CLASS)) {
			parsingSystemEventClass = true;
		}
		else if (localName.equals(SYSTEM_EVENT_LISTENER)) {
			parsingSystemEventListener = true;
		}
		else if (localName.equals(SYSTEM_EVENT_LISTENER_CLASS)) {
			parsingSystemEventListenerClass = true;
		}
		else {
			super.startElement(uri, localName, elementName, attributes);
		}
	}

	protected SAXParser getSAXParser() {
		return saxParser;
	}
}
